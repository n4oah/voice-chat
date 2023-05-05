package com.voicechat.channel.application;

import com.voicechat.channel.dto.CreateChannelDto;
import com.voicechat.domain.channel.entity.Channel;
import com.voicechat.domain.channel.repository.ChannelMemberAuthRoleRepository;
import com.voicechat.domain.channel.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChannelService {
    private final ChannelRepository channelRepository;
    private final ChannelMemberAuthRoleRepository channelMemberAuthRoleRepository;

    public void createChannel(Long userId, CreateChannelDto.CreateChannelDtoReq createChannelDtoReq) {
        final var channel =
                Channel.createChannel(userId,
                        createChannelDtoReq.name(),
                        createChannelDtoReq.maxNumberOfMember(),
                        channelMemberAuthRoleRepository
                );

        this.channelRepository.save(channel);
    }
}
