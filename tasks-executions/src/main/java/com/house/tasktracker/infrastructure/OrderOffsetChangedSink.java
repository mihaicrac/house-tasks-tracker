package com.house.tasktracker.infrastructure;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface OrderOffsetChangedSink {

    String OFFSET_CHANGED_INPUT = "offset-changed";

    @Input(OFFSET_CHANGED_INPUT)
    SubscribableChannel input();
}
