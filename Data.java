import java.util.Scanner;
/**
 * class Data: Provides data services for the program.
 * ¥    It demonstrates the OOP concept of encapsulation.
 * ¥    Stores both the transactionÕs details and the 
 *      customerÕs details in private static arrays. 
 * ¥    Imports java.util.Scanner as it is used 
 *      in the search(String, int, int) function 
 *      to parse the String of Results.
 * ¥    No constructors specified as no objects of this
 *      class need to be created.
 * ¥    Upto 10 customers' data and 50 transactions' data
 *      can be stored.
 * Class Data contains static methods for accessing, modifying
 * and deleting data: 
 * -->AccData(int,int,int)-for accessing data.
 * -->AddData(String[],int, boolean)-for modifying data.
 * -->DelData()-for deleting data.
 * Class contains an overloaded search function:
 * -->int search(int, int): 
 * Binary search for finding the index of the corresponding cust/trans ID. 
 * -->String search(String, int, int):
 * Linear search for finding the index(s) containing the values recieved
 * as parameters. 
 * Class contains a method for finding the number of  transactions whose 
 * details are stored in the class:
 * -->totalTrans()
 * Class contains a method for populating the arrays with test data:
 * -->dummyDataPopulator()
 * 
 * @author Darezik Damekevala
 * @version 1.5
 */
public class Data 
{
    /**CustSize stores the number of people in the customer data 
     * table.*/
    private static int CustSize = -1; 

    /**TranSize stores the number of transactions in the transaction
     * data table.*/
    private static int TranSize = -1;

    /**Cust stores each customer's details in a two dimensional array.  
     *  The columns(second index) are:  
     *  >0-CustomerId 
     *  >1-Name 
     *  >2-Address 
     *  >3-PinCode 
     *  >4-Tel. No.
     */
    private static String Cust[] [] = new String [10] [5];

    /**Trans stores each transaction's details in a two dimensional
     * array. The array is private, thus all data access/modification
     * takes place through this class's member methods.
     *  The columns(second index) are:  
     *  >0-TransactionId  
     *  >1-Date  
     *  >2-CustomerId   
     *  >3-fromPinCode  
     *  >4-toName  
     *  >5-toAddress  
     *  >6-toPinCode  
     *  >7-sentDate  
     */
    private static String Trans[] [] = new String [50] [8];

    /**This method retrieves value of the field of the index received 
     * as a parameter.  
     * -->0-Customer Data Array(Cust)  
     * -->1-Transaction Data Array(Trans) 
     * The method returns the index of the record holding the value. 
     * Uses the string-enabled linear search method.
     * @param index takes in the index which's value needs to be 
     *              retrieved.  
     * @param field states which field of the two dimensional array 
     *              is to be searched. 
     * @param array states which array is to be searched.
     * @throws An exception with the error if the operation fails.  
     */
    public static String AccData(int index,int field,int array)
    throws Exception{
        try{
            if(array == 0){
                return Cust[index][field];
            } else if(array == 1){
                return Trans[index][field];
            }else{     //Handles a wrong input for the array parameter.
                Exception ex = new Exception("Wrong array parameter.");
                throw ex;   //Throwing an exception.
            }
        }catch(Exception e){
            Exception ex = new Exception("Record does not exist.");
            throw ex;
        }
    }

