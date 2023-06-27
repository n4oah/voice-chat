package com.voicechat.domain.chat.repository;

import com.voicechat.domain.chat.entity.ChannelChat;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelChatRepository extends MongoRepository<ChannelChat, String> {
    @Query("{'channelId': ?0, '_id' : { '$lt': ObjectId(?1) } }")
    List<ChannelChat> findByChannelId(Long channelId, String nextCursorId, PageRequest pageRequest);
    List<ChannelChat> findByChannelId(Long channelId, PageRequest pageRequest);
}
