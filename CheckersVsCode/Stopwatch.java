
package CheckersVsCode;
import java.util.Scanner;

/**
 * 
 * @author imagi
 *
 * @version 1.0 2025-12-10 Initial implementation
 *
 *
 * @since 1.0
 */
public class Stopwatch {

private long startTime;
private long stoppedTime;
private boolean running;

/**
 * 
 *
 * @since 1.0
 */
public void start() {
    if (!this.running) {
        this.startTime = System.currentTimeMillis() - this.stoppedTime;
        this.running = true; // Starts and resumes stopwatch
    }
}

/**
 * 
 *
 * @since 1.0
 */
public void stop() {
    if (this.running) {
        this.stoppedTime = System.currentTimeMillis() - this.startTime;
        this.running = false;    // Stops the stopwatch
    }
}

/**
 * 
 *
 * @since 1.0
 */
public void reset() {
    this.startTime = 0;
    this.stoppedTime = 0;
    this.running = false;    // Resets stopwatch timer
}

/**
 * 
 * @return
 *
 * @since 1.0
 */
public long getElapsedTime() {
    if (this.running) {
        return System.currentTimeMillis() - this.startTime;
    }
    return this.stoppedTime; // Returns total time in ms
}

/**
 * 
 * @return
 *
 * @since 1.0
 */
public double getElapsedSeconds() {
    return getElapsedTime() / 1000.0;
}

/**
 * 
 * @return
 *
 * @since 1.0
 */
public boolean isRunning() {
    return this.running; // Checks if running
}

/**
 * 
 * @return
 *
 * @since 1.0
 */
public String getFormattedTime() {
    long ms = getElapsedTime();  // Calls previous method to get ms

    long hours = ms / 3600000;
    ms %= 3600000;

    long minutes = ms / 60000;
    ms %= 60000;

    long seconds = ms / 1000;
    long milliseconds  = ms % 1000;

    return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds); // Returns time in stopwatch format
    }

/**
 * 
 * @param args
 *
 * @since 1.0
 */
public static void main(String[] args) {
Scanner scanner = new Scanner(System.in);
Stopwatch stopwatch = new Stopwatch();

System.out.println("Commands: start, stop, reset, time, quit/exit");

while (true) {
    System.out.print("> ");
    String cmd = scanner.nextLine().trim().toLowerCase();

      switch (cmd) {
          case "start":
          stopwatch.start();
          System.out.println("Stopwatch started.");
          break;

          case "stop":
          stopwatch.stop();
          System.out.println("Stopwatch stopped.");
          break;

          case "reset":
          stopwatch.reset();
          System.out.println("Stopwatch reset.");
          break;

          case "time":
          System.out.println("Elapsed time: " + stopwatch.getFormattedTime());
          break;

          case "quit":
          case "exit":
              System.out.println("Exiting.");
              return;

          default:
              System.out.println("Unknown command.");
     }
   }
  }
}

   // end class Stopwatch