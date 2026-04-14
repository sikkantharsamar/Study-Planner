package com.studyplanner.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.studyplanner.model.StudyTask;

@Service
public class ConsoleLogicMigrationService {

    // Move ranking, recommendation, or scheduling logic from your console classes into this service.
    public List<StudyTask> prioritizeTasks(List<StudyTask> tasks) {
        return tasks.stream()
            .sorted((left, right) -> {
                if (left.getDueDate() == null && right.getDueDate() == null) {
                    return 0;
                }
                if (left.getDueDate() == null) {
                    return 1;
                }
                if (right.getDueDate() == null) {
                    return -1;
                }
                return left.getDueDate().compareTo(right.getDueDate());
            })
            .toList();
    }
}
