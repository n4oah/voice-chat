import { AxiosError, AxiosInstance } from 'axios';
import {
  QueryFunctionContext,
  QueryKey,
  UseQueryOptions,
  useQuery,
  useQueryClient,
} from '@tanstack/react-query';
import { useAxios } from '../useAxios';
import { produce } from 'immer';

export namespace UseFetchChannelOnlineUsersApi {
  export type ResponseType = {
    userIds: number[];
  };

  export type RequestType = {
    channelId: number;
  };

  export const KEY_STRING = 'fetch-channel-online-users' as const;

  type QueryKeyType = [typeof KEY_STRING, RequestType];
  type TQueryKey = QueryKeyType & QueryKey;

  export function featch(axiosInstance: AxiosInstance) {
    return async ({ queryKey }: QueryFunctionContext<TQueryKey>) => {
      const response = await axiosInstance.get<ResponseType>(
        `/chat/channel/${queryKey[1].channelId}/online-users/`,
      );

      return response.data;
    };
  }

  export function useAddUser() {
    const queryClient = useQueryClient();

    function addUser(channelId: number, userId: number) {
      queryClient.setQueryData(
        [KEY_STRING, { channelId }],
        (previous: ResponseType | undefined) => {
          if (!previous) {
            return {
              userIds: [userId],
            };
          }

          return produce(previous, (draft) => {
            const userIds = Array.from(new Set(draft.userIds));
            userIds.push(userId);

            return {
              userIds: Array.from(new Set(userIds)),
            };
          });
        },
      );
    }

    return { addUser };
  }

  export function useRemoveUser() {
    const queryClient = useQueryClient();

    function removeUser(channelId: number, userId: number) {
      queryClient.setQueryData(
        [KEY_STRING, { channelId }],
        (previous: ResponseType | undefined) => {
          if (!previous) {
            return undefined;
          }

          return produce(previous, (draft) => {
            const userIds = new Set(draft.userIds);
            userIds.delete(userId);

            return {
              userIds: Array.from(userIds),
            };
          });
        },
      );
    }

    return { removeUser };
  }

  export const useFetch = (
    { channelId }: RequestType,
    {
      ...args
    }: UseQueryOptions<
      ResponseType,
      AxiosError,
      ResponseType,
      QueryKeyType
    > = {},
  ) => {
    const { axiosInstance } = useAxios();

    return useQuery([KEY_STRING, { channelId }], featch(axiosInstance), {
      refetchOnWindowFocus: false,
      staleTime: 300000,
      ...args,
    });
  };
}
