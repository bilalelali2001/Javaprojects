import javax.swing.*;
public class App {
    public static void main(String[] args) throws Exception {
       int boradWidht = 600;
       int boradHeight = boradWidht;
       JFrame frame = new JFrame(" Snake");
       frame.setVisible(true);
       frame.setSize(boradWidht,boradHeight);
       frame.setLocationRelativeTo(null);
       frame.setResizable(false);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       SnakeGame snakegame = new SnakeGame(boradWidht, boradHeight);
       frame.add(snakegame);
       frame.pack();
       snakegame.requestFocus();
    }
}
