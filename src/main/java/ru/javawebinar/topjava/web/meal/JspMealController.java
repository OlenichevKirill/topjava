package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkIsNew;

@RequestMapping("/meals")
@Controller
public class JspMealController {

    private final MealService mealService;

    public JspMealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping
    public String getAll(Model model) {
        int userId = SecurityUtil.authUserId();
        List<MealTo> mealTos = MealsUtil.getTos(mealService.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
        model.addAttribute("meals", mealTos);
        return "meals";
    }

    @GetMapping("/filter")
    public String filter(HttpServletRequest request, Model model) {
        int userId = SecurityUtil.authUserId();
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));

        List<Meal> meals = mealService.getBetweenInclusive(startDate, endDate, userId);
        List<MealTo> mealTos = MealsUtil.getFilteredTos(meals, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
        model.addAttribute("meals", mealTos);
        return "meals";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        int id = getId(request);
        int userId = SecurityUtil.authUserId();
        mealService.delete(id, userId);
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String create(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        model.addAttribute("action", "create");
        return "mealForm";
    }

    @GetMapping("/update")
    public String update(HttpServletRequest request, Model model) {
        int userId = SecurityUtil.authUserId();
        Meal meal = mealService.get(getId(request), userId);
        model.addAttribute("meal", meal);
        model.addAttribute("action", "update");
        return "mealForm";
    }

    @PostMapping
    public String save(HttpServletRequest request) {
        int userId = SecurityUtil.authUserId();
        String id = request.getParameter("id");
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (StringUtils.hasLength(id)) {
            assureIdConsistent(meal, Integer.parseInt(id));
            mealService.update(meal, userId);
        } else {
            checkIsNew(meal);
            mealService.create(meal, userId);
        }
        return "redirect:/meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
