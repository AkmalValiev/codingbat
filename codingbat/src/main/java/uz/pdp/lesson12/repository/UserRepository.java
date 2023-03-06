package uz.pdp.lesson12.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson12.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByMail(String mail);
    boolean existsByMailAndIdNot(String mail, Integer id);

}
