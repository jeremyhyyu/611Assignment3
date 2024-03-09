/*
 * Color.java
 * By Heyang Yu (jhyyu@bu.edu)
 * This class is uesd to print strings with different color
 * Completed this class with the help of chatGPT.
 * A method to get an indexed color list is also included.
 */

import java.util.ArrayList;
import java.util.List;

public class Color {
    // ANSI codes
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    // initialize color list and return it to the caller
    public static List<String> initializeColorList(String game) {
        List<String> colors = new ArrayList<>();
        if(game.equalsIgnoreCase("DotsAndBoxes")) {
            // add colors in the order which matches the order defined in d&b
            colors.add(RED);
            colors.add(BLUE);
            colors.add(YELLOW);
            colors.add(GREEN);
            colors.add(PURPLE);
            colors.add(CYAN);
            colors.add(WHITE);
            colors.add(BLACK);
        }
        if(game.equalsIgnoreCase("Quoridor")) {
            // add colors in the order which matches the order defined in d&b
            colors.add(RED);
            colors.add(BLUE);
            colors.add(YELLOW);
            colors.add(GREEN);
        }
        return colors;
    }
    // initialize color names
    public static List<String> initializeColorNames(String game) {
        List<String> colorNames = new ArrayList<>();
        if(game.equalsIgnoreCase("DotsAndBoxes")) {
            // add the names of the colors
            colorNames.add("Red");
            colorNames.add("Blue");
            colorNames.add("Yellow");
            colorNames.add("Green");
            colorNames.add("Purple");
            colorNames.add("Cyan");
            colorNames.add("White");
            colorNames.add("Black");
        }
        if(game.equalsIgnoreCase("Quoridor")) {
            // add the names of the colors
            colorNames.add("Red");
            colorNames.add("Blue");
            colorNames.add("Yellow");
            colorNames.add("Green");
        }
        return colorNames;
    }

    // set color
    public static void setColor(String color) {
        System.out.print(color);
    }

    // reset color
    public static void resetColor() {
        System.out.print(RESET);
    }

    // print colored text according to the input
    public static void print(String color, String text) {
        setColor(color);
        System.out.print(text);
        resetColor();
    }
    // with arguments
    public static void print(String color, String format, Object... args) {
        setColor(color);
        System.out.printf(format, args);
        resetColor();
    }
    
    // print line
    public static void println(String color, String text) {
        print(color, text);
        System.out.println();
    }
}
