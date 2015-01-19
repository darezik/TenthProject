import java.io.*;
/**
 * This class contains most of the menu based
 * user interactions of the project.
 * It has centralised error handling and modular
 * execution.
 * It also contains static variables for allowing the 
 * user to recover from an error.
 * It uses an InputStreamReader along with a 
 * BufferedReader for input.
 * 
 * @author Darezik Damkevala 
 * @version 1.5
 */
public class ConsoleUserInterface
{
    /**Stores what data is inputted by the user onto the console
     * by reading the System.in Input Stream.*/
    InputStreamReader isr = new InputStreamReader(System.in);

    /**Creating a single shared BufferedReader for user input.*/
    BufferedReader br = new BufferedReader(isr);

    /**Stores the user's position in the program's execution
     * so he can return to his position in the program after
     * remedying the cause which threw an Exception.
     */
    static int pos = 0;

    /**Stores data which helps the methods return the user to
     * his position in the program after remedying the cause
     * which threw an Exception.
     */
    static int state = -1;

    /**This method provides instructions on how to interact with
     * the program to the user, and also populates the Data
     * class's arrays with dummy data if the user chooses to 
     * do so, so that he may test the program without having 
     * to create any customers/transactions himself.
     * 
     * Since this is the root method of all the other methods
     * it contains the error handling code which is applicable
     * to all the classes' methods in this program.
     * Even if one of the deepest methods has thrown an Exception
     * it bubbles up to reach the error handling code in this method.
     * This code provides padding to the error messages to ensure all
     * errors appear uniformly to the user, wherever or whenever they
     * are thrown.
     * This method provides a menu with options to:
     * -->Work with predefined dummy data.
     *    i.e. The user can choose to preload the Data class's arrays
     *         with users and transactions predefined by the programmer
     *         which he can edit/modify/add to.
     * --> Work with blank arrays.
     * 
     *    i.e. The user will be provided with completely blank arrays
     *         in the data class in which he can add data to.
     */
    public void begin()
    {
        pos = 0;
        System.out.println("\f*******************************************");
        System.out.println("*   Welcome to the DBD Courier Service.   *");
        System.out.println("*******************************************");
        System.out.println("When a choice is to be made by the user,   ");
        System.out.println("the options will be displayed.             ");
        System.out.println("To choose your option type in the number   ");
        System.out.println("corresponding to the option of your choice.");
        System.out.println("and press the 'Enter' key on your keyboard.");
        System.out.println("*******************************************");
        System.out.println("Would you like to :                        ");
        System.out.println("1>Work with predefined dummy data.         ");
        System.out.println("2>Work with blank arrays.                  ");
        System.out.println("9>End the program.                         ");
        System.out.println("___________________________________________");
        System.out.print("Please enter your choice :  ");
        try{
            //Store the choice(number input) of the user.
            int choice = Integer.parseInt(br.readLine()+"");
            switch(choice){
                case 1:
                Data.dummyDataPopulator();
                case 2:
                stage1();
                break;
                case 9:
                exitProg();
                break;
                default:
                throw new Exception("Enter y or n.");
            }
        }catch(IOException IOex){
            System.out.println("___________________________________________");
            System.out.println("|        Input / Output  Exception        |");
            System.out.println("|_________________________________________|");
            String msg = IOex.getMessage();
            while(msg.length()<40){
                msg += " ";
            }
            System.out.println("| "+msg                                + "|");
            System.out.println("|_________________________________________|");
            //Find out if user wants to continue or exit.
            if(shouldContinue()){
                prevPlace();
            }
        }catch(Exception ex){
            System.out.println("___________________________________________");
            System.out.println("|                  ERROR                  |");
            System.out.println("|_________________________________________|");
            String msg = ex.getMessage();
            while(msg.length()<40){
                msg += " ";
            }
            System.out.println("| "+msg                                + "|");
            System.out.println("|_________________________________________|");
            //Find out if user wants to continue or exit.
            if(shouldContinue()){
                prevPlace();
            }
        }
    }

