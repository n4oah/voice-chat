package com.voicechat.channelinvite.dto;

import com.voicechat.common.constant.ChannelInviteStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Collections;
import java.util.List;

public class GetInviteChannelListDto {
    public record GetInviteChannelListResDto(@NotNull List<GetInviteChannelListResItemDto> items) {
        @Override
        public List<GetInviteChannelListResItemDto> items() {
            return Collections.unmodifiableList(this.items);
        }

        public record GetInviteChannelListResItemDto(
                @NotNull @Positive Long id,
                @NotNull @Positive Long channelId,
                @NotNull String channelName,
                @NotNull ChannelInviteStatus status,
                @NotNull Long invitedUserId,
                @NotNull String invitedUserEmail
        ) {
        }
    }
}
