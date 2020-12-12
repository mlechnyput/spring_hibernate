package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);
        Car car1 = new Car("Ford", 322);

        userService.add(new User("Petya", "Lastname1", "user1@mail.ru", car1));
        userService.add(new User("Vasya", "Lastname2", "user2@mail.ru", new Car("Lada", 2108)));
        userService.add(new User("Izya", "Lastname3", "user3@mail.ru", new Car("Nissan", 952)));
        userService.add(new User("Ira", "Lastname4", "user4@mail.ru", new Car("Porsche", 911)));

        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println("Car =" + user.getCar());
            System.out.println();
        }

        List<User> usersHaveCar = userService.getUserByCar(car1);
        System.out.println("владельцы " + car1+" :");
        usersHaveCar.stream().map(x->x.getFirstName()+" "+x.getLastName()).forEach(System.out::println);
        context.close();
    }
}
