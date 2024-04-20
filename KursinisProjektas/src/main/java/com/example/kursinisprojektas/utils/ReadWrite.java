package com.example.kursinisprojektas.utils;

import com.example.kursinisprojektas.model.Customer;
import com.example.kursinisprojektas.model.Manager;
import com.example.kursinisprojektas.model.Shop;
import com.example.kursinisprojektas.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadWrite {
    public static void writeToFileAsObject(String fileName, Shop shop) {
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName));
            objectOutputStream.writeObject(shop);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Shop readFromFileAsObject(String fileName) {
        Shop shop = null;
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
            shop = (Shop) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return shop;
    }

    public static void writeToFileAsString(String fileName, List<User> users) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
            for (User u : users) {
                if (u.getClass() == Manager.class) {
                    Manager manager = (Manager) u;
                    //Man siaip jau reiktu visus duomenis saugoti
                    fileWriter.write(manager.getLogin() + ":" + manager.getName() + ":" + manager.isAdmin() + "\n");
                } else {
                    Customer customer = (Customer) u;
                    fileWriter.write(customer.getName() + "#" + customer.getBillingAddress() + "#" + customer.getLogin() + "\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static List<User> restoreUsersFromFile(String fileName) {
        List<User> users = new ArrayList<>();
        Scanner input;
        try {
            File file = new File(fileName);
            input = new Scanner(file);
            while (input.hasNextLine()) {
                String line = input.nextLine();
                //Sioje vietoje reikia moketi atskirti duomenis ir juos identifikuoti
                if(line.contains(":")){
                    String[] info = line.split(":");
                    Manager manager = new Manager(info[0], info[1], info[2], info[3], Boolean.parseBoolean(info[4]));
                    users.add(manager);
                }
                System.out.println(line);
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
