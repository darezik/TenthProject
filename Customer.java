import java.util.Scanner;
import java.io.*;
/**
 * Facilitates creating objects  of the customers stored in the Data class,
 * and functions to create new customers whose details can be stored in the
 * Data class.  
 * Has constructor overloading to:
 * -->Create a blank customer object:Customer()
 * -->Load a customer's details based on his Id:Customer(int custId)
 * -->Create a customer object with the data recieved as parameters:
 *    Customer(int custId, String CustName, String CustAdd, 
 *    int PinCode, String telephone).
 * Provides functionality to save the objects into the Data class.
 * -->Save()
 * Provides a function to print the customer's details.
 * -->function printDetails()
 * Provides static methods to tabulate the customer data.
 * -->print(int[])
 * -->printAll()
 * 
 * @author Darezik Damkevala
 * @version 1.5
 */
public class Customer
{   /**Stores what data is inputted by the user onto the console
     * by reading the System.in Input Stream.*/
    static InputStreamReader isr = new InputStreamReader(System.in);
    /**Creating a single shared BufferedReader for user input.*/
    static BufferedReader br = new BufferedReader(isr);
    /**Stores the user's position in the program's execution*/
    int pos = 0;
    /**Stores the index number of the customer in class Data*/
    private int custIndex;
    /**Stores the Customer's id.*/
    int customerId;
    /**Stores the customer's name*/
    String name;
    /**Stores the customer's address.*/
    String address;
    /**Stores the customer's pin code.*/
    int pinCode;
    /**Stores the customer's telephone number.*/
    String telNo;
    /**Initialises the Customer object with dummy values.
       */
    Customer(){
        custIndex = -1;
        customerId=-1;
        name = "";
        address = "";
        pinCode = 0;
        telNo = "";
    }

    /**Initialises the Customer object with values taken from the Data 
     * class.
     * @param custId The customer ID of the customer, as saved in the Data class.
     * 
     * @throws Any exception that reaches it from the methods it calls
     *         or Exceptions which its code throws.
     */
    Customer(int custId) throws Exception{
        customerId=custId;
        custIndex = Data.search(custId,0);
        name = Data.AccData(custIndex,1,0);
        address = Data.AccData(custIndex,2,0);
        pinCode = Integer.parseInt(Data.AccData(custIndex,3,0));
        telNo = Data.AccData(custIndex,4,0);
    }

    /**
     * Initialises the Customer object with values recieved as 
     * parameters.
     * The parameters are:
     * @param custId   :The customer's Id.
     * @param CustName :The abovementioned customer's name.
     * @param CustAdd  :The customer's address.
     * @param Pincode  :The customer's pin code.
     * @param telephone:The customer's tel. no.
     */
    Customer(int custId, String CustName, String CustAdd, int PinCode,
    String telephone){
        customerId=custId;
        custIndex = -1;
        name = CustName;
        address = CustAdd;
        pinCode = PinCode;
        telNo = telephone;
    }

    /**
     * Saves the data stored in the object into the data class.
     * In case the object was created using data from the Data class,
     * it overwrites the data with which the object was created.
     * The class method calling it has to handle any exceptions that
     * may be thrown during its execution.
     * 
     * @throws Any exception that reaches it from the methods it calls
     *         or Exceptions which its code throws.
     */
    void Save() throws Exception{
        System.out.println("\f*******************************************");
        System.out.println("*******Validating and Saving Details*******");
        //Creates a pause and progress line.
        ConsoleUserInterface.drawLine('.');
        //validates the data before saving it.
        validate();
        if(customerId>=0){
            //Sring info stores data to be added.
            String info[] =  {customerId+"",name,address,pinCode+"",telNo};
            custIndex = Data.AddData(info,0,false);//Overwrites the old customer.
        }else{
            //Sring info stores data to be added.
            String info[] = {name+"",address,pinCode+"",telNo};
            custIndex = Data.AddData(info,0,true);//creates a new customer in Data.
        }
        customerId = Integer.parseInt(Data.AccData(custIndex,0,0));
    }

    /**Prints the details stored in the customer object  
     * onto the console.
     */
    void printDetails(){
        System.out.println("Customer Id              : " + customerId);
        System.out.print  ("The customer's name is   : ");
        //Formats the name to fit within the page's bounds.
        ConsoleUserInterface.formatter(name,13,"\t\t\t   ");
        System.out.print  ("The customer lives at    : ");
        //Formats the address to fit within the page's bounds.
        ConsoleUserInterface.formatter(address,13,"\t\t\t   ");
        System.out.println("The customer's PinCode is: " + pinCode);
        System.out.println("The customer's Tel.No. is: " + telNo);
    }

