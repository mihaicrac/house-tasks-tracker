package com.house.tasktracker.domain;

import com.house.tasktracker.infrastructure.OrderOffsetChangedSink;
import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;

@AllArgsConstructor
@Service
public class TaskEventService {

    private HouseTaskRepository houseTaskRepository;

    @StreamListener(OrderOffsetChangedSink.OFFSET_CHANGED_INPUT)
    @Transactional
    public void getEvent(TaskExecutionEvent event) {
        HouseTask task = houseTaskRepository.findByRuleIdAndUserIdAndStatusOrderByClaimedTimestampDesc(event.getOrderRule(),
                                                                                                       event.getDoneBy(),
                                                                                                       Status.CLAIMED)
                                            .get();
        task.setStatus(Status.DONE);
        task.setCheckedTimestamp(Instant.now());
    }
}