    /**Static method AddData stores the one dimensional array recieved 
     * as a parameter into the array specified.  
     * Updates the value of the array's size variable(TranSize/CustSize).  
     * Depending on the value of the append parameter, it either appends 
     * the data, or replaces it.
     * There are (n) values in the one dimensional array when appending, 
     * which are inserted into the array.
     * There are (n+1) values in the parameter array when it is to be 
     * replaced, the first one being the customer id.
     * 
     * @param data[] contains the data to be added to the arrays.
     * @param array states which array is to be searched.
     * @param append determines if the data is to be appended to the
     *               array, or has to replace some existing data.
     *               
     * @return the index at which the data has been saved.
     */
    public static int AddData(String data[],int array, boolean append)
    throws Exception{
        if(array==0){       //Determines the array to add to.
            try{
                if(append){     //Copies the data into a new entry.
                    CustSize += 1;
                    if(CustSize > 0){
                        //Stores a new CustId which is
                        //one digit greater than the old one.
                        int newCustId=(Integer.parseInt
                                (Cust[CustSize-1][0])+1);
                        Cust [CustSize] [0] =  newCustId + "";
                    }else{
                        Cust [CustSize] [0] = CustSize+"";
                    }
                    for(int i = 1; i < Cust[0].length; i++){
                        //Data is copied into its respective field.
                        Cust [CustSize] [i]= data[i-1];
                    }
                    return CustSize;
                }else{          //Overwrites an existing entry.
                    //Stores the index corresponding to the CustId.
                    int custIndex=search(Integer.parseInt(data[0]),0);
                    for(int i = 0; i < Cust[0].length; i++){
                        //Overwrites the original data with new data.
                        Cust [custIndex] [i]= data[i];
                    }
                    return custIndex;
                }
            }catch(ArrayIndexOutOfBoundsException arex){
                CustSize -= 1;
                throw new Exception("No more customers can be created.");
            }
        }else if(array == 1){
            try{
                if(append){     //Copies the data into a new entry.
                    TranSize += 1;
                    if(TranSize > 0){
                        //Stores a new TransactionId which is
                        //one digit greater than the old one.
                        int newTranId=(Integer.parseInt
                                (Trans[TranSize-1][0])+1);
                        Trans [TranSize] [0] =  newTranId + "";
                    }else{
                        Trans [TranSize] [0] = TranSize+"";
                    }
                    for(int i = 1; i < Trans[0].length; i++){
                        //Data is copied into its respective field.
                        Trans [TranSize] [i]= data[i-1];
                    }
                    return TranSize;
                }else{          //Overwrites an existing entry.
                    //Stores the index corresponding to the TranId.
                    int tranIndex=search(Integer.parseInt(data[0]),1);
                    for(int i = 0; i < Trans[0].length; i++){
                        //Overwrites the original data with new data.
                        Trans [tranIndex] [i]= data[i];
                    }
                    return tranIndex;
                }
            }catch(ArrayIndexOutOfBoundsException arex){
                TranSize -=1;
                throw new Exception("No more transactions can be created.");
            }
        }else{        //Handles a wrong input for the array parameter.
            Exception ex = new Exception("Wrong array parameter.");
            throw ex; //Throwing an exception for the internal error.
        }                
    }

    /**Static method DelData deletes the record corresponding to the index
     * recieved as a parameter from the specified array.  
     * Updates the value of the array's size variable(TranSize/CustSize).  
     * Pushes all the data one step up.
     * 
     * @param id    stores the id of the record to be deleted.
     * @param array states which array is to be searched.
     * 
     * @throws an exception with the error if the operation fails.
     */
    public static void DelData(int id,int array)throws Exception{
        if(array==0){
            //Stores the customer ID's index in the array.
            int index = search(id,0);
            if(index>CustSize){
                Exception ex = new Exception("Record does not exist.");
                //Throwing an exception since the element was not found.
                throw ex;   
            }
            int i = 0;          //Counter for the loop.
            try{
                for(i = index;i<=CustSize; i++){
                    Cust[i] = Cust[i+1];    
                }        
            }catch(ArrayIndexOutOfBoundsException arex){
                for(int j = 0;j<Cust[i].length;j++){
                    Cust[i][j] = null;
                }
            }
            CustSize -= 1;
        }else if(array == 1){
            int index = search(id,1);
            if(index>TranSize){
                Exception ex = new Exception("Record does not exist.");
                //Throwing an exception since the element was not found.
                throw ex;
            }
            int i = 0;          //Counter for the loop.
            try{
                for(i = index;i<=TranSize; i++){
                    Trans[i] = Trans[i+1];  
                }        
            }catch(ArrayIndexOutOfBoundsException arex){
                for(int j = 0;j<Trans[i].length;j++){
                    Trans[i][j] = null;
                }
            }
            TranSize -= 1; 
        }else{              //Handles a wrong input for the array parameter.
            Exception ex = new Exception("Wrong array parameter.");
            throw ex;   //Throwing an exception for the internal error.
        }
    }

