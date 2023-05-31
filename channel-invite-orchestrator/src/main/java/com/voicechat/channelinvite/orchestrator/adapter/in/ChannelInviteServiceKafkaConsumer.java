package com.voicechat.channelinvite.orchestrator.adapter.in;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.voicechat.channelinvite.orchestrator.adapter.in.dto.ApprovedChannelInviteDto;
import com.voicechat.channelinvite.orchestrator.adapter.out.ChannelInviteOrchestratorServiceKafkaProducer;
import com.voicechat.channelinvite.orchestrator.adapter.out.dto.ChannelInviteTerminateEventDto;
import com.voicechat.channelinvite.orchestrator.application.ApproveChannelInviteWorkflowService;
import com.voicechat.common.saga.WorkflowStepStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChannelInviteServiceKafkaConsumer {
    private final ApproveChannelInviteWorkflowService channelInviteWorkflowService;
    private final ChannelInviteOrchestratorServiceKafkaProducer channelInviteOrchestratorServiceKafkaProducer;

    @KafkaListener(topics = "channel-invite-service")
    public void approvedChannelInviteConsume(
            @Payload @Valid ApprovedChannelInviteDto.ApprovedChannelInviteReqDto approvedChannelInviteReqDto
    ) throws JsonProcessingException {
        final var response =
                this.channelInviteWorkflowService.process(approvedChannelInviteReqDto.channelInviteId()).block();

        if (response.workflowStatus() == WorkflowStepStatus.FAILED) {
            channelInviteOrchestratorServiceKafkaProducer.channelInviteTerminateOrchestrator(
                new ChannelInviteTerminateEventDto.ChannelInviteTerminateEventDtoRes(
                        approvedChannelInviteReqDto.channelInviteId(),
                        ChannelInviteTerminateEventDto.ChannelInviteTerminateEventStatus.FAILED
                )
            );
        }

        if (response.workflowStatus() == WorkflowStepStatus.COMPLETED) {
            channelInviteOrchestratorServiceKafkaProducer.channelInviteTerminateOrchestrator(
                new ChannelInviteTerminateEventDto.ChannelInviteTerminateEventDtoRes(
                        approvedChannelInviteReqDto.channelInviteId(),
                        ChannelInviteTerminateEventDto.ChannelInviteTerminateEventStatus.SUCCESS
                )
            );
        }
    }
}
