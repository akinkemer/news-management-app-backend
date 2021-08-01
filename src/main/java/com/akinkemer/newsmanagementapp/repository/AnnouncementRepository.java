package com.akinkemer.newsmanagementapp.repository;

import com.akinkemer.newsmanagementapp.domain.app.Announcement;
import com.akinkemer.newsmanagementapp.domain.app.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement,Long> {
    @Query("SELECT n FROM Announcement n WHERE n.imageLink IS NOT NULL")
    List<Announcement> findAll();
}
