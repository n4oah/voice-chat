package com.voicechat.channelinvite.application;

import com.voicechat.channelinvite.adapter.out.ChannelServiceClient;
import com.voicechat.channelinvite.dto.GetInviteChannelDetailDto;
import com.voicechat.channelinvite.dto.GetInviteChannelListDto;
import com.voicechat.channelinvite.dto.InviteChannelDto;
import com.voicechat.channelinvite.exception.AlreadyChannelInviteUserException;
import com.voicechat.channelinvite.exception.NotFoundChannelInviteException;
import com.voicechat.common.constant.ChannelInviteStatus;
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

    public void inviteChannel(Long channelId, Long userId) {

        if (this.channelInviteRepository.findByInvitedChannelIdAndInvitedUserIdAndStatus(
                channelId, userId, ChannelInviteStatus.WAITED).isPresent()) {
            throw new AlreadyChannelInviteUserException();
        }

        final var channelInvite =
                ChannelInvite.inviteChannel(
                        channelId, userId, channelInviteChecker
                );

        this.channelInviteRepository.save(channelInvite);
    }

    @Transactional(readOnly = true)
    public GetInviteChannelListDto.GetInviteChannelListResDto getInviteChannels(Long userId) {
        final var channelInvites = this.channelInviteRepository.findByInvitedUserIdAndStatus(userId, ChannelInviteStatus.WAITED);

        return new GetInviteChannelListDto.GetInviteChannelListResDto(
                channelInvites.stream().map((channelInvite) -> new GetInviteChannelListDto.GetInviteChannelListResDto.GetInviteChannelListResItemDto(
                    channelInvite.getId(),
                    channelInvite.getInvitedChannelId(),
                    channelServiceClient.getChannelDetail(channelInvite.getInvitedChannelId()).getBody().name()
                )
        ).collect(Collectors.toUnmodifiableList()));
    }

    public void approveInvitedChannel(Long invitedUserId, Long channelInviteId) {
        final var channelInvite = this.channelInviteRepository.findByIdAndInvitedUserIdAndStatus(
                channelInviteId,
            invitedUserId,
            ChannelInviteStatus.WAITED
        ).orElseThrow(NotFoundChannelInviteException::new);

        channelInvite.approveInvitedChannel(channelInviteChecker);

        this.channelInviteRepository.save(channelInvite);
    }

    public void refuseInvitedChannel(Long invitedUserId, Long channelInviteId) {
        final var channelInvite = this.channelInviteRepository.findByIdAndInvitedUserIdAndStatus(
                channelInviteId,
                invitedUserId,
                ChannelInviteStatus.WAITED
        ).orElseThrow(NotFoundChannelInviteException::new);

        channelInvite.refuseInvitedChannel();

        this.channelInviteRepository.save(channelInvite);
    }

    public void rejectChannelInvite(Long channelInviteId) {
        this.channelInviteRepository.findById(channelInviteId)
                .ifPresent(ChannelInvite::rejectInvitedChannel);
    }

    public GetInviteChannelDetailDto.GetInviteChannelDetailRes getInviteChannel(
            Long channelInviteId
    ) {
        final var channelInvite =
            this.channelInviteRepository.findById(channelInviteId).orElseThrow(
                NotFoundChannelInviteException::new
            );

        return new GetInviteChannelDetailDto.GetInviteChannelDetailRes(
            channelInvite.getId(),
            channelInvite.getInvitedChannelId(),
            channelInvite.getInvitedUserId(),
            channelInvite.getStatus()
        );
    }
}