    /**Makes sure all the data stored in the object is valid.
     * 
     * @throws Any exception that reaches it from the methods it calls
     *         or Exceptions which its code throws.
     */
    void validate() throws Exception{
        name = name.trim();
        if (name.length()>2){
            //Stores if the name contains an invalid character.
            boolean error = false;
            for(int i = 1;i<name.length();i++)
                if(Character.isDigit(name.charAt(i))){
                    error = true;
                    break;
            }
            if(error){
                System.out.print  ("Invalid name : ");
                ConsoleUserInterface.formatter(name,24,"\t\t");
                System.out.println("Name cannot contain numbers.");
                System.out.println("Please enter a valid name : ");
                name = br.readLine();
                Save();
            }
        }else{
            System.out.print  ("Invalid name : ");
            ConsoleUserInterface.formatter(name,24,"\t\t");
            System.out.println("At least first name and last name needed.");
            System.out.println("Please enter a valid name : ");
            name = br.readLine();
            Save();
        }
        address = address.trim();
        if (!(address.length()>5)){
            System.out.println("Invalid address : "+address);
            System.out.println("More than five characters needed.");
            System.out.println("Please enter a valid address : ");
            address = br.readLine();
            Save();
        } 
        if((pinCode+"").length()!=6){
            System.out.println("Invalid pin code : "+pinCode);
            System.out.println("The pin code should consist of six digits.");
            System.out.println("Please enter a valid pin code : ");
            pinCode = Integer.parseInt(br.readLine());
            Save();
        }
        if(telNo.length()<10||telNo.length()>15){
            System.out.println("Invalid telephone number : "+telNo);
            System.out.println("The tel. no. should have more than 9 digits,");
            System.out.println("and less than 15 digits.");
            System.out.println("Make sure you have included the area code.");
            System.out.println("Please enter a valid telephone number : ");
            telNo = br.readLine();
            Save();
        }
    }

    /**This method provides an command line interface to edit the customer 
     * currently loaded in the object.
     * It also contains an option to modify the following details of the user:
     * -->Name
     * -->Address
     * -->Pin Code
     * -->Telephone Number.
     * The method also contains options to:
     * -->Create a new transaction in the customer's name.
     * -->Go to the previous menu.
     * -->End the program.
     * 
     * @throws Any exception that reaches it from the methods it calls
     *         or Exceptions which its code throws.
     */
    void editCust()throws Exception{
        System.out.println("\f*******************************************");
        System.out.println("*  DBD Courier Services - Edit Customer   *");
        System.out.println("*******************************************");
        System.out.println("Each attribute's current value is in the ");
        System.out.println("square brackets beside it. ");
        System.out.println("Choose an attribute to modify:");
        System.out.print  ("1>Name   \t");
        ConsoleUserInterface.formatter(name,24,"\t\t");
        System.out.print  ("2>Address \t");
        //Formats the address to fit within the page's bounds.
        ConsoleUserInterface.formatter(address,24,"\t\t");
        System.out.println("3>Pin Code\t["+pinCode+"]");
        System.out.println("4>Tel. No.\t["+telNo+"]");
        System.out.println("5>Create a new transaction for the customer");
        System.out.println("9>End the program.");
        System.out.println("0>Go to the previous menu.");
        System.out.println("___________________________________________");
        System.out.print("Please enter your choice :  ");
        int choice = Integer.parseInt(br.readLine().charAt(0)+"");
        switch(choice){
            case 1:
            System.out.println("\f*******************************************");
            System.out.println("*  DBD Courier Services - Edit Attribute  *");
            System.out.println("*******************************************");
            System.out.println("Customer Id : " + customerId);
            System.out.println("Attribute being edited is : Name.");
            System.out.print  ("Original Value :");
            ConsoleUserInterface.formatter(name,24,"\t\t");
            System.out.println("Please enter the new customer name : ");
            name = br.readLine();
            Save();
            editCust();
            break;
            case 2:
            System.out.println("\f*******************************************");
            System.out.println("*  DBD Courier Services - Edit Attribute  *");
            System.out.println("*******************************************");
            System.out.println("Customer Id : " + customerId);
            System.out.println("Attribute being edited is : Address.");
            System.out.print  ("Original Value :");
            ConsoleUserInterface.formatter(address,24,"\t\t");
            System.out.println("Please enter the new customer address : ");
            address = br.readLine();
            Save();
            editCust();
            break;
            case 3:
            System.out.println("\f*******************************************");
            System.out.println("*  DBD Courier Services - Edit Attribute  *");
            System.out.println("*******************************************");
            System.out.println("Customer Id : " + customerId);
            System.out.println("Attribute being edited is : Pin Code.");
            System.out.println("Original Value : " + pinCode);
            System.out.println("Please enter the new customer Pin Code : ");
            pinCode = Integer.parseInt(br.readLine());
            Save();
            editCust();
            break;
            case 4:
            System.out.println("\f*******************************************");
            System.out.println("*  DBD Courier Services - Edit Attribute  *");
            System.out.println("*******************************************");
            System.out.println("Customer Id : " + customerId);
            System.out.println("Attribute being edited is : Tel. No.");
            System.out.println("Original Value : " + telNo);
            System.out.println("Please enter the new customer Tel. No. : ");
            telNo = br.readLine(); 
            Save();
            editCust();
            break;
            case 5:
            new ConsoleUserInterface().newTrans(customerId);
            break;
            case 9:
            new ConsoleUserInterface().exitProg();
            break;
            case 0:
            new ConsoleUserInterface().prevPlace();
            break;
            default:
            throw new Exception("Enter a valid choice.");
        }
    }

