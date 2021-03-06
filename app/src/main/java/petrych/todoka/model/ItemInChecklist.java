package petrych.todoka.model;

/**
 * This class is designed for future releases. Not used for v.1.0.
 *
 * This class is responsible for creating a single item in a checklist. An item consists of a text field and a checkbox,
 * representing a checked state - true or false. False by default.
 */

public class ItemInChecklist {

    private String name;
    private static final String DEFAULT_NAME = "?";
    private boolean checked;

    public ItemInChecklist() {
        this.name = DEFAULT_NAME;
        this.checked = false;
    }

    public ItemInChecklist(String name) {
        this.name = name;
        this.checked = false;
    }

    public String getName() {
        return this.name;
    }

    public boolean getChecked() {
        return this.checked;
    }

    public boolean setChecked() {
        if (this.checked) { return this.checked = false; }
        else { return this.checked = true; }
    }
}

