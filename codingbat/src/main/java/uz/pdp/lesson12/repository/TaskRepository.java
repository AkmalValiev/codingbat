package uz.pdp.lesson12.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson12.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
