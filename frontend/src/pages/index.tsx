import { withOnlyLoggingPage } from '../hoc/withOnlyLoggingPage';
import { Layout } from '../components/layout/Layout';

function Home() {
  return <Layout>hihi</Layout>;
}

export default withOnlyLoggingPage(Home);
