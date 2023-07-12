import { Box, Container } from '@mui/material';
import { Header } from './Header';
import { Sidebar } from './Sidebar';
import { grey } from '@mui/material/colors';

export function Layout({ children }: { children: React.ReactNode }) {
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
    </Container>
  );
}
