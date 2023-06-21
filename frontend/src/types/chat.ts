export type ChatReceiveMessage = {
  id: string;
  uuid: string;
  senderUserId: number;
  channelId: number;
  content: string;
  timestamp: string;
};
