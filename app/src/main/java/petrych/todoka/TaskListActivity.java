package petrych.todoka;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import petrych.todoka.controller.DataLoadedListener;
import petrych.todoka.controller.DBHandler;
import petrych.todoka.controller.TaskItemAdapter;
import petrych.todoka.model.TaskItem;
import petrych.todoka.model.TimePeriod;

public class TaskListActivity extends AppCompatActivity implements DataLoadedListener {

    // Identifies a request from this activity
    static final int PICK_CONTACT_REQUEST = 1;  // The request code

    // Views for task lists
    private ListView todayTaskListView;
    private ListView weekTaskListView;
    private ListView laterTaskListView;

    // This button opens a screen for task creation
    private ImageButton plusButton;

    // Task lists handling with the database
    public DBHandler dbHandler;

    // Custom adapter for each task list
    private TaskItemAdapter todayTasksAdapter;
    private TaskItemAdapter weekTasksAdapter;
    private TaskItemAdapter laterTasksAdapter;

    // Storage for tasks
    private ArrayList<TaskItem> todayTasks;
    private ArrayList<TaskItem> weekTasks;
    private ArrayList<TaskItem> laterTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        // Initialize connection to the database
        dbHandler = DBHandler.getInstance();
        dbHandler.addDataLoadedListener(this);

        // Set view for each task list
        todayTaskListView = (ListView) findViewById(R.id.today_task_list_view);
        weekTaskListView = (ListView) findViewById(R.id.week_task_list_view);
        laterTaskListView = (ListView) findViewById(R.id.later_task_list_view);

        // Update list views with list items
        updateAllListsAndViews();

        plusButton = (ImageButton) findViewById(R.id.plus_button);
        plusButton.setOnClickListener(new View.OnClickListener() {
            // Opens a new screen for task creation
            @Override
            public void onClick(View v) {
                Intent addTask = new Intent(TaskListActivity.this, TaskActivity.class);
                startActivityForResult(addTask, PICK_CONTACT_REQUEST);
            }
        });
    }

    /**
     * Sets ListView height dynamically based on the height of the items.
     *
     * @param listView to be resized
     **/
    public static void setListViewHeightBasedOnItems(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();
        }
    }

    /**
     * Receives the result from the activity where a task was created.
     * When you receive the result Intent, the callback provides the same request code
     * so that your app can properly identify the result and determine how to handle it.
     * @param requestCode
     * @param resultCode
     * @param data The activity which sends the result
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (PICK_CONTACT_REQUEST): {
                if (resultCode == Activity.RESULT_OK) {
                    // Update all task lists and their adapters
                    updateAllListsAndViews();
                }
            }
        }
    }

    /**
     * Updates task lists from the database and connects corresponding adapters to them,
     * If a task list is empty, displays a message about it.
     * Thus ListViews are updated with fresh data.
     */
    private void updateAllListsAndViews() {
        // Get all the task lists from the database and connect corresponding adapters to them
        todayTasks = dbHandler.getListWithTasks(TimePeriod.TODAY);
        todayTasksAdapter = setAdapterForTaskList(todayTasks, todayTaskListView);

        weekTasks = dbHandler.getListWithTasks(TimePeriod.WEEK);
        weekTasksAdapter = setAdapterForTaskList(weekTasks, weekTaskListView);

        laterTasks = dbHandler.getListWithTasks(TimePeriod.LATER);
        laterTasksAdapter = setAdapterForTaskList(laterTasks, laterTaskListView);

        // Display a message that a list is empty if there is no data in the list
        displayEmptyListMessage();

        // Set height of listView so all list items are shown in their list view
        setListViewHeightBasedOnItems(todayTaskListView);
        setListViewHeightBasedOnItems(weekTaskListView);
        setListViewHeightBasedOnItems(laterTaskListView);
    }

    /**
     * Display a message that a list is empty if there is no data in the list.
     * Text for the message is defined in the corresponding list layout.
     */
    private void displayEmptyListMessage() {
        if (todayTasksAdapter.getCount() == 0) {
            todayTaskListView.setEmptyView((View)findViewById(R.id.empty_list_today));
        }
        if (weekTasksAdapter.getCount() == 0) {
            weekTaskListView.setEmptyView((View)findViewById(R.id.empty_list_week));
        }
        if (laterTasksAdapter.getCount() == 0) {
            laterTaskListView.setEmptyView((View)findViewById(R.id.empty_list_later));
        }
    }

    /**
     * Couples adapter with the corresponding task list.
     * @param taskList The content to show in a specific view
     * @param listView Part of the view where to show the content
     * @return adapter
     */
    private TaskItemAdapter setAdapterForTaskList(ArrayList<TaskItem> taskList, ListView listView) {

        // Create the adapter to convert list to view
        TaskItemAdapter adapter = new TaskItemAdapter(this, taskList);

        // Attach the adapter to a ListView
        listView.setAdapter(adapter);

        return adapter;
    }

    @Override
    public void onDataLoaded() {
        updateAllListsAndViews();
    }
}