    /**This is the main menu which allows the user to choose what he would like
     * to use the program to do. It provides him with a menu based system,
     * from which he can either choose to
     * -->Create a new Customer.
     * -->Create a new transaction.
     * -->View/Edit existing customers.
     * -->View/Edit existing transactions.
     * -->End the program.
     * 
     * In case an invalid input is provided it throws an error handling code which is 
     * handled by the Exception handling routines in the begin() method.
     * 
     * @throws Any exception that reaches it from the methods it calls
     *         or Exceptions which its code throws.
     */
    void stage1() throws Exception{
        pos = 1;
        System.out.println("\f*******************************************");
        System.out.println("*     DBD Courier Services-Main Menu      *");
        System.out.println("*******************************************");
        //Uses the Data class's method to find the number of transactions.
        System.out.println("Number of existing transactions : "
            +Data.totalTrans());
        System.out.println("*******************************************");
        System.out.println("Choose how you would like to proceed:      ");
        System.out.println("1>Create a new customer.");
        System.out.println("2>Create a new transaction.");
        System.out.println("3>View/Edit existing customers.");
        System.out.println("4>View/Edit existing transactions.");
        System.out.println("9>End the program.");
        System.out.println("___________________________________________");
        System.out.print("Please enter your choice :  ");
        //Store the choice(number input) of the user.
        int choice = Integer.parseInt(br.readLine().charAt(0)+"");
        switch(choice){
            case 1:
            newCust();
            break;
            case 2:
            newTrans();
            break;
            case 3:
            custMenu();
            break;
            case 4:
            System.out.print("\f");
            transSelector();
            break;
            case 9:
            exitProg();
            break;

            default:
            throw new Exception("Enter a valid choice.");
        }
    }

    /**This method contains code for accepting input from the user
     * through the console using the data recieved from the user
     * to create a new object of the Customer class.
     * The data taken in is:
     * -->Name
     * -->Address
     * -->Pin Code
     * -->Telephone number.
     * It uses the Customer class's Save() method to save the 
     * details of the customer into the Data class.
     * 
     * In case an invalid input is provided it throws an error handling code which is 
     * handled by the Exception handling routines in the begin() method.
     * 
     * @throws Any exception that reaches it from the methods it calls
     *         or Exceptions which its code throws.
     */
    void newCust() throws Exception{
        /**Allows the error handling code to recognise that this is the
         * method which was running when the exception occurred.
         */
        pos = 2;
        //Creates a new object of the customer type.
        Customer objCust = new Customer();
        System.out.println("\f*******************************************");
        System.out.println("*  DBD Courier Services -  New Customer   *");
        System.out.println("*******************************************");
        System.out.println("Please enter the customer's name     :  ");
        //Store the input of the user into the object's variables.
        objCust.name = br.readLine();
        System.out.println("\nPlease enter the customer's address  :  ");
        //Store the input of the user into the object's variables.
        objCust.address = br.readLine();
        System.out.println("\nPlease enter the customer's pin code :  ");
        //Store the input of the user into the object's variables.
        objCust.pinCode = Integer.parseInt(br.readLine());
        System.out.println("\nPlease enter the customer's tel. no. :  ");
        //Store the input of the user into the object's variables.
        objCust.telNo = br.readLine();
        objCust.Save();
        custMenu(objCust.customerId);
    }

    /**This overloaded method acts as a bridge between Stage1() and newTrans(int).
     * It uses the Customer class's searchCust() method to find the
     * customer ID of the customer the user wants to create the 
     * transaction for and then passes this value on to the 
     * newTrans(int) method.
     * 
     * @throws Any exception that reaches it from the methods it calls
     *         or Exceptions which its code throws.
     */
    void newTrans() throws Exception{
        /**Allows the error handling code to recognise that this is the
         * method which was running when the exception occurred.
         */
        pos = 1;
        System.out.println("\f*******************************************");
        System.out.println("* DBD Courier Services -  New Transaction *");
        System.out.println("*******************************************");
        System.out.println("Please choose the customer who's creating ");
        System.out.println("this transaction: ");
        //Finds which customer the transaction is being created for.
        newTrans(Customer.searchCust());

    }