    /**This method finds index of the id recieved as a parameter.  
     * Parameter int id takes in the CustId/TransId which's index needs to be found.
     * Parameter int array states which array is to be searched.
     * -->0-Customer Data Array(Cust)
     * -->1-Transaction Data Array(Cust)
     * Returns the index of the record holding the id. 
     * Uses the efficient binary search method.
     * 
     * @param id    stores the id which is to be found.
     * @param array states which array is to be searched.
     * 
     * @throws an exception with the error if the operation fails.
     */
    public static int search(int id,int array)throws Exception{
        int M = -1;         //Stores the index of the found value.
        int n = id;         //Stores the value to be found.
        if(array == 0){
            int flag= 0, L = 0, U = CustSize;
            while(L<=U){
                M= (L+U)/2;
                if(n>Integer.parseInt(Cust[M][0])){
                    L=M+1;
                }
                else if(n<Integer.parseInt(Cust[M][0])){
                    U = M-1;
                }else{
                    flag = 1;
                    break;
                }
            }
            if(!(flag == 1)){
                Exception ex = new Exception("Record does not exist.");
                //Throwing an exception since the element was not found.
                throw ex;   
            }
        }else if(array == 1){
            int flag= 0, L = 0, U = TranSize;
            while(L<=U){
                M= (L+U)/2;
                if(n>Integer.parseInt(Trans[M][0])){
                    L=M+1;
                }
                else if(n<Integer.parseInt(Trans[M][0])){
                    U = M-1;
                }else{
                    flag = 1;
                    break;
                }
            }
            if(!(flag == 1)){
                Exception ex = new Exception("Record does not exist.");
                //Throwing an exception since the element was not found.  
                throw ex;   
            }
        }else{           //Handles a wrong input for the array parameter.
            Exception ex = new Exception("Wrong array parameter.");
            throw ex;   //Throwing an exception.
        }
        return M;
    }

    /**This returns finds index(s) of the value recieved as a parameter.  
     * Parameter int inValue takes in the value which's index needs
     * to be found.
     * Parameter field states which field of the two dimensional
     * array is to be searched.
     * Parameter int array states which array is to be searched.
     * -->0-Customer Data Array(Cust)  
     * -->1-Transaction Data Array(Trans) 
     * Returns an array containing the index(s) of the record(s)
     * holding the value. 
     * Uses the string-enabled linear search method.
     * 
     * @param inValue contains the value to be found.
     * @param field   states which field of the two dimensional array 
     *                is to be searched. 
     * @param array   states which array is to be searched.
     * 
     * @throws an exception with the error if the operation fails.  
     */
    public static int [] search(String inValue,int field,int array)
    throws Exception{
        int [] output = {};
        inValue = inValue.toLowerCase();
        if(array == 0){
            int noOfResults = 0;
            String results = "";
            for (int i = 0; i <= CustSize; i++)
            {
                //Stores the array's specific value in the variable.
                String arrValue = Cust[i][field].toLowerCase();
                if(arrValue.equals(inValue)){
                    noOfResults += 1;
                    results += i + " ";
                }
            }
            if (noOfResults == 0)
            {
                Exception ex = new Exception("No matching records were found.");
                throw ex;
            }else{
                output = new int [noOfResults];
                Scanner parseResults = new Scanner(results);
                for(int i= 0; i<output.length;i++){
                    output[i] = parseResults.nextInt();
                }
            }
        } else if(array == 1){
            int noOfResults = 0;
            String results = "";
            for (int i = 0; i <= TranSize; i++)
            {
                if (Trans[i][field].toLowerCase().equals(inValue))
                {
                    noOfResults += 1;
                    results += i + " ";
                }
            }
            if (noOfResults == 0)
            {
                Exception ex = new Exception("No matching records were found.");
                throw ex;
            }else{
                output = new int [noOfResults];
                Scanner parseResults = new Scanner(results);
                for(int i= 0; i<output.length;i++){
                    output[i] = parseResults.nextInt();
                }
            }
        }else{        //Handles a wrong input for the array parameter.
            Exception ex = new Exception("Wrong array parameter.");
            throw ex; //Throwing an exception for the internal error.
        }
        return output;
    }

