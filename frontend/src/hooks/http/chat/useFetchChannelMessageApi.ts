import { AxiosError, AxiosInstance } from 'axios';
import {
  QueryFunctionContext,
  QueryKey,
  UseInfiniteQueryOptions,
  useInfiniteQuery,
} from '@tanstack/react-query';
import { useAxios } from '../useAxios';
import { ChatReceiveMessage } from '../../../types/chat';

export namespace UseFetchChannelMessageApi {
  export type ResponseType = {
    messages: ChatReceiveMessage[];
    nextCursorId: string | null;
  };

  export type RequestType = {
    nextCursorId?: string | null;
    channelId: number;
  };

  export const KEY_STRING = 'fetch-channel-message' as const;

  type QueryKeyType = [typeof KEY_STRING, RequestType];
  type TQueryKey = QueryKeyType & QueryKey;

  export function featch(axiosInstance: AxiosInstance) {
    return async ({
      queryKey,
      pageParam,
    }: QueryFunctionContext<TQueryKey, string>) => {
      const response = await axiosInstance.get<ResponseType>(
        `/chat/channel/${queryKey[1].channelId}/`,
        {
          params: {
            nextCursorId: pageParam ?? queryKey[1].nextCursorId,
          },
        },
      );

      return response.data;
    };
  }

  export const useFetch = (
    { channelId, nextCursorId }: RequestType,
    {
      ...args
    }: UseInfiniteQueryOptions<
      ResponseType,
      AxiosError,
      ResponseType,
      ResponseType,
      QueryKeyType
    > = {},
  ) => {
    const { axiosInstance } = useAxios();

    return useInfiniteQuery(
      [KEY_STRING, { channelId, nextCursorId }],
      featch(axiosInstance),
      {
        getNextPageParam: (data) => {
          return data.nextCursorId || false;
        },
        refetchOnWindowFocus: false,
        staleTime: 300000,
        ...args,
      },
    );
  };
}
