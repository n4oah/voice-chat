import { Box, Button, TextField, Typography } from '@mui/material';
import { Layout } from '../../components/layout/Layout';
import { withOnlyLoggingPage } from '../../hoc/withOnlyLoggingPage';
import VolumeDownIcon from '@mui/icons-material/VolumeDown';
import { ChannelInviteModal } from '../../components/feature/ChannelInviteModal';
import { KeyboardEvent, useState } from 'react';
import { useRouter } from 'next/router';
import { Type, plainToClass } from 'class-transformer';
import { withChannelPage } from '../../hoc/withChannelPage';
import { UseSendMessageByChannelApi } from '../../hooks/http/chat/useSendMessageByChannelApi';
import { grey } from '@mui/material/colors';

class RouterQuery {
  @Type(() => Number)
  channelId!: number;
}

function ChannelPage() {
  const [isShowChannelInivteModal, setShowChannelInivteModal] = useState(false);
  const router = useRouter();

  const { channelId } = plainToClass(RouterQuery, router.query);

  const sendMessageByChannelApi = UseSendMessageByChannelApi.useMutate();

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
        <Box overflow={'auto'} padding="12px" height={'100%'} width={'100%'}>
          <Box
            display={'flex'}
            width={'100%'}
            height={'100%'}
            flexDirection={'column'}
          >
            <Box flex={1}>a</Box>
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
