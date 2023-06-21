import { selectorFamily } from 'recoil';
import { channelChatAtom } from '../atoms/channel-chat-atom';
import { produce } from 'immer';

export const getChannelChat = selectorFamily({
  key: 'getChannelChat',
  get:
    (channelId: number) =>
    ({ get }) => {
      const chatting = get(channelChatAtom(channelId));

      return produce(chatting, (draft) => {
        draft.sort((a, b) => b.id.localeCompare(a.id));
      });
    },
});
