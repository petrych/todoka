/**
 * IN PROGRESS
 *
 * This class is responsible for starting the application and setting up all the objects.
 * TODO - for now this class serves for only testing purposes. Needs to be in "startup" module.
 */

public class Startup {
    public static void main(String[] args) {
        Task aTask = new Task();
        Checklist checklist1 = new Checklist();
        ItemInChecklist item1 = new ItemInChecklist("read the book");
        ItemInChecklist item2 = new ItemInChecklist("find the plate");
        checklist1.addItem(item1, false);
        checklist1.addItem(item2, true);
        checklist1.getChecklist();
        System.out.println("Name: " + aTask.getName() + ", timeList is: " + aTask.getTimeList() +
                ", category is: " + aTask.getCategory());
    }
}
