package com.house.tasktracker.application;

import com.house.taskstracker.application.OrderRuleService;
import com.house.taskstracker.controller.AddOrderRuleCommand;
import com.house.taskstracker.controller.DtoTransformer;
import com.house.taskstracker.controller.OrderRuleDto;
import com.house.taskstracker.domain.OrderRule;
import com.house.taskstracker.domain.OrderRuleRepository;
import com.house.taskstracker.infrastructure.OrderOffsetChangedSource;
import com.house.tasktracker.DataSourceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DataSourceConfig.class, OrderRuleService.class, DtoTransformer.class})
public class OrderRuleServiceTest {

    private final UUID zero = UUID.fromString("00000000-0000-0000-0000-000000000000");
    private final UUID one = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private final UUID two = UUID.fromString("00000000-0000-0000-0000-000000000002");
    private final UUID three = UUID.fromString("00000000-0000-0000-0000-000000000003");
    private final UUID groupId = UUID.fromString("10000000-0000-0000-0000-000000000000");

    @InjectMocks
    @Autowired
    private OrderRuleService orderRuleService;

    @Autowired
    private OrderRuleRepository orderRuleRepository;

    @MockBean
    private OrderOffsetChangedSource orderOffsetChangedSource;

    @Test
    public void testAddRule() {
        AddOrderRuleCommand command = createAddOrderRuleCommand();
        OrderRuleDto order = orderRuleService.addOrderRule(command);
    }

    @Test
    public void testGetRule() {
        AddOrderRuleCommand command = createAddOrderRuleCommand();
        OrderRuleDto order = orderRuleService.addOrderRule(command);
        command.getOrderRuleItems().remove(0);
        orderRuleService.addOrderRule(command);
        List<OrderRule> rules = orderRuleRepository.findByUserId(zero);
    }

    public AddOrderRuleCommand createAddOrderRuleCommand() {
        List<AddOrderRuleCommand.OrderRuleItemDto> orderRuleItems = new ArrayList<>();
        orderRuleItems.add(createOrderRuleItem(zero, 0));
        orderRuleItems.add(createOrderRuleItem(one, 1));
        orderRuleItems.add(createOrderRuleItem(two, 2));
        orderRuleItems.add(createOrderRuleItem(three, 3));

        AddOrderRuleCommand orderRule = new AddOrderRuleCommand();
        orderRule.setGroupId(groupId);
        orderRule.setName("group1");
        orderRule.setOrderRuleItems(orderRuleItems);

        return orderRule;
    }

    private AddOrderRuleCommand.OrderRuleItemDto createOrderRuleItem(UUID userId, int orderId) {
        AddOrderRuleCommand.OrderRuleItemDto item = new AddOrderRuleCommand.OrderRuleItemDto();
        item.setUserId(userId);
        item.setOrderId(orderId);
        return item;
    }

}
