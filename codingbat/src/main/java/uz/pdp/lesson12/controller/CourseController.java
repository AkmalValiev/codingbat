package uz.pdp.lesson12.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson12.entity.Course;
import uz.pdp.lesson12.payload.ApiResponse;
import uz.pdp.lesson12.payload.CourseDto;
import uz.pdp.lesson12.service.CourseService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Course>> getCourses(){
        return ResponseEntity.ok(courseService.getCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Integer id){
        return ResponseEntity.ok(courseService.getCourse(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addCourse(@Valid @RequestBody CourseDto courseDto){
        ApiResponse apiResponse = courseService.addCourse(courseDto);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editCourse(@PathVariable Integer id, @Valid @RequestBody CourseDto courseDto){
        ApiResponse apiResponse = courseService.editCourse(id, courseDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCourse(@PathVariable Integer id){
        ApiResponse apiResponse = courseService.deleteCourse(id);
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
