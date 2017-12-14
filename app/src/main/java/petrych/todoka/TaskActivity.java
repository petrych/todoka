package petrych.todoka;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

                // Get input string from the text field
                EditText inputTaskName = (EditText) findViewById(R.id.task_name_input);
                taskName = inputTaskName.getText().toString();

                // Get the database from the previous activity that called the current one
                Bundle bundle = getIntent().getExtras();
                db = bundle.getParcelable("db");

                // Update the database with the newly created task
                TaskItem task = db.createTask(taskName);
                db.addTaskToList(task);

                // Pass the result to the previous activity that called the current one
                Intent resultIntent = new Intent();
                resultIntent.putExtra("db", db);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
