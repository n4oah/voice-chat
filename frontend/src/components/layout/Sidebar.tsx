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
import SockJS from 'sockjs-client';
import { StompSubscription, Client as StompClient } from '@stomp/stompjs';
import { useRecoilValue } from 'recoil';
import { memberAccessTokenAtom } from '../../recoil/atoms/member-atom';

type SidebarChannel = {
  type: 'channel';
  channelId: number;
  name: string;
};

const ChannelButton = styled(ListItemButton)(
  ({ active = false }: { active?: boolean }) => ({
    padding: 0,
    backgroundColor: active ? deepPurple[900] : grey[500],
    color: active ? 'white' : 'black',
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
  const uniqueId = useId();

  const memberAccessToken = useRecoilValue(memberAccessTokenAtom);

  const subscribeChannels = useRef<StompSubscription[]>([]);

  const stompClient = useRef<StompClient>();
  const [isWebSocketConnected, setWebSocketConnected] = useState(false);

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

  useEffect(() => {
    console.log('hi');
    stompClient.current = new StompClient({
      webSocketFactory: () =>
        new SockJS(
          (process.env.NEXT_PUBLIC_VOICE_CHAT_API_URL as string) + '/chat/ws',
        ),
      connectHeaders: {
        // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
        authorization: `Bearer ${memberAccessToken!.accessToken}`,
      },
      onConnect: () => {
        setWebSocketConnected(true);
      },
      reconnectDelay: 1000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    stompClient.current.activate();

    return () => {
      stompClient.current?.deactivate();
      setWebSocketConnected(false);
    };
  }, [memberAccessToken]);

  useEffect(() => {
    if (!stompClient.current) {
      return;
    }

    if (!isWebSocketConnected) {
      return;
    }

    if (myChannels.data?.channels.length) {
      subscribeChannels.current.forEach((subChannel) => {
        if (
          !myChannels.data.channels.some(
            (channel) => subChannel.id === `${uniqueId}_${channel.id}`,
          )
        ) {
          subChannel.unsubscribe();
        }
      });

      subscribeChannels.current = myChannels.data.channels.map((channel) => {
        for (const subscribeChannel of subscribeChannels.current) {
          if (
            myChannels.data.channels.some(
              (channel) => subscribeChannel.id === `${uniqueId}_${channel.id}`,
            )
          ) {
            return subscribeChannel;
          }
        }

        console.log('stompClient.current', stompClient.current);
        // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
        return stompClient.current!.subscribe(
          `/topic/channel/${channel.id}`,
          (message) => {
            console.log('message', message);
          },
          {
            id: `${uniqueId}_${channel.id}`,
            // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
            authorization: `Bearer ${memberAccessToken!.accessToken}`,
          },
        );
      });
    } else {
      subscribeChannels.current.forEach((subChannel) => {
        subChannel.unsubscribe();
      });
    }
  }, [
    isWebSocketConnected,
    memberAccessToken,
    myChannels.data?.channels,
    uniqueId,
  ]);

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
            <ChannelButton active={router.asPath === '/'}>
              <ListItemText primary={'홈'} />
            </ChannelButton>
          </Link>
        </ListItem>
        {sidebarMenus.map((sidebarMenu) => (
          <ListItem key={sidebarMenu.channelId} disablePadding>
            <Link href={`/channel/${sidebarMenu.channelId}`}>
              <ChannelButton
                active={router.asPath === `/channel/${sidebarMenu.channelId}`}
              >
                <ListItemText primary={sidebarMenu.name} />
              </ChannelButton>
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
