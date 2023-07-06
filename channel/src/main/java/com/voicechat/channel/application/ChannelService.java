package com.voicechat.channel.application;

import com.voicechat.channel.adapter.out.UserServiceClient;
import com.voicechat.channel.dto.CreateChannelDto;
import com.voicechat.channel.dto.GetChannelDetailDto;
import com.voicechat.channel.dto.GetChannelListDto;
import com.voicechat.channel.dto.GetChannelMemberDto;
import com.voicechat.channel.exception.NotFoundChannelException;
import com.voicechat.domain.channel.entity.Channel;
import com.voicechat.domain.channel.entity.ChannelMember;
import com.voicechat.domain.channel.repository.ChannelMemberAuthRoleRepository;
import com.voicechat.domain.channel.repository.ChannelMemberRepository;
import com.voicechat.domain.channel.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChannelService {
    private final ChannelRepository channelRepository;
    private final ChannelMemberRepository channelMemberRepository;
    private final ChannelMemberAuthRoleRepository channelMemberAuthRoleRepository;
    private final UserServiceClient userServiceClient;

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

    @Transactional(readOnly = true)
    public GetChannelDetailDto.GetChannelDetailResDto getChannelDetail(Long channelId) {
        return this.channelRepository.findById(channelId).map((channel -> new GetChannelDetailDto.GetChannelDetailResDto(
                channel.getId(), channel.getName(), channel.getMaxNumberOfMember()
        ))).orElseThrow(NotFoundChannelException::new);
    }

    public void addChannelMember(Long channelId, Long userId) {
        final var channel = this.channelRepository.findById(channelId).orElseThrow(
                NotFoundChannelException::new
        );

        channel.addChannelMember(userId, this.channelMemberAuthRoleRepository);
    }

    public void removeChannelMember(Long channelId, Long userId) {
        final var channel = this.channelRepository.findById(channelId).orElseThrow(
                NotFoundChannelException::new
        );

        channel.removeChannelMember(userId);
    }

    @Transactional(readOnly = true)
    public GetChannelListDto.GetChannelListResDto getChannelsByUserId(Long userId) {
        List<ChannelMember> channelMembers = this.channelMemberRepository.findByUserId(userId);

        return new GetChannelListDto.GetChannelListResDto(channelMembers.stream().map((channelMember) ->
            new GetChannelDetailDto.GetMyChannelDetailResDto(
                channelMember.getChannel().getId(),
                channelMember.getChannel().getName(),
                channelMember.getChannel().getMaxNumberOfMember(),
                channelMember.getAuthorities().stream().map((a) -> a.getName()).collect(Collectors.toList())
            )
        ).collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    public GetChannelDetailDto.GetMyChannelDetailResDto getChannelByUserId(Long channelId, Long userId) {
        final var channel = this.channelRepository.findById(channelId).orElseThrow(NotFoundChannelException::new);

        ChannelMember channelMember = this.channelMemberRepository.findByChannelAndUserId(channel, userId)
                .orElseThrow(NotFoundChannelException::new);

        return new GetChannelDetailDto.GetMyChannelDetailResDto(
                channelMember.getChannel().getId(),
                channelMember.getChannel().getName(),
                channelMember.getChannel().getMaxNumberOfMember(),
                channelMember.getAuthorities().stream().map((a) -> a.getName()).collect(Collectors.toList())
        );
    }

    @Transactional(readOnly = true)
    public List<GetChannelMemberDto.GetChannelMemberDtoRes.ChannelMemberItem>
        getChannelMemberByChannelId(Long channelId) {

        final var channel = this.channelRepository.findById(channelId).orElseThrow(NotFoundChannelException::new);
        final var channelMembers = this.channelMemberRepository.findByChannel(channel);

        return channelMembers.stream().map(channelMember ->
            new GetChannelMemberDto.GetChannelMemberDtoRes.ChannelMemberItem(
                channelMember.getId(),
                this.userServiceClient.getUserInfo(channelMember.getUserId()).getBody().name(),
                channelMember.getUserId()
            )
        ).toList();
    }
}
