import { Box, Button, TextField, Typography } from '@mui/material';
import { Layout } from '../../components/layout/Layout';
import { withOnlyLoggingPage } from '../../hoc/withOnlyLoggingPage';
import VolumeDownIcon from '@mui/icons-material/VolumeDown';
import { ChannelInviteModal } from '../../components/feature/ChannelInviteModal';
import React, {
  KeyboardEvent,
  useCallback,
  useEffect,
  useId,
  useRef,
  useState,
} from 'react';
import { useRouter } from 'next/router';
import { Type, plainToClass } from 'class-transformer';
import { withChannelPage } from '../../hoc/withChannelPage';
import { UseSendMessageByChannelApi } from '../../hooks/http/chat/useSendMessageByChannelApi';
import { grey } from '@mui/material/colors';
import { useRecoilValue } from 'recoil';
import { getChannelChat } from '../../recoil/selector/get-channel-chat';
import { getMyInfoByAccessToken } from '../../recoil/selector/get-my-info-by-access-token';
import { useInView } from 'react-intersection-observer';
import { UseFetchChannelMessageApi } from '../../hooks/http/chat/useFetchChannelMessageApi';
import { useAddChannelChat } from '../../hooks/channel-chat/useAddChannelChat';
import InfiniteScroll from 'react-infinite-scroll-component';
import { useChannelOnlineStatusUsers } from '../../hooks/channel-chat/useChannelOnlineStatusUsers';
import { UserOnlineStatus } from '../../types/user-online-status';
import { useStompSessionContext } from '../../context/StompSession';
import { memberAccessTokenAtom } from '../../recoil/atoms/member-atom';
import { StompSubscription } from '@stomp/stompjs';
import Peer from 'simple-peer';

class RouterQuery {
  @Type(() => Number)
  channelId!: number;
}

