import { selector } from 'recoil';
import { memberAccessTokenAtom } from '../atoms/member-atom';
import jwtDecode from 'jwt-decode';
import { MemberJwtPayload } from '../../types/member-jwt-payload';

export const getMyInfoByAccessToken = selector<MemberJwtPayload>({
  key: 'getMyInfoByAccessToken',
  get: ({ get }) => {
    const accessToken = get(memberAccessTokenAtom);

    if (!accessToken?.accessToken) {
      throw new Error('missing access token');
    }

    return jwtDecode<MemberJwtPayload>(accessToken.accessToken);
  },
});
