import { useEffect, useState } from 'react';
import { UseFetchChannelMembersApi } from '../http/channel/useFetchChannelMembersApi';
import { UseFetchChannelOnlineUsersApi } from '../http/chat/useFetchChannelOnlineUsersApi';
import { ChannelMemberOnlineStatus } from '../../types/channel-member-online-status';
import { produce } from 'immer';

export function useChannelOnlineStatusUsers(channelId: number) {
  const channelMembers = UseFetchChannelMembersApi.useFetch({ channelId });
  const onlineChannelUsers = UseFetchChannelOnlineUsersApi.useFetch({
    channelId,
  });

  const [users, setUsers] = useState<
    {
      id: number;
      userId: number;
      name: string;
      status: ChannelMemberOnlineStatus;
    }[]
  >([]);

  useEffect(() => {
    if (channelMembers.data && onlineChannelUsers.data) {
      channelMembers.data.channelMembers.forEach((channelMember) => {
        if (
          onlineChannelUsers.data.userIds.some(
            (userId) => userId === channelMember.userId,
          )
        ) {
          setUsers((users) =>
            produce(users, (draft) => {
              draft.push({
                ...channelMember,
                status: ChannelMemberOnlineStatus.ONLINE,
              });
            }),
          );
        } else {
          setUsers((users) =>
            produce(users, (draft) => {
              draft.push({
                ...channelMember,
                status: ChannelMemberOnlineStatus.OFFLINE,
              });
            }),
          );
        }
      });
    }
  }, [channelMembers.data, onlineChannelUsers.data]);

  return users;
}
