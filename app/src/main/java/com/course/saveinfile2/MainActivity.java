package com.course.saveinfile2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText et_date, et_description;
    private static final String DATE_SUCCESSFULY_MSG = "The data were successfuly saved";
    private static final String NO_DATA_MSG = "There is no data in the file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_date = findViewById(R.id.et_date);
        et_description = findViewById(R.id.et_result);
    }

    public void save(View v) {
        String file_name = et_date.getText().toString();
        file_name = file_name.replace('/', '-');

        try {
            OutputStreamWriter file = new OutputStreamWriter(openFileOutput(file_name, Activity.MODE_PRIVATE));
            file.write(et_description.getText().toString());
            file.flush();
            file.close();
        } catch (IOException ioe) { }
        Toast toast = Toast.makeText(this, DATE_SUCCESSFULY_MSG, Toast.LENGTH_LONG);
        toast.show();
        et_date.setText("");
        et_description.setText("");
    }

    public void recover(View v) {
        String file_name = et_date.getText().toString();
        file_name = file_name.replace('/', '-');
        boolean flag = false;
        String[] files = fileList();
        for (int i = 0; i < files.length; i ++) {
            if (file_name.equals(files[i]))
                flag = true;
        }
        if(flag == true) {
            try {
                InputStreamReader file = new InputStreamReader(openFileInput(file_name));
                BufferedReader br = new BufferedReader(file);
                String line = br.readLine();
                String todo = "";
                while ( line != null) {
                    todo = todo  + line + "\n";
                    line = br.readLine();
                }
                br.close();
                file.close();
                et_description.setText(todo);
            } catch (IOException ioex) {}
        } else {
            Toast.makeText(this, NO_DATA_MSG, Toast.LENGTH_LONG).show();
            et_description.setText("");
        }
    }
}
