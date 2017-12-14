package petrych.todoka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import petrych.todoka.controller.TaskDatabase;
import petrych.todoka.controller.TaskItemAdapter;
import petrych.todoka.model.TaskItem;
import petrych.todoka.model.TimePeriod;

public class TaskListActivity extends AppCompatActivity {

    private ListView todayTaskListView;
    private ListView weekTaskListView;
    private ListView laterTaskListView;

    private ImageButton addTaskButton;

    private TaskDatabase db;

    private ArrayList<TaskItem> todayTasks;
    private ArrayList<TaskItem> weekTasks;
    private ArrayList<TaskItem> laterTasks;
    private ArrayList<TaskItem> completedTasks;

    private ArrayList<List<TaskItem>> allTaskLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        addTaskButton = (ImageButton) findViewById(R.id.add_task_button);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addTask = new Intent(TaskListActivity.this, TaskActivity.class);
                startActivity(addTask);
            }
        });

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