    /**This method contains code for accepting input from the user
     * through the console using the data recieved from the user
     * to create a new object of the Transaction class.
     * It takes in the customer Id of the customer who is 
     * creating the transaction as a parameter.
     * It takes in and stores the following details into the new
     * object.
     * -->The source location's Pin Code.
     * -->The recipient's name.
     * -->The recipient's address.
     * -->The recipient's pin code.
     * It then uses the objct's Save() method to save the details
     * into the Data class's array.
     * 
     * In case an invalid input is provided it throws an error handling code which is 
     * handled by the Exception handling routines in the begin() method.
     * @param custId The customer ID of the customer, as saved in the Data class.
     * 
     * @throws Any exception that reaches it from the methods it calls
     *         or Exceptions which its code throws.
     */
    void newTrans(int custId) throws Exception{

        /**Allows the error handling code to recognise that this is the
         * method which was running when the exception occurred.
         */
        pos = 7;
        /**Stores data which helps the exception handler return the user
         * to his position in the program after remedying the cause
         * which threw an Exception.
         */
        state = custId;
        //Creates a new object of the customer type.
        Transaction objTrans = new Transaction();
        System.out.println("\f*******************************************");
        System.out.println("* DBD Courier Services -  New Transaction *");
        System.out.println("*******************************************");
        System.out.println("Customer creating this transaction      :  ");
        new Customer(custId).printDetails();
        //Store the ID of the user into the object's variable.
        objTrans.customerId = custId;
        System.out.println("\nPlease enter source location's Pin Code:  ");
        //Store the input of the user into the object's variable.
        objTrans.fromPinCode =  Integer.parseInt(br.readLine());
        System.out.println("\nPlease enter the recipient's name :  ");
        //Store the input of the user into the object's variable.
        objTrans.toName = br.readLine();
        System.out.println("\nPlease enter the recipient's address :  ");
        //Store the input of the user into the object's variable.
        objTrans.toAddress = br.readLine();
        System.out.println("\nPlease enter the recipient's Pin Code :  ");
        //Store the input of the user into the object's variable.
        objTrans.toPinCode = Integer.parseInt(br.readLine());
        objTrans.Save();
        objTrans.printBill();
        transMenu(objTrans.TransactionId);
    }

    /**This overloaded method prints out all the customers in a tabular
     * form and gives the user a choce to edit/delete them, also gives
     * the user the choice to create a new customer.
     * The choices the user has got are:
     * -->Edit a customer.
     * -->Search for a customer by a detail of his.
     * -->Delete a customer.
     * -->Create a new cutsomer.
     * 
     * In case an invalid input is provided it throws an error handling code which is 
     * handled by the Exception handling routines in the begin() method.
     * 
     * @throws Any exception that reaches it from the methods it calls
     *         or Exceptions which its code throws.
     */
    void custMenu() throws Exception{
        /**Allows the error handling code to recognise that this is the
         * method which was running when the exception occurred.
         */
        pos = 4;
        System.out.println("\f**********************************************************");
        System.out.println("*          DBD Courier Services - Edit Customers         *");
        System.out.println("**********************************************************");
        Customer.printAll();
        System.out.println("Choose how you would like to proceed:      ");
        System.out.println("1>Enter the customer id of the customer you want to EDIT..");
        System.out.println("2>SEARCH for a customer using a detail of his.");
        System.out.println("3>DELETE a customer using his customer ID.");
        System.out.println("4>Create a NEW customer.");
        System.out.println("5>Go to the main menu.");
        System.out.println("9>End the program.");
        System.out.println("__________________________________________________________");
        System.out.print("Please enter your choice :  ");
        //Store the choice(number input) of the user.
        int choice = Integer.parseInt(br.readLine()+"");
        switch(choice){
            case 1:
            System.out.print("Please enter the customer ID :  ");
            int custId = Integer.parseInt(br.readLine());
            new Customer(custId).editCust();
            break;
            case 2:
            custId = Customer.searchCust();
            new Customer(custId).editCust();
            break;
            case 3:
            System.out.print("Please enter the customer ID :  ");
            custId = Integer.parseInt(br.readLine());
            //confirms the deletion.
            System.out.print("Enter 1 to confirm the customer's deletion:");
            if(Integer.parseInt(br.readLine()) == 1){
                Customer.delCust(custId);
                custMenu();
            }else{
                custMenu();
            }
            break;
            case 4:
            newCust();
            break;
            case 5:
            stage1();
            case 9:
            exitProg();

            break;
            default:
            throw new Exception("Enter a valid choice.");
        }
    }

