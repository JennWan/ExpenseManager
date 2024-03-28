package ui.gui;

import model.Expense;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayExpense extends JInternalFrame implements ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    JLabel expenseInfo;
    JButton button;
    Expense expense;

    public DisplayExpense(Expense e) {
        this.expense = e;
        this.setLayout(new FlowLayout());
        this.setTitle("Expense");
        this.setSize(WIDTH, HEIGHT);

        expenseInfo = new JLabel(e.getTitle() + ": " + e.getBalance());
        button = new JButton("Track");

        button.addActionListener(this);

        this.add(expenseInfo);
        this.add(button);

        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            TrackExpenseUI trackExpenseUI = new TrackExpenseUI(this);
        }
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpenseInfo(Expense e) {
        expenseInfo.setText(e.getTitle() + ": " + e.getBalance());
    }
}
