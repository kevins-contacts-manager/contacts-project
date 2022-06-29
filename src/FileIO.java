/**
 * TODO: BUG FIXES / THINGS TO DO
 *      1. Edit functionality needs a better way to compare the list to the name
 *              a. using the list.contains() is messing with results! (ex.. if you enter k..
 *              it thinks its talking about any name with the letter k in it)
 *
 **/


package src;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class FileIO {


    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<String> lines;

        //TODO: Create a file path
        Path filepath = Paths.get("data", "contacts.txt");
        ArrayList<String> deleter = new ArrayList<>();

        //TODO: Get data from user
        boolean userInteraction = true;
        while (userInteraction) {
            System.out.println("1. View contacts\n2. Add a new contact\n3. Search a contact by name\n4. Delete an existing contact\n5. Exit\nEnter the number for your selection!");

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
                boolean canContinue = true;
                boolean dontContinue = false;
                System.out.println("Enter in the name you would like to add");
                scanner.nextLine();
                String name = scanner.nextLine();
                System.out.println(name.length());


                //TODO: Checks to see if name exists in the list
                lines = Files.readAllLines(filepath);

                for (String line : lines) {

                    //line.toLowerCase().contains(name.toLowerCase())
                    if (line.toLowerCase().contains(name.toLowerCase())) {
                        String storedValue = line.toLowerCase();
                        //TODO: If name exists... choice of editing starts here
                        System.err.println("CONTACT EXISTS: Would you like to edit existing contact?[Y/N]");
                        String input = scanner.nextLine();
                        if (input.equalsIgnoreCase("y")) {

                            //TODO: Remakes the text file
                            Files.delete(filepath);
                            Files.createFile(filepath);

                            //TODO: Adds the contacts not being edited back to the text file
                            deleter.forEach((del) -> { // this adds back to the text file
                                try {
                                    Files.write(filepath, List.of(del), StandardOpenOption.APPEND);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });

                            System.err.println("Would you like to change the current name?[Y/N]");
                            String decision = scanner.nextLine();
                            if (decision.equalsIgnoreCase("y")) {
                                System.err.println("Enter in a new name");
                                name = scanner.nextLine();

                            } else if (decision.equalsIgnoreCase("n")) {
                                continue;
                            } else {
                                System.err.println("Next time... try to enter [Y/N]");
                                dontContinue = true;
                            }

                            if (dontContinue) {
                                Files.write(filepath, List.of(storedValue), StandardOpenOption.APPEND);
                                canContinue = false;
                            } else {
                                canContinue = true;
                            }


                            //TODO: Sends user to enter in new phone number for existing contact

                        } else if (input.equalsIgnoreCase("n")) {
                            deleter.clear();
                            canContinue = false;
                        } else {
                            System.err.println("Next time... try to enter [Y/N]");
                            canContinue = false;
                        }

                    } else {
                        //TODO: Gets the contacts that arent being edited to add back to text file
                        deleter.add(line); // this takes in the lines that did not match the user input
                    }
                }


                //TODO: If name doesn't exist then this part is fired off
                if (canContinue) {
                    deleter.clear();
                    System.out.println("Enter in the phone number you would like to add");
                    String number = String.valueOf(Long.parseLong(scanner.nextLine()));

                    //TODO: Checks if number is 10 digits in length
                    if (number.length() == 10) {

                        //TODO: Writes to the file
                        try {
                            String userInfoToAdd = String.format("%s -- %s", name.toLowerCase(), number);
                            Files.write(filepath, List.of(userInfoToAdd), StandardOpenOption.APPEND);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        System.out.println("10 digit number required");
                    }
                }
            }

            //TODO: Contact search functionality
            if (answer == 3) {
                System.out.println("Enter the name of the contact you wish to search!");
                scanner.nextLine();
                String searchFor = scanner.nextLine();

                lines = Files.readAllLines(filepath);
                for (String line : lines) {
                    if (line.toLowerCase().contains(searchFor.toLowerCase())) {
                        System.out.println("\nSearch Results: " + line + "\n");
                    }
                }
            }

            //TODO: Delete functionality
            if (answer == 4) {
                System.out.println("Enter the contact name you want to delete!");
                scanner.nextLine();
                String deleteContact = scanner.nextLine();
                lines = Files.readAllLines(filepath);
                boolean lineExists = false; // this sets up the condition to check if the contact exists or not

                //TODO: Checks to see if contact exists
                for (String line : lines) {
                    if (line.indexOf(deleteContact) == 0) {
                        lineExists = true;
                        break;
                    }
                }

                //TODO: Delete process for if user input actually exists
                if (lineExists) {
                    deleter.clear(); // this clears the ArrayList<> deleter;
                    lines = Files.readAllLines(filepath);

                    for (String line : lines) {
                        if (line.contains(deleteContact)) { // if a name exists path will reset
                            System.out.println("Deleting Contact: " + line);
                            Files.delete(filepath);
                            Files.createFile(filepath);
                        } else {
                            deleter.add(line); // this takes in the lines that did not match the user input
                        }
                    }
                    deleter.forEach((del) -> { // this adds back to the text file
                        try {
                            Files.write(filepath, List.of(del), StandardOpenOption.APPEND);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } else {

                    //TODO: Shows the user if they typed in invalid contact information
                    System.out.println("THAT CONTACT DOESN'T EXIST!! ARE YOU TRYING TO BREAK ME!?!?");
                }
            }

            //TODO: Exit functionality
            if (answer == 5) {
                System.out.println("Goodbye! :)");
                userInteraction = false;
            }
        }
    }
}
