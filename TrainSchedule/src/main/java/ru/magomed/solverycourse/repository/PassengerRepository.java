package ru.magomed.solverycourse.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.magomed.solverycourse.entity.PassengerEntity;
import java.util.Date;

@Repository
public interface PassengerRepository extends CrudRepository<PassengerEntity,Integer> {

    boolean existsByFirstNameAndLastNameAndBirthday(String firstName, String lastName, Date birthday);
}
