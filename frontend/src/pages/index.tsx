import Head from 'next/head';
import styles from '@/styles/Home.module.css';
import { withOnlyLoggingPage } from '../hoc/withOnlyLoggingPage';

function Home() {
  return (
    <>
      <Head>
        <title>voice-chat</title>
        <meta name="description" content="voice-chat app" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="icon" href="/favicon.ico" />
      </Head>
      <main className={`${styles.main}`}></main>
    </>
  );
}

export default withOnlyLoggingPage(Home);
