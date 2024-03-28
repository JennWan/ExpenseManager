package ui.gui;

import model.ExpenseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetEarning extends JFrame implements ActionListener {
    ExpenseManagerUI managerUI;
    JTextField incomeToUse;
    JButton button;
    JTextField earning;
    JLabel earningLabel;

    public SetEarning(ExpenseManagerUI managerUI) {
        this.managerUI = managerUI;
        incomeToUse = managerUI.getIncomeToUse();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setTitle("CPSC 210: Set Earning Available To Allocate");

        button = new JButton("Submit");
        button.addActionListener(this);

        earning = new JTextField();
        earning.setPreferredSize(new Dimension(250, 40));
        earningLabel = new JLabel("Annual Earning: ");

        this.add(earningLabel);
        this.add(earning);
        this.add(button);

        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            managerUI.getManager().setIncomeToUse(Double.valueOf(earning.getText()));
            incomeToUse.setText(String.valueOf(earning.getText()));
        }
        this.dispose();
    }
}
