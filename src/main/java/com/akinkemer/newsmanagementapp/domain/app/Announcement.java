package com.akinkemer.newsmanagementapp.domain.app;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "event")
public class Announcement extends Event {
    private String imageLink;
    public Announcement(String subject,
                        String content,
                        LocalDate invalidAt,
                        String imageLink,
                        LocalDateTime createdAt) {
        setSubject(subject);
        setContent(content);
        setInvalidAt(invalidAt);
        setCreatedAt(createdAt);
        this.imageLink=imageLink;
    }
}
