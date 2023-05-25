import { Box, Button, TextField, Typography } from '@mui/material';
import { useState } from 'react';
import { UseSigninApi } from '../hooks/http/useSigninApi';

function SigninPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const useSinginApi = UseSigninApi.useMutate({
    onSuccess: () => {
      alert('로그인 성공');
    },
  });

  function onClickSignin() {
    if (!email) {
      alert('이메일 입력');
      return;
    }

    if (!password) {
      alert('비밀번호 입력');
      return;
    }

    useSinginApi.mutate({ email, password });
  }

  return (
    <div
      style={{
        display: 'flex',
        height: '100vh',
        justifyContent: 'center',
        alignItems: 'center',
        flexDirection: 'column',
        gap: '30px',
      }}
    >
      <Typography variant="h5">Voice Chat</Typography>
      <Box
        component="form"
        autoComplete="off"
        style={{ display: 'flex', flexDirection: 'column', gap: '8px' }}
      >
        <TextField
          placeholder="이메일 입력"
          type="email"
          value={email}
          onChange={(event) => setEmail(event.target.value)}
        />
        <TextField
          placeholder="비밀번호 입력"
          type="password"
          value={password}
          onChange={(event) => setPassword(event.target.value)}
        />
        <Button
          variant="contained"
          color="success"
          onClick={() => onClickSignin()}
        >
          로그인
        </Button>
        <Button variant="outlined" color="secondary">
          회원가입
        </Button>
      </Box>
    </div>
  );
}

export default SigninPage;
