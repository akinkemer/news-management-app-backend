package com.akinkemer.newsmanagementapp.domain.app;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "event")
public class News extends Event {
    public News(String subject,
                String content,
                LocalDate invalidAt,
                String link,
                LocalDateTime createdAt) {
        setSubject(subject);
        setContent(content);
        setInvalidAt(invalidAt);
        setCreatedAt(createdAt);
        this.link = link;
    }

    private String link;
}
