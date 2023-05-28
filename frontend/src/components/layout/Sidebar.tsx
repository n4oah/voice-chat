import {
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
} from '@mui/material';
import { grey, teal } from '@mui/material/colors';
import { useEffect, useState } from 'react';
import { UseMyChannelsApi } from '../../hooks/http/useMyChannels';
import Link from 'next/link';
import AddIcon from '@mui/icons-material/Add';
import { AddChannelModal } from '../feature/AddChannelModal';

type SidebarChannel = {
  type: 'channel';
  channelId: number;
  name: string;
};

export function Sidebar() {
  const [sidebarMenus, setSidebarMenu] = useState<SidebarChannel[]>([]);
  const myChannels = UseMyChannelsApi.useFetch();
  const [isOpenAddChannelModal, setOpenAddChannelModal] = useState(false);

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
            <ListItemButton
              style={{
                padding: 0,
                backgroundColor: grey[500],
                borderRadius: '50%',
                minWidth: '60px',
                width: '60px',
                height: '60px',
                textAlign: 'center',
                overflow: 'hidden',
                justifyContent: 'center',
              }}
            >
              <ListItemText primary={'홈'} />
            </ListItemButton>
          </Link>
        </ListItem>
        {sidebarMenus.map((sidebarMenu) => (
          <ListItem key={sidebarMenu.channelId} disablePadding>
            <Link href={'/'}>
              <ListItemButton
                style={{
                  padding: 0,
                  backgroundColor: grey[500],
                  borderRadius: '50%',
                  minWidth: '60px',
                  width: '60px',
                  height: '60px',
                  textAlign: 'center',
                  overflow: 'hidden',
                  justifyContent: 'center',
                }}
              >
                <ListItemText primary={sidebarMenu.name} />
              </ListItemButton>
            </Link>
          </ListItem>
        ))}
        <ListItem disablePadding>
          <ListItemButton
            style={{
              padding: 0,
              backgroundColor: grey[500],
              borderRadius: '50%',
              minWidth: '60px',
              width: '60px',
              height: '60px',
              textAlign: 'center',
              overflow: 'hidden',
              justifyContent: 'center',
            }}
            onClick={() => onClickAddChannel()}
          >
            <ListItemIcon
              style={{
                alignItems: 'center',
                justifyContent: 'center',
              }}
            >
              <AddIcon fontSize={'large'} htmlColor={teal[500]} />
            </ListItemIcon>
          </ListItemButton>
        </ListItem>
      </List>
    </section>
  );
}
