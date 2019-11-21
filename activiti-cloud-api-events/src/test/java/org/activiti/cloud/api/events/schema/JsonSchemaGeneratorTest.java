package org.activiti.cloud.api.events.schema;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import org.activiti.cloud.api.model.shared.events.CloudVariableCreatedEvent;
import org.activiti.cloud.api.model.shared.events.CloudVariableDeletedEvent;
import org.activiti.cloud.api.model.shared.events.CloudVariableUpdatedEvent;
import org.activiti.cloud.api.model.shared.impl.conf.CloudCommonModelAutoConfiguration;
import org.activiti.cloud.api.process.model.events.CloudBPMNActivityCancelledEvent;
import org.activiti.cloud.api.process.model.events.CloudBPMNActivityCompletedEvent;
import org.activiti.cloud.api.process.model.events.CloudBPMNActivityStartedEvent;
import org.activiti.cloud.api.process.model.events.CloudBPMNErrorReceivedEvent;
import org.activiti.cloud.api.process.model.events.CloudBPMNMessageReceivedEvent;
import org.activiti.cloud.api.process.model.events.CloudBPMNMessageSentEvent;
import org.activiti.cloud.api.process.model.events.CloudBPMNMessageWaitingEvent;
import org.activiti.cloud.api.process.model.events.CloudBPMNSignalReceivedEvent;
import org.activiti.cloud.api.process.model.events.CloudBPMNTimerCancelledEvent;
import org.activiti.cloud.api.process.model.events.CloudBPMNTimerExecutedEvent;
import org.activiti.cloud.api.process.model.events.CloudBPMNTimerFailedEvent;
import org.activiti.cloud.api.process.model.events.CloudBPMNTimerFiredEvent;
import org.activiti.cloud.api.process.model.events.CloudBPMNTimerRetriesDecrementedEvent;
import org.activiti.cloud.api.process.model.events.CloudBPMNTimerScheduledEvent;
import org.activiti.cloud.api.process.model.events.CloudIntegrationRequestedEvent;
import org.activiti.cloud.api.process.model.events.CloudIntegrationResultReceivedEvent;
import org.activiti.cloud.api.process.model.events.CloudMessageSubscriptionCancelledEvent;
import org.activiti.cloud.api.process.model.events.CloudProcessCancelledEvent;
import org.activiti.cloud.api.process.model.events.CloudProcessCompletedEvent;
import org.activiti.cloud.api.process.model.events.CloudProcessCreatedEvent;
import org.activiti.cloud.api.process.model.events.CloudProcessDeployedEvent;
import org.activiti.cloud.api.process.model.events.CloudProcessResumedEvent;
import org.activiti.cloud.api.process.model.events.CloudProcessStartedEvent;
import org.activiti.cloud.api.process.model.events.CloudProcessSuspendedEvent;
import org.activiti.cloud.api.process.model.events.CloudProcessUpdatedEvent;
import org.activiti.cloud.api.process.model.events.CloudSequenceFlowTakenEvent;
import org.activiti.cloud.api.process.model.events.CloudStartMessageDeployedEvent;
import org.activiti.cloud.api.process.model.impl.conf.CloudProcessModelAutoConfiguration;
import org.activiti.cloud.api.task.model.events.CloudTaskActivatedEvent;
import org.activiti.cloud.api.task.model.events.CloudTaskAssignedEvent;
import org.activiti.cloud.api.task.model.events.CloudTaskCancelledEvent;
import org.activiti.cloud.api.task.model.events.CloudTaskCandidateGroupAddedEvent;
import org.activiti.cloud.api.task.model.events.CloudTaskCandidateGroupRemovedEvent;
import org.activiti.cloud.api.task.model.events.CloudTaskCandidateUserAddedEvent;
import org.activiti.cloud.api.task.model.events.CloudTaskCandidateUserRemovedEvent;
import org.activiti.cloud.api.task.model.events.CloudTaskCompletedEvent;
import org.activiti.cloud.api.task.model.events.CloudTaskCreatedEvent;
import org.activiti.cloud.api.task.model.events.CloudTaskSuspendedEvent;
import org.activiti.cloud.api.task.model.events.CloudTaskUpdatedEvent;
import org.activiti.cloud.api.task.model.impl.conf.CloudTaskModelAutoConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.activiti.cloud.api.events.CloudRuntimeEventType.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                JsonSchemaGeneratorTest.Config.class,
                CloudProcessModelAutoConfiguration.class,
                CloudTaskModelAutoConfiguration.class,
                CloudCommonModelAutoConfiguration.class
        })
public class JsonSchemaGeneratorTest {

    private static Map<String, Class<?>> cloudEventRegistry = new HashMap<>();

