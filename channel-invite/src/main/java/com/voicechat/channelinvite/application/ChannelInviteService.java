package com.voicechat.channelinvite.application;

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

@Service
@Transactional
@RequiredArgsConstructor
public class ChannelInviteService {
    private final ChannelInviteChecker channelInviteChecker;
    private final ChannelInviteRepository channelInviteRepository;

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
}
