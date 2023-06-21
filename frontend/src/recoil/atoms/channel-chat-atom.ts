import { atomFamily } from 'recoil';
import { ChatReceiveMessage } from '../../types/chat';

export const channelChatAtom = atomFamily<ChatReceiveMessage[], number>({
  key: 'channel-chat-atom',
  default: () => [],
});
