package com.akinkemer.newsmanagementapp.observer;

import com.akinkemer.newsmanagementapp.domain.app.Announcement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LOGAnnouncementSubscriber implements IAnnouncementObserver {
    @Override
    public void update(Announcement announcement) {
        log.info("Announcement subject:{}, content:{}",
                announcement.getSubject(),
                announcement.getContent());
    }
}
