package src;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class FileIO {


    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<String> lines;
        HashMap<String, Long> contactInfo = new HashMap<>();


        //TODO: Create a file path
        Path filepath = Paths.get("data", "contacts.txt");


        //TODO: Get data from user


        boolean userInteraction = true;
        while (userInteraction) {
            System.out.println("\n1. View contacts\n2. Add a new contact\n3. Search a contact by name\n4. Delete an existing contact\n5. Exit\nEnter a number for your selection!");
            int answer = scanner.nextInt();

            //TODO: Reads the file
            if (answer == 1) {
                try {
                    lines = Files.readAllLines(filepath);
                    lines.forEach((line) -> {
                        System.err.println(line);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //TODO: Write to the file / Edit existing file
            if (answer == 2) {
                System.out.println("Enter in the name you would like to add");
                scanner.nextLine();
                String name = scanner.nextLine();
                //TODO: Checks to see if name exists in the list
                lines = Files.readAllLines(filepath);
                lines.forEach((line) -> {
                    if (line.contains(name)) {
                        System.out.println("Would you like to edit existing contact?[Y/N]");
                        String input = scanner.nextLine();
                    } else {
                        //TODO: If name doesn't exist then this part is fired off
                        System.out.println("Enter in the phone number you would like to add");
                        String number = String.valueOf(Long.parseLong(scanner.nextLine()));

                        //TODO: Checks if number is 10 digits in length
                        if (number.length() == 10) {

                            //TODO: Writes to the file
                            contactInfo.put(name, Long.valueOf(number));
                            try {
                                String userInfoToAdd = String.format("%s -- %s", name, number);
                                Files.write(filepath, List.of(userInfoToAdd), StandardOpenOption.APPEND);
                                System.out.println(number.length());
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            System.err.println("10 digit number required");

                        }
                    }

                });
            }

            if (answer == 5) {
                System.err.println("Goodbye! :)");
                userInteraction = false;
            }
        }


        //TODO: Write to the file

//        contactInfo.forEach((k, v) -> {
//            String userContacts = String.format("%s -- %s", k, v);
//            try {
//                if (contactInfo.keySet().contains(name) || contactInfo.values().contains(number)){
//                    System.out.println("This is a double!");
//                    contactInfo.keySet().remove(name);
//                    contactInfo.values().remove(number);
//                }
//                contactInfo.put(name, number);
//                Files.write(filepath, List.of(userContacts), StandardOpenOption.APPEND);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });


    }
}
