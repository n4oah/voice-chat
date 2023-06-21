import { selectorFamily } from 'recoil';
import { channelChatAtom } from '../atoms/channel-chat-atom';

export const getChannelChat = selectorFamily({
  key: 'getChannelChat',
  get:
    (channelId: number) =>
    ({ get }) => {
      const chatting = get(channelChatAtom(channelId));

      return chatting;
    },
});
