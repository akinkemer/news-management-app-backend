package com.akinkemer.newsmanagementapp.utilities.request;

import lombok.Data;

import java.time.LocalDateTime;
@Data

public class NewsSaveRequest {
    private String subject;
    private String content;
    private LocalDateTime invalidAt;
    private String link;
}
