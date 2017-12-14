package petrych.todoka;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import petrych.todoka.controller.TaskDatabase;
import petrych.todoka.controller.TaskItemAdapter;
import petrych.todoka.model.TaskItem;
import petrych.todoka.model.TimePeriod;

public class TaskListActivity extends AppCompatActivity {

    static final int PICK_CONTACT_REQUEST = 1;  // The request code

    private ListView todayTaskListView;
    private ListView weekTaskListView;
    private ListView laterTaskListView;

    private ImageButton plusButton;

    public TaskDatabase db;

    private ArrayList<TaskItem> todayTasks;
    private ArrayList<TaskItem> weekTasks;
    private ArrayList<TaskItem> laterTasks;
    private ArrayList<TaskItem> completedTasks;

    private ArrayList<List<TaskItem>> allTaskLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        todayTaskListView = (ListView) findViewById(R.id.today_task_list_view);
        weekTaskListView = (ListView) findViewById(R.id.week_task_list_view);
        laterTaskListView = (ListView) findViewById(R.id.later_task_list_view);

        this.db = new TaskDatabase();
        this.todayTasks = db.getListWithTasks(TimePeriod.TODAY);
        this.weekTasks = db.getListWithTasks(TimePeriod.WEEK);
        this.laterTasks = db.getListWithTasks(TimePeriod.LATER);

        setAdapterForTaskList(todayTasks, todayTaskListView);
        setAdapterForTaskList(weekTasks, weekTaskListView);
        setAdapterForTaskList(laterTasks, laterTaskListView);

        plusButton = (ImageButton) findViewById(R.id.plus_button);

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addTask = new Intent(TaskListActivity.this, TaskActivity.class);

                //Add content to intent
                addTask.putExtra("db", db);

                startActivityForResult(addTask, PICK_CONTACT_REQUEST);
            }
        });
    }

    // todo - redraw the screen after adding new tasks
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (PICK_CONTACT_REQUEST) : {
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    db = bundle.getParcelable("db");

                    this.todayTasks = db.getListWithTasks(TimePeriod.TODAY);
                    this.weekTasks = db.getListWithTasks(TimePeriod.WEEK);
                    this.laterTasks = db.getListWithTasks(TimePeriod.LATER);
                }
                break;
            }
        }
    }

    private void setAdapterForTaskList(ArrayList<TaskItem> taskList, ListView listView) {

        // Create the adapter to convert list to view
        TaskItemAdapter adapter = new TaskItemAdapter(this, taskList);

        // Attach the adapter to a ListView
        listView.setAdapter(adapter);

        // Add items to adapter
        adapter.addAll(taskList);

    }
}
