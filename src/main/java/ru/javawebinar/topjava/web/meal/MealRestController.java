package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        return MealsUtil.getTos(service.getAll(authUserId()), authUserCaloriesPerDay());
    }

    public List<MealTo> getAll(LocalDateTime localDateTimeStart, LocalDateTime localDateTimeEnd) {
        List<MealTo> mealTo = getAll();
        List<MealTo> mealToFiltered = MealsUtil.getTos(service.getAll(authUserId(), localDateTimeStart, localDateTimeEnd),
                authUserCaloriesPerDay());
        mealTo.retainAll(mealToFiltered);
        return mealTo;
    }

    public Meal get(int id) {
        return service.get(id, authUserId());
    }

    public Meal create(Meal meal) {
        checkNew(meal);
        return service.create(meal, authUserId());
    }

    public void delete(int id) {
        service.delete(id, authUserId());
    }

    public void update(Meal meal, int id) {
        service.update(meal, id, authUserId());
    }
}