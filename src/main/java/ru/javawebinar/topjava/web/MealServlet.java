package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/meals")
public class MealServlet extends HttpServlet {

    List<Meal> meals;
    public static final int CALORIES_PER_DAY = 2000;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<MealTo> mealTo = MealsUtil.filteredByStreams(getAll(), LocalTime.of(0, 0), LocalTime.of(21, 0), CALORIES_PER_DAY);
        req.setAttribute("mealTo", mealTo);
        req.getRequestDispatcher("WEB-INF/jsp/meals.jsp").forward(req, resp);
    }

    public List<Meal> getAll() {
        if (meals == null) {
            meals = new ArrayList<>();
            meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
            meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
            meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
            meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
            meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
            meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
            meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));

        }
        return meals;
    }
}
