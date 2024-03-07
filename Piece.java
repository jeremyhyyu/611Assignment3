/*
 * Piece.java
 * By Heyang Yu (jhyyu@bu.edu)
 * The class for pieces.
 * Two attributes, value and color, color here are represented by string
 */
public class Piece {
    private String value;
    private String color; // 0: white, 1: red, 2: blue
    public static final int WHITE = 0;
    public static final int RED = 1;
    public static final int BLUE = 2;
    // constructors
    public Piece(String value, String color) {
        setValue(value);
        setColor(color);
    }
    public Piece(String color) {
        // for edge pieces, no value
        setColor(color);
    }
    // getters
    public String getValue() {
        return this.value;
    }
    public String getColor() {
        return this.color;
    }
    // setters
    public void setValue(String value) {
        this.value = value;
    }
    public void setColor(String color) {
        this.color = color;
    }
}