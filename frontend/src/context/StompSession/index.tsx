import React, { useContext, useEffect, useState } from 'react';
import { Client as StompClient } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

type ContextType = {
  stompClient: StompClient | null;
  isWebSocketConnected: boolean;
  sessionId: string | null;
};

const StompSessionContext = React.createContext<ContextType>({
  stompClient: null,
  isWebSocketConnected: false,
  sessionId: null,
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
  const [sessionId, setSssionId] = useState<string | null>(null);

  useEffect(() => {
    if (!accessToken) {
      return;
    }

    const sockJsClient = new SockJS(
      (process.env.NEXT_PUBLIC_VOICE_CHAT_API_URL as string) + '/chat/ws',
    );

    setStompClient(() => {
      return new StompClient({
        webSocketFactory: () => sockJsClient,
        connectHeaders: {
          // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
          authorization: accessToken ? `Bearer ${accessToken}` : '',
        },
        onConnect: () => {
          // eslint-disable-next-line @typescript-eslint/ban-ts-comment
          // @ts-ignore
          const urlSplits = (sockJsClient._transport.url as string).split('/');
          const sessionId = urlSplits[urlSplits.length - 2];

          setSssionId(sessionId);

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
    <StompSessionContext.Provider
      value={{ stompClient, isWebSocketConnected, sessionId }}
    >
      {children}
    </StompSessionContext.Provider>
  );
}

export function useStompSessionContext() {
  const context = useContext(StompSessionContext);

  return context;
}
