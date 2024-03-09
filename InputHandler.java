/*
 * InputHandler.java
 * By Heyang Yu (jhyyu@bu.edu)
 * This class is to handle user input in the terminal.
 */
import java.util.Arrays;
import java.util.Scanner;

public class InputHandler {
    // get a integer within a range
    public static int getAnIntegerInARange(String variableName, int min, int max) {
        Scanner scanner = new Scanner(System.in);
        int returnValue = -1;
        do {
            System.out.print("Input an integer within " + Integer.toString(min) + "-" + Integer.toString(max) + " as the " + variableName + ": ");
            // first check if the input is an integer
            while (!scanner.hasNextInt()) {
                System.out.print("Your " +  variableName +" must be an integer, input again: ");
                scanner.next();
            }
            returnValue = scanner.nextInt();
            scanner.nextLine();
            // then check if the input is within the range
            if (returnValue < min || returnValue > max) {
                System.out.println("Input out of range!");
            }
        } while (returnValue < min || returnValue > max);

        // return
        return returnValue;
    }
    // get a non-empty string
    public static String getANonEmptyString(String message) {
        String returnString = "";
        Scanner scanner = new Scanner(System.in);
        System.out.print(message);
        returnString = scanner.nextLine();
        while(returnString.length() == 0) {
            System.out.print("Input cannot be empty, input again: ");
            returnString = scanner.nextLine();
        }
        return returnString;
    }
    // get a string witin a valid set
    public static String getAValidChoiceString(String prompt, String[] validInputs) {
        Scanner scanner = new Scanner(System.in);
        boolean isValidInput = false;
        String input = "";
        System.out.print(prompt);
        while(!isValidInput) {
            input = scanner.nextLine();
            if (Arrays.asList(validInputs).contains(input)) {
                isValidInput = true;
            } else {
                System.out.print("Invalid input! " + prompt);
            }
        }

        return input;
    }
    public static void main(String[] args) {
        String teamname = getAValidChoiceString("Please input side", new String[]{"a", "1", "wq"});
        System.out.println(teamname);
    }
}
