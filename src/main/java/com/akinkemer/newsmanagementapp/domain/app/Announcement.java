package com.akinkemer.newsmanagementapp.domain.app;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "event")
public class Announcement extends Event {
    private String imageLink;
    public Announcement(String subject,
                        String content,
                        LocalDateTime invalidAt,
                        String imageLink,
                        LocalDateTime createdAt) {
        setSubject(subject);
        setContent(content);
        setInvalidAt(invalidAt);
        setCreatedAt(createdAt);
        this.imageLink=imageLink;
    }
}
