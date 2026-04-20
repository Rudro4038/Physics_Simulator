package view.utils;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Text {

    // Enum for text types
    public enum TextType {
        PLAIN, BOLD, ITALIC
    }

    private String content;
    private TextType type;
    private int x;
    private int y;
    private javafx.scene.text.Text textView;

    // Constructor
    public Text(String content, TextType type, int x, int y, Color color) {
        this.content = content;
        this.type = type;
        this.x = x;
        this.y = y;
        this.textView = new javafx.scene.text.Text(x, y, content);
        this.textView.setFill(color);
        updateFont();
    }

    private void updateFont() {
        FontWeight weight = FontWeight.NORMAL;
        FontPosture posture = FontPosture.REGULAR;

        if (type == TextType.BOLD) {
            weight = FontWeight.BOLD;
        }
        if (type == TextType.ITALIC) {
            posture = FontPosture.ITALIC;
        }

        this.textView.setFont(Font.font("Verdana", weight, posture, 20));
    }

    public javafx.scene.text.Text getView() {
        return textView;
    }

    // Method to print text at the specified location
    public void print() {
        System.out.println("Printing text: " + content);
        System.out.println("Type: " + type);
        System.out.println("Location: (" + x + ", " + y + ")");
    }

    // Getters and Setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        this.textView.setText(content);
    }

    public TextType getType() {
        return type;
    }

    public void setType(TextType type) {
        this.type = type;
        updateFont();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        this.textView.setX(x);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        this.textView.setY(y);
    }
}
