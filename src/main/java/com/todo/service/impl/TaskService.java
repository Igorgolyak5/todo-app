package com.todo.service.impl;

import com.todo.entity.Task;
import com.todo.exceptions.TaskNotFoundException;
import com.todo.repository.TaskRepository;
import com.todo.service.ITaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Igor Holiak
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskService implements ITaskService {

    private final TaskRepository taskRepository;

    /**
     * @see ITaskService#getAll
     */
    @Transactional(readOnly = true)
    @Override
    public List<Task> getAll() {
        return taskRepository.getAllSortDate().collect(Collectors.toList());
    }

    /**
     * @see ITaskService#remove
     */
    @Override
    public void remove(final Long id) {
        taskRepository.delete(id);
    }

    /**
     * @see ITaskService#getById
     */
    @Override
    public Task getById(final Long id) throws TaskNotFoundException {
        Task task = taskRepository.findOne(id);

        if(task == null) throw new TaskNotFoundException();

        return task;
    }

    /**
     * @see ITaskService#changeStatus
     */
    @Override
    public Task changeStatus(final Long id) throws TaskNotFoundException {
        Task task = taskRepository.findOne(id);

        if (task == null) throw new TaskNotFoundException();

        task.setStatus(!task.getStatus());
        return taskRepository.save(task);
    }

    /**
     * @see ITaskService#add
     */
    @Override
    public Task add(final Task task) {
        return taskRepository.save(task);
    }

    /**
     * @see ITaskService#update
     */
    @Override
    public Task update(final Task task) {
        taskRepository.update(task.getId(), task.getName(), task.getDate());
        return task;
    }
}
