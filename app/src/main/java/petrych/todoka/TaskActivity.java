package petrych.todoka;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Vector;

import petrych.todoka.controller.DBHandler;
import petrych.todoka.model.TaskItem;
import petrych.todoka.model.TimePeriod;

public class TaskActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // List of time period choices for a drop down (spinner)
    private Vector<CharSequence> timePeriodChoices;
    // Button to add a newly created task
    private Button addTaskButton;
    // Cancel task creation
    private Button cancelButton;

    // Data needed for task creation
    private String taskName;
    private String timePeriodString;
    private String taskCategory;

    // Storage for all tasks
    private DBHandler dbHandler;
    private DatabaseReference tasksDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        dbHandler = DBHandler.getInstance();

        // Initialize Firebase database reference
        tasksDB = FirebaseDatabase.getInstance().getReference().child("tasks");

        // Handle spinner input for time periods

        timePeriodChoices = new Vector<>();
        for (TimePeriod tp : TimePeriod.getAllTimePeriods()) {
            String timePeriod = tp.toString();
            timePeriodChoices.add(timePeriod);
        }

        // Set view for spinner
        Spinner timePeriodSpinner = (Spinner) findViewById(R.id.time_period_spinner);

        // Create an ArrayAdapter using the string list and a default spinner layout
        ArrayAdapter<CharSequence> timePeriodAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, this.timePeriodChoices);

        // Specify the layout to use when the list of choices appears
        timePeriodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        timePeriodSpinner.setAdapter(timePeriodAdapter);

        // Listen to selected items
        timePeriodSpinner.setOnItemSelectedListener(this);

        // Handle adding task

        addTaskButton = (Button) findViewById(R.id.add_task_button);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get input string from the text fields
                EditText inputTaskName = (EditText) findViewById(R.id.task_name_input);
                taskName = inputTaskName.getText().toString();

                EditText inputTaskCategory = (EditText) findViewById(R.id.category_input);
                taskCategory = inputTaskCategory.getText().toString();

                // Create a new task and update the database
                TaskItem task = dbHandler.createTask(taskName, TimePeriod.getTimePeriodFromString(timePeriodString), taskCategory);
                tasksDB.child(timePeriodString).push().setValue(task);

                setResult(Activity.RESULT_OK, new Intent());
                finish();
            }
        });

        // Handle cancelling

        cancelButton = (Button) findViewById(R.id.cancel_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * This method is a part of the implementation of AdapterView.OnItemSelectedListener interface
     * which retrieves data from spinner
     * @param adapterView
     * @param view
     * @param pos
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        timePeriodString = adapterView.getItemAtPosition(pos).toString();
    }

    /**
     * This method is a part of the implementation of AdapterView.OnItemSelectedListener interface
     * which retrieves data from spinner
     * @param adapterView
     */
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
