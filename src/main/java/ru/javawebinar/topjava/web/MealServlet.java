package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.service.InMemoryMealServiceImpl;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.DateTimeFormatterUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/meals")
public class MealServlet extends HttpServlet {

    MealService mealService = InMemoryMealServiceImpl.getInstance();
    public static final int CALORIES_PER_DAY = 2000;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        switch (action) {
            case "getAll": {
                List<Meal> meal = mealService.getAll();
                List<MealTo> mealTo = MealsUtil.filteredByStreams(meal, LocalTime.of(0, 0), LocalTime.of(23, 59), CALORIES_PER_DAY);
                req.setAttribute("mealTo", mealTo);
                req.getRequestDispatcher("WEB-INF/jsp/meals.jsp").forward(req, resp);
                break;
            }
            case "insert":
                req.getRequestDispatcher("WEB-INF/jsp/meal.jsp").forward(req, resp);
                break;
            case "update": {
                int id = Integer.parseInt(req.getParameter("id"));
                Meal meal = mealService.getById(id);
                req.setAttribute("meal", meal);
                req.getRequestDispatcher("WEB-INF/jsp/meal.jsp").forward(req, resp);
                break;
            }
            case "delete": {
                int id = Integer.parseInt(req.getParameter("id"));
                mealService.delete(id);
                resp.sendRedirect(req.getContextPath() + "/meals?action=getAll");
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = DateTimeFormatterUtil.getLocalDateTime(req.getParameter("dateTime"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        String id = req.getParameter("id");

        Meal meal;
        if (id == null || id.isEmpty()) {
            meal = new Meal(dateTime, description, calories);
            mealService.save(meal);
        } else {
            meal = new Meal(Integer.parseInt(id), dateTime, description, calories);
            mealService.update(meal);
        }
        resp.sendRedirect(req.getContextPath() + "/meals?action=getAll");
    }
}
