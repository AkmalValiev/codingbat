package uz.pdp.lesson12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson12.entity.Subject;
import uz.pdp.lesson12.entity.Task;
import uz.pdp.lesson12.payload.ApiResponse;
import uz.pdp.lesson12.payload.TaskDto;
import uz.pdp.lesson12.repository.SubjectRepository;
import uz.pdp.lesson12.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    SubjectRepository subjectRepository;

    public List<Task> getTasks(){
        return taskRepository.findAll();
    }

    public Task getTask(Integer id){
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.orElseGet(Task::new);
    }

    public ApiResponse addTask(TaskDto taskDto){
        Optional<Subject> optionalSubject = subjectRepository.findById(taskDto.getSubjectId());
        if (!optionalSubject.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha subject topilmadi!", false);

        Task task = new Task();
        task.setName(taskDto.getName());
        task.setDone(taskDto.isDone());
        task.setSubject(optionalSubject.get());
        task.setDescription(taskDto.getDescription());
        task.setCodeDescription(taskDto.getCodeDescription());
        taskRepository.save(task);
        return new ApiResponse("Task qo'shildi!", true);
    }

    public ApiResponse editTask(Integer id, TaskDto taskDto){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha task topilmadi!", false);
        Task task = optionalTask.get();
        Optional<Subject> optionalSubject = subjectRepository.findById(taskDto.getSubjectId());
        if (!optionalSubject.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha subject topilmadi!", false);

        task.setName(taskDto.getName());
        task.setDone(taskDto.isDone());
        task.setSubject(optionalSubject.get());
        task.setDescription(taskDto.getDescription());
        task.setCodeDescription(taskDto.getCodeDescription());
        taskRepository.save(task);
        return new ApiResponse("Task taxrirlandi!", true);
    }

    public ApiResponse deleteTask(Integer id){
        try {
            taskRepository.deleteById(id);
            return new ApiResponse("Task o'chirildi!", true);
        }catch (Exception e){
            return new ApiResponse("Xatolik!!!", false);
        }
    }

}
