import { AppBar, Box, Button, Toolbar, Typography } from '@mui/material';
import { grey } from '@mui/material/colors';
import { useSignout } from '../../hooks/useSignout';

export function Header() {
  const { signout } = useSignout();

  return (
    <Box>
      <AppBar position="static" style={{ background: `${grey[700]}` }}>
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Voice-Chat
          </Typography>
          <Button color="inherit" onClick={() => signout()}>
            로그아웃
          </Button>
        </Toolbar>
      </AppBar>
    </Box>
  );
}
