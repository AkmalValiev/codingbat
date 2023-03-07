package uz.pdp.lesson12.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson12.entity.Answer;
import uz.pdp.lesson12.payload.AnswerDto;
import uz.pdp.lesson12.payload.ApiResponse;
import uz.pdp.lesson12.service.AnswerService;

import java.util.List;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {

    @Autowired
    AnswerService answerService;

    @GetMapping
    public ResponseEntity<List<Answer>> getAnswers(){
        return ResponseEntity.ok(answerService.getAnswers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Answer> getAnswer(@PathVariable Integer id){
        return ResponseEntity.ok(answerService.getAnswer(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addAnswer(@RequestBody AnswerDto answerDto){
        ApiResponse apiResponse = answerService.addAnswer(answerDto);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editAnswer(@PathVariable Integer id, @RequestBody AnswerDto answerDto){
        ApiResponse apiResponse = answerService.editAnswer(id, answerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAnswer(@PathVariable Integer id){
        ApiResponse apiResponse = answerService.deleteAnswer(id);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }
}
