package com.akinkemer.newsmanagementapp.service;

import com.akinkemer.newsmanagementapp.domain.app.Announcement;
import com.akinkemer.newsmanagementapp.domain.app.News;
import com.akinkemer.newsmanagementapp.utilities.request.AnnouncementSaveRequest;
import com.akinkemer.newsmanagementapp.utilities.request.NewsSaveRequest;
import com.akinkemer.newsmanagementapp.utilities.result.DataResult;
import com.akinkemer.newsmanagementapp.utilities.result.Result;

import java.util.List;

public interface EventService {
    Result saveNews(NewsSaveRequest request);
    Result saveAnnouncement(AnnouncementSaveRequest request);
    DataResult<List<Announcement>> getAllAnnouncements();
    DataResult<List<News>> getAllNews();
    }
