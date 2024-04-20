package com.example.kursinisprojektas.utils;

import com.example.kursinisprojektas.model.Manager;
import com.example.kursinisprojektas.model.Product;
import com.example.kursinisprojektas.model.User;

import java.util.List;
import java.util.Scanner;

public class MenuGenerator {
    public static void generateUserMenu(Scanner scanner, List<User> systemUsers) {
        var userMenuCmd = 0;

        while (userMenuCmd != 6) {
            System.out.println("""
                    --------------------
                    Choose an action:
                    1 - create User
                    2 - view all users
                    3 - update user
                    4 - delete user
                    5 - view specific user
                    6 - quit
                    --------------------""");

            userMenuCmd = scanner.nextInt();
            scanner.nextLine();

            switch (userMenuCmd) {
                case 1:
                    System.out.println("Which type of user? C/M");
                    var response = "";
                    response = scanner.nextLine();
                    if (response.equals("C")) {

                    } else if (response.equals("M")) {
                        System.out.println("Enter manager data: name;surname;login;psw;isAdmin;");
                        response = scanner.nextLine();
                        String[] info = response.split(";");
                        Manager manager = new Manager(info[0], info[1], info[2], info[3], Boolean.parseBoolean(info[4]));
                        systemUsers.add(manager);
                        System.out.println(manager);
                    } else {
                        System.out.println("Wrong user type\n");
                    }
                    break;
                case 2:
                    for (User u : systemUsers) System.out.println(u);
                    //systemUsers.forEach(System.out::println);
                    break;
                default:
                    System.out.println("Wrong command");
            }
        }
    }

    public static void generateProductMenu(Scanner scanner, List<Product> systemProducts) {
        var productMenuCmd = 0;

        while (productMenuCmd != 6) {
            System.out.println("""
                    --------------------
                    Choose an action:
                    1 - create Product
                    2 - view all Products
                    3 - update Product
                    4 - delete Product
                    5 - view specific Product
                    6 - quit
                    --------------------""");

            productMenuCmd = scanner.nextInt();
            scanner.nextLine();

            switch (productMenuCmd) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Wrong command");
            }
        }
    }
}
