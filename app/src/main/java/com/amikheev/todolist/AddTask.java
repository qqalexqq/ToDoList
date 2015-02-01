package com.amikheev.todolist;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.amikheev.todolist.helpers.DatePickerFragment;
import com.amikheev.todolist.helpers.TaskDAO;


public class AddTask extends FragmentActivity {

    private TaskDAO taskDAO;
    EditText taskDateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskDAO = new TaskDAO(this);

        this.taskDateText = (EditText) findViewById(R.id.taskDateText);

        findViewById(R.id.addTaskButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });
        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToMainActivity();
            }
        });
        findViewById(R.id.setDateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    public void showDatePickerDialog() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();

        datePickerFragment.setCallback(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                taskDateText.setText(
                        String.valueOf(dayOfMonth) + "." + String.valueOf(monthOfYear + 1) + "."
                                + String.valueOf(year)
                );
            }
        });
        datePickerFragment.show(getSupportFragmentManager(), "Date picker");
    }

    private void addTask() {
        EditText taskTitleText = (EditText) findViewById(R.id.taskTitleText);

        if (taskTitleText.length() < 1 || this.taskDateText.length() < 1) {
            Toast.makeText(
                    getApplicationContext(), "You should fill all fields of the form.",
                    Toast.LENGTH_LONG
            ).show();

            return;
        }

        taskDAO.createTask(taskTitleText.getText().toString(), this.taskDateText.getText().toString());

        Toast.makeText(
                getApplicationContext(),
                "Task '" + taskTitleText.getText().toString() + "' was successfully added.",
                Toast.LENGTH_LONG
        ).show();

        this.returnToMainActivity();
    }

    private void returnToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);

        this.finish();

        taskDAO.close();
    }
}
