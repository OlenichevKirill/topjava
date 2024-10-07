package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {

    List<Meal> getAll();

    Meal getById(int id);

    void save(Meal meal);

    void update(Meal meal);

    void delete(int id);
}
