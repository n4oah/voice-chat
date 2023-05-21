package com.voicechat.channelinvite.orchestrator.step;

import com.voicechat.channelinvite.orchestrator.adapter.out.ChannelServiceClient;
import com.voicechat.channelinvite.orchestrator.dto.AddChannelMemberDto;
import com.voicechat.common.saga.AbstractWorkflowStep;
import com.voicechat.common.saga.WorkflowStepStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
public class AddChannelMemberStep extends AbstractWorkflowStep {
    private final ChannelServiceClient channelServiceClient;
    private final AddChannelMemberDto.AddChannelMemberReqDto addChannelMemberReqDto;

    @Override
    public Mono<Boolean> process() {
        log.info("AddChannelMemberStep process");
        return this.channelServiceClient.addChannelMember(
                addChannelMemberReqDto.channelId(),
                addChannelMemberReqDto.userId()
        )
                .map((r) -> true)
                .doOnSuccess((r) -> this.setWorkflowStepStatus(WorkflowStepStatus.COMPLETED))
                .doOnError((e) -> {
                    System.out.println(e);
                    this.setWorkflowStepStatus(WorkflowStepStatus.FAILED);
                })
                .onErrorReturn(false);
    }

    @Override
    public Mono<Boolean> revert() {
        log.info("AddChannelMemberStep revert");
        return channelServiceClient.removeChannelMember(
                addChannelMemberReqDto.channelId(),
                addChannelMemberReqDto.userId()
        )
                .map((r) -> true)
                .doOnError(System.out::println)
                .onErrorReturn(false);
    }
}
