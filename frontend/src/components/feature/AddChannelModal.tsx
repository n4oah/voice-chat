import {
  Box,
  Button,
  Container,
  Modal,
  TextField,
  Typography,
} from '@mui/material';
import { useEffect, useState } from 'react';
import CloseIcon from '@mui/icons-material/Close';
import { UseCreateChannelApi } from '../../hooks/http/useCreateChannelApi';
import { UseFetchMyChannelInviteApi } from '../../hooks/http/useFetchMyChannelInviteApi';

type Props = {
  isOpen?: boolean;
  handleClose?: () => void;
};

export function AddChannelModal({
  isOpen: isOpenProps,
  handleClose: handleCloseProps,
}: Props) {
  const [isOpen, setOpen] = useState<boolean>(isOpenProps ?? false);
  const [step, setStep] = useState<'list' | 'create' | 'invite-list'>('list');

  useEffect(() => {
    if (isOpenProps !== undefined) {
      setOpen(isOpenProps);
      if (isOpenProps === false) {
        setStep('list');
      }
    }
  }, [isOpenProps]);

  function handleClose() {
    if (isOpenProps === undefined) {
      setOpen(false);
    }
    handleCloseProps?.();
  }

  function renderByStep() {
    switch (step) {
      case 'list':
        return (
          <Box
            display={'flex'}
            flexDirection={'column'}
            width={'100%'}
            gap={'8px'}
          >
            <Typography variant="h5" textAlign={'center'}>
              서버를 새롭게 생성하거나 채널 초대를 확인할 수 있습니다.
            </Typography>
            <Button
              size="large"
              color="success"
              variant="outlined"
              onClick={() => setStep('create')}
            >
              <Typography fontWeight={'700'}>서버 생성하기</Typography>
            </Button>
            <Button
              size="large"
              color="primary"
              variant="outlined"
              onClick={() => setStep('invite-list')}
            >
              <Typography fontWeight={'700'}>초대 리스트 확인</Typography>
            </Button>
          </Box>
        );
      case 'create':
        return <CreateChannelForm handleClose={handleClose} />;
      case 'invite-list':
        return <ChannelInviteList />;
    }
  }

  return (
    <Modal open={isOpen} onClose={handleClose}>
      <Container
        maxWidth="xs"
        style={{
          width: '80vw',
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
        <Box
          display={'flex'}
          flexDirection={'row'}
          width={'100%'}
          justifyContent={'flex-end'}
        >
          <Button
            style={{ color: '#000', padding: '0' }}
            onClick={() => handleClose()}
          >
            <CloseIcon fontSize="large" />
          </Button>
        </Box>
        {renderByStep()}
      </Container>
    </Modal>
  );
}

function CreateChannelForm({ handleClose }: { handleClose: () => void }) {
  const [name, setName] = useState('');
  const [maxNumberOfMember, setMaxNumberOfMember] = useState('');

  const createChannelApi = UseCreateChannelApi.useMutate({
    onSuccess: () => {
      alert('채널 생성 완료');
      handleClose();
    },
  });

  function createChannel() {
    if (!name) {
      alert('채널이름을 입력해주세요.');
      return;
    }

    if (!maxNumberOfMember || Number(maxNumberOfMember) < 1) {
      alert('최대 인원을 제대로 입력해주세요');
      return;
    }

    createChannelApi.mutate({
      maxNumberOfMember: Number(maxNumberOfMember),
      name,
    });
  }

  return (
    <Box
      display={'flex'}
      flexDirection={'column'}
      width={'100%'}
      gap={'8px'}
      component="form"
      onSubmit={(event) => {
        event.stopPropagation();
        event.preventDefault();
        createChannel();
      }}
    >
      <TextField
        size="small"
        value={name}
        placeholder="채널 이름을 입력해주세요."
        onChange={(event) => setName(event.target.value)}
      />
      <TextField
        size="small"
        value={maxNumberOfMember}
        placeholder="채널 최대 인원을 입력해주세요. (최대 10)"
        onChange={(event) => {
          if (!event.target.value) {
            setMaxNumberOfMember('');
          }
          if (Number(event.target.value) > 0) {
            setMaxNumberOfMember(event.target.value);
          }
        }}
      />
      <Button color="success" variant="outlined" size="large" type="submit">
        <Typography>생성하기</Typography>
      </Button>
    </Box>
  );
}

function ChannelInviteList() {
  const myChannelInvite = UseFetchMyChannelInviteApi.useFetch();

  return (
    <Box display={'flex'} flexDirection={'column'} width={'100%'} gap={'8px'}>
      {myChannelInvite.data?.items.map((item) => (
        <Box key={item.id}>
          <Typography>{item.channelName}</Typography>
        </Box>
      ))}
      {!myChannelInvite.data?.items ||
        (!myChannelInvite.data.items.length && (
          <Typography>초대된 채널이 없습니다.</Typography>
        ))}
    </Box>
  );
}
