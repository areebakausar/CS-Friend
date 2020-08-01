# Multithread-Greetings
Java Multithreading Queue-based Printing Program, reads concurrent .txt files and utilizes locks and conditions to print greetings into cards.
This contains implementation of CardCreator Runnabletasks for the company that will “thinkup” greeting card slogans to be printed, which will be read from their respective “inspiration” files.Each  line  of  the  inspiration  file  (for  exampleroses.txt)  contains  one  new  greeting  card  slogan.TheCardCreator will then submit that slogan into thePrintQueue.  The printer will remove theslogans from the queue and print them out in a card-formatted design.

For PrintQueue, a <b>Lock and Condition</b> ensure that two CardCreators do not attempt to add to the queue at the same time and for the Printer to be able to wait for a job to be queued. CardCreator,reads input line-by-line from the filenamegiven  as  a  parameter  to  the  constructor  and  add  that  to  thePrintQueue. Program Code enqueues the  new  greeting  card  slogan  onto  thePrintQueue and  then  sleep  for  1  second  (i.e.,  1000  ms) before submitting the next slogan.  (CardCreatorsmustrest between slogan creation so as not to face creative burnout or writers block.)  When the lastCardCreator <b> thread </b> has finished executing, they must turn off thePrintQueue, which will stop the printer thread and end your program.  (For this part, we implement a counter of running threads inside theCardCreatorclass, we also signal the printer which may be waiting on the Condition of an empty queue)

Following is the console output after multithreaded running of the program:

![Image](https://i.imgur.com/tc99kt0.png)
![Image](https://i.imgur.com/6bLSRuT.png)
![Image](https://imgur.com/uokfwEe)
