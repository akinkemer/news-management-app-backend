package com.akinkemer.newsmanagementapp.domain.app;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "event")
public class Announcement extends Event {
}
