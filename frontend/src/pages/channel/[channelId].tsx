import { Box, Button, Typography } from '@mui/material';
import { Layout } from '../../components/layout/Layout';
import { withOnlyLoggingPage } from '../../hoc/withOnlyLoggingPage';
import VolumeDownIcon from '@mui/icons-material/VolumeDown';
import { ChannelInviteModal } from '../../components/feature/ChannelInviteModal';
import { useState } from 'react';
import { useRouter } from 'next/router';
import { Type, plainToClass } from 'class-transformer';
import { withChannelPage } from '../../hoc/withChannelPage';

class RouterQuery {
  @Type(() => Number)
  channelId!: number;
}

function ChannelPage() {
  const [isShowChannelInivteModal, setShowChannelInivteModal] = useState(false);
  const router = useRouter();

  const { channelId } = plainToClass(RouterQuery, router.query);

  function onClickChannelInviteBtn() {
    setShowChannelInivteModal(true);
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
          <Box>a</Box>
        </Box>
      </Box>
    </Layout>
  );
}

export default withOnlyLoggingPage(withChannelPage(ChannelPage));
