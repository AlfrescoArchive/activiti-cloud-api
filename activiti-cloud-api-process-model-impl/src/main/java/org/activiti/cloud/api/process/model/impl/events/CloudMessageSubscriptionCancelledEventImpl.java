/*
 * Copyright 2018 Alfresco, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.cloud.api.process.model.impl.events;

import org.activiti.api.process.model.MessageSubscription;
import org.activiti.api.process.model.events.MessageSubscriptionEvent;
import org.activiti.api.process.model.events.MessageSubscriptionEvent.MessageSubscriptionEvents;
import org.activiti.cloud.api.model.shared.impl.events.CloudRuntimeEventImpl;
import org.activiti.cloud.api.process.model.events.CloudMessageSubscriptionCancelledEvent;

public class CloudMessageSubscriptionCancelledEventImpl  extends CloudRuntimeEventImpl<MessageSubscription, MessageSubscriptionEvent.MessageSubscriptionEvents> implements CloudMessageSubscriptionCancelledEvent {

    public CloudMessageSubscriptionCancelledEventImpl() {
    }

    public CloudMessageSubscriptionCancelledEventImpl(MessageSubscription entity,
                                           String processDefinitionId,
                                           String processInstanceId) {
        super(entity);    
        
        setProcessInstanceId(entity.getProcessInstanceId());
        setProcessDefinitionId(entity.getProcessDefinitionId());
        
        if (entity!=null) {
            setEntityId(entity.getActivityId());
        }
    }
    
    public CloudMessageSubscriptionCancelledEventImpl(String id,
                                           Long timestamp,
                                           MessageSubscription entity,
                                           String processDefinitionId,
                                           String processInstanceId) {
        super(id,
              timestamp,
              entity);
        
        setProcessDefinitionId(processDefinitionId);
        setProcessInstanceId(processInstanceId);
        
        if (entity!=null) {
            setEntityId(entity.getActivityId());
        }
    }
    
    @Override
    public MessageSubscriptionEvent.MessageSubscriptionEvents getEventType() {
        return MessageSubscriptionEvents.MESSAGE_SUBSCRIPTION_CANCELLED;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CloudMessageSubscriptionCancelledEventImpl [getEventType()=")
               .append(getEventType())
               .append(", toString()=")
               .append(super.toString())
               .append("]");
        return builder.toString();
    }
}
