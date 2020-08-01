import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * PrintQueue
 * 
 * Implement the class below as specified in the
 * homework 5 document.
 * 
 * @author ak3rej
 * Sources: TA office hours , Big Java book

 */

// Don't forget to include the appropriate import statements

public class PrintQueue {

    private LinkedList<String> toPrint;     // the printer's list of documents
    private Lock documentChangeLock;  // a ReentrantLock lock
    private Condition hasPrintTask;   // a condition object
    private boolean isOn;             // boolean variable describing if the 
    // queue is on (accepting jobs)

    //TODO:  Handle locking and conditions around the
    //       enqueueing and dequeuing of documents
    //       in this PrintQueue's document list (toPrint)
    //       Note: See example in the zip folder 'Thread Example 6 - Bank Deadlock' 


    /**
     * Constructor
     */
    public PrintQueue() {
        toPrint = new LinkedList<String>(); // create the list of documents
        isOn = true; // turn on the print queue
        documentChangeLock = new ReentrantLock();// Complete instantiation of documentChangeLock and hasPrintTask here
        hasPrintTask = documentChangeLock.newCondition();
    }


    /***
     * This method removes the head of the element and returns it
     * Printer will call this to get the next job to print. 
     * @return the head if queue is full and on, null otherwise
     * @throws InterruptedException
     */
    public String dequeue() throws InterruptedException {
        // This seems dangerous! Something needs to change here...!
        documentChangeLock.lock(); // lock
        try {
            if (toPrint.size() == 0 && isOn() == false)
            {   // Only remove an item from the queue if it's not empty 
                return null; 
            }
            else if (toPrint.size() == 0  && isOn() == true)
            {   // Do not dequeue if the queue is empty, but wait if the queue is on
                hasPrintTask.await(); 
                return null;
            }
            else {
                return toPrint.remove();
            }
        }
        finally {
            documentChangeLock.unlock();
        }
    }

    /***
     * CardCreator threads in Greeting Card Printer will use this method to add
     * greetings given by the String parameter onto the end of the print queue.
     * @param s the string that is being read by scanner in CardCreator
     * @throws InterruptedException
     */
    public void enqueue(String s) throws InterruptedException {
        // This seems dangerous! Something needs to change here...!
        documentChangeLock.lock(); // lock
        try {
            toPrint.add(s); // add to the list of documents
            hasPrintTask.signalAll(); 
        }
        finally {
            documentChangeLock.unlock();
        }
    }

    /***
     * Turns off the print queue
     */
    public void turnOff() {
        // Implement this method
        documentChangeLock.lock(); // lock
        try {
            isOn = false; // set a boolean field denoting that thePrintQueue is no longer accepting jobs
            hasPrintTask.signalAll(); 
        }
        finally {
            documentChangeLock.unlock();
        }
    }


    /**
     * isOn
     * TODO: Write more comments
     * @return  // returns a boolean field denoting whether thePrintQueue is accepting jobs
     */
    public boolean isOn() {
        documentChangeLock.lock(); // lock
        try {

            hasPrintTask.signalAll(); 
            return isOn;
        }
        finally {
            documentChangeLock.unlock();
        }

    }	

}
