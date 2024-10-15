package ru.magomed.solverycourse.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.magomed.solverycourse.entity.TicketEntity;

@Repository
public interface TicketRepository extends CrudRepository<TicketEntity,Integer> {
}
