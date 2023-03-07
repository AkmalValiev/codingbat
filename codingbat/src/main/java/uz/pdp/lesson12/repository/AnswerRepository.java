package uz.pdp.lesson12.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson12.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
