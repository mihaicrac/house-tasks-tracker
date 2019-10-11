package com.house.tasktracker.application;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.house.tasktracker.domain.UserToken;
import com.house.tasktracker.domain.UserTokensRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
@Service
public class FirebaseNotificationService {

    private UserTokensRepository userTokensRepository;

    public void sendNotification(List<UUID> users) throws FirebaseMessagingException {
        List<UserToken> userTokens = userTokensRepository.findByIdIn(users);
        List<Message> messages = createMessages(userTokens);
        BatchResponse response = FirebaseMessaging.getInstance().sendAll(messages);
        // See the BatchResponse reference documentation
        // for the contents of response.
        System.out.println(response.getSuccessCount() + " messages were sent successfully");
    }

    private List<Message> createMessages(List<UserToken> userTokens) {
        return userTokens.stream()
                         .map(e -> Message.builder()
                                          .setNotification(new Notification("Price drop", "5% off all electronics"))
                                          .setToken(e.getToken())
                                          .build())
                         .collect(Collectors.toList());
    }

}
