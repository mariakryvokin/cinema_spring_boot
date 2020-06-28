package app.services.discounts;

import app.models.EventHasAuditorium;
import app.models.User;
import app.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class BirthdayStrategyTest {

    @Mock
    private UserService userService;
    private BirthdayStrategy birthdayStrategy;

    @BeforeEach
    void init() {
        birthdayStrategy = new BirthdayStrategy(userService);
    }

    @Test
    void calculateDiscountWithNotExistingUserTest() {
        Mockito.when(userService.exists(ArgumentMatchers.any(User.class))).thenReturn(false);
        Assertions.assertEquals(0d, birthdayStrategy.calculateDiscount(new User(), new EventHasAuditorium(),
                1l, 1d));
    }

    @Test
    void calculateDiscountWithUserWithoutBirthDataTest() {
        Mockito.when(userService.exists(ArgumentMatchers.any(User.class))).thenReturn(true);
        Assertions.assertEquals(0d, birthdayStrategy.calculateDiscount(new User(), new EventHasAuditorium(),
                1l, 1d));
    }

    @ParameterizedTest
    @MethodSource("provideParametersForCalculateDiscountWithExistingUserTest")
    void calculateDiscountWithExistingUserTest(int days, double initialTotalPrice, double discount) {
        Mockito.when(userService.exists(ArgumentMatchers.any(User.class))).thenReturn(true);
        User user = User.builder().birthday(Date.valueOf(LocalDate.now())).build();
        EventHasAuditorium eventHasAuditorium = EventHasAuditorium.builder()
                .airDate(Timestamp.valueOf(LocalDateTime.now().plusDays(days))).build();
        Assertions.assertEquals(discount, birthdayStrategy.calculateDiscount(user, eventHasAuditorium,
                1l, initialTotalPrice));
    }

    private static Stream<Arguments> provideParametersForCalculateDiscountWithExistingUserTest() {
        return Stream.of(Arguments.of(7, 1, 0), Arguments.of(-7, 1, 0d), Arguments.of(6, 5, 0),
                Arguments.of(-6, 5, 0), Arguments.of(5, 5, 0.25d), Arguments.of(-5, 5, 0.25d),
                Arguments.of(0, 5, 0.25d));
    }
}