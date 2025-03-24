package calculator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator {
 
    
    private final Frame frame; 
    private final TextField textField; 
    private String currentInput = ""; 
    private double firstNum = 0; 
    private String operator = ""; 

    public Calculator() {
        
        frame = new Frame("Calculator");
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());
        frame.setBackground(Color.BLACK);

       
        textField = new TextField();
        textField.setEditable(false); 
        textField.setFont(new Font("Arial", Font.PLAIN, 35));
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.WHITE);
        textField.setPreferredSize(new Dimension(80, 80));

        frame.add(textField, BorderLayout.NORTH);

        
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10)); // 5 rows, 4 columns
        buttonPanel.setBackground(Color.GRAY);

        
        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "C", "+",
            "√", "%", "^", "=" 
        };

        
        for (String label : buttonLabels) {
            Button button = new Button(label);
            button.setFont(new Font("Arial", Font.PLAIN, 24));
            button.setBackground(Color.DARK_GRAY);
            button.setForeground(Color.WHITE);
            button.setFocusTraversalKeysEnabled(false);
            button.addActionListener(new ButtonClickListener(button)); 
            buttonPanel.add(button);
        }

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);

        
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent we) {
                System.exit(0);
            }
        });
    }

    
    private class ButtonClickListener implements ActionListener {
        private final Button button;
        
        public ButtonClickListener(Button button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand(); 

            switch (command) {
                case "=" -> calculate(); 
                case "/", "*", "-", "+" -> {
                    if (!currentInput.isEmpty()) {
                        firstNum = Double.parseDouble(currentInput); 
                        operator = command; 
                        currentInput = ""; 
                        textField.setText("");
                    }
                }
                case "C" -> { 
                    currentInput = "";
                    firstNum = 0;
                    operator = "";
                    textField.setText(currentInput);
                }
                case "√" -> { 
                    if (!currentInput.isEmpty()) {
                        double num = Double.parseDouble(currentInput);
                        currentInput = String.valueOf(Math.sqrt(num));
                        textField.setText(currentInput);
                    }
                }
                case "%" -> { 
                    if (!currentInput.isEmpty()) {
                        double num = Double.parseDouble(currentInput);
                        currentInput = String.valueOf(num / 100);
                        textField.setText(currentInput);
                    }
                }
                case "^" -> { 
                    if (!currentInput.isEmpty()) {
                        double num = Double.parseDouble(currentInput);
                        currentInput = String.valueOf(Math.pow(num, 2));
                        textField.setText(currentInput);
                    }
                }
                default -> { 
                    if (currentInput.equals("0")) {
                        currentInput = command;
                    } else {
                        currentInput += command;
                    }
                    textField.setText(currentInput);
                }
            }
        }

       
        private void calculate() {
            if (currentInput.isEmpty()) return;

            double secondNum = Double.parseDouble(currentInput);
            double result = 0;

            switch (operator) {
                case "+" -> result = firstNum + secondNum;
                case "-" -> result = firstNum - secondNum;
                case "*" -> result = firstNum * secondNum;
                case "/" -> {
                    if (secondNum != 0) {
                        result = firstNum / secondNum;
                    } else {
                        textField.setText("Error"); 
                        return;
                    }
                }
            }

            textField.setText(String.valueOf(result)); 
            currentInput = String.valueOf(result); 
        }
    }

    public static void main(String[] args) {
        new Calculator(); 
    }
}
