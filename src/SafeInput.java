import java.util.Scanner;
import java.util.regex.Pattern;

public class SafeInput {

    public static String getRegExString(Scanner pipe, String prompt, String regEx) {
        String input;
        boolean isValid = false;

        do {
            System.out.print(prompt + ": ");
            input = pipe.nextLine();
            if (Pattern.matches(regEx, input)) {
                isValid = true;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        } while (!isValid);

        return input;
    }

    public static int getRangedInt(Scanner pipe, String prompt, int low, int high) {
        int result = 0;
        boolean isValid = false;

        do {
            System.out.print(prompt + " [" + low + "-" + high + "]: ");
            if (pipe.hasNextInt()) {
                result = pipe.nextInt();
                pipe.nextLine();
                if (result >= low && result <= high) {
                    isValid = true;
                } else {
                    System.out.println("Input out of range. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                pipe.nextLine(); // clear invalid input
            }
        } while (!isValid);

        return result;
    }

    public static boolean getYNConfirm(Scanner pipe, String prompt) {
        String input;
        boolean isValid = false;

        do {
            System.out.print(prompt + " [Y/N]: ");
            input = pipe.nextLine().trim().toUpperCase();
            if (input.equals("Y") || input.equals("N")) {
                isValid = true;
            } else {
                System.out.println("Invalid input. Please enter 'Y' or 'N'.");
            }
        } while (!isValid);

        return input.equals("Y");
    }

    public static String getNonZeroLenString(Scanner pipe, String prompt) {
        String input;

        do {
            System.out.print(prompt + ": ");
            input = pipe.nextLine().trim();
            if (input.length() == 0) {
                System.out.println("Input cannot be empty. Please try again.");
            }
        } while (input.length() == 0);

        return input;
    }
}
