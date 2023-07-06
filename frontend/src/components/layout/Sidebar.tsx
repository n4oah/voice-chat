import {
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
} from '@mui/material';
import { deepPurple, grey, teal } from '@mui/material/colors';
import { useEffect, useId, useRef, useState } from 'react';
import { UseMyChannelsApi } from '../../hooks/http/channel/useMyChannels';
import Link from 'next/link';
import AddIcon from '@mui/icons-material/Add';
import { AddChannelModal } from '../feature/AddChannelModal';
import { useRouter } from 'next/router';
import styled from '@emotion/styled';
import { useRecoilValue } from 'recoil';
import { memberAccessTokenAtom } from '../../recoil/atoms/member-atom';
import { useStompSessionContext } from '../../context/StompSession';
import { ChatReceiveMessage } from '../../types/chat';
import { StompSubscription } from '@stomp/stompjs';
import { useAddChannelChat } from '../../hooks/channel-chat/useAddChannelChat';

type SidebarChannel = {
  type: 'channel';
  channelId: number;
  name: string;
};

const ChannelButton = styled(ListItemButton)(
  ({ isActive = false }: { isActive?: boolean }) => ({
    padding: 0,
    backgroundColor: isActive ? deepPurple[900] : grey[500],
    color: isActive ? 'white' : 'black',
    borderRadius: '50%',
    minWidth: '60px',
    width: '60px',
    height: '60px',
    textAlign: 'center',
    overflow: 'hidden',
    justifyContent: 'center',
  }),
);

export function Sidebar() {
  const [sidebarMenus, setSidebarMenu] = useState<SidebarChannel[]>([]);
  const myChannels = UseMyChannelsApi.useFetch();
  const [isOpenAddChannelModal, setOpenAddChannelModal] = useState(false);
  const router = useRouter();

  useEffect(() => {
    if (myChannels.data) {
      setSidebarMenu(
        myChannels.data.channels.map((channel) => ({
          type: 'channel' as const,
          channelId: channel.id,
          name: channel.name,
          key: channel.id,
        })),
      );
    }
  }, [myChannels.data]);

  function onClickAddChannel() {
    setOpenAddChannelModal(true);
  }

  return (
    <section>
      <AddChannelModal
        isOpen={isOpenAddChannelModal}
        handleClose={() => setOpenAddChannelModal(false)}
      />
      <List style={{ display: 'flex', flexDirection: 'column', gap: '8px' }}>
        <ListItem disablePadding>
          <Link href={'/'}>
            <ChannelButton isActive={router.asPath === '/' ?? false}>
              <ListItemText primary={'홈'} />
            </ChannelButton>
          </Link>
        </ListItem>
        {sidebarMenus.map((sidebarMenu) => (
          <ListItem key={sidebarMenu.channelId} disablePadding>
            <Link href={`/channel/${sidebarMenu.channelId}`}>
              <ChannelWrapper
                isActive={router.asPath === `/channel/${sidebarMenu.channelId}`}
                channelId={sidebarMenu.channelId}
                channelName={sidebarMenu.name}
              />
            </Link>
          </ListItem>
        ))}
        <ListItem disablePadding>
          <ChannelButton onClick={() => onClickAddChannel()}>
            <ListItemIcon
              style={{
                alignItems: 'center',
                justifyContent: 'center',
              }}
            >
              <AddIcon fontSize={'large'} htmlColor={teal[500]} />
            </ListItemIcon>
          </ChannelButton>
        </ListItem>
      </List>
    </section>
  );
}

function ChannelWrapper({
  isActive,
  channelId,
  channelName,
}: {
  channelName: string;
  channelId: number;
  isActive: boolean;
}) {
  const { isWebSocketConnected, stompClient } = useStompSessionContext();
  const memberAccessToken = useRecoilValue(memberAccessTokenAtom);
  const uniqueId = useId();
  const stompSubscribes = useRef<StompSubscription[]>();
  const addChannelChat = useAddChannelChat(channelId);

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

    stompSubscribes.current = [
      stompClient?.subscribe(
        `/topic/channel/${channelId}`,
        (message) => {
          const messageBody = JSON.parse(message.body) as ChatReceiveMessage;

          addChannelChat(messageBody);
        },
        {
          id: `${uniqueId}_${channelId}_message`,
          authorization: `Bearer ${memberAccessToken.accessToken}`,
        },
      ),
      stompClient?.subscribe(
        `/topic/channel/${channelId}/online-status`,
        (message) => {
          const messageBody = JSON.parse(message.body) as ChatReceiveMessage;
          console.log('messageBody', messageBody);
        },
        {
          id: `${uniqueId}_${channelId}_online_status`,
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
    addChannelChat,
    channelId,
    isWebSocketConnected,
    memberAccessToken,
    stompClient,
    uniqueId,
  ]);

  return (
    <ChannelButton isActive={isActive}>
      <ListItemText primary={channelName} />
    </ChannelButton>
  );
}
