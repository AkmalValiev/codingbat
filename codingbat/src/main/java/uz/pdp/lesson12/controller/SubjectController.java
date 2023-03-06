package uz.pdp.lesson12.controller;

import jakarta.validation.Valid;
import org.hibernate.id.IntegralDataTypeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson12.entity.Subject;
import uz.pdp.lesson12.payload.ApiResponse;
import uz.pdp.lesson12.payload.SubjectDto;
import uz.pdp.lesson12.service.SubjectService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @GetMapping
    public ResponseEntity<List<Subject>> getSubjects(){
        return ResponseEntity.ok(subjectService.getSubjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubject(@PathVariable Integer id){
        return ResponseEntity.ok(subjectService.getSubject(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addSubject(@Valid @RequestBody SubjectDto subjectDto){
        ApiResponse apiResponse = subjectService.addSubject(subjectDto);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editSubject(@PathVariable Integer id, @Valid @RequestBody SubjectDto subjectDto){
        ApiResponse apiResponse = subjectService.editSubject(id, subjectDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteSubject(@PathVariable Integer id){
        ApiResponse apiResponse = subjectService.deleteSubject(id);
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