    /**Prints out the customer's details and gives the user a choce to edit/delete the 
     * customer and view his transactions, also gives the user a
     * choice to create a new transaction for the customer.
     * The choices the user has got are:
     * -->EDIT a customer.
     * -->SEARCH for a customer by a detail of his.
     * -->DELETE a customer.
     * -->CREATE a new transaction for the customer.
     * -->VIEW the customer's transactions.
     * 
     * In case an invalid input is provided it throws an error handling code which is 
     * handled by the Exception handling routines in the begin() method.
     * 
     * @param custId The customer ID of the customer, as saved in the Data class.
     * 
     * @throws Any exception that reaches it from the methods it calls
     *         or Exceptions which its code throws.
     */
    void custMenu(int custId) throws Exception{
        /**Allows the error handling code to recognise that this is the
         * method which was running when the exception occurred.
         */
        pos = 5;
        /**Stores data which helps the exception handler return the user
         * to his position in the program after remedying the cause
         * which threw an Exception.
         */
        state = custId;

        System.out.println("\f*******************************************");
        System.out.println("* DBD Courier Services -  Manage Customer *");
        System.out.println("*******************************************");
        new Customer(custId).printDetails();
        System.out.println("___________________________________________");
        System.out.println("Choose how you would like to proceed:      ");
        System.out.println("1>EDIT the customer's details.");
        System.out.println("2>DELETE the customer");
        System.out.println("3>ADD a transaction for this cutomer.");
        System.out.println("4>VIEW this customer's transactions.");
        System.out.println("5>Go to the main menu.");
        System.out.println("9>End the program.");
        System.out.println("___________________________________________");
        System.out.print("Please enter your choice :  ");
        //Store the choice(number input) of the user.
        int choice = Integer.parseInt(br.readLine().charAt(0)+"");
        switch(choice){
            case 1:
            new Customer(custId).editCust();
            break;
            case 2:
            //confirms the deletion.
            System.out.print("Enter 1 to confirm the customer's deletion:");
            if(Integer.parseInt(br.readLine()) == 1){
                Customer.delCust(custId);
                System.out.println("Customer Deleted.");
                //Creates a pause and progress line.
                drawLine('.');
                stage1();
            }else{
                custMenu();
            }
            break;
            case 3:
            newTrans(custId);
            break;
            case 4:
            new Transaction().view(custId);
            System.out.println("Enter the Trans ID of the transaction you want to modify.");
            int transID =Integer.parseInt(br.readLine());
            transMenu(transID);
            break;
            case 5:
            stage1();
            case 9:
            exitProg();
            break;

            default:
            throw new Exception("Enter a valid choice.");
        }
    }

