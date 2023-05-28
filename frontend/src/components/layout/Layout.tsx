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
        display: 'flex',
        flexDirection: 'row',
        padding: '0',
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
      <Box flexGrow={1}>
        <Header />
        <main style={{ overflowY: 'auto' }}>{children}</main>
      </Box>
    </Container>
  );
}
