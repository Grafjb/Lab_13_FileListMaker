import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;


public class Main {
    private static ArrayList<String> myArrList = new ArrayList<>();
    private static boolean needsToBeSaved = false;
    private static String currentFilename = "";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String menu = "A - Add\nD - Delete\nI - Insert\nM - Move\nV - View\nO - Open\nS - Save\nC - Clear\nQ - Quit";
        boolean quit = false;

        while (!quit) {
            displayList();
            System.out.println(menu);
            String choice = SafeInput.getRegExString(in, "Enter a choice", "[AaDdIiMmVvOoSsCcQq]");
            switch (choice.toUpperCase()) {
                case "A":
                    addItem();
                    break;
                case "D":
                    deleteItem();
                    break;
                case "I":
                    insertItem();
                    break;
                case "M":
                    moveItem();
                    break;
                case "V":
                    printList();
                    break;
                case "O":
                    openList();
                    break;
                case "S":
                    saveList();
                    break;
                case "C":
                    clearList();
                    break;
                case "Q":
                    quit = SafeInput.getYNConfirm(in, "Are you sure you want to quit?");
                    if (quit && needsToBeSaved) {
                        boolean save = SafeInput.getYNConfirm(in, "You have unsaved changes. Do you want to save them?");
                        if (save) {
                            saveList();
                        }
                    }
                    break;
            }
        }
    }

    private static void addItem() {
        Scanner in = new Scanner(System.in);
        String item = SafeInput.getNonZeroLenString(in, "Enter the item to add");
        myArrList.add(item);
        needsToBeSaved = true;
    }

    private static void deleteItem() {
        Scanner in = new Scanner(System.in);
        if (myArrList.isEmpty()) {
            System.out.println("The list is empty. Nothing to delete.");
            return;
        }
        int index = SafeInput.getRangedInt(in, "Enter the item number to delete", 1, myArrList.size()) - 1;
        myArrList.remove(index);
        needsToBeSaved = true;
    }

    private static void insertItem() {
        Scanner in = new Scanner(System.in);
        if (myArrList.isEmpty()) {
            System.out.println("The list is empty. Adding item to the start.");
            String item = SafeInput.getNonZeroLenString(in, "Enter the item to add");
            myArrList.add(0, item);
        } else {
            int index = SafeInput.getRangedInt(in, "Enter the position to insert the item", 1, myArrList.size()) - 1;
            String item = SafeInput.getNonZeroLenString(in, "Enter the item to insert");
            myArrList.add(index, item);
        }
        needsToBeSaved = true;
    }

    private static void moveItem() {
        Scanner in = new Scanner(System.in);
        if (myArrList.isEmpty()) {
            System.out.println("The list is empty. Nothing to move.");
            return;
        }
        int fromIndex = SafeInput.getRangedInt(in, "Enter the item number to move", 1, myArrList.size()) - 1;
        int toIndex = SafeInput.getRangedInt(in, "Enter the new position for the item", 1, myArrList.size()) - 1;
        String item = myArrList.remove(fromIndex);
        myArrList.add(toIndex, item);
        needsToBeSaved = true;
    }

    private static void printList() {
        displayList();
    }

    private static void openList() {
        Scanner in = new Scanner(System.in);
        if (needsToBeSaved) {
            boolean save = SafeInput.getYNConfirm(in, "You have unsaved changes. Do you want to save them?");
            if (save) {
                saveList();
            }
        }
        String filename = SafeInput.getNonZeroLenString(in, "Enter the filename to open") + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            myArrList.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                myArrList.add(line);
            }
            currentFilename = filename;
            needsToBeSaved = false;
            System.out.println("List loaded from " + filename);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static void saveList() {
        Scanner in = new Scanner(System.in);
        if (currentFilename.isEmpty()) {
            currentFilename = SafeInput.getNonZeroLenString(in, "Enter the filename to save as") + ".txt";
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(currentFilename))) {
            for (String item : myArrList) {
                writer.println(item);
            }
            needsToBeSaved = false;
            System.out.println("List saved to " + currentFilename);
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    private static void clearList() {
        myArrList.clear();
        needsToBeSaved = true;
    }

    private static void displayList() {
        if (myArrList.isEmpty()) {
            System.out.println("The list is empty.");
        } else {
            for (int i = 0; i < myArrList.size(); i++) {
                System.out.println((i + 1) + ": " + myArrList.get(i));
            }
        }
    }
}
