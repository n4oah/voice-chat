import { UseMutationOptions, useMutation } from '@tanstack/react-query';
import { AxiosError, AxiosResponse } from 'axios';
import { useAxios } from './useAxios';

export namespace UseChannelInviteApi {
  export type ParameterType = {
    email: string;
    channelId: number;
  };

  export type ResponseType = {
    accessToken: string;
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
      ({ email, channelId }) => {
        return axiosInstance.post(`/channel-invite/channel/${channelId}/`, {
          email,
        });
      },
      {
        ...options,
      },
    );

    return mutation;
  };
}
