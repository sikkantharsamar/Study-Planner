package com.studyplanner.service;

import java.util.List;

import com.studyplanner.model.StudyTask;

public interface StudyTaskService {
    List<StudyTask> findAll();
    StudyTask save(StudyTask task);
}
