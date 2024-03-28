package ui.gui;

import model.Expense;
import model.ExpenseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//creates JFrame to process filter option DueNDays Away
public class DueNDays extends JFrame implements ActionListener {
    ExpenseManager manager;
    JButton button;
    JTextField ndays;
    JLabel dateLabel;

    //EFFECTS: initializes manager with today's date
    public DueNDays(ExpenseManager manager) {
        this.manager = manager;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setTitle("CPSC 210: Select 'N' for N Days Away Due Dates");

        button = new JButton("Submit");
        button.addActionListener(this);

        ndays = new JTextField();
        ndays.setPreferredSize(new Dimension(250, 40));
        dateLabel = new JLabel("For expenses how many days away: ");

        this.add(dateLabel);
        this.add(ndays);
        this.add(button);

        this.pack();
        this.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: process button pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            dueInNDays(Integer.parseInt(ndays.getText()));
        }
    }

    //EFFECTS: prints the filtered list of Expenses due in N days
    private void dueInNDays(int n) {
        if (!manager.dueWithinDays(n, manager.getToday()).isEmpty()) {
            for (Expense e : manager.dueWithinDays(n, manager.getToday())) {
                System.out.println(e.getTitle());
            }
        } else {
            System.out.println("Nothing is due!");
        }
        this.dispose();
    }
}
