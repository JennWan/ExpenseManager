package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;
import ui.gui.ExpenseManagerUI;

import java.util.ArrayList;
import java.util.List;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

//Represents an Expense Manager with income to allocate (in dollars), a list of expenses to pay,
//  and the date for today in yymmdd format
public class ExpenseManager implements Writable {
    private Double incomeToUse;
    private List<Expense> expenseList;
    private String today;
    private ExpenseManagerUI expenseManagerUI;

    //EFFECTS: creates an expense manager with an empty expenseList
    public ExpenseManager() {
        this.incomeToUse = 0.0;
        this.expenseList = new ArrayList<>();
        this.today = null;
        this.expenseManagerUI = null;
    }

    //MODIFIES: this
    //EFFECTS: adds expense to expenseList
    public void addExpense(Expense expense) {
        this.expenseList.add(expense);
        this.decreaseIncomeToUse(expense.getGoalSetAmount());
        this.notifyUI();
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

    //REQUIRES the expense must not have today == null
    //EFFECTS: return a filtered expenseList with daysLeft() <= day
    public List<Expense> dueWithinDays(int day, String today) {
        List<Expense> list = new ArrayList<>();
        for (Expense e: expenseList) {
            if (e.daysLeft(today) != null) {
                if (e.daysLeft(today) <= day) {
                    list.add(e);
                }
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

    //REQUIRES: amount >= 0.0
    //MODIFIES: this
    //EFFECTS: decreases IncomeToUse by amount and returns it
    public Double decreaseIncomeToUse(Double amount) {
        this.incomeToUse = this.incomeToUse - amount;
        return this.incomeToUse;
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

    public void setToday(String date) {
        this.today = date;
    }

    public String getToday() {
        return this.today;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("expense", expenseToJson());
        json.put("incomeToUse", incomeToUse);
        json.put("today", today);
        return json;
    }

    // EFFECTS: returns expenses in this workroom as a JSON array
    private JSONArray expenseToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Expense t : expenseList) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

    public void notifyUI() {
        if (expenseManagerUI != null) {
            expenseManagerUI.update();
        }
    }

    public void setExpenseManagerUI(ExpenseManagerUI o) {
        expenseManagerUI = o;
    }
}
