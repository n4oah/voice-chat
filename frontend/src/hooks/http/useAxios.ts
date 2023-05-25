import axios, { AxiosInstance } from 'axios';
import { useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';
import { memberAccessTokenAtom } from '../../recoil/atoms/member-atom';

const cacheAxios = new Map<string, AxiosInstance>();

const NEXT_PUBLIC_VOICE_CHAT_API_URL = process.env
  .NEXT_PUBLIC_VOICE_CHAT_API_URL as NonNullable<string>;

function createAxiosInstance(parameter: {
  baseURL: string;
  accessToken?: string | null;
}) {
  const key = JSON.stringify({
    ...parameter,
    isBrowser: typeof window === 'object',
  });

  if (cacheAxios.has(key)) {
    return cacheAxios.get(key) as NonNullable<AxiosInstance>;
  }

  let axiosInstance = null;

  if (parameter.accessToken) {
    axiosInstance = axios.create({
      baseURL: parameter.baseURL,
      headers: {
        Authorization: `Bearer ${parameter.accessToken}`,
      },
    });
  }

  if (!axiosInstance) {
    axiosInstance = axios.create({
      baseURL: parameter.baseURL,
    });
  }

  cacheAxios.set(key, axiosInstance);

  return axiosInstance;
}

export const globalAxiosInstance = createAxiosInstance({
  baseURL: `${NEXT_PUBLIC_VOICE_CHAT_API_URL}`,
});

export function useAxios() {
  const memberAccessToken = useRecoilValue(memberAccessTokenAtom);
  const [axiosInstance, setAxiosInstance] = useState(() =>
    createAxiosInstance({
      baseURL: `${process.env.NEXT_PUBLIC_VOICE_CHAT_API_URL}`,
      accessToken: memberAccessToken?.accessToken,
    }),
  );

  useEffect(() => {
    setAxiosInstance(() =>
      createAxiosInstance({
        baseURL: `${process.env.NEXT_PUBLIC_VOICE_CHAT_API_URL}`,
        accessToken: memberAccessToken?.accessToken,
      }),
    );
  }, [memberAccessToken]);

  return { axiosInstance };
}
