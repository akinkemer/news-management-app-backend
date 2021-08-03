package com.akinkemer.newsmanagementapp.observer;

import com.akinkemer.newsmanagementapp.domain.app.Announcement;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class AnnouncementPublisher implements IAnnouncementSubject {

    private List<IAnnouncementObserver> subscribers = new ArrayList<>();

    @Override
    public void attach(IAnnouncementObserver o) {
        subscribers.add(o);
    }

    @Override
    public void detach(IAnnouncementObserver o) {
        subscribers.remove(o);
    }

    @Override
    public void notify(Announcement announcement) {
        subscribers
                .stream()
                .forEach(subscriber -> subscriber.update(announcement));
    }
}
