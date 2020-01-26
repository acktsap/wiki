package acktsap.sample.demowebmvc.requestmapping.task;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest
public class TaskControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  public void testGetTasks() throws Exception {
    mockMvc.perform(get("/tasks"))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().string("getTasks"));
  }

  @Test
  public void testGetTasksOnSingleItem() throws Exception {
    mockMvc.perform(get("/tasks/1"))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().string("getTasksWithId"));
    mockMvc.perform(get("/tasks/2"))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().string("getTasksWithId"));
    mockMvc.perform(get("/tasks/3"))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().string("getTasksWithId"));
  }

  @Test
  public void testPostTasks() throws Exception {
    mockMvc.perform(post("/tasks")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().string("postTasks"));
  }

  @Test
  public void testDeleteTasksOnSingleItem() throws Exception {
    mockMvc.perform(delete("/tasks/1"))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().string("deleteTasks"));
    mockMvc.perform(delete("/tasks/2"))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().string("deleteTasks"));
    mockMvc.perform(delete("/tasks/3"))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().string("deleteTasks"));
  }

  @Test
  public void testPutTasksOnSingleItem() throws Exception {
    mockMvc.perform(put("/tasks/1")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().string("putTasks"));
    mockMvc.perform(put("/tasks/2")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().string("putTasks"));
  }

}
