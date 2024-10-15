package ru.magomed.solverycourse.service.passenger;

import org.springframework.stereotype.Service;
import ru.magomed.solverycourse.exception.PassengerException;
import ru.magomed.solverycourse.repository.PassengerRepository;

import java.util.Date;

@Service
public class PassengerValidationService {
    private final PassengerRepository passengerRepository;

    public PassengerValidationService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public void validateByFirstNameAndLastNameAndBirthday(String firstName, String lastName, Date birthday) {
        if (passengerRepository.existsByFirstNameAndLastNameAndBirthday(firstName, lastName, birthday)) {
            throw new PassengerException("У пассажира: " + firstName + " " + lastName + " "
                    + birthday + " уже куплен билет в поезде!");
        }
    }
}
