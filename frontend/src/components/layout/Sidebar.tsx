import {
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
} from '@mui/material';
import { deepPurple, grey, teal } from '@mui/material/colors';
import { useEffect, useState } from 'react';
import { UseMyChannelsApi } from '../../hooks/http/channel/useMyChannels';
import Link from 'next/link';
import AddIcon from '@mui/icons-material/Add';
import { AddChannelModal } from '../feature/AddChannelModal';
import { useRouter } from 'next/router';
import styled from '@emotion/styled';

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
