package persistence;

import model.Expense;
import model.ExpenseManager;
import org.json.JSONArray;
import org.json.JSONObject;
import ui.gui.ExpenseManagerUI;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a reader that reads expenseManager from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads ExpenseManager from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ExpenseManager read(ExpenseManagerUI ui) throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseExpenseManager(jsonObject, ui);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses ExpenseManager from JSON object and returns it
    private ExpenseManager parseExpenseManager(JSONObject jsonObject, ExpenseManagerUI ui) {
        ExpenseManager em = new ExpenseManager();
        em.setIncomeToUse(jsonObject.getDouble("incomeToUse"));
        em.setToday(jsonObject.getString("today"));
        em.setExpenseManagerUI(ui);
        ui.setManager(em);
        addExpenses(em, jsonObject);
        em.setIncomeToUse(jsonObject.getDouble("incomeToUse"));
        return em;
    }

    // MODIFIES: em
    // EFFECTS: parses expenses from JSON object and adds them to ExpenseManager
    private void addExpenses(ExpenseManager em, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("expense");
        for (Object json : jsonArray) {
            JSONObject nextExpense = (JSONObject) json;
            addExpense(em, nextExpense);
        }
    }

    // MODIFIES: em
    // EFFECTS: parses expense from JSON object and adds it to ExpenseManager
    private void addExpense(ExpenseManager em, JSONObject jsonObject) {
        String name = jsonObject.getString("title");
        Double goalSet = jsonObject.getDouble("goalSetAmount");
        Expense exp = new Expense(name, goalSet);
        if (jsonObject.toMap().containsKey("dueDate")) {
            exp.setDueDate(jsonObject.getString("dueDate"));
        }
        exp.setBalance(jsonObject.getDouble("balance"));
        exp.setPaidOff(jsonObject.getBoolean("paidOff"));
        em.addExpense(exp);
    }
}