    /**Prints all the customers saved in the Data class in a tabular form.
     * @throws Any exception that reaches it from the methods it calls
     *         or Exceptions which its code throws.
     */
    static void printAll()throws Exception{
        System.out.println("__________________________________________________________");
        System.out.println("Id\tName      \tAddress   \tPin     Tel.No.       ");
        System.out.println("__________________________________________________________");
        for(int i = 0; i<Data.totalCust();i++){
            //Sores the customer ID corresponding to the index(i).
            int custID = Integer.parseInt(Data.AccData(i,0,0));
            //Creates a new Customer object using the customer ID.
            Customer temp = new Customer(custID);
            //Print the customer id.
            System.out.print(temp.customerId+"\t");
            //Step 1 for handling names of different lengths.
            if(temp.name.length()>12){
                System.out.print("["+temp.name.substring(0,12)+"-]");
            }else{
                //creates a string with enough spaces as padding to append.
                String padding = "";
                for(int j = temp.name.length(); j< 10;j++){
                    padding += " ";
                }
                System.out.print("["+temp.name+"]"+padding);
            }
            //Handle long addresses.
            if(temp.address.length()>14){
                System.out.print("\t"+temp.address.substring(0,12)+"...");
            }else{
                //creates a string with enough spaces as padding to append.
                String padding = "";
                for(int j = temp.address.length(); j< 10;j++){
                    padding += " ";
                }
                System.out.print("\t"+temp.address+padding);
            }
            //Prints the pin code.
            System.out.print("\t"+temp.pinCode);
            //Prints the Telephone No.
            System.out.print("  "+temp.telNo);
            //Step 2 for handling names of different lengths.
            //Prints onto line 2 of the customer's entry.
            if(temp.name.length()>12){
                int j = 12;
                while(j<=temp.name.length()-12){
                    System.out.print("\n\t["+temp.name.substring(j,j+12)+"-]");
                    j+=12;
                }
                System.out.print("\n\t["+temp.name.substring(j)+"]");
            }
            System.out.println();
        }
        System.out.println("__________________________________________________________");
    }

    /** Prints the details of the customers corresponding to the customer IDs it recieves
     *  in the array passed to it as a parameter.
     *  @param custs[] It is an array of the customer IDs of the customers who's details
     *                 need to be shown in a tabular form.
     * 
     *  @throws Any exception that reaches it from the methods it calls
     *         or Exceptions which its code throws.
     */
    static void print(int custs[])throws Exception{
        System.out.println("****        Customers returned by your search         ****");
        System.out.println("__________________________________________________________");
        System.out.println("Id\tName      \tAddress   \tPin     Tel.No.       ");
        System.out.println("__________________________________________________________");
        for(int m = 0; m<custs.length;m++){
            //Stores the id of the currently printed customer.
            int i = custs[m];
            Customer temp = new Customer(i);
            //Print the customer id.
            System.out.print(temp.customerId+"\t");
            //Step 1 for handling names of different lengths.
            if(temp.name.length()>12){
                System.out.print("["+temp.name.substring(0,12)+"-]");
            }else{
                //creates a string with enough spaces as padding to append.
                String padding = "";
                for(int j = temp.name.length(); j< 10;j++){
                    padding += " ";
                }
                System.out.print("["+temp.name+"]"+padding);
            }
            //Handle long addresses.
            if(temp.address.length()>14){
                System.out.print("\t"+temp.address.substring(0,12)+"...");
            }else{
                //creates a string with enough spaces as padding to append.
                String padding = "";
                for(int j = temp.address.length(); j< 10;j++){
                    padding += " ";
                }
                System.out.print("\t"+temp.address+padding);
            }
            //Prints the pn code.
            System.out.print("\t"+temp.pinCode);
            //Prints the Telephone No.
            System.out.print("  "+temp.telNo);
            //Step 2 for handling names of different lengths.
            //Prints onto line 2 of the customer's entry.
            if(temp.name.length()>12){
                int j = 12;
                while(j<=temp.name.length()-12){
                    System.out.print("\n\t["+temp.name.substring(j,j+12)+"-]");
                    j+=12;
                }
                System.out.print("\n\t["+temp.name.substring(j)+"]");
            }
            System.out.println();
        }
        System.out.println("__________________________________________________________");
    }

