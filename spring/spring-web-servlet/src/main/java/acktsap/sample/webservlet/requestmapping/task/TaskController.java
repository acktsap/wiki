package acktsap.sample.webservlet.requestmapping.task;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TaskController {

  @GetMapping("/tasks")
  @ResponseBody
  public String getTasks() {
    return "getTasks";
  }

  @GetMapping("/tasks/{id}")
  @ResponseBody
  public String getTasksWithId(@PathVariable String id) {
    return "getTasksWithId";
  }

  @PostMapping(value = "/tasks", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public String postTasks() {
    return "postTasks";
  }

  @DeleteMapping("/tasks/{id}")
  @ResponseBody
  public String deleteTasks(@PathVariable String id) {
    return "deleteTasks";
  }

  @PutMapping(value = "/tasks/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public String putTasks(@PathVariable String id) {
    return "putTasks";
  }

}
