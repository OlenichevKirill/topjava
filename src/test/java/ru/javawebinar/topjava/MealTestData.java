package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int MEAL_USER_ONE_ID = START_SEQ + 3;
    public static final int MEAL_USER_TWO_ID = START_SEQ + 4;
    public static final int MEAL_USER_THREE_ID = START_SEQ + 5;
    public static final int MEAL_USER_FOUR_ID = START_SEQ + 6;
    public static final int MEAL_USER_FIVE_ID = START_SEQ + 7;
    public static final int MEAL_USER_SIX_ID = START_SEQ + 8;
    public static final int MEAL_USER_SEVEN_ID = START_SEQ + 9;

    public static final Meal MEAL_USER_ONE = new Meal(MEAL_USER_ONE_ID,
            LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_USER_TWO = new Meal(MEAL_USER_TWO_ID,
            LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL_USER_THREE = new Meal(MEAL_USER_THREE_ID,
            LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL_USER_FOUR = new Meal(MEAL_USER_FOUR_ID,
            LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal MEAL_USER_FIVE = new Meal(MEAL_USER_FIVE_ID,
            LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal MEAL_USER_SIX = new Meal(MEAL_USER_SIX_ID,
            LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal MEAL_USER_SEVEN = new Meal(MEAL_USER_SEVEN_ID,
            LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final LocalDate START_DATE = LocalDate.of(2020, Month.JANUARY, 30);
    public static final LocalDate END_DATE = LocalDate.of(2020, Month.JANUARY, 30);

    public static Meal newMeal() {
        return new Meal(null, LocalDateTime.of(2024, Month.OCTOBER, 21, 10, 0),
                "newDescription", 1000);
    }

    public static Meal newMealDuplicateDateTime() {
        return new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    }

    public static Meal updateMeal() {
        Meal updatedMeal = new Meal(MEAL_USER_ONE);
        updatedMeal.setDateTime(LocalDateTime.of(2020, Month.JANUARY, 30, 11, 11));
        updatedMeal.setDescription("Обновленный завтрак");
        updatedMeal.setCalories(450);
        return updatedMeal;

    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
