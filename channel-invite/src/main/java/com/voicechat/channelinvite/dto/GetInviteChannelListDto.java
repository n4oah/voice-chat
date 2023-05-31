package com.voicechat.channelinvite.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Collections;
import java.util.List;

public class GetInviteChannelByUserListDto {
    public record GetInviteChannelByUserListResDto(@NotNull List<GetInviteChannelListByUserResItemDto> items) {
        @Override
        public List<GetInviteChannelListByUserResItemDto> items() {
            return Collections.unmodifiableList(this.items);
        }

        public record GetInviteChannelListByUserResItemDto(
                @NotNull @Positive Long id,
                @NotNull @Positive Long channelId,
                @NotNull String channelName
        ) {
        }
    }
}
