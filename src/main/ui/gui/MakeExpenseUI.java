package ui.gui;

import model.Expense;
import model.ExpenseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//creates a JFrame to gather information in order to initialize an Expense
public class MakeExpenseUI extends JFrame implements ActionListener {
    ExpenseManagerUI managerUI;
    ExpenseManager em;
    JTextField incomeToUse;
    JButton button;
    JTextField title;
    JTextField amount;
    JLabel titleLabel;
    JLabel amountLabel;

    //EFFECTS: creates a JFrame
    public MakeExpenseUI(ExpenseManagerUI managerUI) {
        this.managerUI = managerUI;
        this.em = managerUI.getManager();
        this.incomeToUse = managerUI.getIncomeToUse();

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setTitle("CPSC 210: Add New Expense");

        button = new JButton("Submit");
        button.addActionListener(this);

        title = new JTextField();
        title.setPreferredSize(new Dimension(250, 40));
        titleLabel = new JLabel("Expense Title:");

        amount = new JTextField();
        amount.setPreferredSize(new Dimension(250, 40));
        amountLabel = new JLabel("Amount to allocate:");


        this.add(titleLabel);
        this.add(title);
        this.add(amountLabel);
        this.add(amount);
        this.add(button);

        this.pack();
        this.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: create an Expense then add it to ExpenseManager
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            Expense expense = new Expense(title.getText(), Double.valueOf(amount.getText()));
            em.addExpense(expense);
            this.dispose();
        }
    }
}
