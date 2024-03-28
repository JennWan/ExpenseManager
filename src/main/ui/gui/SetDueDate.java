package ui.gui;

import model.Expense;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//creates a JFrame to gather Due Date information
public class SetDueDate extends JFrame implements ActionListener {
    DisplayExpense displayExpense;
    Expense expense;
    JButton button;
    JTextField date;
    JLabel dateLabel;

    //EFFECTS: creates a JFrame
    public SetDueDate(DisplayExpense e) {
        this.displayExpense = e;
        this.expense = displayExpense.getExpense();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setTitle("CPSC 210: Set Expense Due Date");

        button = new JButton("Submit");
        button.addActionListener(this);

        date = new JTextField();
        date.setPreferredSize(new Dimension(250, 40));
        dateLabel = new JLabel("Due Date: ");

        this.add(dateLabel);
        this.add(date);
        this.add(button);

        this.pack();
        this.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: process button pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            expense.setDueDate(date.getText());
            System.out.println("Due Date Setup Completed!");
        }
        this.dispose();
    }
}
