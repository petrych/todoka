package petrych.todoka;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import petrych.todoka.controller.TaskItemAdapter;
import petrych.todoka.model.TaskItem;

public class TaskListActivity extends AppCompatActivity {

    private ListView todayTaskListView = (ListView) findViewById(R.id.today_task_list_view);
    private ListView weekTaskListView = (ListView) findViewById(R.id.week_task_list_view);
    private ListView laterTaskListView = (ListView) findViewById(R.id.later_task_list_view);

    private ArrayList<TaskItem> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);


        // Construct the data source
        tasks = new ArrayList<>();
        tasks.add(new TaskItem("Task 1"));
        tasks.add(new TaskItem("Task 2"));
        tasks.add(new TaskItem("Task 3"));

        // Create the adapter to convert the array to views
        TaskItemAdapter adapter = new TaskItemAdapter(this, tasks);

        // Attach the adapter to a ListView
        todayTaskListView.setAdapter(adapter);

        // Add items to adapter
        adapter.addAll(tasks);
    }
}
