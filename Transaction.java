import java.util.*;
import java.io.*;
/**classTransaction:  
 * Allows creating objects containing the details of the Transaction.  
 * Imports the java.util package for its Date and Calender classes.
 * Imports the java.io package for input purposes.
 * Has constructor overloading to:
 * -->Create a blank Transaction object:Transaction()
 * -->Load a Transaction's details based on his Id:Transaction(int transId)
 * -->Create a Transaction object with the data recieved as parameters:
 *    Transaction(int transId,String date, int custId, int fromPin, 
 *    String toName, String toAddress, int toPin)
 * Provides functionality save the data stored in the object into the Data class.
 * -->Save()
 * Provides functionality to prin the data stored in the object to the console.
 * -->printDetails():Prints the details to the console
 * -->printBill():Prints a bill for the transaction to the console.
 * 
 * @author Darezik Damekevala
 * @version 1.5
 */
public class Transaction
{
    /**Stores what data is inputted by the user onto the console
     * by reading the System.in Input Stream.*/
    static InputStreamReader isr = new InputStreamReader(System.in);
    /**Creating a single shared BufferedReader for user input.*/
    static BufferedReader br = new BufferedReader(isr);
    /**Creates an object of the Calendar class to provide the current date.*/
    Calendar c = Calendar.getInstance();
    /**Stores the index of the transaction in the Data class's trans array.*/
    private int transIndex;
    /**Stores the transaction's Id.*/
    int TransactionId;
    /**Stores the transaction's date.*/
    String transDate;
    /**Stores the customer who made the transaction's id.*/
    int customerId;
    /**Stores the Pin code from which the mail was sent.*/
    int fromPinCode;
    /**Stores the recipient's name.*/
    String toName;
    /**Stores the recipient's address.*/
    String toAddress;
    /**Stores the recipient's Pin Code.*/
    int toPinCode;
    /**Stores the date the mail reached its destination.
     * In case the mail is not sent this field contans
     * the String "Not Sent".
     */
    String sentDate;

    /**Initializes the Transaction object with dummy values.
     */
    Transaction(){
        transIndex = -1;
        TransactionId=-1;
        transDate = (c.get(Calendar.MONTH)+1)+"/"+
        (c.get(Calendar.DATE))   +"/"+
        (c.get(Calendar.YEAR));
        customerId = -1;
        fromPinCode = -1;
        toName = "";
        toAddress = "";
        toPinCode = -1;
        sentDate = "Not Sent";
    }

    /**Initialises the Transaction object with values taken from the Data class.
     * Takes in the transaction's Id as a parameter.
     * 
     * @param transId The transaction ID of the transaction who's deatils are to
     *                be loaded from the Data class.
     * 
     * @throws Any exception that reaches it from the methods it calls
     *         or Exceptions which its code throws.
     */
    Transaction(int transId) throws Exception{
        TransactionId=transId;
        transIndex = Data.search(transId,1);
        transDate = Data.AccData(transIndex,1,1);
        customerId = Integer.parseInt(Data.AccData(transIndex,2,1));
        fromPinCode = Integer.parseInt(Data.AccData(transIndex,3,1));
        toName = Data.AccData(transIndex,4,1);
        toAddress = Data.AccData(transIndex,5,1);
        toPinCode = Integer.parseInt(Data.AccData(transIndex,6,1));
        sentDate = Data.AccData(transIndex,7,1);
    }

    /**
     * Initialises the Transaction object with values recieved as parameters.
     * The parameters are:
     * @param transId The transaction's Id.
     * @param date    The date the transaction was made.
     * @param custId  The customer who intitiated the transaction's Id.
     * @param fromPin The pin code the mail was posted from.
     * @param name    The customer who intitiated the transaction's name.
     * @param address The address of the customer who initiated the 
     *                transactinon.
     * @param toPin   The pin code the mail was posted to.
     * @param sent    The date the mail was delivered or "Not Sent" if the
     *                mail is yet to be delivered.
     */
    Transaction(int transId,String date, int custId, int fromPin, String name,
    String address, int toPin, String sent){
        TransactionId=transId;
        transIndex = -1;
        transDate = date;
        customerId = custId;
        fromPinCode = fromPin;
        toName = name;
        toAddress = address;
        toPinCode = toPin;
        sentDate = sent;
    }

