package model;

import org.json.JSONObject;
import persistence.Writable;

//Referenced from the JsonSerialization Demo
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
//Represents an expense having a title, optional dueDate,
//      goalSetAmount, balance (in CAD dollars) and if it is finished paying off
public class Expense implements Writable {
    private String dueDate;        // due date in format yymmdd
    private Boolean paidOff;       // true if the Expense is at balance 0
    private Double goalSetAmount;  // the amount to be paid towards the Expense
    private Double balance;        // the current balance of the account
    private String title;          // the Expense title

    //REQUIRES: getIncomeToUse() >= setAmount
    //     and BigDecimal.valueOf(setAmount).scale() <= 2
    //EFFECTS: creates an expense with title = name and balance = goalSetAmount = setAmount
    //           and paidOff is false
    public Expense(String name, Double setAmount) {
        this.title = name;
        this.goalSetAmount = setAmount;
        this.balance = setAmount;
        this.paidOff = false;
        this.dueDate = null;
    }

    //REQUIRES: balance >= amount >= 0 and BigDecimal.valueOf(amount).scale() <= 2
    //MODIFIES: this
    //EFFECTS: return new balance subtracting the amount paid and checks if it is paidOff
    public Double payAmount(Double amount) {
        this.balance = this.balance - amount;
        if (this.balance == 0.0) {
            setPaidOff(true);
        }
        return this.balance;
    }

    //REQUIRES: length must be = 6 and the integer value of date
    //  must be >= the integer value of today
    public void setDueDate(String date) {
        this.dueDate = date;
    }

    //REQUIRES: date must be a string consisting only of integers of valid calendar days
    //EFFECTS: converts date to total number of days
    public Integer convertToDaysNum(String date) {
        int yearsToDays = 365 * Integer.valueOf(date.substring(0, 2));
        int months = Integer.valueOf(date.substring(2, 4));
        int days = Integer.valueOf(date.substring(4, 6));

        int monthsToDays = 0;
        for (int i = months; i > 0; i--) {
            if ((i <= 7 && !(i % 2 == 0))
                    || (i > 7 && (i % 2 == 0))) {
                monthsToDays = monthsToDays + 31;
            } else if (i == 2) {
                monthsToDays = monthsToDays + 28;
            } else {
                monthsToDays = monthsToDays + 30;
            }
        }
        return yearsToDays + monthsToDays + days;
    }

    //REQUIRES: Expense must have a dueDate
    //EFFECTS: return days remaining till due date
    public Integer daysLeft(String today) {
        if (this.dueDate == null) {
            return null;
        }
        int dueDateDay = convertToDaysNum(this.dueDate);
        int currentDays = convertToDaysNum(today);

        return dueDateDay - currentDays;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    public String getTitle() {
        return this.title;
    }

    public Double getGoalSetAmount() {
        return goalSetAmount;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance1) {
        this.balance = balance1;
    }

    public Boolean isPaidOff() {
        return paidOff;
    }

    public void setPaidOff(Boolean b) {
        this.paidOff = b;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("paidOff", paidOff);
        json.put("balance", balance);
        json.put("dueDate", dueDate);
        json.put("goalSetAmount", goalSetAmount);
        return json;
    }
}
