import { Box, Container } from '@mui/material';
import { Header } from './Header';
import { Sidebar } from './Sidebar';
import { grey } from '@mui/material/colors';
import { StompSessionProvider } from '../../context/StompSession';
import { useRecoilValue } from 'recoil';
import { memberAccessTokenAtom } from '../../recoil/atoms/member-atom';

export function Layout({ children }: { children: React.ReactNode }) {
  const memberAccessToken = useRecoilValue(memberAccessTokenAtom);

  return (
    <Container
      maxWidth="sm"
      style={{
        minHeight: '100vh',
        maxHeight: '100vh',
        display: 'flex',
        flexDirection: 'row',
        padding: '0',
        overflow: 'hidden',
      }}
    >
      {/* eslint-disable-next-line @typescript-eslint/no-non-null-assertion */}
      <StompSessionProvider accessToken={memberAccessToken!.accessToken}>
        <Box>
          <Box
            bgcolor={grey[800]}
            padding={'8px'}
            display={'flex'}
            alignItems={'start'}
            justifyContent={'center'}
            height={'100%'}
          >
            <Sidebar />
          </Box>
        </Box>
        <Box
          display={'flex'}
          flexDirection={'column'}
          width={'100%'}
          overflow={'hidden'}
        >
          <Header />
          <main
            style={{
              overflowY: 'auto',
              height: '100%',
              flex: 1,
            }}
          >
            {children}
          </main>
        </Box>
      </StompSessionProvider>
    </Container>
  );
}
