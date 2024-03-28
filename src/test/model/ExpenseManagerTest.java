package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.gui.ExpenseManagerUI;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseManagerTest {
    private Expense expense1;
    private Expense expense2;
    private Expense expense3;
    private ExpenseManager manager;

    @BeforeEach
    public void runBefore() {
        expense1 = new Expense("test1", 300.00);
        expense1.setDueDate("240101");
        expense2 = new Expense("test2", 750.00);
        expense3 = new Expense("test3", 400.00);
        manager = new ExpenseManager();
        manager.addExpense(expense1);
        manager.addExpense(expense2);
    }

    @Test
    public void TestCompletedExpenses() {
        assertTrue(manager.completedExpenses(true).isEmpty());
        assertEquals(2, manager.completedExpenses(false).size());
        assertTrue(manager.completedExpenses(false).contains(expense1));
        assertTrue(manager.completedExpenses(false).contains(expense2));

        expense1.setPaidOff(true);
        assertTrue(manager.completedExpenses(true).contains(expense1));
        assertFalse(manager.completedExpenses(true).contains(expense2));
        assertFalse(manager.completedExpenses(false).contains(expense1));
        assertTrue(manager.completedExpenses(false).contains(expense2));
    }

    @Test
    public void TestDueWithinNDays() {
        expense1.setDueDate("240103");
        expense2.setDueDate("240210");
        String today = "240101";
        assertTrue(manager.dueWithinDays(1, today).isEmpty());
        assertFalse(manager.dueWithinDays(2, today).isEmpty());
        assertEquals(1 , manager.dueWithinDays(2, today).size());
        assertTrue(manager.dueWithinDays(2, today).contains(expense1));
        assertFalse(manager.dueWithinDays(2, today).contains(expense2));
        assertFalse(manager.dueWithinDays(40, today).isEmpty());
        assertEquals(2 , manager.dueWithinDays(40, today).size());
        assertTrue(manager.dueWithinDays(40, today).contains(expense1));
        assertTrue(manager.dueWithinDays(40, today).contains(expense2));
    }

    @Test
    public void TestGetMonthlyBalance() {
        manager.setToday("240101");
        assertEquals("240101", manager.getToday());
        manager.addExpense(expense3);
        expense3.setDueDate("250120");

        assertEquals(300.00, manager.getMonthlyBalance("01"));
        assertEquals(0, manager.getMonthlyBalance("02"));

        expense2.setDueDate("240228");
        assertEquals(750.00, manager.getMonthlyBalance("02"));
        assertEquals(300.00, manager.getMonthlyBalance("01"));

        expense1.setDueDate("240204");
        assertEquals(1050.00, manager.getMonthlyBalance("02"));
        assertEquals(0.0, manager.getMonthlyBalance("01"));
    }

    @Test
    public void TestDecreaseIncomeToUse() {
        manager.setIncomeToUse(500.00);
        assertEquals(500.00, manager.getIncomeToUse());
        assertEquals(199.50 , manager.decreaseIncomeToUse(300.50));
    }

    @Test
    public void TestGetExpenseList() {
        assertTrue(manager.getExpenseList().contains(expense1));
        assertEquals(expense1, manager.getExpenseList().get(0));
        assertTrue(manager.getExpenseList().contains(expense2));
        assertEquals(expense2, manager.getExpenseList().get(1));
        assertEquals(2, manager.getExpenseList().size());
    }

    @Test
    public void TestExpenseManagerUI() {
        ExpenseManagerUI ui = new ExpenseManagerUI();
        manager.setExpenseManagerUI(ui);
        assertEquals(manager, ui.getManager());
        ui.update();
    }
}
