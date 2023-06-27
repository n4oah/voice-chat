export type ChatReceiveMessage = {
  id: string;
  uuid: string;
  senderUserId: number;
  channelId: number;
  content: string;
  createdAt: string;
  senderUserName: string;
};
