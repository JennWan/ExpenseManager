package ui;

import model.Expense;
import model.ExpenseManager;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.gui.ExpenseManagerUI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

//Referenced the TellerApp application
//Referenced from the JsonSerialization Demo
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
//Expense Tracker application
public class ExpenseTrackerApp {
    private static final String JSON_STORE = "./data/ExpenseManager.json";
    private Double income;
    private Scanner input;
    private ExpenseManager manager;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private ExpenseManagerUI ui = new ExpenseManagerUI();

    //EFFECTS: runs the expense tracker application
    public ExpenseTrackerApp() {
        runManager();
    }

    //MODIFIES: this
    //EFFECTS: processes user input
    private void runManager() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
    }

    //MODIFIES: this
    //EFFECTS: asks the user for their annual income information and initializes ExpenseManager
    private void init() {
        this.manager = new ExpenseManager();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        System.out.println("\nWhat is your annual income (with two decimal places)?");
        Double values = null;
        values = Double.valueOf(input.next());
        processInit(values);
    }

    //REQUIRES: earning must be >= 0
    //MODIFIES: this
    //EFFECTS: processes user command
    private void processInit(Double earning) {
        this.income = earning;
        manager.setIncomeToUse(earning);
        System.out.println("\nWhat day are you using this app yymmdd?");
        manager.setToday(input.next());
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ne -> new expense");
        System.out.println("\nb -> list of expenses and its corresponding balance");
        System.out.println("\nt -> track expense");
        System.out.println("\nf -> filter viewing options");
        System.out.println("\ns -> save ExpenseManager");
        System.out.println("\nl -> load ExpenseManager");
        System.out.println("\nq -> quit application");
    }

    //MODIFIES: this
    //EFFECTS: processes user command
    private void processCommand(String todo) {
        if (todo.equals("e")) {
            System.out.println("What is the name of this expense?");
            String name = input.next();
            makeExpensePrompt(name);
        } else if (todo.equals("b")) {
            viewExpense();
        } else if (todo.equals("t")) {
            dealTrackExpense();
        } else if (todo.equals("f")) {
            filterExpense();
        } else if (todo.equals("s")) {
            saveExpenseManager();
        } else if (todo.equals("l")) {
            loadExpenseManager();
        } else {
            System.out.println("Retry a valid selection!");
        }
    }

    //REQUIRES: input has to be <= 2 decimal point of string containing integers only
    //MODIFIES: this
    //EFFECTS: display and process user command on tracking expenses
    private void dealTrackExpense() {
        System.out.println("How much in dollar has been paid off? (2 decimal point)");
        String toPrint = trackExpense(Double.valueOf(input.next()));
        if (toPrint == null) {
            System.out.println("Return valid expense name");
        } else {
            System.out.print(toPrint);
        }
    }

    //MODIFIES: this
    //EFFECTS: display action for the user regarding tracking expenses
    //  and returns the new balance else returns null
    private String trackExpense(Double amount) {
        System.out.println("Which expense would you like to track?");
        String expenseName = input.next();
        for (Expense e : manager.getExpenseList()) {
            if (expenseName.equals(e.getTitle())) {
                return "Remainder to pay: " + e.payAmount(amount);
            }
        }
        return null;
    }

    //REQUIRES: input has to be <= 2 decimal point of string containing integers only
    //MODIFIES: this
    //EFFECTS: display and process user command regarding creating an expense
    //  also creates a new Expense object added to the expenseManager
    private void makeExpensePrompt(String name) {
        System.out.println("How much $ would you allocate this expense? (in two decimal places)");
        Double setAmount = Double.valueOf(input.next());
        Expense expense = new Expense(name, setAmount);
        manager.addExpense(expense);
//        manager.decreaseIncomeToUse(setAmount);
        expense.setDueDate(expenseDueDate());
        System.out.println("Expense setup completed!");
        System.out.println("Remainder of income to allocate: " + manager.getIncomeToUse());
    }

    //MODIFIES: this
    //EFFECTS: display action for the user regarding creating an expenses due date
    private String expenseDueDate() {
        System.out.println("Would you like to give this expense a due date?");
        System.out.println("\nSelect from:");
        System.out.println("\ny -> yes");
        System.out.println("\nn -> no");
        String command1 = input.next();
        return processDueDate(command1);
    }

    //MODIFIES: this
    //EFFECTS: display and processes user input regarding Expense due date
    private String processDueDate(String todo) {
        if (todo.equals("y")) {
            System.out.println("What is the due date for this expense (yymmdd)");
            return input.next();
        } else if (todo.equals("n")) {
            return null;
        } else {
            System.out.println("Retry a valid selection!");
            return null;
        }
    }

    //EFFECTS: prints list of Expenses with its corresponding balance
    private void viewExpense() {
        List<Expense> list = manager.getExpenseList();
        for (Expense e:list) {
            System.out.println(e.getTitle() + "\n: " + e.getBalance());
        }
    }

    //MODIFIES: this
    //EFFECTS: displays menu of filtering Expense options to user
    private void filterExpense() {
        System.out.println("\nSelect from:");
        System.out.println("\np -> expense completely paid off");
        System.out.println("\nr -> expense to be paid off");
        System.out.println("\nn -> expense due in n days");
        System.out.println("\nm -> total monthly balance");
        String command1 = input.next();
        processFiltered(command1);
    }

    //MODIFIES: this
    //EFFECTS: processes and print filtered list of Expense to user
    private void processFiltered(String todo) {
        if (todo.equals("p")) {
            for (Expense e: manager.completedExpenses(true)) {
                System.out.println(e.getTitle());
            }
        } else if (todo.equals("r")) {
            for (Expense e: manager.completedExpenses(false)) {
                System.out.println(e.getTitle() + "\n :" + e.getBalance());
            }
        } else if (todo.equals("n")) {
            System.out.println("\nWhat day are you using this app yymmdd?");
            manager.setToday(input.next());
            processDueInNDay();
        } else if (todo.equals("m")) {
            System.out.println("What month would you like to see?");
            String month = input.next();
            monthlyBalance(month);
        } else {
            System.out.println("Retry a valid selection!");
        }
    }

    private void processDueInNDay() {
        System.out.println("Within what range in days would you like to see?");
        int days = Integer.parseInt(input.next());
        dueInNDays(days);
    }

    //EFFECTS: prints the filtered list of Expenses due in N days
    private void dueInNDays(int n) {
        if (!manager.dueWithinDays(n, manager.getToday()).isEmpty()) {
            System.out.println(manager.dueWithinDays(n, manager.getToday()));
        } else {
            System.out.println("Nothing is due!");
        }
    }

    //EFFECTS: prints the filtered list of given due date month's Expenses
    private void monthlyBalance(String month) {
        System.out.println(manager.getMonthlyBalance(month));
    }

    // EFFECTS: saves the manager to file
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

    // MODIFIES: this
    // EFFECTS: loads manager from file
    private void loadExpenseManager() {
        try {
            manager = jsonReader.read(ui);
            System.out.println("Loaded ExpenseManager from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
