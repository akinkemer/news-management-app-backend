package com.akinkemer.newsmanagementapp.observer;

import com.akinkemer.newsmanagementapp.domain.app.Announcement;

public interface IAnnouncementSubject {
    public void attach(IAnnouncementObserver o);
    public void detach(IAnnouncementObserver o);
    public void notify(Announcement announcement);
}
