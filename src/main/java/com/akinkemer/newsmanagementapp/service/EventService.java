package com.akinkemer.newsmanagementapp.service;

import com.akinkemer.newsmanagementapp.domain.app.Announcement;
import com.akinkemer.newsmanagementapp.domain.app.News;
import com.akinkemer.newsmanagementapp.utilities.results.DataResult;
import com.akinkemer.newsmanagementapp.utilities.results.Result;

import java.util.List;

public interface EventService {
    Result saveNews(News news);
    Result saveAnnouncement(Announcement announcement);
    DataResult<List<Announcement>> getAllAnnouncements();

    }
