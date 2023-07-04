import React, { useContext, useEffect, useState } from 'react';
import { Client as StompClient } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

type ContextType = {
  stompClient: StompClient | null;
  isWebSocketConnected: boolean;
};

const StompSessionContext = React.createContext<ContextType>({
  stompClient: null,
  isWebSocketConnected: false,
});

export function StompSessionProvider({
  accessToken,
  children,
}: {
  accessToken?: string;
  children: React.ReactNode;
}) {
  const [stompClient, setStompClient] = useState<StompClient | null>(null);
  const [isWebSocketConnected, setWebSocketConnected] =
    useState<boolean>(false);

  useEffect(() => {
    if (!accessToken) {
      return;
    }

    setStompClient(() => {
      return new StompClient({
        webSocketFactory: () =>
          new SockJS(
            (process.env.NEXT_PUBLIC_VOICE_CHAT_API_URL as string) + '/chat/ws',
          ),
        connectHeaders: {
          // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
          authorization: accessToken ? `Bearer ${accessToken}` : '',
        },
        onConnect: () => {
          setWebSocketConnected(true);
        },
        reconnectDelay: 1000,
        // heartbeatIncoming: 4000,
        // heartbeatOutgoing: 4000,
      });
    });
  }, [accessToken]);

  useEffect(() => {
    if (stompClient) {
      stompClient.activate();
    }

    return () => {
      if (stompClient) {
        stompClient.deactivate();
      }
      setWebSocketConnected(false);
    };
  }, [stompClient]);

  return (
    <StompSessionContext.Provider value={{ stompClient, isWebSocketConnected }}>
      {children}
    </StompSessionContext.Provider>
  );
}

export function useStompSessionContext() {
  const context = useContext(StompSessionContext);

  return context;
}
