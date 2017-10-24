/**
 * This class is responsible for creating a checklist as a map of text fields and checkboxes.
 *
 */

import java.util.HashMap;
import java.util.Iterator;


public class Checklist {

    private HashMap<ItemInChecklist, Boolean> checklist;

    public Checklist() {
        checklist = new HashMap<>();
    }

    public void addItem(ItemInChecklist item, Boolean checked) {
        checklist.put(item, checked);
    }

    /**
     * Print all items in the checklist as a text field + its checkbox status which can be true or false
     */
    public void getChecklist(){
        Iterator it = checklist.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            String itemName = ((ItemInChecklist) pair.getKey()).getName();

            // TODO - How to change printing to returning without quitting the loop? Delete all unnecessary comments after implementation
            System.out.println(itemName + " = " + pair.getValue());
            //return "loop finished"; // return statement finishes the loop :(
        }
        // return "getChecklist finished";
    }
}
