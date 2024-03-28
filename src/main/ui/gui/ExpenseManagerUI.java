package ui.gui;

import model.Expense;
import model.ExpenseManager;

import javax.swing.*;
import java.awt.*;

//Expense Tracking Manager application
public class ExpenseManagerUI extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    ExpenseManager manager;
    JDesktopPane desktop;
    JMenuBar menuBar;
    GridBagConstraints gbc;
    JTextField incomeToUse;
    JLabel incomeLabel;

    //EFFECTS: creates a new ExpenseManagerUI
    public ExpenseManagerUI() {
        manager = new ExpenseManager();
        desktop = new JDesktopPane();
        menuBar = new MenuBar(this);
        manager.setExpenseManagerUI(this);


        this.setContentPane(desktop);
        this.setTitle("CPSC210: Expense Tracker Console");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        incomeLabel = new JLabel("Available Income To Allocate");
        incomeToUse = new JTextField(String.valueOf(manager.getIncomeToUse()));

        desktop.add(incomeLabel);
        desktop.add(incomeToUse);
        desktop.add(menuBar, gbc);

        this.setVisible(true);
        SetDate todayDate = new SetDate(manager);
        SetEarning incomeToUse = new SetEarning(this);
    }

    //MODIFIES: this
    //EFFECTS: updates UI with changes in ExpenseManager
    public String update() {
        String str = "Expense setup completed!";
        System.out.println(str);
        if (manager.getExpenseList().isEmpty() == false) {
            Expense e = manager.getExpenseList().get(manager.getExpenseList().size() - 1);
            DisplayExpense displayExpense = new DisplayExpense(e);
            desktop.add(displayExpense, gbc);
            incomeToUse.setText(String.valueOf(manager.getIncomeToUse()));
        }
        return str;
    }

    public ExpenseManager getManager() {
        return manager;
    }

    public void setManager(ExpenseManager expenseManager) {
        manager = expenseManager;
    }

    public JTextField getIncomeToUse() {
        return incomeToUse;
    }
}