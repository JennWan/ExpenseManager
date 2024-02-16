package ui;

import model.Expense;
import model.ExpenseManager;

import java.util.List;
import java.util.Scanner;

public class ExpenseTrackerApp {
    private Double income;
    private Scanner input;
    private ExpenseManager manager;

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

    //EFFECTS: asks the user for earning information
    private void init() {
        this.manager = new ExpenseManager();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        System.out.println("\nWhat is your annual income (with two decimal places)?");
        Double values = null;
        values = Double.valueOf(input.next());
        processInit(values);
    }

    //REQUIRES: earning must be >= 0
    //MODIFIES: this
    //EFFECTS:
    private void processInit(Double earning) {
        this.income = earning;
        manager.setIncomeToUse(earning);
        System.out.println("\nWhat day are you using this app yymmdd?");
        manager.setToday(input.next());
    }

    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ne -> new expense");
        System.out.println("\nb -> list of expenses and its corresponding balance");
        System.out.println("\nf -> filter viewing options");
        System.out.println("\nq -> quit application");
    }

    private void processCommand(String todo) {
        if (todo.equals("e")) {
            System.out.println("What is the name of this expense?");
            String name = input.next();
            makeExpensePrompt(name);
        } else if (todo.equals("b")) {
            viewExpense();
        } else if (todo.equals("f")) {
            filterExpense();
        } else {
            System.out.println("Retry a valid selection!");
        }
    }

    private void makeExpensePrompt(String name) {
        System.out.println("How much $ would you allocate this expense? (in two decimal places)");
        Double setAmount = Double.valueOf(input.next());
        Expense expense = new Expense(name, setAmount);
        manager.addExpense(expense);
        manager.decreaseIncomeToUse(setAmount);
        expense.setDueDate(expenseDueDate());
        System.out.println("Expense setup completed!");
        System.out.println("Remainder of income to allocate: " + manager.getIncomeToUse());
    }

    private String expenseDueDate() {
        System.out.println("Would you like to give this expense a due date?");
        System.out.println("\nSelect from:");
        System.out.println("\ny -> yes");
        System.out.println("\nn -> no");
        String command1 = input.next();
        return processDueDate(command1);
    }

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

    private void viewExpense() {
        List<Expense> list = manager.getExpenseList();
        for (Expense e:list) {
            System.out.println(e.getTitle() + "\n: " + e.getBalance());
        }
    }

    private void filterExpense() {
        System.out.println("\nSelect from:");
        System.out.println("\np -> expense completely paid off");
        System.out.println("\nt -> expense to be paid off");
        System.out.println("\nn -> expense due in n days");
        System.out.println("\nm -> total monthly balance");
        String command1 = input.next();
        processFiltered(command1);
    }

    private void processFiltered(String todo) {
        if (todo.equals("p")) {
            System.out.println(manager.completedExpenses(true));
        } else if (todo.equals("t")) {
            System.out.println(manager.completedExpenses(false));
        } else if (todo.equals("n")) {
            System.out.println("Within what range in days would you like to see?");
            int days = Integer.parseInt(input.next());
            dueInNDays(days);
        } else if (todo.equals("m")) {
            System.out.println("What month would you like to see?");
            String month = input.next();
            monthlyBalance(month);
        } else {
            System.out.println("Retry a valid selection!");
        }
    }

    private void dueInNDays(int n) {
        System.out.println(manager.dueWithinDays(n, manager.getToday()));
    }

    //EFFECTS: prints the
    private void monthlyBalance(String month) {
        System.out.println(manager.getMonthlyBalance(month));
    }
}
