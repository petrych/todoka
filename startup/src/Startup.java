import java.io.IOException;
import java.util.ArrayList;

/**
 * IN PROGRESS
 *
 * This class is responsible for starting the application and setting up all the objects.
 * TODO - for now this class serves for only testing purposes. Needs to be in "startup" module.
 */

public class Startup {
    public static void main(String[] args) throws IOException {
        ConsoleView console = new ConsoleView();
        console.mainLoop();

    }

    public void testFunctionality() {
        TaskItem aTask1 = new TaskItem("read the book");
        TaskItem aTask2 = new TaskItem("do the homework");

        aTask1.setTimePeriod(TimePeriod.WEEK);

        ArrayList <TaskItem> list = new ArrayList<>();
        list.add(aTask1);
        list.add(aTask2);

        printTaskDetails(list);

        aTask1.setName("improve smth");
        aTask2.setTimePeriod(TimePeriod.LATER);
        aTask2.setCompleted();

        printTaskDetails(list);
    }

    public static void printTaskDetails(ArrayList<TaskItem> list) {
        System.out.println("------------");
        for (TaskItem task : list) {
            //taskToString(task);
        }
        System.out.println("------------");
    }
}
