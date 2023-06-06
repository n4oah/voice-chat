import { AxiosError, AxiosInstance } from 'axios';
import {
  UseQueryOptions,
  useQuery,
  useQueryClient,
} from '@tanstack/react-query';
import { useAxios } from '../useAxios';
import { ChannelInviteStatus } from '../../../types/channel-invite';

export namespace UseFetchMyChannelInviteApi {
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

  export const KEY_STRING = 'my-channel-invite' as const;

  type QueryKeyType = [typeof KEY_STRING];

  export function featch(axiosInstance: AxiosInstance) {
    return async () => {
      const response = await axiosInstance.get<ResponseType>(
        '/channel-invite/me',
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

  export const useFetch = ({
    ...args
  }: UseQueryOptions<
    ResponseType,
    AxiosError,
    ResponseType,
    QueryKeyType
  > = {}) => {
    const { axiosInstance } = useAxios();

    return useQuery([KEY_STRING], featch(axiosInstance), {
      refetchOnWindowFocus: false,
      staleTime: 300000,
      ...args,
    });
  };
}
