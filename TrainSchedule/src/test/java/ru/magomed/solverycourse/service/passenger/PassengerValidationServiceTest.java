package ru.magomed.solverycourse.service.passenger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.magomed.solverycourse.exception.PassengerException;
import ru.magomed.solverycourse.repository.PassengerRepository;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PassengerValidationServiceTest {

    @Mock
    private PassengerRepository passengerRepository;

    @InjectMocks
    private PassengerValidationService passengerValidationService;

    @Test
    @DisplayName("Выброс исключения, если нашелся пассажир")
    void shouldWhenExistsPassengerExceptionThrown() {
        String firstName = "John";
        String lastName = "Doe";
        Date birthday = new Date();

        when(passengerRepository.existsByFirstNameAndLastNameAndBirthday(
                firstName, lastName, birthday)).thenReturn(true);

        String expectedMessage = "У пассажира: " + firstName + " " + lastName + " "
                + birthday + " уже куплен билет в поезде!";

        PassengerException exception = assertThrows(PassengerException.class, () ->
                passengerValidationService.validateByFirstNameAndLastNameAndBirthday(firstName, lastName, birthday));

        assertEquals(expectedMessage, exception.getMessage());
        verify(passengerRepository, times(1)).
                existsByFirstNameAndLastNameAndBirthday(firstName, lastName, birthday);
        verifyNoMoreInteractions(passengerRepository);
    }
}