    /**This method returns the total number of 
     * customers present in the Data class.
     * No parameters are passed to the method.  
     * @returns the total as an int value.  
     */
    static int totalCust(){
        return (CustSize+1);
    }

    /**This method returns the total number of 
     * transactions present in the Data class.
     * No parameters are passed to the method.  
     * @returns the total as an int value.  
     */
    public static int totalTrans(){
        return (TranSize+1);
    }

    /**This method populates the arrays of 
     * the class with dummy values for 
     * testing this program.
     */
    public static void dummyDataPopulator()throws Exception{
        String [] a = {"Darezik Damkevala","J.S.S. Road ,Mumbai"
            ,"400002","9920980964"};
        AddData(a,0,true);
        String [] b = {"Mr Adit Sam","Karnatak Housing society,Banglore","800002",
                "9123542039"};
        AddData(b,0,true);
        String [] c = {"Mr Raj Singh","Raj Mahal,Punjab","100007","9676872193"};
        AddData(c,0,true);
        String [] d = {"Ms Sita Ram","Padmini Palace, Rajasthan","600097","9830465948"};
        AddData(d,0,true);
        String [] e = {"Sir Walter Scott","English House, Mumbai","4000052","9935254678"};
        AddData(e,0,true);
        String [] f = {"Mrs.  Swati Fernandes","Apartment No 13, All India Housing Society",
                "555555","9820976546"};
        AddData(f,0,true);
        String [] g = {"3/1/2011","3","400002","Mr Subhramaniyamiyer Venkataraman.",
                "The South Indian Co-Operative Housing Society.","123456","Not Sent"};
        AddData(g,1,true);
        String [] h = {"4/2/2011","1","545234","Mr. Mohan Khanna",
                "Emulsified Apartments, Cuffe Parade","454456","12/12/2012"};
        AddData(h,1,true);
        String [] i = {"6/3/2011","4","500525","Mr Naresh Bhikshuk"
            ,"Apartment 132,So-Bo Central.","123456","1/2/2012"};
        AddData(i,1,true);
        String [] j = {"1/4/2011","0","235132","Mr Dave Mason",
                "All India Housing Society","123456","19/12/2012"};
        AddData(j,1,true);        
        String [] k = {"8/5/2011","2","754002","Sir Billimoria",
                "Buckingham Palace, Delhi","123456","19/3/2010"};
        AddData(k,1,true);
        String [] l = {"4/6/2011","4","647802","Miss Jill Andrews",
                "Mantralaya,Navi Mumbai","123456","4/7/2011"};
        AddData(l,1,true);
        String [] m = {"9/7/2011","2","244002","Miss Propheria Black"
            ,"Flat No. 100, Pearl Towers, Kolkata.","123456","8/4/2011"};
        AddData(m,1,true);
        String [] n = {"1/8/2011","3","100000","Mr. Bholaram Bajaj"
            ,"Apartment 43, Godrej Towers, Tatanagar.","999999","1/9/2011"};
        AddData(n,1,true);
        String [] o = {"3/9/2011","1","973742","Mrs. Rema Mukherjee",
                "Apartment 124, Bengalee Housing, West Bengal.","123456","10/2/2011"};
        AddData(o,1,true);
        String [] p = {"6/5/2011","0","982046",
                "Mr. Adil Screwwalla.","Rythm House,Mumbai.","123456","6/8/2011"};
        AddData(p,1,true);
    }
}