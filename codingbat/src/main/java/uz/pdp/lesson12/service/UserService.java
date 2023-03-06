package uz.pdp.lesson12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson12.entity.Course;
import uz.pdp.lesson12.entity.User;
import uz.pdp.lesson12.payload.ApiResponse;
import uz.pdp.lesson12.payload.UserDto;
import uz.pdp.lesson12.repository.CourseRepository;
import uz.pdp.lesson12.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CourseRepository courseRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElseGet(User::new);
    }

    public ApiResponse addUser(UserDto userDto) {
        boolean existsByMail = userRepository.existsByMail(userDto.getMail());
        if (existsByMail)
            return new ApiResponse("Bunday mailli user mavjud!", false);

        List<Integer> coursesId = userDto.getCoursesId();
        List<Course> courses = new ArrayList<>();
        if (!coursesId.isEmpty()) {
            if (!courseRepository.findAll().isEmpty()) {
                List<Course> all = courseRepository.findAll();
                for (Course course : all) {
                    for (Integer integer : coursesId) {
                        if (!course.getId().equals(integer))
                            return new ApiResponse("Kiritilgan id bo'yicha course topilmadi!", false);
                        courses.add(course);
                    }
                }
            }
        }
        User user = new User();
        user.setCourses(courses);
        user.setMail(userDto.getMail());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
        return new ApiResponse("User qo'shildi!", true);
    }

    public ApiResponse editUser(Integer id, UserDto userDto){
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha user topilmadi!", false);

        User user = optionalUser.get();

        boolean existsByMailAndIdNot = userRepository.existsByMailAndIdNot(userDto.getMail(), id);
        if (existsByMailAndIdNot)
            return new ApiResponse("Bunaqa mailli user mavjud!", false);

        List<Integer> coursesId = userDto.getCoursesId();
        List<Course> courses = new ArrayList<>();
        if (!coursesId.isEmpty()) {
            if (!courseRepository.findAll().isEmpty()) {
                List<Course> all = courseRepository.findAll();
                for (Course course : all) {
                    for (Integer integer : coursesId) {
                        if (!course.getId().equals(integer))
                            return new ApiResponse("Kiritilgan id bo'yicha course topilmadi!", false);
                        courses.add(course);
                    }
                }
            }
        }
        user.setCourses(courses);
        user.setMail(userDto.getMail());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
        return new ApiResponse("User taxrirlandi!", true);
    }

    public ApiResponse deleteUser(Integer id){
        try {
            userRepository.deleteById(id);
            return new ApiResponse("User o'chirildi!", true);
        }catch (Exception e){
            return new ApiResponse("Xatolik!!!", false);
        }
    }

}
