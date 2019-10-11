package com.house.tasktracker;

import com.house.taskstracker.domain.OrderRule;
import com.house.taskstracker.domain.OrderRuleItem;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderRuleTest {

    private final UUID zero = UUID.fromString("00000000-0000-0000-0000-000000000000");
    private final UUID one = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private final UUID two = UUID.fromString("00000000-0000-0000-0000-000000000002");
    private final UUID three = UUID.fromString("00000000-0000-0000-0000-000000000003");

    @Test
    public void moveOffset() {
        List<OrderRuleItem> orderRuleItems = new ArrayList<>();
        orderRuleItems.add(createOrderRuleItem(zero, 0, 0));
        orderRuleItems.add(createOrderRuleItem(one, 1, 0));
        orderRuleItems.add(createOrderRuleItem(two, 2, 0));
        orderRuleItems.add(createOrderRuleItem(three, 3, 0));

        OrderRule orderRule = new OrderRule(zero, orderRuleItems);
        orderRule.moveOffset(zero);
        orderRule.moveOffset(two);
        orderRule.moveOffset(three);

        Assert.assertEquals(1, orderRule.getOffset());
        Assert.assertEquals(1, orderRule.getOffset());

        orderRule.moveOffset(zero);
        orderRule.moveOffset(two);
        Assert.assertEquals(1, orderRule.getOffset());
        Assert.assertEquals(2, orderRule.getOrderRuleItems().get(2).getOutOfOrderOccurrences());
        Assert.assertEquals(1, orderRule.getOrderRuleItems().get(0).getOutOfOrderOccurrences());
        Assert.assertEquals(1, orderRule.getOrderRuleItems().get(3).getOutOfOrderOccurrences());

        orderRule.moveOffset(one);
        Assert.assertEquals(1, orderRule.getOffset());
        orderRule.moveOffset(one);
        Assert.assertEquals(3, orderRule.getOffset());

    }

    private OrderRuleItem createOrderRuleItem(UUID userId, int orderId, int occurences) {
        OrderRuleItem item = new OrderRuleItem();
        item.setOutOfOrderOccurrences(occurences);
        item.setUserId(userId);
        item.setOrderId(orderId);
        return item;
    }

}
