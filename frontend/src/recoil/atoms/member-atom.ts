import { atom, AtomEffect } from 'recoil';

export type MemberAccessTokenAtomType = {
  accessToken: string;
};

const localStorageEffect =
  (key: string): AtomEffect<MemberAccessTokenAtomType | null> =>
  ({ setSelf, onSet }) => {
    if (typeof window === 'object') {
      const savedValue = localStorage.getItem(key);
      if (savedValue != null) {
        setSelf(JSON.parse(savedValue));
      }
      onSet((newValue: MemberAccessTokenAtomType | null) => {
        if (newValue === null) {
          localStorage.removeItem(key);
          return;
        }
        localStorage.setItem(key, JSON.stringify(newValue));
      });
    }
  };

export const memberAccessTokenAtom = atom<MemberAccessTokenAtomType | null>({
  key: 'member-access-token-atom',
  default: null,
  effects: [localStorageEffect('access_token')],
});
