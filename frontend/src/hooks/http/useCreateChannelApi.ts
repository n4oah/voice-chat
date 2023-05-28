import { UseMutationOptions, useMutation } from '@tanstack/react-query';
import { AxiosError, AxiosResponse } from 'axios';
import { useAxios } from './useAxios';

export namespace UseCreateChannelApi {
  export type ParameterType = {
    email: string;
    password: string;
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
      ({ email, password }) => {
        const formData = new FormData();

        formData.append('email', email);
        formData.append('password', password);

        return axiosInstance.post(`/users/signin`, formData);
      },
      {
        ...options,
      },
    );

    return mutation;
  };
}
