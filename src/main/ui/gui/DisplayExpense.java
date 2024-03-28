package ui.gui;

import model.Expense;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Displays an Expense in JInternalFrame with a Track and setDueDate option
public class DisplayExpense extends JInternalFrame implements ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    JLabel expenseInfo;
    JButton trackButton;
    JButton dueDateButton;
    Expense expense;

    //EFFECTS: creates JInternalFrame
    public DisplayExpense(Expense e) {
        this.expense = e;
        this.setLayout(new FlowLayout());
        this.setTitle("Expense");
        this.setSize(WIDTH, HEIGHT);

        expenseInfo = new JLabel(e.getTitle() + ": " + e.getBalance());
        trackButton = new JButton("Track");
        dueDateButton = new JButton("Set Due Date");

        trackButton.addActionListener(this);
        dueDateButton.addActionListener(this);

        this.add(expenseInfo);
        this.add(trackButton);
        this.add(dueDateButton);

        this.pack();
        this.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: process the button pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == trackButton) {
            TrackExpenseUI trackExpenseUI = new TrackExpenseUI(this);
        }
        if (e.getSource() == dueDateButton) {
            SetDueDate setDueDate = new SetDueDate(this);
        }
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpenseInfo(Expense e) {
        expenseInfo.setText(e.getTitle() + ": " + e.getBalance());
    }
}
