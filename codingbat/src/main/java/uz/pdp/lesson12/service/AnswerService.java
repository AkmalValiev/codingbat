package uz.pdp.lesson12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson12.entity.Answer;
import uz.pdp.lesson12.entity.Task;
import uz.pdp.lesson12.entity.User;
import uz.pdp.lesson12.payload.AnswerDto;
import uz.pdp.lesson12.payload.ApiResponse;
import uz.pdp.lesson12.repository.AnswerRepository;
import uz.pdp.lesson12.repository.TaskRepository;
import uz.pdp.lesson12.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskRepository taskRepository;

    public List<Answer> getAnswers(){
        return answerRepository.findAll();
    }

    public Answer getAnswer(Integer id){
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        return optionalAnswer.orElseGet(Answer::new);
    }

    public ApiResponse addAnswer(AnswerDto answerDto){

        Optional<User> optionalUser = userRepository.findById(answerDto.getUserId());
        if (!optionalUser.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha user topilmadi!", false);

        Optional<Task> optionalTask = taskRepository.findById(answerDto.getTaskId());
        if (!optionalTask.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha task topilmadi!", false);

        Answer answer = new Answer();
        answer.setAnswerDescription(answerDto.getAnswerDescription());
        answer.setUser(optionalUser.get());
        answer.setTask(optionalTask.get());
        answer.setResult(answerDto.isResult());
        answerRepository.save(answer);
        return new ApiResponse("Answer qo'shildi!", true);
    }

    public ApiResponse editAnswer(Integer id, AnswerDto answerDto){

        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        if (!optionalAnswer.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha answer topilmadi!", false);
        Answer answer = optionalAnswer.get();

        Optional<User> optionalUser = userRepository.findById(answerDto.getUserId());
        if (!optionalUser.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha user topilmadi!", false);

        Optional<Task> optionalTask = taskRepository.findById(answerDto.getTaskId());
        if (!optionalTask.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha task topilmadi!", false);

        answer.setResult(answerDto.isResult());
        answer.setUser(optionalUser.get());
        answer.setTask(optionalTask.get());
        answer.setAnswerDescription(answerDto.getAnswerDescription());
        answerRepository.save(answer);
        return new ApiResponse("Answer taxrirlandi!", true);
    }

    public ApiResponse deleteAnswer(Integer id){
        try {
            answerRepository.deleteById(id);
            return new ApiResponse("Answer o'chirildi!", true);
        }catch (Exception e){
            return new ApiResponse("Xatolik!!!", false);
        }
    }

}
