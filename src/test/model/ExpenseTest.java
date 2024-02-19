package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ExpenseTest {
    private Expense expense1;

    @BeforeEach
    public void runBefore() {
        expense1 = new Expense("test1", 300.00);
        expense1.setDueDate("240110");
    }

    @Test
    public void TestPaidAmount() {
        assertEquals(300.00, expense1.getGoalSetAmount());
        assertEquals(300.00, expense1.getBalance());
        assertEquals("test1", expense1.getTitle());
        assertFalse(expense1.isPaidOff());
        assertEquals("240110", expense1.getDueDate());
        assertEquals(300.00 - 100.50, expense1.payAmount(100.50));
        assertEquals(300.00 - 100.50 - 0.50, expense1.payAmount(0.50));
    }

    @Test
    public void TestDaysLeft() {
        String today = "240101";
        assertEquals(9, expense1.daysLeft(today));
    }

    @Test
    public void TestConvertToDaysNum() {
        assertEquals(8427, expense1.convertToDaysNum("230101"));
        assertEquals(8821, expense1.convertToDaysNum("240202"));
        assertEquals(8883, expense1.convertToDaysNum("240403"));
        assertEquals(8976, expense1.convertToDaysNum("240704"));
        assertEquals(9008, expense1.convertToDaysNum("240805"));
        assertEquals(9404, expense1.convertToDaysNum("250906"));
    }
}