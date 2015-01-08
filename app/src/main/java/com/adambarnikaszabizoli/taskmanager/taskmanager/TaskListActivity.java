package com.adambarnikaszabizoli.taskmanager.taskmanager;


import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.adambarnikaszabizoli.taskmanager.taskmanager.restful.TaskRequest;
import com.adambarnikaszabizoli.taskmanager.taskmanager.restful.VolleyRequestManager;
import com.adambarnikaszabizoli.taskmanager.taskmanager.tasks.Task;
import com.adambarnikaszabizoli.taskmanager.taskmanager.tasks.TasksAdapter;
import com.android.volley.Response;
import com.android.volley.VolleyError;

public class TaskListActivity extends Activity {

	List<Task> tasks;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_list);
        tasks = new ArrayList<Task>();
        executeTasksRequest();
	}

    public void executeTasksRequest() {
        TaskRequest taskRequest = new TaskRequest(new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                System.out.println("Success HEREEE");
                tasks = TaskRequest.tasks;
                createList();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("BAAD REQUEST");
            }
        }, 0);
        VolleyRequestManager.getSharedInstance(this).getRequestQueue().add(taskRequest);
    }

    protected void createList() {
        ListView taskList = (ListView)findViewById(R.id.tasksList);
        TasksAdapter taskAdapter =new TasksAdapter(this, tasks);
        taskList.setAdapter(taskAdapter);
    }

    public void goToManageTask(int id) {
        Intent intent = new Intent(this, ManageTaskActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void deleteTask(View v) {

    }

    public void goToCreateTask(View v) {
        startActivity(new Intent(this, ManageTaskActivity.class));
    }
}