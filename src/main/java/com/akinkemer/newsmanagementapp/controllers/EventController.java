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
    public Result saveNews(@RequestBody NewsSaveRequest request) {
        return eventService.saveNews(request);
    }

    @PostMapping("/announcement/save")
    public Result saveNews(@RequestBody AnnouncementSaveRequest request) {
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



}
