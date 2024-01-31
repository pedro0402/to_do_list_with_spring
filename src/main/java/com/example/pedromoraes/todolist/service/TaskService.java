package com.example.pedromoraes.todolist.service;

import com.example.pedromoraes.todolist.model.Task;
import com.example.pedromoraes.todolist.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task){
        return taskRepository.save(task);
    }

    public List<Task> listAllTasks(){
        return taskRepository.findAll();
    }

    public ResponseEntity<Task> findTaskById(Long id){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isPresent()) {
            Task task = optionalTask.get();
            return ResponseEntity.ok().body(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    public ResponseEntity<Task> updateTaskById(Task task, Long id){
    	return taskRepository.findById(id)
    			.map(taskToUpdate ->{
    				taskToUpdate.setTask(task.getTask());
    				taskToUpdate.setDescription(task.getDescription());
    				taskToUpdate.setDescription(task.getDescription());
    				taskToUpdate.setDeadLine(task.getDeadLine());
    				Task updated = taskRepository.save(taskToUpdate);
    				return ResponseEntity.ok().body(updated);
    				
    		}).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Object> deleteTaskById(Long id){
        return taskRepository.findById(id)
                .map(taskToDelete -> {
                    taskRepository.deleteById(id);
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());
    }
    
}
