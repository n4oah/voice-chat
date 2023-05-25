import { useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';
import { memberAccessTokenAtom } from '../recoil/atoms/member-atom';

export function useLogging(): boolean | null {
  const accessToken = useRecoilValue(memberAccessTokenAtom);
  const [isLogging, setIsLogging] = useState<boolean | null>(null);

  useEffect(() => {
    if (accessToken?.accessToken) {
      setIsLogging(true);
    } else {
      setIsLogging(false);
    }
  }, [accessToken?.accessToken]);

  return isLogging;
}
