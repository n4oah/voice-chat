import { AxiosError, AxiosInstance } from 'axios';
import {
  QueryFunctionContext,
  QueryKey,
  UseQueryOptions,
  useQuery,
} from '@tanstack/react-query';
import { useAxios } from '../useAxios';

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
