package com.studyplanner.model;

import java.time.LocalDate;

public class StudyTask {
    private Long id;
    private String title;
    private String subject;
    private LocalDate dueDate;
    private boolean completed;

    public StudyTask() {
    }

    public StudyTask(Long id, String title, String subject, LocalDate dueDate, boolean completed) {
        this.id = id;
        this.title = title;
        this.subject = subject;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
