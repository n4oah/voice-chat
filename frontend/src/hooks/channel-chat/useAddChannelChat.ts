import { useSetRecoilState } from 'recoil';
import { channelChatAtom } from '../../recoil/atoms/channel-chat-atom';
import { ChatReceiveMessage } from '../../types/chat';
import { produce } from 'immer';
import { useCallback } from 'react';

export function useAddChannelChat(channelId: number) {
  const setChannelChat = useSetRecoilState(channelChatAtom(channelId));

  const addMessage = useCallback(
    (message: ChatReceiveMessage) => {
      setChannelChat((channelChats) => {
        return produce(channelChats, (draft) => {
          draft.push(message);

          draft.sort((a, b) => b.id.localeCompare(a.id));
        });
      });
    },
    [setChannelChat],
  );

  return addMessage;
}
