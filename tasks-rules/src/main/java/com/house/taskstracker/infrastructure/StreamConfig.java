package com.house.taskstracker.infrastructure;

import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(OrderOffsetChangedSource.class)
public class StreamConfig {
}
