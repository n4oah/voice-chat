import { AxiosError, AxiosInstance } from 'axios';
import {
  QueryFunctionContext,
  QueryKey,
  UseQueryOptions,
  useQuery,
  useQueryClient,
} from '@tanstack/react-query';
import { useAxios } from './useAxios';
import { ChannelInviteStatus } from '../../types/channel-invite';

export namespace UseFetchMyChannelInviteMembersApi {
  export type RequestType = {
    channelId: number;
  };

  export type ResponseType = {
    items: {
      id: number;
      channelId: number;
      channelName: number;
      status: ChannelInviteStatus;
      invitedUserId: number;
      invitedUserEmail: string;
    }[];
  };

  export const KEY_STRING = 'my-channel-invite-members' as const;

  type QueryKeyType = [typeof KEY_STRING, RequestType];
  type TQueryKey = QueryKeyType & QueryKey;

  export function featch(axiosInstance: AxiosInstance) {
    return async ({ queryKey }: QueryFunctionContext<TQueryKey>) => {
      const response = await axiosInstance.get<ResponseType>(
        `/channel-invite/channel/${queryKey[1].channelId}/`,
      );
      return response.data;
    };
  }

  export function useRefetch() {
    const queryClient = useQueryClient();

    function refetch() {
      queryClient.refetchQueries([KEY_STRING]);
    }

    return refetch;
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
      refetchOnMount: true,
      ...args,
    });
  };
}
