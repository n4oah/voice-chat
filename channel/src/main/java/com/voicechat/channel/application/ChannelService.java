package com.voicechat.channel.application;

import com.voicechat.channel.dto.CreateChannelDto;
import com.voicechat.channel.dto.GetChannelDetailDto;
import com.voicechat.channel.exception.NotFoundChannelException;
import com.voicechat.domain.channel.entity.Channel;
import com.voicechat.domain.channel.repository.ChannelMemberAuthRoleRepository;
import com.voicechat.domain.channel.repository.ChannelMemberRepository;
import com.voicechat.domain.channel.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChannelService {
    private final ChannelRepository channelRepository;
    private final ChannelMemberRepository channelMemberRepository;
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

    @Transactional(readOnly = true)
    public boolean isFullChannel(Long channelId) {
        final var channel = this.channelRepository.findById(channelId).orElseThrow(NotFoundChannelException::new);
        final var channelCount = this.channelMemberRepository.countByChannel(channel);

        if (channelCount >= channel.getMaxNumberOfMember()) {
            return true;
        }

        return false;
    }

    @Transactional(readOnly = true)
    public boolean hasUserByChannel(Long channelId, Long userId) {
        final var channel = this.channelRepository.findById(channelId).orElseThrow(NotFoundChannelException::new);

        return this.channelMemberRepository.findByChannelAndUserId(channel, userId)
                .isPresent();
    }

    public GetChannelDetailDto.GetChannelDetailResDto getChannelDetail(Long channelId) {
        return this.channelRepository.findById(channelId).map((channel -> new GetChannelDetailDto.GetChannelDetailResDto(
                channel.getId(), channel.getName(), channel.getMaxNumberOfMember()
        ))).orElseThrow(NotFoundChannelException::new);
    }
}
