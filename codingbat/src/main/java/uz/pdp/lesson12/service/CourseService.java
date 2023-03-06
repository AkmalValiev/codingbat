package uz.pdp.lesson12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson12.entity.Course;
import uz.pdp.lesson12.payload.ApiResponse;
import uz.pdp.lesson12.payload.CourseDto;
import uz.pdp.lesson12.repository.CourseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    public List<Course> getCourses(){
        return courseRepository.findAll();
    }

    public Course getCourse(Integer id){
        Optional<Course> optionalCourse = courseRepository.findById(id);
        return optionalCourse.orElseGet(Course::new);
    }

    public ApiResponse addCourse(CourseDto courseDto){
        boolean existsByName = courseRepository.existsByName(courseDto.getName());
        if (existsByName)
            return new ApiResponse("Bunday nomli course mavjud!", false);
        Course course = new Course();
        course.setName(courseDto.getName());
        courseRepository.save(course);
        return new ApiResponse("Course qo'shildi!", true);
    }

    public ApiResponse editCourse(Integer id, CourseDto courseDto){
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (!optionalCourse.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha course topilmadi!", false);

        boolean existsByNameAndIdNot = courseRepository.existsByNameAndIdNot(courseDto.getName(), id);
        if (existsByNameAndIdNot)
            return new ApiResponse("Bunday nomli course mavjud!", false);

        Course course = optionalCourse.get();
        course.setName(courseDto.getName());
        courseRepository.save(course);
        return new ApiResponse("Course taxrirlandi!", true);

    }

    public ApiResponse deleteCourse(Integer id){
        try {
            courseRepository.deleteById(id);
            return new ApiResponse("Course o'chirildi!", true);
        }catch (Exception e){
            return new ApiResponse("Xatolik!!", false);
        }

    }

}