    /**
     * Saves the data stored in the object into the data class.
     * In case the object was created using data from the Data class,
     * it overwrites the data with which the object was created.
     * 
     * @throws Any exception that reaches it from the methods it calls
     *         or Exceptions which its code throws.
     */
    void Save() throws Exception{
        toName = toName.trim();
        System.out.println("\f*******************************************");
        System.out.println("*******Validating and Saving Details*******");
        //Creates a pause and progress line.
        ConsoleUserInterface.drawLine('.');
        //validates the data before saving it.
        validate();
        if(TransactionId>=0){
            try{
                //Stores data to be added.
                String info[] =  {TransactionId+"",transDate,customerId+""
                    ,fromPinCode+"",toName,toAddress,toPinCode+"",sentDate};
                Data.AddData(info,1,false);//Overwrites the old Transaction.
                //Finds the transaction index.
                transIndex = Data.search(TransactionId,1);
            }catch(Exception ex){
                System.out.println("The details could not be overwritten.");
                throw ex;
            }
        }else{
            try{
                //Stores data to be added.
                String info[] = {transDate,customerId+"",fromPinCode+"",toName,
                        toAddress,toPinCode+"",sentDate};
                //creates a new Transaction in Data, and saves its index in its
                //instance variable.
                transIndex = Data.AddData(info,1,true);
                TransactionId = Integer.parseInt(Data.AccData(transIndex,0,1));
            }catch(Exception ex){
                System.out.println("The new transaction was not created.");
                throw ex;
            }
        }
    }

    /**Prints the details stored in the transaction object  
     * onto the console.
     * 
     * @throws Any exception that reaches it from the methods it calls
     *         or Exceptions which its code throws.
     */
    void printDetails() throws Exception{
        System.out.println("Transaction Id :" + TransactionId);
        System.out.println("Date           :" + transDate);
        System.out.print  ("The mail is to :"            );
        ConsoleUserInterface.formatter(toName,24,"\t\t");
        System.out.print  ("Who resides at :"            );
        ConsoleUserInterface.formatter(toAddress,24,"\t\t");
        System.out.println("The mail is from Pin Code : " + fromPinCode);
        System.out.println("The mail is to Pin Code   : " + toPinCode);
        System.out.println("The mail was delivered on : " + sentDate);
        System.out.println("The transaction was created by : ");
        new Customer(customerId).printDetails();
    }

    /**Prints the details stored in the transaction object  
     * in the form of a bill onto the console after calculating
     * the cost and tax.
     * Calls the Cost class.
     * Also provides additional information in the bill.
     * 
     * @throws Any exception that reaches it from the methods it calls
     *         or Exceptions which its code throws.
     */
    void printBill() throws Exception{
        System.out.println("\f*************************************************");
        System.out.println("|   DBD Courier Services-We deliver results!!   |");
        System.out.println("*************************************************");
        System.out.println("|     Transaction Invoice / Customer's Bill     |");
        System.out.println("*************************************************");
        System.out.println("|Transaction Id:" + TransactionId+"\t|"+"Date:" + transDate+"\t\t|");
        System.out.println("*************************************************");
        System.out.print  ("The mail is to :"            );
        ConsoleUserInterface.formatter(toName,32,"\t\t");
        System.out.print  ("Who resides at :"            );
        ConsoleUserInterface.formatter(toAddress,32,"\t\t");
        System.out.println("Sender Details : ");
        new Customer(customerId).printDetails();
        Cost cost = new Cost(fromPinCode,toPinCode);
        System.out.println("*************************************************");
        System.out.println("From PinCode\tTo PinCode\t\tCost");
        System.out.println(fromPinCode+"\t\t"+toPinCode+"\t\t\tRs  "+cost.Calc());
        System.out.println("Tax(at 10%)----------------------------\tRs   " + cost.Calc()/10);
        System.out.println("_________________________________________________");
        //The ternary operator below is used to resolve formatting issues.
        System.out.println("\t\t\t\tTotal:\tRs " +( ((""+(cost.Calc()+cost.Calc()/10)).length()<1000)?
                    " "+(cost.Calc()+cost.Calc()/10):(cost.Calc()+cost.Calc()/10)));
        System.out.println("*************************************************");
        System.out.println("We calculate the cost as follows:");
        System.out.println("For local mails (Pin difference less than 100):");
        System.out.println("->The cost is numerically equal to the difference");
        System.out.println("   of the areas' pin codes added to the base cost");
        System.out.println("   of Rs 10.");
        System.out.println("For other mails:");
        System.out.println("->The cost is numerically equal to the difference");
        System.out.println("  of the areas' pin codes divided by 100,which is");
        System.out.println("  added to the base cost of Rs 100.");
        System.out.println("*************************************************");
        System.out.println("*      We do not send international mails.      *");
        System.out.println("*************************************************");
        System.out.println("\n\nHit enter to continue...");
        br.readLine();
        System.out.print("\f");
    }

