package com.akinkemer.newsmanagementapp.service;

import com.akinkemer.newsmanagementapp.domain.app.Announcement;
import com.akinkemer.newsmanagementapp.domain.app.News;
import com.akinkemer.newsmanagementapp.repository.AnnouncementRepository;
import com.akinkemer.newsmanagementapp.repository.NewsRepository;
import com.akinkemer.newsmanagementapp.utilities.results.DataResult;
import com.akinkemer.newsmanagementapp.utilities.results.Result;
import com.akinkemer.newsmanagementapp.utilities.results.SuccessDataResult;
import com.akinkemer.newsmanagementapp.utilities.results.SuccessResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EventServiceImpl implements EventService {
    private final NewsRepository newsRepository;
    private final AnnouncementRepository announcementRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public Result saveNews(News news) {
        newsRepository.save(news);
        return new SuccessResult("News saved successfully");
    }

    @Override
    public Result saveAnnouncement(Announcement announcement) {
        announcementRepository.save(announcement);
        messagingTemplate.convertAndSend("/announcementBroker", announcement);
        return new SuccessResult("Announcement saved successfully");
    }

    @Override
    public DataResult<List<Announcement>> getAllAnnouncements() {
        return new SuccessDataResult<List<Announcement>>(announcementRepository.findAll());
    }
}
