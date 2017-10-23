/**
 * This class is responsible for creating a single item in a checklist. An item consists of a text field and a checkbox,
 * representing a checked state - true or false. False by default.
 */

public class ItemInChecklist {

    private String name;
    private boolean checked;

    public ItemInChecklist() {
        this.name = "?";
        this.checked = false;
    }

    public ItemInChecklist(String name) {
        this.name = name;
        this.checked = false;
    }

    public String getName() {
        return this.name;
    }

    // TODO - Do I need to have another constructor with 2 params (name, checked)?

    public boolean getChecked() {
        return this.checked;
    }

    public boolean setChecked() {
        if (this.checked) { return this.checked = false; }
        else { return this.checked = true; }
    }
}
