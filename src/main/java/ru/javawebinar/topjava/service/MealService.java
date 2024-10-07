package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealService {

    List<Meal> getAll();

    Meal getById(int id);

    void save(Meal meal);

    void update(Meal meal);

    void delete(int id);
}
