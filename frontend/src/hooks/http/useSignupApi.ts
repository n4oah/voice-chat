import { UseMutationOptions, useMutation } from '@tanstack/react-query';
import { AxiosError, AxiosResponse } from 'axios';
import { useAxios } from './useAxios';

export namespace UseSignupApi {
  export type ParameterType = {
    email: string;
    password: string;
    name: string;
  };

  export const useMutate = ({
    ...options
  }: UseMutationOptions<AxiosResponse, AxiosError, ParameterType> = {}) => {
    const { axiosInstance } = useAxios();

    const mutation = useMutation(
      ({ email, password, name }) => {
        return axiosInstance.post(`/users/signup`, { email, password, name });
      },
      {
        ...options,
      },
    );

    return mutation;
  };
}
