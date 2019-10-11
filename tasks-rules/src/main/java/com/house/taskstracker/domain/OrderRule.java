package com.house.taskstracker.domain;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name = "order_rules")
public class OrderRule {
    @Id
    private UUID id;

    @OrderBy("orderId ASC")
    @ElementCollection
    @CollectionTable(name = "order_rule_items", joinColumns = @JoinColumn(name = "order_rule_id"))
    @OrderColumn(name = "index_id")
    private List<OrderRuleItem> orderRuleItems;

    @Column(name = "rule_offset")
    private int offset;

    public OrderRule() {
    }

    public OrderRule(UUID id, List<OrderRuleItem> orderRuleItems) {
        this.id = id;
        this.orderRuleItems = orderRuleItems;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<OrderRuleItem> getOrderRuleItems() {
        return orderRuleItems;
    }

    public void setOrderRuleItems(List<OrderRuleItem> orderRuleItems) {
        this.orderRuleItems = orderRuleItems;
    }

    public void moveOffset(UUID actionDoneBy) {
        UUID currentTurn = orderRuleItems.get(offset).getUserId();
        if (actionDoneBy.equals(currentTurn)) {
            while (incrementOffset()) {
            }
        } else {
            OrderRuleItem actionDoneByItem = getItemById(actionDoneBy);
            actionDoneByItem.incrementExtraOccurences();
            return;
        }
    }

    private OrderRuleItem getItemById(UUID userId) {
        return orderRuleItems.stream()
                .filter(e -> e.getUserId().equals(userId))
                .findAny()
                .orElseThrow(RuntimeException::new);
    }

    private boolean incrementOffset() {
        orderRuleItems.stream()
                .filter(e -> e.getOutOfOrderOccurrences() == 0)
                .findAny()
                .orElseThrow(RuntimeException::new);
        int endOfCycle = orderRuleItems.size();
        offset = (offset + 1) % endOfCycle;
        if (orderRuleItems.get(offset).getOutOfOrderOccurrences() > 0) {
            orderRuleItems.get(offset).decrementExtraOccurences();
            return true;
        } else {
            return false;
        }
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
