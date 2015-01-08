package com.adambarnikaszabizoli.taskmanager.taskmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.adambarnikaszabizoli.taskmanager.taskmanager.restful.CreateTaskRequest;
import com.adambarnikaszabizoli.taskmanager.taskmanager.restful.DeleteTaskRequest;
import com.adambarnikaszabizoli.taskmanager.taskmanager.restful.GetSingleTaskRequest;
import com.adambarnikaszabizoli.taskmanager.taskmanager.restful.LoginRequest;
import com.adambarnikaszabizoli.taskmanager.taskmanager.restful.TaskRequest;
import com.adambarnikaszabizoli.taskmanager.taskmanager.restful.UpdateTaskRequest;
import com.adambarnikaszabizoli.taskmanager.taskmanager.restful.VolleyRequestManager;
import com.adambarnikaszabizoli.taskmanager.taskmanager.tasks.Task;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;


public class ManageTaskActivity extends Activity {

    private  int taskID = 0;
    private Task task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_task);
        if (getIntent().hasExtra("id"))
            taskID = getIntent().getExtras().getInt("id");
        if (taskID > 0 )
            executeTasksRequest();
    }

    public void executeTasksRequest() {
        GetSingleTaskRequest taskRequest = new GetSingleTaskRequest(new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                System.out.println("Success HEREEE");
                task = GetSingleTaskRequest.responseTask;
                fillInInputs();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("BAAD REQUEST");
            }
        }, taskID);
        VolleyRequestManager.getSharedInstance(this).getRequestQueue().add(taskRequest);
    }

    public void fillInInputs() {
        ((EditText) findViewById(R.id.due_date)).setText(task.getDueDateString());
        ((EditText) findViewById(R.id.name)).setText(task.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manage_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveTask(View v) {
        HashMap<String, String> taskParams = new HashMap<String, String>();
        taskParams.put("name", ((EditText) findViewById(R.id.name)).getText().toString());
        taskParams.put("due_date", ((EditText) findViewById(R.id.due_date)).getText().toString());
        taskParams.put("token", TaskManagerApplication.getToken().toString());
        if (taskID > 0) {
            taskParams.put("id", Integer.toString(taskID));
            updateTask(taskParams);
        } else
            createTask(taskParams);
    }

    protected void createTask(HashMap<String, String> taskParams) {
        CreateTaskRequest createTaskRequest = new CreateTaskRequest(new Response.Listener() {
            public void onResponse(Object response) {
                Toast.makeText(getApplicationContext(), "Task Created Successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(ManageTaskActivity.this, TaskListActivity.class));
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                System.out.println("BAD REQUEST");
                Toast.makeText(getApplicationContext(), "Invalid email or password.", Toast.LENGTH_LONG).show();
            }
        }, taskParams);
        VolleyRequestManager.getSharedInstance(this).getRequestQueue().add(createTaskRequest);
    }

    protected void updateTask(HashMap<String, String> taskParams) {
        UpdateTaskRequest createTaskRequest = new UpdateTaskRequest(new Response.Listener() {
            public void onResponse(Object response) {
                System.out.print("UPDATED EMBER" + response);
                Toast.makeText(getApplicationContext(), "Task Updated Successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(ManageTaskActivity.this, TaskListActivity.class));
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                System.out.println("BAD REQUEST");
                Toast.makeText(getApplicationContext(), "Invalid email or password.", Toast.LENGTH_LONG).show();
            }
        }, taskParams);
        VolleyRequestManager.getSharedInstance(this).getRequestQueue().add(createTaskRequest);
    }


    public void deleteTask(View v) {
        DeleteTaskRequest deleteTaskRequest = new DeleteTaskRequest(new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                System.out.println("Success HEREEE");
                Toast.makeText(getApplicationContext(), "Deleted Task Sucessfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(ManageTaskActivity.this, TaskListActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("BAAD REQUEST");
            }
        }, taskID);
        VolleyRequestManager.getSharedInstance(this).getRequestQueue().add(deleteTaskRequest);
    }

}
