import { useSetRecoilState } from 'recoil';
import { memberAccessTokenAtom } from '../recoil/atoms/member-atom';
import { useRouter } from 'next/router';

export function useSignin() {
  const setMemberAccessTokenAtom = useSetRecoilState(memberAccessTokenAtom);
  const router = useRouter();

  const signin = (accessToken: string) => {
    setMemberAccessTokenAtom({ accessToken });
    router.push('/');
  };

  return { signin };
}
