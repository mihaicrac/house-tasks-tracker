package com.house.taskstracker.infrastructure;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OrderOffsetChangedSource {

    String OFFSET_CHANGED_OUTPUT = "offset-changed";

    @Output(OFFSET_CHANGED_OUTPUT)
    MessageChannel output();
}
