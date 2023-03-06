package uz.pdp.lesson12.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson12.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    boolean existsByNameAndCourse_Id(String name, Integer course_id);
    boolean existsByNameAndCourse_IdAndIdNot(String name, Integer course_id, Integer id);

}
