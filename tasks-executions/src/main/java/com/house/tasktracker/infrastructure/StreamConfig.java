package com.house.tasktracker.infrastructure;

import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding({ OrderOffsetChangedSink.class })
public class StreamConfig {

}
