/**
 * Contains methods for calculating the cost of sending
 * the mail from the source to the destination.
 * The logic used for calculating the cost is as follows:
 * -->The numerical difference between the pin codes is 
 *    calculated.
 * -->If this difference is less than 100 the mail is 
 *    considered a local mail.
 * -->For local mails the cost is numerically equal to
 *    the difference of the areas' pin codes added
 *    to the base cost of Rs 10.
 * -->For other mails the cost is equal to the 
 *    difference of the area's pin codes added
 *    to the base cost of Rs 100.
 *    
 * @author Darezik Damkevala 
 * @version 1.5
 */
public class Cost
{
    /**Stores the Pin Code of the Area from which the mail 
     * originates.
     */
    int fromPin = 0;
    
    /**Stores the Pin Code of the Area to which the mail is
     * being sent.
     */
    int toPin = 0;

    /**Stores the values passed in as parameters into the 
     * object's variables.
     * The parameters are:
     * -->from:Stores the Pin Code of the Area from which
     *    the mail originates.
     * -->to:Stores the Pin Code of the Area to which the 
     *    mail is being sent.
     *    
     * @param from The pin code the mail was sent from.
     * @param to The pin code the mail was sent to.
     */
    Cost(int from, int to){
        fromPin = from;
        toPin = to;
    }

    /**Calculates the amount to be paid by the customer.
     * Returns the cost as an int.
     * The logic used for calculating the cost is as follows:
     * -->The numerical difference between the pin codes is 
     *    calculated.
     * -->If this difference is less than 100 the mail is 
     *    considered a local mail.
     * -->For local mails the cost is equal to the 
     *    difference of the areas' pin codes added
     *    to the base cost of Rs 10.
     * -->For other mails the cost is equal to the 
     *    difference of the areas' pin codes added
     *    to the base cost of Rs 100.
     *    
     * @return cost The cost of sending the mail.
     */
    int Calc(){
        int cost;//stores the calculated cost which is to be paid.
        int diff = Math.abs(fromPin-toPin);//Stores the difference 
        //between the pin codes.
        if(diff<100){//Executes the below code block for local mails.
            cost = 10 + diff;
        }else{//Executes the below code block for other mails.
            cost = 100 + diff/100;
        }
        return  cost;//Returns the calculated cost.
    }
}