function ChannelPage() {
  const [isShowChannelInivteModal, setShowChannelInivteModal] = useState(false);

  const router = useRouter();

  const uniqueId = useId();

  const { channelId } = plainToClass(RouterQuery, router.query);

  const onlineStatusUsers = useChannelOnlineStatusUsers(channelId);

  const chattingHistorys = useRecoilValue(getChannelChat(channelId));
  const ignorePages = useRef<Set<number>>(new Set());

  const [audioStream, setAudioStream] = useState<MediaStream | null>(null);
  const stompSubscribes = useRef<StompSubscription[]>([]);

  const addChannelChat = useAddChannelChat(channelId);

  const { ref: lastChatRef, inView: isLastChatView } = useInView({
    threshold: 1,
  });

  const channelMessageApi = UseFetchChannelMessageApi.useFetch({
    channelId,
  });

  const sendMessageByChannelApi = UseSendMessageByChannelApi.useMutate();
  const myInfo = useRecoilValue(getMyInfoByAccessToken);

  const chattingRoomWrapper = useRef<HTMLDivElement>(null);

  const { isWebSocketConnected, stompClient, sessionId } =
    useStompSessionContext();
  const memberAccessToken = useRecoilValue(memberAccessTokenAtom);

  const createPeer = useCallback(
    (peerId: string, userId: number, initiator: boolean) => {
      if (!stompClient) {
        throw new Error('stompClient not exists');
      }
      if (!audioStream) {
        throw new Error('audioStream not exists');
      }

      const peer = new Peer({
        initiator: initiator,
        stream: audioStream,
      });

      peer.on('signal', (signal: unknown) => {
        const data = { signal, to: peerId };

        stompClient.publish({
          destination: `/app/channel/voice/${channelId}/signal`,
          body: JSON.stringify(data),
          headers: {
            authorization: `Bearer ${memberAccessToken?.accessToken}`,
          },
        });
      });

      peer.on('stream', (stream: unknown) => {
        console.log(stream);
      });
    },
    [audioStream, channelId, memberAccessToken?.accessToken, stompClient],
  );

  useEffect(() => {
    navigator.mediaDevices
      .getUserMedia({
        audio: true,
        video: false,
      })
      .then((stream) => {
        setAudioStream(stream);
      });
  }, []);

  useEffect(() => {
    if (!stompClient) {
      return;
    }

    if (!isWebSocketConnected) {
      return;
    }

    if (!memberAccessToken) {
      return;
    }

    if (!audioStream) {
      return;
    }

    stompClient.publish({
      destination: `/app/channel/voice/${channelId}/peer`,
      headers: {
        authorization: `Bearer ${memberAccessToken.accessToken}`,
      },
    });

    stompSubscribes.current = [
      stompClient?.subscribe(
        `/topic/channel/voice/${channelId}/peer`,
        (message) => {
          console.log('message', message);
          const messageBody = JSON.parse(message.body) as {
            userId: number;
            peerId: string;
          };

          if (sessionId === messageBody.peerId) {
            return;
          }

          createPeer(messageBody.peerId, messageBody.userId, true);
        },
        {
          id: `${uniqueId}_${channelId}_voice_peer`,
          authorization: `Bearer ${memberAccessToken.accessToken}`,
        },
      ),
      stompClient?.subscribe(
        `/topic/channel/voice/${channelId}/signal/${sessionId}`,
        (message) => {
          const messageBody = JSON.parse(message.body) as {
            fromPeerId: string;
            fromUserId: number;
            toPeerId: string;
          };

          if (sessionId === messageBody.toPeerId) {
            return;
          }

          createPeer(messageBody.fromPeerId, messageBody.fromUserId, false);
        },
        {
          id: `${uniqueId}_${channelId}_voice_peer`,
          authorization: `Bearer ${memberAccessToken.accessToken}`,
        },
      ),
    ];

    return () => {
      if (stompSubscribes.current && stompSubscribes.current.length) {
        stompSubscribes.current.forEach((stompSubscribe) => {
          stompSubscribe.unsubscribe();
        });
      }
    };
  }, [
    audioStream,
    channelId,
    createPeer,
    isWebSocketConnected,
    memberAccessToken,
    sessionId,
    stompClient,
    uniqueId,
  ]);

  useEffect(() => {
    if (
      chattingHistorys &&
      chattingHistorys.length &&
      chattingRoomWrapper.current
    ) {
      if (isLastChatView) {
        chattingRoomWrapper.current.scrollTo(
          0,
          chattingRoomWrapper.current.scrollHeight,
        );
      }
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [chattingHistorys]);

  useEffect(() => {
    if (channelMessageApi.data && channelMessageApi.data.pages.length) {
      channelMessageApi.data.pages.forEach((page, pageIndex) => {
        if (ignorePages.current.has(pageIndex)) {
          return;
        }

        page.messages.forEach((message) => addChannelChat(message));

        ignorePages.current.add(pageIndex);
      });
    }
  }, [addChannelChat, channelMessageApi.data]);

  function onClickChannelInviteBtn() {
    setShowChannelInivteModal(true);
  }

  function onKeyDownChatBox(event: KeyboardEvent<HTMLDivElement>) {
    if (event.nativeEvent.isComposing) {
      return;
    }

    if (!event.shiftKey && event.key === 'Enter') {
      const chatContentTarget = event.target as unknown as { value: string };
      if (
        !chatContentTarget.value ||
        !chatContentTarget.value.replaceAll('\n', '').replaceAll('\r', '')
      ) {
        return;
      }

      sendMessageByChannelApi.mutate({
        channelId,
        content: chatContentTarget.value,
      });

      chatContentTarget.value = '';

      event.preventDefault();
    }
  }

  function getScrollableId() {
    return uniqueId + 'scrollable';
  }

  return (
    <Layout>
      {isShowChannelInivteModal && (
        <ChannelInviteModal
          isOpen={isShowChannelInivteModal}
          handleClose={() => setShowChannelInivteModal(false)}
          channelId={channelId}
        />
      )}
      <Box
        display={'flex'}
        flexDirection={'row'}
        width="100%"
        height={'100%'}
        overflow={'hidden'}
      >
        <Box
          display={'flex'}
          width="160px"
          gap="24px"
          flexDirection={'column'}
          padding={'12px'}
          borderRight={'solid black'}
        >
          <Box>
            <Typography variant="h6">채널초대</Typography>
            <Button
              variant="outlined"
              color="success"
              onClick={() => onClickChannelInviteBtn()}
            >
              <Typography>초대하기</Typography>
            </Button>
          </Box>
          <Box>
            <Box>
              <Box
                display={'flex'}
                flexDirection={'row'}
                alignItems={'center'}
                gap={'2px'}
              >
                <VolumeDownIcon />
                <Typography variant="h6">음성채널</Typography>
              </Box>
              <Box display={'flex'} flexDirection={'column'}>
                {/* 참여중인 애들 */}
                <Typography>닉네임</Typography>
                <Typography>닉네임</Typography>
                <Typography>닉네임</Typography>
              </Box>
            </Box>
          </Box>
          <Box>
            <Box>
              <Typography variant="h6">온라인</Typography>
              <Box display={'flex'} flexDirection={'column'}>
                {onlineStatusUsers
                  .filter(
                    (onlineStatusUser) =>
                      onlineStatusUser.status === UserOnlineStatus.ONLINE,
                  )
                  .map((onlineStatusUser) => (
                    <Typography key={onlineStatusUser.id}>
                      {onlineStatusUser.name}
                    </Typography>
                  ))}
              </Box>
            </Box>
            <Box>
              <Typography variant="h6">오프라인</Typography>
              <Box display={'flex'} flexDirection={'column'}>
                {onlineStatusUsers
                  .filter(
                    (onlineStatusUser) =>
                      onlineStatusUser.status === UserOnlineStatus.OFFLINE,
                  )
                  .map((onlineStatusUser) => (
                    <Typography key={onlineStatusUser.id}>
                      {onlineStatusUser.name}
                    </Typography>
                  ))}
              </Box>
            </Box>
          </Box>
        </Box>
        <Box padding="12px" height={'100%'} width={'100%'} overflow={'hidden'}>
          <Box
            display={'flex'}
            width={'100%'}
            height={'100%'}
            flexDirection={'column'}
            overflow={'hidden'}
          >
            <Box
              overflow={'auto'}
              id={getScrollableId()}
              display={'flex'}
              height={'100%'}
              flexDirection={'column-reverse'}
              ref={chattingRoomWrapper}
            >
              <InfiniteScroll
                inverse={true}
                dataLength={chattingHistorys.length}
                next={channelMessageApi.fetchNextPage}
                hasMore={channelMessageApi.hasNextPage ?? false}
                loader={'loading'}
                style={{
                  display: 'flex',
                  flexDirection: 'column-reverse',
                  padding: '12px',
                  gap: '4px',
                }}
                scrollableTarget={getScrollableId()}
              >
                {chattingHistorys.map((chattingHistory, index) =>
                  chattingHistory.senderUserId === myInfo.id ? (
                    <Box
                      key={chattingHistory.id}
                      display={'flex'}
                      flexDirection={'column'}
                      alignItems={'flex-end'}
                      ref={index === 0 ? lastChatRef : undefined}
                    >
                      {(chattingHistorys[index + 1]
                        ? chattingHistorys[index + 1].senderUserId !== myInfo.id
                          ? true
                          : false
                        : true) && (
                        <Box>
                          <Typography>
                            {chattingHistory.senderUserName}
                          </Typography>
                        </Box>
                      )}
                      <Box
                        padding={'8px'}
                        bgcolor={grey['600']}
                        maxWidth={'80%'}
                        width={'max-content'}
                        borderRadius={'4px'}
                        color={'white'}
                      >
                        {chattingHistory.content}
                      </Box>
                    </Box>
                  ) : (
                    <Box
                      ref={index === 0 ? lastChatRef : undefined}
                      key={chattingHistory.id}
                      display={'flex'}
                      flexDirection={'column'}
                      alignItems={'flex-start'}
                    >
                      {(chattingHistorys[index + 1]
                        ? chattingHistorys[index + 1].senderUserId !==
                          chattingHistory.senderUserId
                          ? true
                          : false
                        : true) && (
                        <Box>
                          <Typography>
                            {chattingHistory.senderUserName}
                          </Typography>
                        </Box>
                      )}
                      <Box
                        padding={'8px'}
                        bgcolor={grey['500']}
                        maxWidth={'80%'}
                        width={'max-content'}
                        borderRadius={'4px'}
                        color={'white'}
                      >
                        {chattingHistory.content}
                      </Box>
                    </Box>
                  ),
                )}
              </InfiniteScroll>
            </Box>
            <Box display={'flex'} flexDirection={'row'} gap={'4px'}>
              <Box flex={1} bgcolor={grey[300]}>
                <TextField
                  id="outlined-multiline-flexible"
                  multiline
                  minRows={3.3}
                  maxRows={3.3}
                  fullWidth={true}
                  onKeyDown={(event) => onKeyDownChatBox(event)}
                />
              </Box>
              <Button variant="contained">
                <Typography>전송</Typography>
              </Button>
            </Box>
          </Box>
        </Box>
      </Box>
    </Layout>
  );
}

export default withOnlyLoggingPage(withChannelPage(ChannelPage));
