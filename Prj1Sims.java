
package prj1sims;

import java.awt.*;
import java.awt.event.*;
import static java.lang.Character.isLetter;
import static java.lang.Character.isDigit;
import java.util.Stack;
import java.util.StringTokenizer;
import javax.swing.*;


public class Prj1Sims {
    public static class GUI extends JFrame {
        
        private JLabel inputLabel, resultLabel, resultOutput;
        private JButton evaluate;
        private JTextField input;
        
        public GUI() {
            setLayout(new GridBagLayout());
            GridBagConstraints window = new GridBagConstraints();
            
            window.insets = new Insets (1,1,1,1);
            
            inputLabel = new JLabel("Enter Infix Expression:");
            window.fill = GridBagConstraints.HORIZONTAL;
            window.gridx = 0;
            window.gridy = 0;
            window.gridwidth = 2;
            add(inputLabel, window);
            
            resultLabel = new JLabel("               Result:");
            window.fill = GridBagConstraints.HORIZONTAL;
            window.gridx = 0;
            window.gridy = 3;
            add(resultLabel, window);
            
            resultOutput = new JLabel("");
            window.fill = GridBagConstraints.HORIZONTAL;
            window.gridx = 2;
            window.gridy = 3;
            add(resultOutput, window);
            
            evaluate = new JButton("Evaluate");
            window.fill = GridBagConstraints.HORIZONTAL;
            window.gridx = 2;
            window.gridy = 2;
            add(evaluate, window);
            
            input = new JTextField(20);
            window.fill = GridBagConstraints.HORIZONTAL;
            window.gridx = 2;
            window.gridy = 0;
            window.gridwidth = 3;
            add(input, window);
            
            event mathAction = new event();
            evaluate.addActionListener(mathAction);
        }
        
        public class event implements ActionListener {
            
            public void actionPerformed (ActionEvent mathAction) {
                
                String operators = mathAction.getActionCommand();
                Stack<Integer> operandStack = new Stack<>();
                Stack<String> operatorStack = new Stack<>();
                int result = 0;
                

                if(operators.equals("Evaluate")) {
                    //tokenize string
                    String initialInput = input.getText().replaceAll(" ", "");
                    StringBuilder inputString = new StringBuilder(initialInput);
                    final JPanel panel = new JPanel();
                    
                    //Loop that inserts a , before and after each operator to use as a token separator 
                    for (int i = 0; i < inputString.length(); i++) {
                        if (inputString.charAt(i) == '*' || inputString.charAt(i) == '/' || inputString.charAt(i) == '+' || inputString.charAt(i) == '-' || inputString.charAt(i) == '(' || inputString.charAt(i) == ')') {
                            inputString.insert(i+1, ',');
                            inputString.insert(i, ',');
                            i++;
                       } else if (isLetter(inputString.charAt(i))) {
                           JOptionPane.showMessageDialog(panel, "Please enter Integers and Operators only", "Warning", JOptionPane.WARNING_MESSAGE);
                       } 
                    }

                    StringTokenizer st = new StringTokenizer(inputString.toString(), ",");
                    
                    while (st.hasMoreTokens()) {
                    //get the next token 
                    String currentToken = st.nextToken();
                    System.out.println(operandStack.toString());
                    System.out.println(operatorStack.toString());
                    System.out.println();
                        //if (operand)
                        if (isDigit(currentToken.charAt(0))) {
                            operandStack.push(Integer.parseInt(currentToken));
                                if (operandStack.size() >= 2 && !(operatorStack.empty())) {
                                    try {
                                        if ("*".equals(operatorStack.peek()) || "/".equals(operatorStack.peek())) {
                                            operandStack.push(calculation(operandStack.pop(), operandStack.pop(), operatorStack.pop()));
                                        }
                                    }catch (ArithmeticException e) {
                                        JOptionPane.showMessageDialog(panel, "Cannot Divide By Zero", "Warning", JOptionPane.WARNING_MESSAGE);
                                    }
                                }
                        }else if ("(".equals(currentToken)) {
                            operatorStack.push(currentToken);
                        } else if (")".equals(currentToken)) {
                            while (!"(".equals(operatorStack.peek())) { //doesn't apply pemdas within the parenthesis
                                operandStack.push(calculation(operandStack.pop(),operandStack.pop(),operatorStack.pop()));
                            }
                                operatorStack.pop();
                        } else if (isOperator(currentToken)) {
                            operatorStack.push(currentToken);                           
                        }
                    }
                    while (!(operatorStack.empty()) && !("(".equals(operatorStack.peek()))) {
                        operandStack.push(calculation(operandStack.pop(),operandStack.pop(),operatorStack.pop()));
                    }
                    
                    resultOutput.setText("" + operandStack.peek());
                }        
            }
        }
        
        public boolean isOperator(String token) {
            if ("*".equals(token) || "/".equals(token) || "+".equals(token) || "-".equals(token)) {
                return true;
            } else {
                return false;
            }
        }
        
        public int calculation(int operand1, int operand2, String operator1) {
            int calculation = 0;
            
            switch (operator1) {
                case "*": calculation = operand1 * operand2;
                break;
                case "/": calculation = operand2 / operand1;
                break;
                case "+": calculation = operand1 + operand2;
                break;
                case "-": calculation = operand2 - operand1;
                break;
            }
            return calculation;
        }
    }
        
    public static void main(String[] args) {
        GUI Box = new GUI ();

        Box.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Box.setLocationRelativeTo(null);
        Box.setVisible(true);
        Box.setSize(500,200);
        Box.setTitle("Infix Expression Evaluator");
        Box.setResizable(false);

    }
    
 }
