import { UseMutationOptions, useMutation } from '@tanstack/react-query';
import { AxiosError, AxiosResponse } from 'axios';
import { useAxios } from '../useAxios';

export namespace UseSendMessageByChannelApi {
  export type ParameterType = {
    content: string;
    channelId: number;
  };

  export type ResponseType = {
    uuid: string;
  };

  export const useMutate = ({
    ...options
  }: UseMutationOptions<
    AxiosResponse<ResponseType>,
    AxiosError,
    ParameterType
  > = {}) => {
    const { axiosInstance } = useAxios();

    const mutation = useMutation(
      ({ content, channelId }) => {
        return axiosInstance.post(`/chat/channel/${channelId}/`, {
          content,
        });
      },
      {
        ...options,
      },
    );

    return mutation;
  };
}
