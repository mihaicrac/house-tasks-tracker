package com.house.taskstracker.domain;

import javax.persistence.Embeddable;
import java.util.UUID;

//TODO: make this an entity (even if it is a value) with PRIMARY KEY(order_rule_id, order_id)
// find a nice way for updating a rule in case of making OrderRuleItem an Entity
@Embeddable
public class OrderRuleItem {

    // we can remove this as we shouldn't know the identity of the user mapped to position in the queue, we could use only the orderId
    private UUID userId;

    private Integer orderId;

    private int outOfOrderOccurrences;

    public OrderRuleItem() {
    }

    public OrderRuleItem(UUID userId, Integer orderId, Integer outOfOrderOccurrences) {
        this.userId = userId;
        this.orderId = orderId;
        this.outOfOrderOccurrences = outOfOrderOccurrences;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public int getOutOfOrderOccurrences() {
        return outOfOrderOccurrences;
    }

    public void setOutOfOrderOccurrences(int outOfOrderOccurrences) {
        this.outOfOrderOccurrences = outOfOrderOccurrences;
    }

    public void incrementExtraOccurences() {
        outOfOrderOccurrences = outOfOrderOccurrences + 1;
    }

    public void decrementExtraOccurences() {
        outOfOrderOccurrences = outOfOrderOccurrences - 1;
    }
}