    /**This method provides the user with options to modify the 
     * transacation he has selected. 
     * The options availiable to the user are:
     * -->To edit the transaction.
     * -->To delete the transaction.
     * -->To add another transaction for the same user.
     * -->Make the transaction as delivered.
     * -->Print a bill for that transaction.
     * -->End the program.
     * -->Return to the previous menu.
     * As with all the other methods, any exception occurring is 
     * thrown to the stage1() method where it is handled. 
     * 
     * @param transId The transaction ID of the transaction the customer
     *                selected.
     * 
     * @throws Any exception that reaches it from the methods it calls
     *         or Exceptions which its code throws.
     */
    void transMenu(int transId) throws Exception{
        /**Allows the error handling code to recognise that this is the
         * method which was running when the exception occurred.
         */
        pos = 6;
        /**Stores data which helps the exception handler return the user
         * to his position in the program after remedying the cause
         * which threw an Exception.
         */
        state = transId;
        /**Creates an object ot the transaction to be edited.
         */
        Transaction trans = new Transaction(transId);

        System.out.println("\f*******************************************");
        System.out.println("*DBD Courier Services - Manage Transaction*");
        System.out.println("*******************************************");
        trans.printDetails();
        System.out.println("___________________________________________");
        System.out.println("Choose how you would like to proceed:      ");
        System.out.println("1>EDIT the transaction's details.");
        System.out.println("2>DELETE the transaction");
        System.out.println("3>ADD a transaction for this cutomer.");
        System.out.println("4>MARK a transaction as delivered.");
        System.out.println("5>Print a bill for this transaction.");        
        System.out.println("9>End the program.");
        System.out.println("0>Go to the main menu.");
        System.out.println("___________________________________________");
        System.out.print("Please enter your choice :  ");
        //Store the choice(number input) of the user.
        int choice = Integer.parseInt(br.readLine().charAt(0)+"");
        switch(choice){
            case 1:
            trans.editTrans();
            break;
            case 2:
            //Confirms the deletion.
            System.out.print("Enter 1 to confirm the transaction's deletion:");
            if(Integer.parseInt(br.readLine()) == 1){
                //Deletes the transaction.
                Data.DelData(transId,1);
                System.out.print("Transaction's record has been deleted.");
                stage1();
            }else{
                transMenu(transId);
            }
            break;
            case 3:
            newTrans(trans.customerId);
            break;
            case 4:
            trans.delivered();
            transMenu(transId);
            break;
            case 5:
            trans.printBill();
            transMenu(transId);
            case 0:
            stage1();
            case 9:
            exitProg();
            break;

            default:
            throw new Exception("Enter a valid choice.");
        }
    }

    /**Provides a system for choosing a transaction to edit/modify,
     * allows the user to choose the customer in an intutive and
     * friendly way.
     * The steps followed are:
     * -->The user chooses the customer who created the transaction,
     * through any of the customers details using the searchCust() method
     * of the Customer class.
     * -->This method calls the Transaction class's view(int) method to 
     * display a table containing all of that customer's transactions.
     * -->The customer has to refer to the table and choose the transaction
     * he wishes to edit.
     * 
     * @throws Any exception that reaches it from the methods it calls
     *         or Exceptions which its code throws.
     */
    void transSelector() throws Exception{
        /**Allows the error handling code to recognise that this is the
         * method which was running when the exception occurred.
         */
        pos = 1;
        System.out.println("**********************************************************");
        System.out.println("Please use the Customer Selector menu  to select the");
        System.out.println("sender who's records you want to view/edit/delete/create. ");
        System.out.println("__________________________________________________________");
        //Srtores the ID of the customer we are dealing with.
        int customerID = Customer.searchCust();
        /**Allows the error handling code to recognise that this is the
         * method which was running when the exception occurred.
         */
        pos = 8;
        System.out.println("**********************************************************");
        System.out.println("Please use the Customer Selector menu  to select the");
        System.out.println("sender who's records you want to view/edit/delete/create. ");
        System.out.println("__________________________________________________________");
        new Transaction().view(customerID);
        System.out.println("Enter the Trans ID of the transaction you want to modify.");
        int transID =Integer.parseInt(br.readLine());
        transMenu(transID);
    }

    /**This is the first layer of the specialised error handling
     * mechanism in the program. Instead of forcing the user to start afresh 
     * when an exception occurs, this method gives him a choice if he wants
     * to resume from where he left off when the error occurred or to end the 
     * program.
     * In case the user decided to continue, the second phase of error handling
     * would be invoked, else, the exitProg() method would be executed, which 
     * would end the program execution and reset the Java virtual machine.
     * It has a nested call system, thus, in case the user enters a wrong
     * input even to the question if the program should continue, it 
     * re-issues the question, and one 'y'(yes) input causes it to fall through
     * all the calls and return a true value.
     * 
     * @throws Any exception that reaches it from the methods it calls
     *         or Exceptions which its code throws.
     *         
     *  @return shouldContinue The choice of the user.
     */
    boolean shouldContinue()
    {
        boolean shouldContinue = false;
        System.out.println("Do you wish to continue?");
        System.out.println("y>Will return you to where you ");
        System.out.println("  to your position in the program");
        System.out.println("  before the error occurred.");
        System.out.println("n>Will end the program's execution.");
        try{
            //Store the choice(char input) of the user.
            char choice = br.readLine().charAt(0);
            if(choice=='y'){
                shouldContinue = true;
            }else if(choice=='n'){
                exitProg();
            }else{
                throw new IOException("Enter y or n.      ");
            }
        }catch(IOException IOex){
            System.out.println("___________________________________________");
            System.out.println("|        Input / Output  Exception        |");
            System.out.println("|_________________________________________|");
            String msg = IOex.getMessage();
            while(msg.length()<40){
                msg += " ";
            }
            System.out.println("| "+msg                                + "|");
            System.out.println("|_________________________________________|");
            //Find out if user wants to continue or exit.
            if(shouldContinue()){
                shouldContinue = true;
                System.out.println("\f");
            }
        }finally{
            return shouldContinue;
        }
    }

