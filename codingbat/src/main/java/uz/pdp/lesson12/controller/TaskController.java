package uz.pdp.lesson12.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson12.entity.Task;
import uz.pdp.lesson12.payload.ApiResponse;
import uz.pdp.lesson12.payload.TaskDto;
import uz.pdp.lesson12.service.TaskService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getTasks(){
        return ResponseEntity.ok(taskService.getTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Integer id){
        return ResponseEntity.ok(taskService.getTask(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addTask(@Valid @RequestBody TaskDto taskDto){
        ApiResponse apiResponse = taskService.addTask(taskDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editTask(@PathVariable Integer id, @Valid @RequestBody TaskDto taskDto){
        ApiResponse apiResponse = taskService.editTask(id, taskDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTask(@PathVariable Integer id){
        ApiResponse apiResponse = taskService.deleteTask(id);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
