import { Box, Button, TextField, Typography } from '@mui/material';
import { Layout } from '../../components/layout/Layout';
import { withOnlyLoggingPage } from '../../hoc/withOnlyLoggingPage';
import VolumeDownIcon from '@mui/icons-material/VolumeDown';
import { ChannelInviteModal } from '../../components/feature/ChannelInviteModal';
import React, { KeyboardEvent, useEffect, useRef, useState } from 'react';
import { useRouter } from 'next/router';
import { Type, plainToClass } from 'class-transformer';
import { withChannelPage } from '../../hoc/withChannelPage';
import { UseSendMessageByChannelApi } from '../../hooks/http/chat/useSendMessageByChannelApi';
import { grey } from '@mui/material/colors';
import { useRecoilValue } from 'recoil';
import { getChannelChat } from '../../recoil/selector/get-channel-chat';
import { getMyInfoByAccessToken } from '../../recoil/selector/get-my-info-by-access-token';
import { useInView } from 'react-intersection-observer';

class RouterQuery {
  @Type(() => Number)
  channelId!: number;
}

function ChannelPage() {
  const [isShowChannelInivteModal, setShowChannelInivteModal] = useState(false);
  const router = useRouter();

  const { channelId } = plainToClass(RouterQuery, router.query);

  const chattingHistorys = useRecoilValue(getChannelChat(channelId));

  const { ref: lastChatRef, inView: isLastChatView } = useInView({
    threshold: 1,
  });

  const sendMessageByChannelApi = UseSendMessageByChannelApi.useMutate();
  const myInfo = useRecoilValue(getMyInfoByAccessToken);

  const chattingRoomWrapper = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (
      chattingHistorys &&
      chattingHistorys.length &&
      chattingRoomWrapper.current
    ) {
      if (isLastChatView || chattingHistorys[0].senderUserId === myInfo.id) {
        chattingRoomWrapper.current.scrollTo(
          0,
          chattingRoomWrapper.current.scrollHeight,
        );
      }
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [chattingHistorys]);

  function onClickChannelInviteBtn() {
    setShowChannelInivteModal(true);
  }

  function onKeyDownChatBox(event: KeyboardEvent<HTMLDivElement>) {
    if (event.nativeEvent.isComposing) {
      return;
    }
    // console.log(
    //   'chattingRoomWrapper.current.scrollTop',
    //   chattingRoomWrapper.current!.scrollTop,
    // );
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
                {/* 참여중인 애들 */}
                <Typography>닉네임</Typography>
                <Typography>닉네임</Typography>
                <Typography>닉네임</Typography>
              </Box>
            </Box>
            <Box>
              <Typography variant="h6">오프라인</Typography>
              <Box display={'flex'} flexDirection={'column'}>
                {/* 참여중인 애들 */}
                <Typography>닉네임</Typography>
                <Typography>닉네임</Typography>
                <Typography>닉네임</Typography>
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
              flex={1}
              display={'flex'}
              flexDirection={'column-reverse'}
              overflow={'auto'}
              padding={'12px'}
              gap={'4px'}
              ref={chattingRoomWrapper}
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
                        <Typography>{myInfo.name}</Typography>
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
