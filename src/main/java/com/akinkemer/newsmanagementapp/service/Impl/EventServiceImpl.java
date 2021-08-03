package com.akinkemer.newsmanagementapp.service.Impl;

import com.akinkemer.newsmanagementapp.domain.app.Announcement;
import com.akinkemer.newsmanagementapp.domain.app.News;
import com.akinkemer.newsmanagementapp.repository.AnnouncementRepository;
import com.akinkemer.newsmanagementapp.repository.NewsRepository;
import com.akinkemer.newsmanagementapp.service.EventService;
import com.akinkemer.newsmanagementapp.utilities.request.AnnouncementSaveRequest;
import com.akinkemer.newsmanagementapp.utilities.request.NewsSaveRequest;
import com.akinkemer.newsmanagementapp.utilities.result.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EventServiceImpl implements EventService {
    private final NewsRepository newsRepository;
    private final AnnouncementRepository announcementRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public DataResult<News> saveNews(NewsSaveRequest request) {
        News news = new News(
                request.getSubject(),
                request.getContent(),
                request.getInvalidAt(),
                request.getLink(),
                LocalDateTime.now());
        newsRepository.save(news);
        return new SuccessDataResult(news, "News saved successfully");
    }

    @Override
    public DataResult<Announcement> saveAnnouncement(AnnouncementSaveRequest request) {
        Announcement announcement = new Announcement(
                request.getSubject(),
                request.getContent(),
                request.getInvalidAt(),
                request.getImageLink(),
                LocalDateTime.now());
        announcementRepository.save(announcement);
        messagingTemplate.convertAndSend("/announcementBroker", announcement);
        return new SuccessDataResult<>(announcement, "Announcement saved successfully");
    }

    @Override
    public DataResult<List<Announcement>> getAllAnnouncements() {
        return new SuccessDataResult<>(announcementRepository.findAll());
    }

    @Override
    public DataResult<List<News>> getAllNews() {
        return new SuccessDataResult<>(newsRepository.findAll());
    }

    @Override
    public Result deleteAnnouncement(Long id) {
        boolean exist = announcementRepository.existsById(id);
        if (!exist) {
            return new ErrorResult("Announcement with id:" + id + " does not exist");
        } else {
            announcementRepository.deleteById(id);
            return new SuccessResult("Announcement with id:" + id + " deleted");
        }

    }

    @Override
    public Result deleteNews(Long id) {
        boolean exist = newsRepository.existsById(id);
        if (!exist) {
            return new ErrorResult("News with id:" + id + " does not exist");
        } else {
            newsRepository.deleteById(id);
            return new SuccessResult("News with id:" + id + " deleted");
        }
    }

    @Override
    @Transactional
    public Result updateNews(
            Long id,
            News news) {
        News saved = newsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("There is no news with id:"+id));
        if (news.getSubject() != null
                && news.getSubject().length() > 0
                && !Objects.equals(news.getSubject(), saved.getSubject())) {
            saved.setSubject(news.getSubject());
        }
        if (news.getContent() != null
                && news.getContent().length() > 0
                && !Objects.equals(news.getContent(), saved.getContent())) {
            saved.setContent(news.getContent());
        }
        if (news.getLink() != null
                && news.getLink().length() > 0
                && !Objects.equals(news.getLink(), saved.getLink())) {
            saved.setLink(news.getLink());
        }
        if (news.getInvalidAt() != null
                && !Objects.equals(news.getInvalidAt(), saved.getInvalidAt())) {
            saved.setInvalidAt(news.getInvalidAt());
        }
        return new SuccessResult("News updated successfully");
    }

    @Override
    @Transactional
    public Result updateAnnouncement(
            Long id,
            Announcement announcement) {
        Announcement saved = announcementRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("There is no announcement with id:" + id)
        );

        if (announcement.getSubject() != null
                && announcement.getSubject().length() > 0
                && !Objects.equals(announcement.getSubject(), saved.getSubject())) {
            saved.setSubject(announcement.getSubject());
        }
        if (announcement.getContent() != null
                && announcement.getContent().length() > 0
                && !Objects.equals(announcement.getContent(), saved.getContent())) {
            saved.setContent(announcement.getContent());
        }
        if (announcement.getImageLink() != null
                && announcement.getImageLink().length() > 0
                && !Objects.equals(announcement.getImageLink(), saved.getImageLink())) {
            saved.setImageLink(announcement.getImageLink());
        }
        if (announcement.getInvalidAt() != null
                && !Objects.equals(announcement.getInvalidAt(), saved.getInvalidAt())) {
            saved.setInvalidAt(announcement.getInvalidAt());
        }
        return new SuccessResult("Announcement updated successfully");
    }

}
