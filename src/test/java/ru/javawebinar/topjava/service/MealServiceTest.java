package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.END_DATE;
import static ru.javawebinar.topjava.MealTestData.MEAL_USER_FIVE;
import static ru.javawebinar.topjava.MealTestData.MEAL_USER_FOUR;
import static ru.javawebinar.topjava.MealTestData.MEAL_USER_ONE;
import static ru.javawebinar.topjava.MealTestData.MEAL_USER_ONE_ID;
import static ru.javawebinar.topjava.MealTestData.MEAL_USER_SEVEN;
import static ru.javawebinar.topjava.MealTestData.MEAL_USER_SIX;
import static ru.javawebinar.topjava.MealTestData.MEAL_USER_THREE;
import static ru.javawebinar.topjava.MealTestData.MEAL_USER_TWO;
import static ru.javawebinar.topjava.MealTestData.START_DATE;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.MealTestData.newMeal;
import static ru.javawebinar.topjava.MealTestData.newMealDuplicateDateTime;
import static ru.javawebinar.topjava.MealTestData.updateMeal;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService mealService;

    @Test
    public void get() {
        Meal meal = mealService.get(MEAL_USER_ONE_ID, USER_ID);
        assertMatch(meal, MEAL_USER_ONE);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.get(MEAL_USER_ONE_ID, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal createdMeal = mealService.create(newMeal(), USER_ID);
        Integer newId = createdMeal.getId();
        Meal newMeal = newMeal();
        newMeal.setId(newId);
        assertMatch(createdMeal, newMeal);
        assertMatch(mealService.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DuplicateKeyException.class, () -> mealService.create(newMealDuplicateDateTime(), USER_ID));
    }

    @Test
    public void update() {
        Meal meal = updateMeal();
        mealService.update(meal, USER_ID);
        assertMatch(mealService.get(MEAL_USER_ONE_ID, USER_ID), meal);
    }

    @Test
    public void updateNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.update(updateMeal(), ADMIN_ID));
    }

    @Test
    public void delete() {
        mealService.delete(MEAL_USER_ONE_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> mealService.get(MEAL_USER_ONE_ID, USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.delete(MEAL_USER_ONE_ID, ADMIN_ID));
    }

    @Test
    public void getAll() {
        List<Meal> meals = mealService.getAll(USER_ID);
        assertMatch(meals, MEAL_USER_SEVEN, MEAL_USER_SIX, MEAL_USER_FIVE,
                MEAL_USER_FOUR, MEAL_USER_THREE, MEAL_USER_TWO, MEAL_USER_ONE);
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> meals = mealService.getBetweenInclusive(START_DATE, END_DATE, USER_ID);
        assertMatch(meals, MEAL_USER_THREE, MEAL_USER_TWO, MEAL_USER_ONE);
    }
}