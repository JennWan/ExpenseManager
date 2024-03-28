package ui.gui;

import model.Expense;
import model.ExpenseManager;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

//creates a MenuBar leading to actions to be done with the application
public class MenuBar extends JMenuBar implements ActionListener {
    private static final String JSON_STORE = "./data/ExpenseManager.json";
    private ExpenseManagerUI managerUI;
    private ExpenseManager manager;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JMenuItem newExpense;
    private JMenu expenseActions;
    private JMenu fileMenu;
    private JMenuItem saveItem;
    private JMenuItem loadItem;
    private JMenuItem exitItem;
    private JMenuItem paidOffTrue;
    private JMenuItem paidOffFalse;
    private JMenuItem dueNDays;
    private JMenuItem totalMonthlyBalance;
    private ImageIcon saveIcon;
    private ImageIcon loadIcon;
    private ImageIcon exitIcon;
    private Double monthlyBalance = 0.0;

    //EFFECTS: creates a JMenuBar
    public MenuBar(ExpenseManagerUI managerUI) {
        this.managerUI = managerUI;
        this.manager = managerUI.getManager();
        newExpense = new JMenuItem("New Expense");
        expenseActions = new JMenu("Action Options");
        fileMenu = new JMenu("File");

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        paidOffTrue = new JMenuItem("Paid Off Expense");
        paidOffFalse = new JMenuItem("Expenses To Be Paid Off");
        dueNDays = new JMenuItem("Expenses Due in N Days");
        totalMonthlyBalance = new JMenuItem("Total Monthly Balance");

        saveItem = new JMenuItem("Save Progress");
        loadItem = new JMenuItem("Load Progress");
        exitItem = new JMenuItem("EXIT");

        initializeMenu();

        this.add(fileMenu);
        this.add(newExpense);
        this.add(expenseActions);
    }

    //MODIFIES: this
    //EFFECTS: add components to the menu bar
    public void initializeMenu() {
        saveItem.addActionListener(this);
        loadItem.addActionListener(this);
        exitItem.addActionListener(this);

        newExpense.addActionListener(this);

        paidOffTrue.addActionListener(this);
        paidOffFalse.addActionListener(this);
        dueNDays.addActionListener(this);
        totalMonthlyBalance.addActionListener(this);

        mnemonicKey();

        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        expenseActions.add(paidOffTrue);
        expenseActions.add(paidOffFalse);
        expenseActions.add(dueNDays);
        expenseActions.add(totalMonthlyBalance);
    }

    //MODIFIES: this
    //EFFECTS: add shortcuts to the MenuBar
    public void mnemonicKey() {
        fileMenu.setMnemonic(KeyEvent.VK_F); //Alt + m
        newExpense.setMnemonic(KeyEvent.VK_N); //Alt + n
        expenseActions.setMnemonic(KeyEvent.VK_A); //Alt + a

        saveItem.setMnemonic(KeyEvent.VK_S); //s for save
        loadItem.setMnemonic(KeyEvent.VK_L); //l for load
        exitItem.setMnemonic(KeyEvent.VK_X); //x for exit

        paidOffTrue.setMnemonic(KeyEvent.VK_T); //t for paidOff = True
        paidOffFalse.setMnemonic(KeyEvent.VK_F); //t for paidOff = False
        dueNDays.setMnemonic(KeyEvent.VK_D); //d for due
        totalMonthlyBalance.setMnemonic(KeyEvent.VK_B); //b for monthly Balance
    }

    //MODIFIES: this
    //EFFECTS: process the actions pressed on the MenuBar into 3
    @Override
    public void actionPerformed(ActionEvent e) {
        processFileMenu(e);

        if (e.getSource() == newExpense) {
            MakeExpenseUI expenseUI = new MakeExpenseUI(managerUI);
        }

        processActions(e);
    }

    //MODIFIES: this
    //EFFECTS: process the actions from MenuBar
    private void processActions(ActionEvent e) {
        if (e.getSource() == paidOffTrue) {
            for (Expense expense : manager.completedExpenses(true)) {
                System.out.println(expense.getTitle());
            }
        }
        if (e.getSource() == paidOffFalse) {
            for (Expense expense : manager.completedExpenses(false)) {
                System.out.println(expense.getTitle());
            }
        }
        if (e.getSource() == dueNDays) {
            AskUpdateDate askUpdateDate = new AskUpdateDate(managerUI);
        }
        if (e.getSource() == totalMonthlyBalance) {
            for (int i = 0; i <= 12; i++) {
                if (i <= 9) {
                    monthlyBalance = manager.getMonthlyBalance("0" + i);
                } else {
                    monthlyBalance = manager.getMonthlyBalance("i");
                }
                System.out.println("Balance for month " + i + " is: " + monthlyBalance);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: process the file menu options
    private void processFileMenu(ActionEvent e) {
        if (e.getSource() == saveItem) {
            saveExpenseManager();
            System.out.println("Progress Saved!");
        }
        if (e.getSource() == loadItem) {
            loadExpenseManager(managerUI);
            System.out.println("Progress Loaded!");
        }
        if (e.getSource() == exitItem) {
            System.exit(1);
        }
    }

    //MODIFIES: this
    //EFFECTS: saves the manager to file
    private void saveExpenseManager() {
        try {
            jsonWriter.open();
            jsonWriter.write(manager);
            jsonWriter.close();
            System.out.println("Saved ExpenseManager to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //MODIFIES: this
    //EFFECTS: loads manager from file
    private void loadExpenseManager(ExpenseManagerUI ui) {
        try {
            manager = jsonReader.read(ui);
            System.out.println("Loaded ExpenseManager from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