    /**This is the second layer of error handling in the program and
     * is of vital importance as it permits the user to not start over
     * again but to continue WHERE HE LEFT off and the exception was thrown,
     * thus saving his time and effort.
     * It uses the position and state variables to recall the methods again 
     * with the same attributes as they were called when the error occurred.
     * 
     */
    void prevPlace(){
        try{
            switch(pos){
                case 0:
                stage1();
                break;
                case 1:
                stage1();
                break;
                case 2:
                newCust();
                break;
                case 3:
                stage1();
                break;
                case 4:
                custMenu();
                break;
                case 5:
                custMenu(state);
                break;
                case 6:
                transMenu(state);
                break;
                case 7:
                newTrans(state);
                case 8:
                stage1();
                default:
                throw new Exception("Cannot resume the original state.");
            }
        }catch(Exception ex){
            System.out.println("___________________________________________");
            System.out.println("|                  ERROR                  |");
            System.out.println("|_________________________________________|");
            String msg = ex.getMessage();
            while(msg.length()<40){
                msg += " ";
            }
            System.out.println("| "+msg                                + "|");
            System.out.println("|_________________________________________|");
            //Find out if user wants to continue or exit.
            if(shouldContinue()){
                prevPlace();
            }
        }
    }

    /**This method provides formatting for all text which is longer than
     * the line's length in a way similar to the word wrap feature of notepad.
     * It takes in three arguments which it uses to determine the style 
     * in which the formatting has to be applied.
     * @param value The string to be run through the word-wrap engine.
     * @param len   The length of the line which is to be maintained.
     * @param pre   The number of spaces preceding the subsequent lines which 
     *              are wrapped(indent level of the text).
     */
    static void formatter(String value,int len, String pre){
        if(value.length()>len){
            System.out.print("["+value.substring(0,len)+"-]");
        }else{
            //creates a string with enough spaces as padding to append.
            String padding = "";
            for(int j = value.length(); j< len - 2;j++){
                padding += " ";
            }
            System.out.println("["+value+"]"+padding);
        }
        if(value.length()>len){
            int j = len;
            while(j<=value.length()-len){
                System.out.print("\n"+pre+"["+value.substring(j,j+len)+"-]");
                j+=len;
            }
            System.out.print("\n"+pre+"["+value.substring(j)+"]");
            System.out.println();
        }
    }

    /**This method draws a line in an animated form, using customisable 
     * characters.
     * This method is a personal idea of mine, created to improve the 
     * aesthetics of the program and a visual cue to the user when the 
     * data is being added/modified/deleted.
     * 
     * @param c The character the line is to consist of.
     */
    static void drawLine(char c){
        for(int i = 0;i<1000000000;i++)
        {
            if(i%100000000==0){
                System.out.print("" + c + c + c + c);
            }
        }
        System.out.println("" + c + c + c);

    }

    /**This method is called when the program is needed to exit.
     * It is either called when the user has finished his work
     * and wishes to exit, or else when the user chooses to 
     * terminate the program when an error occurs.
     * It deletes the data stored in RAM and the Data class and 
     * restarts the Java Virtual Machine.
     */
    void exitProg(){
        System.out.println("\f*******************************************");
        System.out.println("*DBD Courier Services-We deliver results!!*");
        System.out.println("*******************************************");
        System.out.println("Thank you for using our computerised system");
        System.out.println("We hope you've had a pleasant experience.  ");
        //Creates a progress bar
        drawLine('*');
        System.exit(0);
    }
}