    /**
     * Validates all the values stored in the object's variables,
     * and prompts for valid inputs if they are not recieved.
     * 
     * @throws Any exception that reaches it from the methods it calls
     *         or Exceptions which its code throws.
     */
    void validate() throws Exception{
        if(transDate==""||transDate.length()<8||transDate.length()>10){
            System.out.println("Invalid creation date. ");
            System.out.println("Replacing it with today's date.");
            transDate = (c.get(Calendar.MONTH)+1)+"/"+
            (c.get(Calendar.DATE))   +"/"+
            (c.get(Calendar.YEAR));
            System.out.println("______________________________________________");
            System.out.println("Date set as "+ transDate);
            Save();
        }
        try{
            Data.search(customerId,0);
        }catch(Exception ex){
            System.out.println("Invalid customer ID : "+customerId);
            System.out.println("Please choose a valid customer id.");
            //Store the input of the user into a variable.
            customerId = new Customer().searchCust();
            Save();
        }
        if((fromPinCode+"").length()!=6){
            System.out.println("This \"sent from\" pin code is invalid : "+fromPinCode);
            System.out.println("Please enter a valid Pin Code:  ");
            //Store the input of the user into the object's variable.
            fromPinCode =  Integer.parseInt(br.readLine());
            Save();
        }
        if((toPinCode+"").length()!=6){
            System.out.println("The recipient's pin code is invalid.");
            System.out.println("Please enter a valid Pin Code:  ");
            //Store the input of the user into the object's variable.
            toPinCode =  Integer.parseInt(br.readLine());
            Save();
        }
        if (toName.length()>2){
            //Stores if the toName contains an invalid character.
            boolean error = false;
            for(int i = 1;i<toName.length();i++)
                if(Character.isDigit(toName.charAt(i))){
                    error = true;
                    break;
            }
            if(error){
                System.out.println("Invalid name : "+toName);
                System.out.println("Name cannot contain numbers.");
                System.out.println("Please enter a valid name : ");
                toName = br.readLine();
                Save();
            }
        }else{
            System.out.println("Invalid name : "+toName);
            System.out.println("At least first name and last name needed.");
            System.out.println("Please enter a valid name : ");
            toName = br.readLine();
            Save();
        }
        if (!(toAddress.length()>5)){
            System.out.println("Invalid  recipient's address Address : "+toAddress);
            System.out.println("More than five characters needed.");
            System.out.println("Please enter a valid toAddress : ");
            toAddress = br.readLine();
            Save();
        } if(sentDate==""||sentDate.length()<8||sentDate.length()>10){
            System.out.println("Invalid \"sent\" date. ");
            System.out.println("Replacing it with today's date.");
            sentDate = (c.get(Calendar.MONTH)+1)+"/"+
            (c.get(Calendar.DATE))   +"/"+
            (c.get(Calendar.YEAR));
            System.out.println("Date set as "+ sentDate);
            System.out.println("______________________________________________");
            Save();
        }
    }

    /**Prints out a table of the transaction details of a customer who's customer ID is 
     * provided to the method in the form of an argument.
     * The table contains the details of the transaction ID, recipient's name, recipient's
     * address, recipient's Pin Code, and the date the transaction was created.
     * 
     * @param custId The customer ID of the customer who's details are to 
     *               be displayed.
     * 
     * @throws Any exception that reaches it from the methods it calls
     *         or Exceptions which its code throws.
     */
    static void view(int custId) throws Exception{

        System.out.println("****        The customer's transactions are :         ****");
        System.out.println("__________________________________________________________");
        System.out.println("Trans\tRecipient's\tRecipient's");
        System.out.println("Id\tName      \tAddress   \tPinCode\tDate    ");
        System.out.println("__________________________________________________________");
        int transactions[] = Data.search(custId+"",2,1);
        for(int m = 0; m<transactions.length;m++){
            //Stores the id of the currently printed customer.
            int i = transactions[m];
            Transaction temp = new Transaction(i);
            //Print the customer id.
            System.out.print(temp.TransactionId+"\t");
            //Step 1 for handling names of different lengths.
            if(temp.toName.length()>12){
                System.out.print("["+temp.toName.substring(0,12)+"-]");
            }else{
                //creates a string with enough spaces as padding to append.
                String padding = "";
                for(int j = temp.toName.length(); j< 10;j++){
                    padding += " ";
                }
                System.out.print("["+temp.toName+"]"+padding);
            }
            //Handle long addresses.
            if(temp.toAddress.length()>14){
                System.out.print("\t"+temp.toAddress.substring(0,12)+"...");
            }else{
                //creates a string with enough spaces as padding to append.
                String padding = "";
                for(int j = temp.toAddress.length(); j< 10;j++){
                    padding += " ";
                }
                System.out.print("\t"+temp.toAddress+padding);
            }
            //Prints the recipint's Pin Code.
            System.out.print("\t"+temp.toPinCode);
            //Prints the date.
            System.out.print("\t"+temp.transDate);
            //Step 2 for handling names of different lengths.
            //Prints onto line 2 of the transaction's entry.
            if(temp.toName.length()>12){
                int j = 12;
                while(j<=temp.toName.length()-12){
                    System.out.print("\n\t["+temp.toName.substring(j,j+12)+"-]");
                    j+=12;
                }
                System.out.print("\n\t["+temp.toName.substring(j)+"]");
            }
            System.out.println();
        }
        System.out.println("__________________________________________________________");
    }

