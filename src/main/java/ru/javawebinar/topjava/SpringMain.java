package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);

            System.out.println(mealRestController.get(1));

            try {
                System.out.println(mealRestController.get(10));
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }

            Meal newMeal = new Meal(LocalDateTime.of(2024, Month.JANUARY, 31, 20, 0), "Ужин", 500);
            mealRestController.create(newMeal);

            System.out.println();

            mealRestController.getAll().forEach(System.out::println);

            MealRepository repository = appCtx.getBean(MealRepository.class);

            repository.save(new Meal(1, LocalDateTime.of(2024, Month.JANUARY, 30, 10, 0), "Завтрак 2024", 100, 2), 2);
            System.out.println(repository.getAll(2));


        }
    }
}
