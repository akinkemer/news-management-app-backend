package com.akinkemer.newsmanagementapp.controllers;

import com.akinkemer.newsmanagementapp.domain.app.Announcement;
import com.akinkemer.newsmanagementapp.domain.app.News;
import com.akinkemer.newsmanagementapp.service.EventService;
import com.akinkemer.newsmanagementapp.utilities.request.AnnouncementSaveRequest;
import com.akinkemer.newsmanagementapp.utilities.request.NewsSaveRequest;
import com.akinkemer.newsmanagementapp.utilities.result.DataResult;
import com.akinkemer.newsmanagementapp.utilities.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1")
@RequiredArgsConstructor

public class EventController {
    private final EventService eventService;

    @PostMapping("/news/save")
    public DataResult<News> saveNews(@RequestBody NewsSaveRequest request) {
        return eventService.saveNews(request);
    }

    @PostMapping("/announcement/save")
    public DataResult<Announcement> saveNews(@RequestBody AnnouncementSaveRequest request) {
        return eventService.saveAnnouncement(request);
    }

    @GetMapping("/announcement/getAll")
    public DataResult<List<Announcement>> getAllAnnouncements() {
        return eventService.getAllAnnouncements();
    }

    @GetMapping("/news/getAll")
    public DataResult<List<News>> getAllNews() {
        return eventService.getAllNews();
    }

    @DeleteMapping(path = "/announcement/delete/{announcementId}")
    public Result deleteAnnouncement(@PathVariable("announcementId") Long id) {
        return eventService.deleteAnnouncement(id);
    }

    @DeleteMapping(path = "/news/delete/{newsId}")
    public Result deleteNews(@PathVariable("newsId") Long id) {
        return eventService.deleteNews(id);
    }

    @PutMapping(path = "/news/update/{id}")
    public void updateNews(
            @PathVariable("id") Long id,
            @RequestBody  News news
            ) {
        eventService.updateNews(id,news);
    }

    @PutMapping(path = "/announcement/update/{id}")
    public void updateAnnouncement(
            @PathVariable("id") Long id,
            @RequestBody Announcement announcement
            ) {
        eventService.updateAnnouncement(id,announcement);
    }
}