    /**This method marks the transaction loaded in the current object as sent,
     * the date the mail was sent is chosen as the current date.
     * The current date is obtained using the object of the Calander class named c.
     * 
     * @throws Any exception that reaches it from the methods it calls
     *         or Exceptions which its code throws.
     */
    void delivered() throws Exception{
        sentDate =  (c.get(Calendar.MONTH)+1)+"/"+
        (c.get(Calendar.DATE))   +"/"+
        (c.get(Calendar.YEAR));
        Save();
        System.out.println("The mail has been marked as sent on "+sentDate);
    }

    /**This method provides an command line interface to edit the transaction 
     * currently loaded in the object.
     * It also contains an option to modify the details of the user who created the 
     * transaction.
     * The recipient's details which can be edited are:
     * -->Name
     * -->Address
     * -->Pin Code
     * -->Sent Date
     * The method also contains options to:
     * -->Go to the previous menu.
     * -->End the program.
     * 
     * @throws Any exception that reaches it from the methods it calls
     *         or Exceptions which its code throws.
     */
    void editTrans() throws Exception{
        System.out.println("\f*******************************************");
        System.out.println("* DBD Courier Services - Edit Transaction *");
        System.out.println("*******************************************");
        System.out.println("Each attribute's current value is in the ");
        System.out.println("square brackets beside it. ");
        System.out.println("Choose an attribute to modify:");
        System.out.println("0>User's Details:");
        //Use the Customer class's method to print the details.
        new Customer(customerId).printDetails();
        System.out.println("Recipient's details.");
        System.out.print  ("1>Name   \t");
        ConsoleUserInterface.formatter(toName,23,"\t\t");
        System.out.print  ("2>Address\t");
        //Formats the address to fit within the page's bounds.
        ConsoleUserInterface.formatter(toAddress,23,"\t\t");
        System.out.println("3>Pin Code\t["+toPinCode+"]");
        System.out.println("4>Sent Date\t["+sentDate+"]");
        System.out.println("5>Go to the previous menu.");
        System.out.println("9>End the program.");
        System.out.println("___________________________________________");
        System.out.print("Please enter your choice :  ");
        int choice = Integer.parseInt(br.readLine().charAt(0)+"");
        switch(choice){
            case 0:
            new Customer(customerId).editCust();
            case 1:
            System.out.println("\f*******************************************");
            System.out.println("*  DBD Courier Services - Edit Attribute  *");
            System.out.println("*******************************************");
            System.out.println("Transaction Id :" + TransactionId);
            System.out.println("Attribute being edited is : Name.");
            System.out.print  ("Original Value :");
            ConsoleUserInterface.formatter(toName,24,"\t\t");
            System.out.println("Please enter the new recipient's name : ");
            toName = br.readLine();
            Save();
            editTrans();
            break;
            case 2:
            System.out.println("\f*******************************************");
            System.out.println("*  DBD Courier Services - Edit Attribute  *");
            System.out.println("*******************************************");
            System.out.println("Transaction Id : " + TransactionId);
            System.out.println("Attribute being edited is : Address.");
            System.out.print("Original Value :");
            ConsoleUserInterface.formatter(toAddress,24,"\t\t");
            System.out.println("Please enter the new recipient's address : ");
            toAddress = br.readLine();
            Save();
            editTrans();
            break;
            case 3:
            System.out.println("\f*******************************************");
            System.out.println("*  DBD Courier Services - Edit Attribute  *");
            System.out.println("*******************************************");
            System.out.println("Transaction Id : " + TransactionId);
            System.out.println("Attribute being edited is : Pin Code.");
            System.out.println("Original Value : " + toPinCode);
            System.out.println("Please enter the new recipient's Pin Code : ");
            toPinCode = Integer.parseInt(br.readLine());
            Save();
            editTrans();
            break;
            case 4:
            System.out.println("\f*******************************************");
            System.out.println("*  DBD Courier Services - Edit Attribute  *");
            System.out.println("*******************************************");
            System.out.println("Transaction Id : " + TransactionId);
            System.out.println("Attribute being edited is : Sent Date.");
            System.out.println("Original Value : " + sentDate);
            System.out.println("Please enter the date the mail was sent : ");
            sentDate = br.readLine(); 
            Save();
            editTrans();
            break;
            case 5:
            new ConsoleUserInterface().prevPlace();
            break;
            case 9:
            new ConsoleUserInterface().exitProg();
            break;
            default:
            throw new Exception("Enter a valid choice.");
        }
    }
}
