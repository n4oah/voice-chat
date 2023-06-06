import { Box, Button, TextField, Typography } from '@mui/material';
import { useState } from 'react';
import { UseSignupApi } from '../hooks/http/user/useSignupApi';
import { useRouter } from 'next/router';

function SignupPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [name, setName] = useState('');

  const router = useRouter();

  const signup = UseSignupApi.useMutate({
    onSuccess: () => {
      alert('회원가입 완료');

      router.push('/signin');
    },
  });

  function onClickSignup() {
    signup.mutate({ email, name, password });
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
      <Typography variant="h5">Voice Chat - 회원가입</Typography>
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
        <TextField
          placeholder="이름"
          type="text"
          value={name}
          onChange={(event) => setName(event.target.value)}
        />
        <Button
          variant="outlined"
          color="secondary"
          onClick={() => onClickSignup()}
        >
          회원가입
        </Button>
      </Box>
    </div>
  );
}

export default SignupPage;
