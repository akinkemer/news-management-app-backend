package com.akinkemer.newsmanagementapp.utilities.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AnnouncementSaveRequest {
    private String subject;
    private String content;
    private LocalDate invalidAt;
    private String imageLink;
}
