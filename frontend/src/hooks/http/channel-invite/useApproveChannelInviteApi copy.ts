import { UseMutationOptions, useMutation } from '@tanstack/react-query';
import { AxiosError, AxiosResponse } from 'axios';
import { useAxios } from '../useAxios';
import { UseFetchMyChannelInviteApi } from './useFetchMyChannelInviteApi';

export namespace UseRefuseChannelInviteApi {
  export type ParameterType = {
    channelInviteId: number;
  };

  export const useMutate = ({
    onSuccess,
    ...options
  }: UseMutationOptions<
    AxiosResponse<ResponseType>,
    AxiosError,
    ParameterType
  > = {}) => {
    const { axiosInstance } = useAxios();
    const refetch = UseFetchMyChannelInviteApi.useRefetch();

    const mutation = useMutation(
      ({ channelInviteId }) => {
        return axiosInstance.patch(
          `/channel-invite/me/${channelInviteId}/refuse`,
        );
      },
      {
        onSuccess(...args) {
          refetch();
          onSuccess?.(...args);
        },
        ...options,
      },
    );

    return mutation;
  };
}
