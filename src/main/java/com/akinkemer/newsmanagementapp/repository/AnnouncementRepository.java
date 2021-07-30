package com.akinkemer.newsmanagementapp.repository;

import com.akinkemer.newsmanagementapp.domain.app.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement,Long> {
}
