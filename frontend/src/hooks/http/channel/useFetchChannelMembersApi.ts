import { AxiosError, AxiosInstance } from 'axios';
import {
  QueryFunctionContext,
  QueryKey,
  UseQueryOptions,
  useQuery,
  useQueryClient,
} from '@tanstack/react-query';
import { useAxios } from '../useAxios';

export namespace UseFetchChannelMembersApi {
  export type ResponseType = {
    channelMembers: {
      id: number;
      name: string;
    }[];
  };

  export type RequestType = {
    channelId: number;
  };

  export const KEY_STRING = 'fetch-channel-members-api' as const;

  type QueryKeyType = [typeof KEY_STRING, RequestType];
  type TQueryKey = QueryKeyType & QueryKey;

  export function featch(axiosInstance: AxiosInstance) {
    return async ({ queryKey }: QueryFunctionContext<TQueryKey>) => {
      const response = await axiosInstance.get<ResponseType>(
        `/channel/${queryKey[1].channelId}/channel-member/`,
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
      staleTime: 300000,
      ...args,
    });
  };
}
