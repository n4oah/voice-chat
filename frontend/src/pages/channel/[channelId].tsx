import { Box, Typography } from '@mui/material';
import { Layout } from '../../components/layout/Layout';
import { withOnlyLoggingPage } from '../../hoc/withOnlyLoggingPage';
import VolumeDownIcon from '@mui/icons-material/VolumeDown';

function ChannelPage() {
  return (
    <Layout>
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
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
          <Box>a</Box>
        </Box>
      </Box>
    </Layout>
  );
}

export default withOnlyLoggingPage(ChannelPage);
