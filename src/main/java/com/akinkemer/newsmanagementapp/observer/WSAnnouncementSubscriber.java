package com.akinkemer.newsmanagementapp.observer;

import com.akinkemer.newsmanagementapp.domain.app.Announcement;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WSAnnouncementSubscriber implements IAnnouncementObserver{
    private final SimpMessagingTemplate messagingTemplate;
    @Override
    public void update(Announcement announcement) {
        messagingTemplate.convertAndSend("/announcementBroker", announcement);
    }
}
