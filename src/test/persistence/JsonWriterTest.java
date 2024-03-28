package persistence;

import model.ExpenseManager;
import model.Expense;
import org.junit.jupiter.api.Test;
import ui.gui.ExpenseManagerUI;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    private ExpenseManagerUI ui = new ExpenseManagerUI();

    @Test
    void testWriterInvalidFile() {
        try {
            ExpenseManager em = new ExpenseManager();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyExpenseManager() {
        try {
            ExpenseManager em = new ExpenseManager();
            em.setToday("240327");
            em.setIncomeToUse(2000.00);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyExpenseManager.json");
            writer.open();
            writer.write(em);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyExpenseManager.json");
            em = reader.read(ui);
            assertEquals("240327", em.getToday());
            assertEquals(2000.0, em.getIncomeToUse());
            assertEquals(0, em.getExpenseList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralExpenseManager() {
        try {
            ExpenseManager em = new ExpenseManager();
            em.addExpense(new Expense("Rent", 200.00));
            em.addExpense(new Expense("Mall", 300.00));
            em.setToday("240306");
            em.setIncomeToUse(1500.00);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralExpenseManager.json");
            writer.open();
            writer.write(em);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralExpenseManager.json");
            em = reader.read(ui);
            assertEquals(1000.0, em.getIncomeToUse());
            List<Expense> list = em.getExpenseList();
            assertEquals(2, list.size());

            assertEquals("Rent", list.get(0).getTitle());
            list.get(0).payAmount(10.00);
            assertEquals(190.0, list.get(0).getBalance());
            list.get(0).setDueDate("240307");
            assertEquals("240307", list.get(0).getDueDate());
            assertEquals(200.0, list.get(0).getGoalSetAmount());
            assertEquals(false, list.get(0).isPaidOff());


            assertEquals("Mall", list.get(1).getTitle());
            list.get(1).payAmount(300.00);
            assertEquals(0.0, list.get(1).getBalance());
            assertEquals(null, list.get(1).getDueDate());
            assertEquals(300.0, list.get(1).getGoalSetAmount());
            assertEquals(true, list.get(1).isPaidOff());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void TestToJsonTodayNull() {
        try {
            ExpenseManager em = new ExpenseManager();
            em.setIncomeToUse(2000.00);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyExpenseManager.json");
            writer.open();
            writer.write(em);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNoTodayExpenseManager.json");
            em = reader.read(ui);
            assertEquals("null", em.getToday());
            assertEquals(2000.0, em.getIncomeToUse());
            assertEquals(0, em.getExpenseList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}