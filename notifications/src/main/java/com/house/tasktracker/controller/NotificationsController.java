package com.house.tasktracker.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.house.tasktracker.application.FirebaseNotificationService;
import com.house.tasktracker.domain.UserToken;
import com.house.tasktracker.domain.UserTokensRepository;
import com.house.tasktracker.infrastructure.TasksRulesClient;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class NotificationsController {

    private UserTokensRepository userTokensRepository;
    private FirebaseNotificationService notificationService;
    private TasksRulesClient rulesClient;

    @PostMapping("/notifications")
    public void addRule(@RequestBody SendNotificationCommand command) throws FirebaseMessagingException {
        TasksRulesClient.OrderRuleDto orderRule = rulesClient.getOrderRule(command.getRuleId());
        List<UUID> recipients = orderRule.getOrderRuleItems()
                                         .stream()
                                         .map(i -> i.getUserId())
                                         .filter(e -> !command.getSenderUser().equals(e))
                                         .collect(Collectors.toList());
        notificationService.sendNotification(recipients);
    }

    @PostMapping("/notifications/tokens")
    public void addToken(@RequestBody RegisterTokenCommand command) {
        userTokensRepository.save(new UserToken(command.getUserId(), command.getToken()));
    }

}
