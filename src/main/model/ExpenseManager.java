package model;


////CLASS SENTENCE NOTE
import java.util.ArrayList;
import java.util.List;

public class ExpenseManager {
    private Double incomeToUse;
    private List<Expense> expenseList;
    private String today;

    //EFFECTS: creates an expense manager
    public ExpenseManager() {
        this.incomeToUse = 0.0;
        this.expenseList = new ArrayList<>();
        this.today = null;
    }

    //MODIFIES: this
    //EFFECTS: adds expense to expenseList
    public void addExpense(Expense expense) {
        this.expenseList.add(expense);
    }

    //EFFECTS: return a filtered expenseList with completed == isPaidOff()
    public List<Expense> completedExpenses(boolean completed) {
        List<Expense> list = new ArrayList<>();
        for (Expense e: expenseList) {
            if (e.isPaidOff() == completed) {
                list.add(e);
            }
        }
        return list;
    }

    //EFFECTS: return a filtered expenseList with daysLeft() <= day
    public List<Expense> dueWithinDays(int day, String today) {
        List<Expense> list = new ArrayList<>();
        for (Expense e: expenseList) {
            if (e.daysLeft(today) <= day) {
                list.add(e);
            }
        }
        return list;
    }

    //REQUIRES: month has to be a string containing only of 2 integers
    //EFFECTS: return the total expense balance for the month of the current year
    public Double getMonthlyBalance(String month) {
        Double sum = 0.0;
        for (Expense e: expenseList) {
            if ((!(e.getDueDate() == null) && e.getDueDate().substring(2, 4).equals(month))
                    && e.getDueDate().substring(0, 2).equals(this.today.substring(0, 2))) {
                sum = sum + e.getBalance();
            }
        }
        return sum;
    }

    public Double getIncomeToUse() {
        return this.incomeToUse;
    }

    public void setIncomeToUse(Double amount) {
        this.incomeToUse = amount;
    }

    public List<Expense> getExpenseList() {
        return expenseList;
    }

    //REQUIRES: amount >= 0.0
    //MODIFIES: this
    //EFFECTS: decreases IncomeToUse by amount and returns it
    public Double decreaseIncomeToUse(Double amount) {
        this.incomeToUse = this.incomeToUse - amount;
        return this.incomeToUse;
    }

    public void setToday(String date) {
        this.today = date;
    }

    public String getToday() {
        return this.today;
    }
}
