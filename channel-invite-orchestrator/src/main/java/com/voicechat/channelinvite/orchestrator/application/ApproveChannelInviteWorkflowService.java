package com.voicechat.channelinvite.orchestrator.application;

import com.netflix.servo.util.UnmodifiableList;
import com.voicechat.channelinvite.orchestrator.adapter.out.ChannelInviteServiceClient;
import com.voicechat.channelinvite.orchestrator.adapter.out.ChannelServiceClient;
import com.voicechat.channelinvite.orchestrator.dto.AddChannelMemberDto;
import com.voicechat.channelinvite.orchestrator.step.AddChannelMemberStep;
import com.voicechat.common.saga.WorkflowStep;
import com.voicechat.common.saga.WorkflowStepStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class ApproveChannelInviteWorkflowService {
    private final ChannelServiceClient channelServiceClient;
    private final ChannelInviteServiceClient channelInviteServiceClient;

    public Mono<AddChannelMemberDto.AddChannelMemberResDto> process(Long channelInviteId) {
        final AtomicReference<AddChannelMemberDto.AddChannelMemberReqDto>
                addChannelMemberReqDtoRef = new AtomicReference<>();
        final AtomicReference<List<WorkflowStep>>
                workflowStepListRef = new AtomicReference<>();


        return this.channelInviteServiceClient.getChannelDetail(channelInviteId)
                .map((response) -> {
                        addChannelMemberReqDtoRef.set(new AddChannelMemberDto.AddChannelMemberReqDto(
                                response.id(),
                                response.channelId(),
                                response.userId()));
                        workflowStepListRef.set(this.getOrderWorkflow(addChannelMemberReqDtoRef.get()));
                        return workflowStepListRef.get();
                }
        )
                .flatMapMany(Flux::fromIterable)
                .flatMap(WorkflowStep::process)
                .handle(((aBoolean, synchronousSink) -> {
                    if(aBoolean)
                        synchronousSink.next(true);
                    else
                        synchronousSink.error(new Exception("Error"));
                }))
                .then(Mono.fromCallable(() -> new AddChannelMemberDto.AddChannelMemberResDto(
                        addChannelMemberReqDtoRef.get().channelInviteId(),
                        addChannelMemberReqDtoRef.get().channelId(),
                        addChannelMemberReqDtoRef.get().userId(),
                        WorkflowStepStatus.COMPLETED
                )))
                .onErrorResume(ex -> this.revert(workflowStepListRef.get()).then(Mono.just(new AddChannelMemberDto.AddChannelMemberResDto(
                        addChannelMemberReqDtoRef.get().channelInviteId(),
                        addChannelMemberReqDtoRef.get().channelId(),
                        addChannelMemberReqDtoRef.get().userId(),
                        WorkflowStepStatus.FAILED
                ))));
    }

    private Mono<Void> revert(
            List<WorkflowStep> workflowSteps) {
        return Flux.fromStream(workflowSteps
                .stream()
                .filter((step) -> step.getStatus().equals(WorkflowStepStatus.COMPLETED)))
                .flatMap(WorkflowStep::revert)
                .then();
    }

    private List<WorkflowStep> getOrderWorkflow(
        AddChannelMemberDto.AddChannelMemberReqDto addChannelMemberReqDto
    ) {
        WorkflowStep paymentStep = new AddChannelMemberStep(
            this.channelServiceClient,
            addChannelMemberReqDto
        );

        return UnmodifiableList.of(paymentStep);
    }
}
