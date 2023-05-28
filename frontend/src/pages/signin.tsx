import { Box, Button, TextField, Typography } from '@mui/material';
import { useState } from 'react';
import { UseSigninApi } from '../hooks/http/useSigninApi';
import { useSignin } from '../hooks/useSignin';
import { useRouter } from 'next/router';
import { HttpStatusCode } from 'axios';

function SigninPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const router = useRouter();

  const { signin } = useSignin();

  const useSinginApi = UseSigninApi.useMutate({
    onSuccess: (response) => {
      alert('로그인 성공');

      signin(response.data.accessToken);
    },
    onError: (error) => {
      if (error.response?.status === HttpStatusCode.Unauthorized) {
        alert('이메일 혹은 비밀번호가 맞지 않습니다.');
        return;
      }
      throw error;
    },
  });

  const onClickSignin = () => {
    if (!email) {
      alert('이메일 입력');
      return;
    }

    if (!password) {
      alert('비밀번호 입력');
      return;
    }

    useSinginApi.mutate({ email, password });
  };

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
      <Typography variant="h5">Voice Chat - 로그인</Typography>
      <Box
        component="form"
        autoComplete="off"
        style={{ display: 'flex', flexDirection: 'column', gap: '8px' }}
        onSubmit={(event) => {
          event.stopPropagation();
          event.preventDefault();

          onClickSignin();
        }}
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
        <Button variant="contained" color="success" type="submit">
          로그인
        </Button>
        <Button
          variant="outlined"
          color="secondary"
          onClick={() => router.push('/signup')}
          type="button"
        >
          회원가입
        </Button>
      </Box>
    </div>
  );
}

export default SigninPage;
