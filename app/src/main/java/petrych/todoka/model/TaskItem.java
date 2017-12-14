package petrych.todoka.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A task consists of a name (text field) and a completed status, time period and a category.
 * A task can be created using a task name.
 * In v.1.0 only one constructor is used, the other two are intended for future releases.
 *
 * A task is always stored in one list, Today by default.
 * May also be listed in one (only one) category.
 */


public class TaskItem implements Parcelable {

    private String taskName; // text field of a task
    private boolean completed;

    private TimePeriod timePeriod;
    private String category;

    public TaskItem(String taskName) {
        this.taskName = taskName;
        this.completed = false;
        this.timePeriod = TimePeriod.TODAY;    // Default time period.
        this.category = null;
    }

    public TaskItem(String taskName, TimePeriod timePeriod) {
        this.taskName = taskName;
        this.completed = false;
        this.timePeriod = timePeriod;
        this.category = null;
    }

    public TaskItem(String taskName, TimePeriod timePeriod, String category) {
        this.taskName = taskName;
        this.completed = false;
        this.timePeriod = timePeriod;
        this.category = category;
    }

    /**
     * This method is a part of the implementation of Parcelable interface
     * which makes the data transferrable between activities.
     * @param in
     */
    protected TaskItem(Parcel in) {
        this.taskName = in.readString();
        this.completed = in.readByte() != 0;
        this.timePeriod = TimePeriod.TODAY;
        this.category = in.readString();
    }

    /**
     * This method is a part of the implementation of Parcelable interface
     * which makes the data transferrable between activities.
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * This method is a part of the implementation of Parcelable interface
     * which makes the data transferrable between activities.
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(taskName);
        dest.writeInt(completed ? 1 : 0);
        dest.writeString(category);
    }

    /**
     * This method is a part of the implementation of Parcelable interface
     * which makes the data transferrable between activities.
     */
    public static final Creator<TaskItem> CREATOR = new Creator<TaskItem>() {
        @Override
        public TaskItem createFromParcel(Parcel in) {
            return new TaskItem(in);
        }

        @Override
        public TaskItem[] newArray(int size) {
            return new TaskItem[size];
        }
    };

    public String taskToString() {
        String str = "Task name: " + this.getName() +
                ", time period: " + this.getTimePeriod();

        if (this.isCompleted()) {
            str = str + ", completed status: yes";
        } else {
            str = str + ", completed status: no";
        }

        if (this.getCategory() == null) {
            str = str + ", category: empty";
        } else {
            str = str + ", category: " + this.getCategory().toString();
        }

        return str;
    }

    public String getName() {
        return taskName;
    }

    public void changeName(String name) {
        this.taskName = name;
    }

    public TimePeriod getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(TimePeriod timePeriod) {
        this.timePeriod = timePeriod;
    }

    public void setTimePeriodFromString(String str) {
        if (str.equals("t")) {
            this.timePeriod = TimePeriod.TODAY;
        }
        else if (str.equals("w")) {
            this.timePeriod = TimePeriod.WEEK;
        }
        else if (str.equals("l")) {
            this.timePeriod = TimePeriod.LATER;
        }
        else if (str.equals("c")) {
            this.timePeriod = TimePeriod.COMPLETED;
            this.setCompleted();
        }
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted() {
        this.completed = true;
    }
}
