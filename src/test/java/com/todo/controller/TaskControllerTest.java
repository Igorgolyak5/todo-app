package com.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.SpringApp;
import com.todo.entity.Task;
import com.todo.service.ITaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for task controller.
 *
 * @author Igor Holiak
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = TaskController.class, secure = false)
@ContextConfiguration(classes = {SpringApp.class})
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ITaskService taskService;

    @Test
    public final void getAll_ReturnContent_IsValid() throws Exception {
        final List<Task> tasks = new ArrayList<Task>(){
            {
                add(new Task("Read book", false, null));
                add(new Task("Create app", false, null));
            }
        };

        when(taskService.getAll()).thenReturn(tasks);

        MvcResult result = mockMvc.perform(get("/tasks/list")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        String expected = "[{\"id\":null,\"name\":\"Read book\",\"status\":false,\"date\":null},{\"id\":null,\"name\":\"Create app\",\"status\":false,\"date\":null}]";
        String notExpected = "{\"id\":1,\"name\":\"Play game\",\"status\":true,\"date\":null}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
        JSONAssert.assertNotEquals(notExpected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public final void getById_ReturnContent_IsValid() throws Exception {
        Task first = new Task("Read book", false, null);

        when(taskService.getById(1L)).thenReturn(first);

        MvcResult result = mockMvc.perform(get("/tasks/get/1/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String expected = "{\"id\":null,\"name\":\"Read book\",\"status\":false,\"date\":null}";
        String notExpected = "{\"id\":20,\"name\":\"Create own framework\",\"status\":true,\"date\":null}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
        JSONAssert.assertNotEquals(notExpected, result.getResponse().getContentAsString(), false);
    }


    @Test
    public final void add_ReturnContent_IsValid() throws Exception {
        final Task task = new Task("Read book", false, null);

        when(taskService.add(task)).thenReturn(task);

        MvcResult result = mockMvc.perform(post("/tasks/add/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(task)))
                .andExpect(status().isOk()).andReturn();

        String expected = "{\"id\":null,\"name\":\"Read book\",\"status\":false,\"date\":null}";
        String notExpected = "{\"id\":2,\"name\":\"Became engineer\",\"status\":false,\"date\":\"2017-02-02\"}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
        JSONAssert.assertNotEquals(notExpected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public final void changeStatus_Update_IsSuccess() throws Exception {
        Task task = new Task("Read book", false, null);

        when(taskService.changeStatus(1L)).thenReturn(task);

        MvcResult result = mockMvc.perform(post("/tasks/change-status/1/")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"name\":\"Read book\",\"status\":false,\"date\":null}"))
                .andExpect(status().isOk()).andReturn();

        String expected = "{\"id\": null, \"name\":\"Read book\",\"status\":false,\"date\":null}";
        String notExpected = "{\"id\":20,\"name\":\"Watch tv\",\"status\":true,\"date\":null}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
        JSONAssert.assertNotEquals(notExpected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public final void update_ReturnContent_IsValid() throws Exception {
        Task task = new Task("Grow oak", true, new Date(1505595127471L));

        when(taskService.update(task)).thenReturn(task);

        MvcResult result = mockMvc.perform(post("/tasks/update/")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(task)))
                .andExpect(status().isOk()).andReturn();

        String expected = "{\"id\": null, \"name\":\"Grow oak\",\"status\":true,\"date\":1505595127471}";
        String notExpected = "{\"id\": null, \"name\":\"Read book\",\"status\":false,\"date\":null}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
        JSONAssert.assertNotEquals(notExpected, result.getResponse().getContentAsString(), false);
    }
}
