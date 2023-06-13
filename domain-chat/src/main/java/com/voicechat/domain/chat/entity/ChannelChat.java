package com.voicechat.domain.chat.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "channel_chats")
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PROTECTED)
@ToString
public class ChannelChat {
    @Id
    private String id;
    private String content;
    private Long senderUserId;
    private Long channelId;
    @Indexed(unique = true)
    private String uuid;
    private Instant sentAt;
    @CreatedDate
    private Instant createdDate;

    public static ChannelChat sendMessage(
        String uuid,
        Long senderUserId,
        Long channelId,
        String content,
        Instant sentAt
    ) {
        return ChannelChat
                .builder()
                .channelId(channelId)
                .uuid(uuid)
                .content(content)
                .senderUserId(senderUserId)
                .sentAt(sentAt)
                .build();
    }
}
