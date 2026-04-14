package com.studyplanner.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.studyplanner.model.StudyTask;

@Service
public class InMemoryStudyTaskService implements StudyTaskService {

    private final AtomicLong idGenerator = new AtomicLong(0);
    private final List<StudyTask> tasks = new ArrayList<>();

    @Override
    public List<StudyTask> findAll() {
        return tasks.stream()
            .sorted(Comparator.comparing(StudyTask::getDueDate, Comparator.nullsLast(Comparator.naturalOrder())))
            .toList();
    }

    @Override
    public synchronized StudyTask save(StudyTask task) {
        task.setId(idGenerator.incrementAndGet());
        tasks.add(task);
        return task;
    }
}
