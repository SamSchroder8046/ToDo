package com.todo.task;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
@Transactional(readOnly = true)
public class TaskService {

    private final TaskItemRepository taskItemRepository;
    private final TaskDetailsRepository taskDetailsRepository;

    public TaskService(TaskItemRepository taskItemRepository, TaskDetailsRepository taskDetailsRepository) {
        this.taskItemRepository = taskItemRepository;
        this.taskDetailsRepository = taskDetailsRepository;
    }

    public List<TaskItem> findItems(String searchTerm, Pageable pageable) {
        return taskItemRepository.findByDescriptionContainingIgnoreCase(searchTerm, pageable).getContent();
    }

    public Optional<TaskDetails> findDetailsById(Long id) {
        return taskDetailsRepository.findById(id);
    }

    @Transactional
    public TaskDetails save(@Valid TaskDetails taskDetails) {
        return taskDetailsRepository.save(taskDetails);
    }
}
