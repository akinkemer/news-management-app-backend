package com.akinkemer.newsmanagementapp.service;

import com.akinkemer.newsmanagementapp.domain.app.Announcement;
import com.akinkemer.newsmanagementapp.domain.app.News;
import com.akinkemer.newsmanagementapp.utilities.request.AnnouncementSaveRequest;
import com.akinkemer.newsmanagementapp.utilities.request.NewsSaveRequest;
import com.akinkemer.newsmanagementapp.utilities.result.DataResult;

import java.util.List;

public interface EventService {
    DataResult<News> saveNews(NewsSaveRequest request);
    DataResult<Announcement> saveAnnouncement(AnnouncementSaveRequest request);
    DataResult<List<Announcement>> getAllAnnouncements();
    DataResult<List<News>> getAllNews();
    }
