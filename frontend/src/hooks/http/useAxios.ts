import axios, { AxiosInstance } from 'axios';
import { useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';
import { memberAccessTokenAtom } from '../../recoil/atoms/member-atom';
import { isAxiosError } from 'axios';
import { useSignout } from '../useSignout';

interface VoiceChatErrorType {
  code: string;
  errors: unknown[];
  httpStatus: string;
  message: string;
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
function isVoiceChatErrorType(data: any): data is VoiceChatErrorType {
  if (
    data?.code &&
    data?.errors &&
    Array.isArray(data?.errors) &&
    data?.httpStatus &&
    data?.message
  ) {
    return true;
  }
  return false;
}

const cacheAxios = new Map<string, AxiosInstance>();

const NEXT_PUBLIC_VOICE_CHAT_API_URL = process.env
  .NEXT_PUBLIC_VOICE_CHAT_API_URL as NonNullable<string>;

function createAxiosInstance(
  parameter: {
    baseURL: string;
    accessToken?: string | null;
  },
  { signout }: { signout?: () => void } = {},
) {
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

  axiosInstance.interceptors.response.use(
    (config) => config,
    (config) => {
      if (
        signout &&
        isAxiosError(config) &&
        config.response &&
        isVoiceChatErrorType(config.response.data)
      ) {
        if (config.response.data.code === 'M002') {
          signout();
        }
      }
      return config;
    },
  );

  cacheAxios.set(key, axiosInstance);

  return axiosInstance;
}

export const globalAxiosInstance = createAxiosInstance({
  baseURL: `${NEXT_PUBLIC_VOICE_CHAT_API_URL}`,
});

export function useAxios() {
  const memberAccessToken = useRecoilValue(memberAccessTokenAtom);
  const { signout } = useSignout();

  const [axiosInstance, setAxiosInstance] = useState(() =>
    createAxiosInstance(
      {
        baseURL: `${process.env.NEXT_PUBLIC_VOICE_CHAT_API_URL}`,
        accessToken: memberAccessToken?.accessToken,
      },
      { signout },
    ),
  );

  useEffect(() => {
    setAxiosInstance(() =>
      createAxiosInstance(
        {
          baseURL: `${process.env.NEXT_PUBLIC_VOICE_CHAT_API_URL}`,
          accessToken: memberAccessToken?.accessToken,
        },
        { signout },
      ),
    );
  }, [memberAccessToken, signout]);

  return { axiosInstance };
}
