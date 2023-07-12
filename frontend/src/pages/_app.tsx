import '@/styles/globals.css';
import type { AppProps } from 'next/app';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { RecoilRoot, useRecoilValue } from 'recoil';
import Head from 'next/head';
import 'reflect-metadata';
import { StompSessionProvider } from '../context/StompSession';
import { memberAccessTokenAtom } from '../recoil/atoms/member-atom';

const queryClient = new QueryClient();

export default function App({ Component, pageProps }: AppProps) {
  return (
    <>
      <Head>
        <title>voice-chat</title>
        <meta name="description" content="voice-chat app" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="icon" href="/favicon.ico" />
      </Head>
      <RecoilRoot>
        <QueryClientProvider client={queryClient}>
          <Providers>
            <Component {...pageProps} />
          </Providers>
          <ReactQueryDevtools initialIsOpen={false} />
        </QueryClientProvider>
      </RecoilRoot>
    </>
  );
}

function Providers({ children }: { children: React.ReactNode }) {
  const memberAccessToken = useRecoilValue(memberAccessTokenAtom);

  return (
    <StompSessionProvider accessToken={memberAccessToken?.accessToken}>
      {children}
    </StompSessionProvider>
  );
}
