package com.akinkemer.newsmanagementapp.observer;

import com.akinkemer.newsmanagementapp.domain.app.Announcement;

public interface IAnnouncementObserver {
    public void update(Announcement announcement);
}
