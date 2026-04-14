package com.studyplanner.controller;

import com.studyplanner.model.StudyTask;
import com.studyplanner.service.ConsoleLogicMigrationService;
import com.studyplanner.service.StudyTaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class StudyPlannerController {

    private final StudyTaskService studyTaskService;
    private final ConsoleLogicMigrationService migrationService;

    public StudyPlannerController(StudyTaskService studyTaskService,
                                  ConsoleLogicMigrationService migrationService) {
        this.studyTaskService = studyTaskService;
        this.migrationService = migrationService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<StudyTask> prioritized = migrationService.prioritizeTasks(studyTaskService.findAll());
        model.addAttribute("tasks", prioritized);
        model.addAttribute("taskForm", new TaskForm());
        return "dashboard";
    }

    @PostMapping("/tasks")
    public String createTask(@Valid @ModelAttribute("taskForm") TaskForm taskForm,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tasks", migrationService.prioritizeTasks(studyTaskService.findAll()));
            return "dashboard";
        }

        StudyTask task = new StudyTask();
        task.setTitle(taskForm.getTitle());
        task.setSubject(taskForm.getSubject());
        task.setDueDate(taskForm.getDueDate());
        task.setCompleted(false);
        studyTaskService.save(task);

        return "redirect:/dashboard";
    }

    public static class TaskForm {
        @NotBlank(message = "Title is required")
        private String title;

        @NotBlank(message = "Subject is required")
        private String subject;

        private LocalDate dueDate;

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
    }
}
