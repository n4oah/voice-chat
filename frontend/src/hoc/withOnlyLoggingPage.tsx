import { useRouter } from 'next/router';
import React, { useEffect, useState } from 'react';
import { useLogging } from '../hooks/useLogging';

export function withOnlyLoggingPage(Component: React.ComponentType) {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  return function OnlyLoggingPage(props: Record<string, unknown>) {
    const router = useRouter();
    const isLogging = useLogging();
    const [isAuth, setAuth] = useState<boolean>(false);

    useEffect(() => {
      if (isLogging === null) {
        return;
      }

      if (router.isReady) {
        const isAuth = !!isLogging;
        setAuth(isAuth);

        if (!isAuth) {
          // alert('접근 권한이 없습니다.');
          router.replace(`/signin`);
        }
      }
    }, [isLogging, router]);

    if (!isAuth) {
      return <div></div>;
    }

    return (
      <React.Fragment>
        <Component {...props}></Component>
      </React.Fragment>
    );
  };
}
