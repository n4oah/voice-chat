package com.voicechat.channelinvite.application;

import com.voicechat.channelinvite.adapter.channel.out.ChannelServiceClient;
import com.voicechat.channelinvite.dto.GetInviteChannelListDto;
import com.voicechat.channelinvite.dto.InviteChannelDto;
import com.voicechat.channelinvite.exception.AlreadyChannelInviteUserException;
import com.voicechat.domain.channelinvite.constants.ChannelInviteStatus;
import com.voicechat.domain.channelinvite.entity.ChannelInvite;
import com.voicechat.domain.channelinvite.repository.ChannelInviteRepository;
import com.voicechat.domain.channelinvite.service.ChannelInviteChecker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChannelInviteService {
    private final ChannelInviteChecker channelInviteChecker;
    private final ChannelInviteRepository channelInviteRepository;
    private final ChannelServiceClient channelServiceClient;

    public void inviteChannel(@RequestBody @Valid InviteChannelDto.InviteChannelReqDto inviteChannelReqDto) {

        if (this.channelInviteRepository.findByInvitedChannelIdAndInvitedUserIdAndStatus(
                inviteChannelReqDto.channelId(), inviteChannelReqDto.userId(), ChannelInviteStatus.WAITED).isPresent()) {
            throw new AlreadyChannelInviteUserException();
        }

        final var channelInvite =
                ChannelInvite.inviteChannel(
                        inviteChannelReqDto.channelId(), inviteChannelReqDto.userId(), channelInviteChecker
                );

        this.channelInviteRepository.save(channelInvite);
    }

    @Transactional(readOnly = true)
    public GetInviteChannelListDto.GetInviteChannelListResDto getInviteChannels(Long userId) {
        final var channelInvites = this.channelInviteRepository.findByInvitedUserIdAndStatus(userId, ChannelInviteStatus.WAITED);

        System.out.println("channelInvites" + channelInvites);

        return new GetInviteChannelListDto.GetInviteChannelListResDto(
                channelInvites.stream().map((channelInvite) -> new GetInviteChannelListDto.GetInviteChannelListResDto.GetInviteChannelListResItemDto(
                    channelInvite.getId(),
                    channelInvite.getInvitedChannelId(),
                    channelServiceClient.getChannelDetail(channelInvite.getInvitedChannelId()).getBody().name()
                )
        ).collect(Collectors.toUnmodifiableList()));
    }
}
