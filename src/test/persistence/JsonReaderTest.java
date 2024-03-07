package persistence;

import model.Expense;
import model.ExpenseManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ExpenseManager em = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyExpenseManager() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyExpenseManager.json");
        try {
            ExpenseManager em = reader.read();
            assertEquals("240306", em.getToday());
            assertEquals(2000, em.getIncomeToUse());
            assertEquals(0, em.getExpenseList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralExpenseManager() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralExpenseManager.json");
        try {
            ExpenseManager em = reader.read();
            List<Expense> list = em.getExpenseList();
            assertEquals(2, list.size());

            assertEquals("Rent", list.get(0).getTitle());
            assertEquals(190, list.get(0).getBalance());
            assertEquals("240307", list.get(0).getDueDate());
            assertEquals(200.0, list.get(0).getGoalSetAmount());
            assertEquals(false, list.get(0).isPaidOff());


            assertEquals("Mall", list.get(1).getTitle());
            assertEquals(0, list.get(1).getBalance());
            assertEquals(null, list.get(1).getDueDate());
            assertEquals(300.0, list.get(1).getGoalSetAmount());
            assertEquals(true, list.get(1).isPaidOff());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}