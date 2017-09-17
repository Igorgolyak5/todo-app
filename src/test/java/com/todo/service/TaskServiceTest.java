package com.todo.service;

import com.todo.SpringApp;
import com.todo.entity.Task;
import com.todo.exceptions.TaskNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;


/**
 * @author Igor Holiak
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringApp.class)
public class TaskServiceTest {

    @Autowired
    private ITaskService taskService;

    @Test
    @DirtiesContext
    public final void getAll_Search_IsValid() {
        final List<Task> tasks = taskService.getAll();
        Assert.assertTrue(tasks.size() > 0);
    }

    @Test
    @DirtiesContext
    public final void getById_Search_IsValid() throws TaskNotFoundException {
        final Task task = taskService.getById(1L);
        Assert.assertTrue(task != null);
        Assert.assertEquals("Read book", task.getName());
        Assert.assertEquals(false, task.getStatus());
    }

    @Test(expected = TaskNotFoundException.class)
    @DirtiesContext
    public final void getById_SearchByNotExistId_ThrowException() throws TaskNotFoundException {
        taskService.getById(10L);
    }

    @Test
    @DirtiesContext
    public final void remove_Size_IsDecrease() {
        Assert.assertTrue(taskService.getAll().size() == 3);
        taskService.remove(1L);
        Assert.assertTrue(taskService.getAll().size() == 2);
    }

    @Test
    @DirtiesContext
    public final void changeStatus_Status_IsNotEqual() throws TaskNotFoundException {
       final Task task = taskService.getById(1L);
       final Task updatedTask = taskService.changeStatus(1L);
       Assert.assertNotEquals(task.getStatus(), updatedTask.getStatus());
    }

    @Test
    @DirtiesContext
    public final void add_SizeAndName_IsEqual() throws TaskNotFoundException {
        final Task task = new Task("Feed the dog", true, new Date());
        taskService.add(task);
        Assert.assertEquals(4, taskService.getAll().size());
        Assert.assertEquals("Feed the dog", taskService.getById(4L).getName());
    }

    @Test
    @DirtiesContext
    public final void update_ChangeNameUpdate_IsTrue() throws TaskNotFoundException {
        final Task task = taskService.getById(1L);
        task.setName("Changed name");
        taskService.update(task);
        final Task updatedTask = taskService.getById(1L);
        Assert.assertEquals("Changed name", updatedTask.getName());
    }
}
