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

import com.melwaresystems.checklists_backend.dto.TaskListDto;
import com.melwaresystems.checklists_backend.models.TaskListModel;
import com.melwaresystems.checklists_backend.models.enums.Status;
import com.melwaresystems.checklists_backend.services.TaskListService;
import com.melwaresystems.checklists_backend.services.UserService;

@RestController
@RequestMapping("/task-list")
public class TaskListController {

    @Autowired
    TaskListService taskListService;

    @Autowired
    UserService userService;

    @GetMapping("/getAll/")
    public List<TaskListDto> getAllByUserId() {
        List<TaskListModel> list = taskListService.getAll();
        List<TaskListDto> listDto = list.stream().map(item -> new TaskListDto(item)).collect(Collectors.toList());
        return listDto;
    }

    @GetMapping("/get-all-by-user-id/{id}")
    public List<TaskListDto> getAllByUserId(@PathVariable UUID id) {

        List<TaskListModel> list = userService.findById(id).getTaskLists();
        List<TaskListDto> listDto = list.stream().map(item -> new TaskListDto(item)).collect(Collectors.toList());
        return listDto;
    }

    @GetMapping("/search-by-id/{id}")
    public ResponseEntity<TaskListDto> findById(@PathVariable UUID id) {
        TaskListModel taskList = taskListService.findById(id);

        return ResponseEntity.ok().body(new TaskListDto(taskList));
    }

    @PostMapping("/create/{idUser}")
    public ResponseEntity<String> createTaskList(@RequestBody TaskListDto taskListDto, @PathVariable UUID idUser) {

        TaskListModel taskList = taskListService.fromDTO(taskListDto);

        taskList.setDateCreated(LocalDateTime.now((ZoneId.of("UTC"))));
        taskList.setStatus(Status.ACTIVE);

        taskList.setUser(userService.findById(idUser));

        taskList = taskListService.createTaskList(taskList);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(taskList.getIdTaskList())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/update-task-list/{id}")
    public ResponseEntity<Void> updateUser(@RequestBody TaskListDto taskListDto, @PathVariable UUID id) {
        TaskListModel taskList = taskListService.fromDTO(taskListDto);
        taskList.setIdTaskList(id);
        taskList = taskListService.updateTask(taskList);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete-task-list/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        taskListService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
