package uz.pdp.lesson12.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson12.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Integer id);
}
