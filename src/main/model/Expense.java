package model;

public class Expense {
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

    //REQUIRES: amount >= 0 and BigDecimal.valueOf(amount).scale() <= 2
    //MODIFIES: this
    //EFFECTS: return balance =- amount
    public Double paidAmount(Double amount) {
        return this.balance = this.balance - amount;
    }

    //REQUIRES: Expense must have a dueDate
    //EFFECTS: return days remaining till due date
    public Integer daysLeft(String today) {
        int yearsToDays = 365 * Integer.valueOf(this.dueDate.substring(0, 2));
        int monthsToDays = Integer.valueOf(this.dueDate.substring(2, 4));
        int days = Integer.valueOf(this.dueDate.substring(4, 6));

        int currentDay = todayDate(today);

        if ((monthsToDays <= 7 && !(monthsToDays % 2 == 0)) || (monthsToDays > 7 && (monthsToDays % 2 == 0))) {
            return yearsToDays + monthsToDays * 31 + days - currentDay;
        } else if (monthsToDays == 2) {
            return yearsToDays + monthsToDays * 28 + days - currentDay;
        } else {
            return yearsToDays + monthsToDays * 30 + days - currentDay;
        }
    }

    //REQUIRES: length must be = 6 and the integer value of it
    //  must be >= the integer value of today
    public void setDueDate(String date) {
        this.dueDate = date;
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

    public Boolean isPaidOff() {
        return paidOff;
    }

    public void setPaidOff(Boolean b) {
        this.paidOff = b;
    }

    public int todayDate(String today) {
        int todayYear = 365 * Integer.valueOf(today.substring(0, 2));
        int todayMonth = Integer.valueOf(today.substring(2, 4));
        int todayDay = Integer.valueOf(today.substring(4, 6));

        if ((todayMonth <= 7 && !(todayMonth % 2 == 0)) || (todayMonth > 7 && (todayMonth % 2 == 0))) {
            return todayYear + todayMonth * 31 + todayDay;
        } else if (todayMonth == 2) {
            return todayYear + todayMonth * 28 + todayDay;
        } else {
            return todayYear + todayMonth * 30 + todayDay;
        }
    }
}
