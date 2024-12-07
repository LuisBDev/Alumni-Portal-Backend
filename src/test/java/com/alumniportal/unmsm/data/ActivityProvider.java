package com.alumniportal.unmsm.data;

import com.alumniportal.unmsm.model.Activity;

import java.time.LocalDate;
import java.util.List;

public class ActivityProvider {

    public static List<Activity> activityList() {
        return List.of(
                Activity.builder()
                        .id(1L)
                        .title("Taller de Java")
                        .description("Taller de Java")
                        .eventType("workshop")
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now())
                        .location("Lima")
                        .url("https://www.google.com")
                        .enrollable(true)
                        .createdAt(LocalDate.now())
                        .updatedAt(LocalDate.now())
                        .build(),
                Activity.builder()
                        .id(2L)
                        .title("Conferencia de Java")
                        .description("Conferencia de Java")
                        .eventType("conference")
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now())
                        .location("Lima")
                        .url("https://www.google.com")
                        .enrollable(true)
                        .createdAt(LocalDate.now())
                        .updatedAt(LocalDate.now())
                        .build(),
                Activity.builder()
                        .id(3L)
                        .title("Seminario de Java")
                        .description("Seminario de Java")
                        .eventType("seminar")
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now())
                        .location("Lima")
                        .url("https://www.google.com")
                        .enrollable(true)
                        .createdAt(LocalDate.now())
                        .updatedAt(LocalDate.now())
                        .build(),
                Activity.builder()
                        .id(4L)
                        .title("Post de Java")
                        .description("Post de Java")
                        .eventType("post")
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now())
                        .location("Lima")
                        .url("https://www.google.com")
                        .enrollable(true)
                        .createdAt(LocalDate.now())
                        .updatedAt(LocalDate.now())
                        .build(),
                Activity.builder()
                        .id(5L)
                        .title("Otro de Java")
                        .description("Otro de Java")
                        .eventType("other")
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now())
                        .location("Lima")
                        .url("https://www.google.com")
                        .enrollable(true)
                        .createdAt(LocalDate.now())
                        .updatedAt(LocalDate.now())
                        .build()
        );

    }

    public static Activity activityOne() {
        return Activity.builder()
                .id(1L)
                .title("Charla magistral de Java")
                .description("Taller de Java")
                .eventType("workshop")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .location("Lima")
                .url("https://www.google.com")
                .enrollable(true)
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .build();
    }

}
