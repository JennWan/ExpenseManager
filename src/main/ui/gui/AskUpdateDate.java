package ui.gui;

import model.ExpenseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//asks User if they wish to update today's date in their Expense Manager
public class AskUpdateDate extends JFrame implements ActionListener {
    private ExpenseManagerUI managerUI;
    private ExpenseManager em;
    private JButton yesButton;
    private JButton noButton;
    private JLabel question;

    //EFFECTS: creates JFrame
    public AskUpdateDate(ExpenseManagerUI managerUI) {
        this.managerUI = managerUI;
        this.em = managerUI.getManager();

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setTitle("CPSC 210: Update Today's Date?");

        question = new JLabel("Is today: " + em.getToday() + "? If not press no to update the date!");
        yesButton = new JButton("Yes");
        yesButton.addActionListener(this);
        noButton = new JButton("No");
        noButton.addActionListener(this);


        this.add(question);
        this.add(yesButton);
        this.add(noButton);

        this.pack();
        this.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: update date if noButton is pressed then proceed with DueNDays()
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == noButton) {
            System.out.println("Updating today's date!");
            SetDate today = new SetDate(em);
            DueNDays days = new DueNDays(em);
        }
        if (e.getSource() == yesButton) {
            DueNDays days = new DueNDays(em);
        }
        this.dispose();
    }
}
