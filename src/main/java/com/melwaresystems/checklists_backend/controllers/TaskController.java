package com.melwaresystems.checklists_backend.controllers;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.melwaresystems.checklists_backend.dto.TaskDto;
import com.melwaresystems.checklists_backend.models.TaskModel;
import com.melwaresystems.checklists_backend.models.enums.Status;
import com.melwaresystems.checklists_backend.services.TaskListService;
import com.melwaresystems.checklists_backend.services.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    TaskListService taskListService;

    @GetMapping("/getAll")
    public List<TaskDto> getTasks() {
        List<TaskModel> list = taskService.getAll();
        List<TaskDto> listDto = list.stream().map(item -> new TaskDto(item)).collect(Collectors.toList());
        return listDto;
    }

    @GetMapping("/search-by-id/{id}")
    public ResponseEntity<TaskDto> findById(@PathVariable Long id) {
        TaskModel task = taskService.findById(id);

        return ResponseEntity.ok().body(new TaskDto(task));
    }

    @PostMapping("/create-task/{idTaskList}")
    public ResponseEntity<String> createTask(@RequestBody TaskDto taskDto, @PathVariable UUID idTaskList) {

        TaskModel task = taskService.fromDTO(taskDto);
        List<TaskModel> tasks = taskListService.findById(idTaskList).getTasks();

        task.setDateCreated(LocalDateTime.now((ZoneId.of("UTC"))));
        task.setStatus(Status.ACTIVE);
        task.setTaskList(taskListService.findById(idTaskList));

        if (!tasks.isEmpty()) {
            int maxOrder = tasks.stream()
                    .mapToInt(TaskModel::getOrder)
                    .max()
                    .orElse(0);

            task.setOrder(maxOrder + 1);
        } else {
            task.setOrder(1);
        }

        task = taskService.createTask(task);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(task.getIdTask())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/update-task/{id}")
    public ResponseEntity<Void> updateTask(@RequestBody TaskDto taskDto, @PathVariable Long id) {
        TaskModel task = taskService.fromDTO(taskDto);
        task.setIdTask(id);
        task = taskService.updateTask(task);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete-task/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        taskService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
