package petrych.todoka.model;

/**
 * IMPORTANT
 *
 * This class is designed for future releases. Not used for v.1.0.
 *
 *
 * This class is responsible for creating a checklist. Checklist is a part of a task item.
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

            System.out.println(itemName + " = " + pair.getValue());
            //return "loop finished"; // return statement finishes the loop :(
        }
        // return "getChecklist finished";
    }
}

