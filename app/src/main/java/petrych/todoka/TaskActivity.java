package petrych.todoka;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import petrych.todoka.controller.TaskDatabase;
import petrych.todoka.model.TaskItem;

public class TaskActivity extends AppCompatActivity {

    private Button addTaskButton;
    private String taskName;
    private TaskDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        addTaskButton = (Button) findViewById(R.id.add_task_button);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputTaskName = (EditText) findViewById(R.id.task_name_input);
                taskName = inputTaskName.getText().toString();
                if (taskName != null) {
                    Bundle bundle = getIntent().getExtras();
                    db = bundle.getParcelable("db");
                    TaskItem task = db.createTask(taskName);
                    db.addTaskToList(task);
                    Toast.makeText(TaskActivity.this, "Task " + taskName + " added", Toast.LENGTH_SHORT).show();

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("db", db);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
                else {
                    Toast.makeText(TaskActivity.this, "Task name is null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
