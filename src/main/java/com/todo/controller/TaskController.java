package com.todo.controller;

import com.todo.entity.Task;
import com.todo.exceptions.TaskNotFoundException;
import com.todo.service.ITaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for work with tasks.
 *
 * @author Igor Holiak
 */
@RestController
@RequestMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskController {

    private final ITaskService taskService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getAll() {
        return ResponseEntity.ok(taskService.getAll());
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
    public ResponseEntity remove(@PathVariable("id") final Long id) {
        taskService.remove(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<Task> getById(@PathVariable("id") final Long id) throws TaskNotFoundException {
        Task task = taskService.getById(id);

        return ResponseEntity.ok(task);
    }

    @RequestMapping(value = "/change-status/{id}", method = RequestMethod.POST)
    public ResponseEntity<Task> changeStatus(@PathVariable("id") final Long id) throws TaskNotFoundException {
        return ResponseEntity.ok(taskService.changeStatus(id));
    }

    @RequestMapping(value = "/add/", method = RequestMethod.POST)
    public ResponseEntity<Task> add(@RequestBody final Task task) {
        return ResponseEntity.ok(taskService.add(task));
    }

    @RequestMapping(value = "/update/", method = RequestMethod.POST)
    public ResponseEntity update(@RequestBody final Task task) {
        return ResponseEntity.ok(taskService.update(task));
    }

}