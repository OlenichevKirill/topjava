package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.InMemoryMealDao;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.DateTimeFormatterUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class MealServlet extends HttpServlet {

    private MealDao mealDao;
    public static final int CALORIES_PER_DAY = 2000;

    @Override
    public void init() throws ServletException {
        super.init();
        mealDao = new InMemoryMealDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            List<Meal> meals = mealDao.getAll();
            List<MealTo> mealTo = MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
            req.setAttribute("mealTo", mealTo);
            req.getRequestDispatcher("WEB-INF/jsp/meals.jsp").forward(req, resp);
        } else if ("insert".equals(action)) {
            req.getRequestDispatcher("WEB-INF/jsp/meal.jsp").forward(req, resp);
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            Meal meal = mealDao.getById(id);
            req.setAttribute("meal", meal);
            req.getRequestDispatcher("WEB-INF/jsp/meal.jsp").forward(req, resp);
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            mealDao.delete(id);
            resp.sendRedirect(req.getContextPath() + "/meals");
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
            mealDao.create(meal);
        } else {
            meal = new Meal(Integer.parseInt(id), dateTime, description, calories);
            mealDao.update(meal);
        }
        resp.sendRedirect(req.getContextPath() + "/meals");
    }
}
