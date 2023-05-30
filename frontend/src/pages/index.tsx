import { withOnlyLoggingPage } from '../hoc/withOnlyLoggingPage';
import { Layout } from '../components/layout/Layout';

function Home() {
  return <Layout>홈화면 입니다.</Layout>;
}

export default withOnlyLoggingPage(Home);
