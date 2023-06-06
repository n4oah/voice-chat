import { useRouter } from 'next/router';
import React, { useEffect, useState } from 'react';
import { UseMyChannelsApi } from '../hooks/http/channel/useMyChannels';
import { Type, plainToClass } from 'class-transformer';

class RouterQuery {
  @Type(() => Number)
  channelId!: number;
}

export function withChannelPage(Component: React.ComponentType) {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  return function ChannelPage(props: Record<string, unknown>) {
    const myChannels = UseMyChannelsApi.useFetch();
    const router = useRouter();
    const { channelId } = plainToClass(RouterQuery, router.query);

    const [isAuthChannel, setAuthChannel] = useState(() => {
      if (
        !myChannels.data?.channels.some((channel) => channel.id === channelId)
      ) {
        return false;
      }
      return true;
    });

    useEffect(() => {
      if (
        !myChannels.data?.channels.some((channel) => channel.id === channelId)
      ) {
        setAuthChannel(false);

        if (router.isReady && myChannels.isSuccess) {
          router.push('/');
        }
        return;
      }

      setAuthChannel(true);
    }, [channelId, myChannels.data?.channels, myChannels.isSuccess, router]);

    if (!isAuthChannel) {
      return <div></div>;
    }

    return (
      <React.Fragment>
        <Component {...props}></Component>
      </React.Fragment>
    );
  };
}
