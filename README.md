# My Personal Project

## Expense Tracking Manager

My non-trivial classes:
- X; types of **expenses** (ie phone bill, rent)
- Y; individual's **collection of expenses** to pay

*What will the application do?*

Track and goal-set monthly expenses by taking in one's earning. The user will 
create an 'expense' assigned with its allocated goal-set/expected spending amount entered by the user.
The user can also enter amount spent on an 'expense' into the application and get the updated balance.
Overtime the user can access statistics on actual spent amount on certain expenses in comparison to goal-set values.

*Who will use it?*

Anyone who wish to goal set and track expenses.
This application will help the user achieve financial awareness.

*Why is this project of interest to you?*

To raise more awareness regarding finances. I believe this application would be a beneficial
first step for individuals to incorporate awareness into day-to-day lives including myself.
This is especially of interest to me, as I will have to learn to budget and pay for my own expenses away from home, 
where almost everything is new.

## User Stories

- As a user, I want to be able to enter my yearly earning.
- As a user, I want to be able to add an expense to my collection of expenses and specify
    the expense name, set spending amount, optional due date and whether it is paid off/completed.
- As a user, I want to be able to view a list of the name of expenses and
    its corresponding balance (based off of the original goal-set value).
- As a user, I want to be able to see the remainder of my earning that is not allocated to an expense.
- As a user, I want to be able to add actual amount spend into an expense. 
- As a user, when I select the quit option from the application menu, 
     I want to be reminded to save my list of Expense to file and have the option to do so or not. 
- As a user, when I start the application, I want to be given the option to load my to-do list from file.

# Instructions for Grader

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by selecting the
menu item 'New Expense' on the MenuBar where the user will be asked to fill out information to create a new expense. 
Upon pressing the submit button, a new expense will be added to the ExpenseManager.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by selecting the
menu item 'Action Options' for filtering options to achieve a subset of the Xs (expenses). 
- You can locate my visual component by selecting 'File' on the MenuBar.
- You can save the state of my application by selecting 'File' on the MenuBar, where the user will be presented with the 
options to save, load, and exit. The user may select save to save the state of my application.
- You can reload the state of my application by selecting 'File' on the MenuBar, where the user will be presented with the
  options to save, load, and exit. The user may select load to reload the state of my application.

# EventLog
*Note to marker:*
- Action 1 related to the Xs and Y: Track amount towards an expense.
- Action 2 related to the Xs and Y: Set DueDate for an expense.

*Representative sample of events:*
- Mon Apr 08 01:43:44 PDT 2024 2000.0 has been set for available income to allocate!
- Mon Apr 08 01:44:08 PDT 2024 300.0 is allocated towards: Rent!
- Mon Apr 08 01:44:17 PDT 2024 150.0 added to Rent!
- Mon Apr 08 01:44:24 PDT 2024 Due date of 240410 set for Rent!

# If I have more time, I would...
*If you had more time to work on the project, what refactoring might you use to improve your design?*

If I had more time to work on the project, I would refactor an aspect of my GUI. One of the refactoring 
I would do will consist of creating an abstract class to refactor out the functionalities in my “GUI setter class” 
such as SetDate, SetEarning and AskUpdateDate, that centers around the ExpenseManager class because the constructor 
consists of many duplicates in code. Within this abstract class, there would be a method somewhat similar to 
setInformation(ExpenseManagerUI ui) for the constructor. In addition to the constructor, an abstract method for 
actionPerformed(ActionEvent e) sets the information given by the user to ExpenseManager. Currently, SetEarning has 
an association with ExpenseManagerUI to access ExpenseManager to serve the purpose of setting the incomeToUse field. 
Having SetEarning refactored to take in a parameter of ExpenseManager would assist the process of setting incomeToUse. 
Lastly, for its functionality of updating the value displayed on the GUI, I would use getExpenseManagerUI() from the 
ExpenseManager class and getIncomeToUse from the ExpenseManagerUI class nextly use setText() with the user input to 
update the JTextField on the GUI desktop. I would also do a similar refactoring for AskUpdateDate. Doing the above 
refactoring mentioned would remove associations and simplify the UML diagram alongside with reducing the amount of 
duplication within the project.

Additionally, I might look into implementing the Singleton Pattern for the ExpenseManager class, as for any moment there 
should be only one ExpenseManager instance. To implement the Singleton Pattern, I would create a private static field of 
type ExpenseManager in the ExpenseManager class, make my constructor private and create a getInstance() method to access 
the single instance of ExpenseManager. I believe this could be a pattern I would want to implement, as the pattern would 
allow me to coordinate a state across the system. Yet, this will require further consideration as the pattern breaks 
Object-oriented conventions, introducing a global method and breaking SRP.
