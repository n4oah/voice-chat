import { useSetRecoilState } from 'recoil';
import { memberAccessTokenAtom } from '../recoil/atoms/member-atom';
import { useRouter } from 'next/router';

export function useSignout() {
  const setMemberAccessTokenAtom = useSetRecoilState(memberAccessTokenAtom);
  const router = useRouter();

  const signout = () => {
    router.replace('/signin');
    setMemberAccessTokenAtom(null);
  };

  return { signout };
}
