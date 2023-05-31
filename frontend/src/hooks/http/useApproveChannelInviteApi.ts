import { UseMutationOptions, useMutation } from '@tanstack/react-query';
import { AxiosError, AxiosResponse } from 'axios';
import { useAxios } from './useAxios';

export namespace UseApproveChannelInviteApi {
  export type ParameterType = {
    channelInviteId: number;
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
      ({ channelInviteId }) => {
        return axiosInstance.patch(
          `/channel-invite/me/${channelInviteId}/approve`,
        );
      },
      {
        ...options,
      },
    );

    return mutation;
  };
}
