package com.akinkemer.newsmanagementapp.service;

import com.akinkemer.newsmanagementapp.domain.app.Announcement;
import com.akinkemer.newsmanagementapp.domain.app.News;
import com.akinkemer.newsmanagementapp.repository.AnnouncementRepository;
import com.akinkemer.newsmanagementapp.repository.NewsRepository;
import com.akinkemer.newsmanagementapp.utilities.request.AnnouncementSaveRequest;
import com.akinkemer.newsmanagementapp.utilities.request.NewsSaveRequest;
import com.akinkemer.newsmanagementapp.utilities.result.DataResult;
import com.akinkemer.newsmanagementapp.utilities.result.Result;
import com.akinkemer.newsmanagementapp.utilities.result.SuccessDataResult;
import com.akinkemer.newsmanagementapp.utilities.result.SuccessResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public Result saveNews(NewsSaveRequest request) {
        newsRepository.save(new News(
                request.getSubject(),
                request.getContent(),
                request.getInvalidAt(),
                request.getLink(),
                LocalDateTime.now()));
        return new SuccessResult("News saved successfully");
    }

    @Override
    public Result saveAnnouncement(AnnouncementSaveRequest request) {
        Announcement announcement=new Announcement(
                request.getSubject(),
                request.getContent(),
                request.getInvalidAt(),
                request.getImageLink(),
                LocalDateTime.now());
        announcementRepository.save(announcement);
        messagingTemplate.convertAndSend("/announcementBroker", announcement);
        return new SuccessResult("Announcement saved successfully");
    }

    @Override
    public DataResult<List<Announcement>> getAllAnnouncements() {
        return new SuccessDataResult<>(announcementRepository.findAll());
    }

    @Override
    public DataResult<List<News>> getAllNews() {
        return new SuccessDataResult<>(newsRepository.findAll());
    }
}
