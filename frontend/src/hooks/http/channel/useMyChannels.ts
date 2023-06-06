import { AxiosError, AxiosInstance } from 'axios';
import { ChannelMemberRole } from '../../../types/channel';
import {
  UseQueryOptions,
  useQuery,
  useQueryClient,
} from '@tanstack/react-query';
import { useAxios } from '../useAxios';

export namespace UseMyChannelsApi {
  export type ResponseType = {
    channels: {
      id: number;
      name: string;
      maxNumberOfMember: number;
      authRoles: ChannelMemberRole[];
    }[];
  };

  export const KEY_STRING = 'my-channels' as const;

  type QueryKeyType = [typeof KEY_STRING];

  export function featch(axiosInstance: AxiosInstance) {
    return async () => {
      const response = await axiosInstance.get<ResponseType>('/channel/me');
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
