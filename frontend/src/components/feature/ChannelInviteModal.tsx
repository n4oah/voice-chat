import {
  Box,
  Button,
  Container,
  Modal,
  TextField,
  Typography,
} from '@mui/material';
import { useEffect, useState } from 'react';
import { UseFetchMyChannelInviteMembersApi } from '../../hooks/http/useFetchChannelInviteMembersApi';
import { UseChannelInviteApi } from '../../hooks/http/useChannelInviteApi';

type Props = {
  isOpen?: boolean;
  handleClose?: () => void;
  channelId: number;
};

export function ChannelInviteModal({
  handleClose: handleCloseProps,
  isOpen: isOpenProps,
  channelId,
}: Props) {
  const [isOpen, setOpen] = useState<boolean>(isOpenProps ?? false);
  const myChannelInviteMembersApi = UseFetchMyChannelInviteMembersApi.useFetch({
    channelId,
  });
  const [email, setEmail] = useState('');
  const channelInviteApi = UseChannelInviteApi.useMutate({
    onSuccess: () => {
      alert('초대 완료');
      handleClose();
    },
  });

  useEffect(() => {
    if (isOpenProps !== undefined) {
      setOpen(isOpenProps);
    }
  }, [isOpenProps]);

  function handleClose() {
    if (isOpenProps === undefined) {
      setOpen(false);
    }
    handleCloseProps?.();
  }

  function onClickInviteChannel() {
    channelInviteApi.mutate({ channelId, email });
  }

  return (
    <Modal open={isOpen} onClose={handleClose}>
      <Container
        maxWidth="xs"
        style={{
          width: '320px',
          transform: 'translate(-50%, -50%)',
          top: '50%',
          left: '50%',
          position: 'absolute',
          backgroundColor: '#FFF',
          display: 'flex',
          flexDirection: 'column',
          padding: '12px',
          gap: '12px',
          borderRadius: '12px',
        }}
      >
        <Box display={'flex'} flexDirection={'column'} gap={'4px'}>
          <TextField
            placeholder="이메일"
            size="small"
            value={email}
            onChange={(event) => setEmail(event.target.value)}
          />
          <Button onClick={() => onClickInviteChannel()} variant="outlined">
            <Typography>초대하기</Typography>
          </Button>
        </Box>
        <Box display={'flex'} flexDirection={'column'} gap={'4px'}>
          <Typography>초대 리스트</Typography>
          <Box display={'flex'} flexDirection={'column'}>
            {myChannelInviteMembersApi.data?.items.map((item) => (
              <Typography component="span" key={item.id}>
                {item.invitedUserEmail}
                <Typography component="span" fontSize={'12px'}>
                  [대기]
                </Typography>
              </Typography>
            ))}
            {/* <Typography component="span">
              n4oahdev@gmail{' '}
              <Typography component="span" fontSize={'12px'}>
                [대기]
              </Typography>
            </Typography> */}
          </Box>
        </Box>
      </Container>
    </Modal>
  );
}
