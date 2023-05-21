package com.voicechat.channelinvite.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
                @NotNull String channelName
        ) {
        }
    }
}
