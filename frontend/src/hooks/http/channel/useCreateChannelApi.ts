import { UseMutationOptions, useMutation } from '@tanstack/react-query';
import { AxiosError, AxiosResponse } from 'axios';
import { useAxios } from '../useAxios';
import { UseMyChannelsApi } from './useMyChannels';

export namespace UseCreateChannelApi {
  export type ParameterType = {
    name: string;
    maxNumberOfMember: number;
  };

  export type ResponseType = {
    accessToken: string;
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
    const refetchMyChannel = UseMyChannelsApi.useRefetch();

    const mutation = useMutation(
      ({ maxNumberOfMember, name }) => {
        return axiosInstance.post(`/channel/`, {
          maxNumberOfMember,
          name,
        });
      },
      {
        onSuccess(...args) {
          refetchMyChannel();
          onSuccess?.(...args);
        },
        ...options,
      },
    );

    return mutation;
  };
}