    /**Provides a menu to the user, which allows him to choose an existing customer using 
     * any of the customer's details. 
     * The customer can choose the user by searching in the following Customer's details:
     * -->Customer's ID
     * -->Customer's name
     * -->Customer's Pin Code
     * -->Customer's Telephone number.
     * After the search he is presented with a table cointaining all the customers who match
     * the search criteria, else, the user is informed that none of the customers' details 
     * match his search.
     * @return custId the customer ID of the customer chosen by the user using this method.
     * 
     * @throws Any exception that reaches it from the methods it calls
     *         or Exceptions which its code throws.
     */
    static int searchCust() throws Exception{
        int custId=-1;
        //         System.out.println("**********************************************************");
        //         System.out.println("*       DBD Courier Services - Customer Selector         *");
        //         System.out.println("**********************************************************");
        System.out.println("Choose which field you would like to search     ");
        System.out.println("0>CustId");
        System.out.println("1>Name");
        System.out.println("2>Address");
        System.out.println("3>Pin Code");
        System.out.println("4>Telephone number.");
        System.out.println("5>Go to the previous menu.");
        System.out.println("9>End the program.");
        System.out.println("___________________________________________");
        System.out.print("Please enter your choice :  ");
        //Store the choice(number input) of the user.
        int field = Integer.parseInt(br.readLine().charAt(0)+"");
        switch(field){
            case 0:
            System.out.println("Enter the customer's ID : ");
            custId = Integer.parseInt(br.readLine());
            break;

            case 1:
            System.out.println("Enter the customer's name : ");
            print(Data.search(br.readLine(),1,0));
            System.out.println("Enter the customer's id from the table above.");
            String input = br.readLine();
            custId = Integer.parseInt(input);
            break;

            case 2:
            System.out.println("Enter the customer's address : ");
            print(Data.search(br.readLine(),2,0));
            System.out.println("Enter the customer's id from the table above.");
            input = br.readLine();
            custId = Integer.parseInt(input);
            break;

            case 3:
            System.out.println("Enter the customer's Pin Code : ");
            print(Data.search(br.readLine(),3,0));
            System.out.println("Enter the customer's id from the table above.");
            input = br.readLine();
            custId = Integer.parseInt(input);
            break;

            case 4:
            System.out.println("Enter the customer's Telephone number : ");
            print(Data.search(br.readLine(),4,0));
            System.out.println("Enter the customer's id from the table above.");
            input = br.readLine();
            custId = Integer.parseInt(input);
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
        return custId;
    }
    
    /**Deletes the customer who's ID is passed to the method as a parameter,
     * and also deletes all the transactions related to the customer.
     * This is done as to prevent any orphaned transactions.
     * 
     * @param custId the cutomer ID of the customer who is to be deleted.
     * 
     * @throws Any exception that reaches it from the methods it calls
     *         or Exceptions which its code throws.
     */
    static void delCust(int custId) throws Exception{
        //Deletes the customer.
        Data.DelData(custId,0);
        System.out.println("Customer's record has been deleted.");
        System.out.println("Deleteing the customer's transactions also.");
        //Finds the transaction ids corresponding to the customer id.
        try{//Searches for realted transactions.
            int transIndexs [] = Data.search(custId+"",2,1);
            int transIds[] = new int[transIndexs.length];
            for(int i=0;i<transIndexs.length;i++){
                //Finds and stores the transaction ID.
                transIds[i] = Integer.parseInt(Data.AccData(transIndexs[i],0,1));
            }
            for(int i=0;i<transIndexs.length;i++){
                //Deletes the transaction.
                Data.DelData(transIds[i],1);
            }
        }catch (Exception ex){ //In case the customer had no transactions
            System.out.println("No related transactions found.");
        }
        finally{
            //Creates a pause and progress line.
            ConsoleUserInterface.drawLine('.');
        }
    }
}
