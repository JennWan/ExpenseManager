package ui.gui;

import model.ExpenseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//creates a JFrame to gather date information
public class SetDate extends JFrame implements ActionListener {
    ExpenseManager manager;
    JButton button;
    JTextField date;
    JLabel dateLabel;

    //EFFECTS: initializes manager with today's date
    public SetDate(ExpenseManager manager) {
        this.manager = manager;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setTitle("CPSC 210: Set Today's Date");

        button = new JButton("Submit");
        button.addActionListener(this);

        date = new JTextField();
        date.setPreferredSize(new Dimension(250, 40));
        dateLabel = new JLabel("Today's Date: ");

        this.add(dateLabel);
        this.add(date);
        this.add(button);

        this.pack();
        this.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: set today's date to ExpenseManager
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            manager.setToday(date.getText());
        }
        this.dispose();
    }
}
