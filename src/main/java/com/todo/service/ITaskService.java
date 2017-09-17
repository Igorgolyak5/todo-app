package com.todo.service;

import com.todo.entity.Task;
import com.todo.exceptions.TaskNotFoundException;

import java.util.List;

/**
 * Interface provide methods for business logic of tasks.
 *
 * @author Igor Holiak
 */
public interface ITaskService {

    /**
     * Method needed for getting all tasks.
     *
     * @return tasks
     */
    List<Task> getAll();

    /**
     * Method removing task.
     *
     * @param id of task in database.
     */
    void remove(final Long id);

    /**
     * Method needed for getting task by identifier.
     *
     * @param id identifier
     * @return task
     */
    Task getById(final Long id) throws TaskNotFoundException;

    /**
     * Method for change status in task.
     *
     * @param id identifier
     */
    Task changeStatus(final Long id) throws TaskNotFoundException;

    /**
     * Method needed for add task to database.
     *
     * @param task task
     */
    Task add(final Task task);


    /**
     * Method needed for update task.
     *
     * @param task task
     */
    Task update(final Task task);
}
