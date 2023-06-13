package com.voicechat.domain.chat.repository;

import com.voicechat.domain.chat.entity.ChannelChat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelChatRepository extends MongoRepository<ChannelChat, String> {
}
