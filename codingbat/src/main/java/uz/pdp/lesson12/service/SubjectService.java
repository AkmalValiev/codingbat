package uz.pdp.lesson12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson12.entity.Course;
import uz.pdp.lesson12.entity.Subject;
import uz.pdp.lesson12.payload.ApiResponse;
import uz.pdp.lesson12.payload.SubjectDto;
import uz.pdp.lesson12.repository.CourseRepository;
import uz.pdp.lesson12.repository.SubjectRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    CourseRepository courseRepository;
    public List<Subject> getSubjects(){
        return subjectRepository.findAll();
    }

    public Subject getSubject(Integer id){
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        return optionalSubject.orElseGet(Subject::new);
    }

    public ApiResponse addSubject(SubjectDto subjectDto){

        Optional<Course> optionalCourse = courseRepository.findById(subjectDto.getCourseId());
        if (!optionalCourse.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha course topilmadi!", false);
        Course course = optionalCourse.get();

        boolean existsByNameAndCourseId = subjectRepository.existsByNameAndCourse_Id(subjectDto.getName(), subjectDto.getCourseId());
        if (existsByNameAndCourseId)
            return new ApiResponse("Kiritilgan nomli subject ko'rsatilgan id li courseda mavjud!", false);

        Subject subject = new Subject();
        subject.setName(subjectDto.getName());
        subject.setDone(subject.isDone());
        subject.setCourse(course);
        subject.setRating(subjectDto.getRating());
        subject.setDescription(subjectDto.getDescription());
        subjectRepository.save(subject);
        return new ApiResponse("Subject qo'shildi!", true);
    }

    public ApiResponse editSubject(Integer id, SubjectDto subjectDto){

        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        if (!optionalSubject.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha subject topilmadi!", false);

        Optional<Course> optionalCourse = courseRepository.findById(subjectDto.getCourseId());
        if (!optionalCourse.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha course topilmadi!", false);

        boolean existsByNameAndCourseIdAndIdNot = subjectRepository.existsByNameAndCourse_IdAndIdNot(subjectDto.getName(), subjectDto.getCourseId(), id);
        if (existsByNameAndCourseIdAndIdNot)
            return new ApiResponse("Kiritilgan nomli subject ko'rsatilgan id li courseda mavjud!", false);

        Subject subject = optionalSubject.get();
        subject.setDescription(subjectDto.getDescription());
        subject.setName(subjectDto.getName());
        subject.setDone(subjectDto.isDone());
        subject.setCourse(optionalCourse.get());
        subject.setRating(subjectDto.getRating());
        subjectRepository.save(subject);
        return new ApiResponse("Subject taxrirlandi!", true);
    }

    public ApiResponse deleteSubject(Integer id){
        try {
            subjectRepository.deleteById(id);
            return new ApiResponse("Subject o'chirildi!", true);
        }catch (Exception e){
            return new ApiResponse("Xatolik!!!", false);
        }
    }

}
