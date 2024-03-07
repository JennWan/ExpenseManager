package persistence;

import model.ExpenseManager;
import model.Expense;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

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
    void testWriterEmptyWorkroom() {
        try {
            ExpenseManager em = new ExpenseManager();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(em);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            em = reader.read();
            assertEquals("240306", em.getToday());
            assertEquals("2000.00", em.getIncomeToUse());
            assertEquals(0, em.getExpenseList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            ExpenseManager em = new ExpenseManager();
            em.addExpense(new Expense("Rent", 200.00));
            em.addExpense(new Expense("Mall", 300.00));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(em);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            em = reader.read();
            assertEquals(1500.00, em.getIncomeToUse());
            List<Expense> list = em.getExpenseList();
            assertEquals(2, list.size());

            assertEquals("Rent", list.get(0).getTitle());
            assertEquals(190, list.get(0).getBalance());
            assertEquals("240307", list.get(0).getDueDate());
            assertEquals("200.00", list.get(0).getGoalSetAmount());
            assertEquals(false, list.get(0).isPaidOff());


            assertEquals("Mall", list.get(1).getTitle());
            assertEquals(0, list.get(1).getBalance());
            assertEquals(null, list.get(1).getDueDate());
            assertEquals("300.00", list.get(1).getGoalSetAmount());
            assertEquals(true, list.get(1).isPaidOff());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}