    static {
        cloudEventRegistry.put(ACTIVITY_STARTED.name(), CloudBPMNActivityStartedEvent.class);
        cloudEventRegistry.put(ACTIVITY_CANCELLED.name(), CloudBPMNActivityCancelledEvent.class);
        cloudEventRegistry.put(ACTIVITY_COMPLETED.name(), CloudBPMNActivityCompletedEvent.class);
        cloudEventRegistry.put(ERROR_RECEIVED.name(), CloudBPMNErrorReceivedEvent.class);
        cloudEventRegistry.put(SIGNAL_RECEIVED.name(), CloudBPMNSignalReceivedEvent.class);
        cloudEventRegistry.put(TIMER_FIRED.name(), CloudBPMNTimerFiredEvent.class);
        cloudEventRegistry.put(TIMER_CANCELLED.name(), CloudBPMNTimerCancelledEvent.class);
        cloudEventRegistry.put(TIMER_SCHEDULED.name(), CloudBPMNTimerScheduledEvent.class);
        cloudEventRegistry.put(TIMER_EXECUTED.name(), CloudBPMNTimerExecutedEvent.class);
        cloudEventRegistry.put(TIMER_FAILED.name(), CloudBPMNTimerFailedEvent.class);
        cloudEventRegistry.put(TIMER_RETRIES_DECREMENTED.name(), CloudBPMNTimerRetriesDecrementedEvent.class);
        cloudEventRegistry.put(MESSAGE_WAITING.name(), CloudBPMNMessageWaitingEvent.class);
        cloudEventRegistry.put(MESSAGE_RECEIVED.name(), CloudBPMNMessageReceivedEvent.class);
        cloudEventRegistry.put(MESSAGE_SENT.name(), CloudBPMNMessageSentEvent.class);
        cloudEventRegistry.put(INTEGRATION_REQUESTED.name(), CloudIntegrationRequestedEvent.class);
        cloudEventRegistry.put(INTEGRATION_RESULT_RECEIVED.name(), CloudIntegrationResultReceivedEvent.class);
        cloudEventRegistry.put(PROCESS_DEPLOYED.name(), CloudProcessDeployedEvent.class);
        cloudEventRegistry.put(PROCESS_CREATED.name(), CloudProcessCreatedEvent.class);
        cloudEventRegistry.put(PROCESS_STARTED.name(), CloudProcessStartedEvent.class);
        cloudEventRegistry.put(PROCESS_COMPLETED.name(), CloudProcessCompletedEvent.class);
        cloudEventRegistry.put(PROCESS_CANCELLED.name(), CloudProcessCancelledEvent.class);
        cloudEventRegistry.put(PROCESS_SUSPENDED.name(), CloudProcessSuspendedEvent.class);
        cloudEventRegistry.put(PROCESS_RESUMED.name(), CloudProcessResumedEvent.class);
        cloudEventRegistry.put(PROCESS_UPDATED.name(), CloudProcessUpdatedEvent.class);
        cloudEventRegistry.put(SEQUENCE_FLOW_TAKEN.name(), CloudSequenceFlowTakenEvent.class);
        cloudEventRegistry.put(START_MESSAGE_DEPLOYED.name(), CloudStartMessageDeployedEvent.class);
        cloudEventRegistry.put(MESSAGE_SUBSCRIPTION_CANCELLED.name(), CloudMessageSubscriptionCancelledEvent.class);

        cloudEventRegistry.put(TASK_CREATED.name(), CloudTaskCreatedEvent.class);
        cloudEventRegistry.put(TASK_UPDATED.name(), CloudTaskUpdatedEvent.class);
        cloudEventRegistry.put(TASK_ASSIGNED.name(), CloudTaskAssignedEvent.class);
        cloudEventRegistry.put(TASK_COMPLETED.name(), CloudTaskCompletedEvent.class);
        cloudEventRegistry.put(TASK_SUSPENDED.name(), CloudTaskSuspendedEvent.class);
        cloudEventRegistry.put(TASK_ACTIVATED.name(), CloudTaskActivatedEvent.class);
        cloudEventRegistry.put(TASK_CANCELLED.name(), CloudTaskCancelledEvent.class);
        cloudEventRegistry.put(TASK_CANDIDATE_USER_ADDED.name(), CloudTaskCandidateUserAddedEvent.class);
        cloudEventRegistry.put(TASK_CANDIDATE_USER_REMOVED.name(), CloudTaskCandidateUserRemovedEvent.class);
        cloudEventRegistry.put(TASK_CANDIDATE_GROUP_ADDED.name(), CloudTaskCandidateGroupAddedEvent.class);
        cloudEventRegistry.put(TASK_CANDIDATE_GROUP_REMOVED.name(), CloudTaskCandidateGroupRemovedEvent.class);

        cloudEventRegistry.put(VARIABLE_CREATED.name(), CloudVariableCreatedEvent.class);
        cloudEventRegistry.put(VARIABLE_UPDATED.name(), CloudVariableUpdatedEvent.class);
        cloudEventRegistry.put(VARIABLE_DELETED.name(), CloudVariableDeletedEvent.class);
    }

    @Configuration
    public static class Config {

        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void generateSchema() {
        JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(objectMapper);
        File outputDir = new File("target/schema/");
        outputDir.mkdirs();
        cloudEventRegistry.forEach((k, v) -> {
            try {
                JsonSchema jsonSchema = schemaGen.generateSchema(v);
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputDir, k + ".json"), jsonSchema);
            } catch (Exception e) {
                System.out.println("unable to generate schema for " + k);
                e.printStackTrace();
            }
        });
    }
}
