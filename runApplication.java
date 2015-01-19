/**
 * This class contains the project/application's main() method,
 * which is the first method called on the program's execution.
 * It creates an Object of the ConsoleUserInterface class.
 * This initial class contains only one method,
 * so as to maintain simplicity.
 * 
 * @author Darezik Damkevala 
 * @version 1.5
 */
public class runApplication
{
    /**This is the main method.
     * It is called when the program is executed.
     * It creates a new Object named userInteractions 
     * of the ConsoleUserInteface class, and calls the
     * begin() method of the object(userInteractions).
     */
    public static void main(){
        //Creating a new object of the ConsoleUserInterface.
        ConsoleUserInterface userInteractions = new ConsoleUserInterface();
        //Calling the begin() method of the userInteractions object.
        userInteractions.begin();
    }
}
