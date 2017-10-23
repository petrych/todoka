/**
 * This class is responsible for creating a checklist as a map of items and their checked states.
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
     * Print all items in the checklist as a line + its checked status which can be true or false
     */
    public void getChecklist(){
        Iterator it = checklist.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            String itemName = ((ItemInChecklist) pair.getKey()).getName();

            // TODO - How to change printing to returning without quitting the loop?
            System.out.println(itemName + " = " + pair.getValue());
        }
    }
}
