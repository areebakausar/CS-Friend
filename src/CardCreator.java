import java.io.FileReader; 
import java.io.IOException;
import java.util.Scanner;

/**
 * 
 * 
 * This class defines the thread task that will
 * "come up with" and submit greeting card ideas
 * to the print queue.  We have added the code
 * necessary to read from the file, but it's up to
 * you to handle turning off the printer (keeping
 * track of how many threads are open) and adding
 * the read-in line from the inspiration file to
 * the queue.
 * 
 * @author ak3rej
 * Sources: Big Java book
 *
 */
public class CardCreator implements Runnable {

    /**
     * Print queue to add new card ideas
     */
    private PrintQueue printQueue;

    /**
     * Inspiration file name
     */
    private String filename;

    /**
     * Keeps Track of number of threads being run
     */
    public static int threadCount;

    /**
     * CardCreator constructor
     */ 
    public CardCreator(PrintQueue d, String filename) {
        printQueue = d;
        this.filename = filename;
    }

    /**
     * Run method that is the main method for the thread
     */
    @Override
    public void run() {
        threadCount += 1;
        Scanner s = null;
        try {
            s = new Scanner(new FileReader(filename));
            while (s.hasNextLine()) {
                try {
                    String reader = s.nextLine(); // TODO: Read the next line from the inspiration file
                    printQueue.enqueue(reader); // TODO: Enqueue the line into the print queue
                    Thread.sleep(1000); // TODO: Have the thread sleep for 1 second (1000)
                } catch (InterruptedException e) {
                    e.printStackTrace(); // TODO Auto-generated catch block
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read file");
        } finally {
            if (s != null)
                s.close();
            threadCount -= 1;
            if (threadCount == 0) {     // if you're the last card creator left
                printQueue.turnOff();   //Turn off the print queue so that the 
                //printer thread(s) know to stop at some point
            }
        }
    }
}
