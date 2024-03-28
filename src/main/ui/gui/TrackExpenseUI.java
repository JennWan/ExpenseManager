package ui.gui;

import model.Expense;
import model.ExpenseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//creates JFrame to gather information on amount to track
public class TrackExpenseUI extends JFrame implements ActionListener {
    DisplayExpense displayExpense;
    Expense expense;
    JButton button;
    JTextField amount;
    JLabel amountLabel;

    //EFFECTS: creates JFrame
    public TrackExpenseUI(DisplayExpense e) {
        this.displayExpense = e;
        this.expense = displayExpense.getExpense();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setTitle("CPSC 210: Track Expense");

        button = new JButton("Submit");
        button.addActionListener(this);

        amount = new JTextField();
        amount.setPreferredSize(new Dimension(250, 40));
        amountLabel = new JLabel("Amount to track:");

        this.add(amountLabel);
        this.add(amount);
        this.add(button);

        this.pack();
        this.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: process button pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            System.out.println(amount.getText() + " added to " + expense.getTitle());
            expense.payAmount(Double.valueOf(amount.getText()));
            displayExpense.setExpenseInfo(expense);
        }
        this.dispose();
    }
}
