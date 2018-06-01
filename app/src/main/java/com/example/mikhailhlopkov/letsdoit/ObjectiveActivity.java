package com.example.mikhailhlopkov.letsdoit;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.mikhailhlopkov.letsdoit.DB.DBHelper;
import com.example.mikhailhlopkov.letsdoit.DB.Task;
import com.example.mikhailhlopkov.letsdoit.fragments.TasksFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ObjectiveActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objective);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ReadFromAdapter();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuiltWindow();
            }
        });
    }
    private void BuiltWindow(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ObjectiveActivity.this);
        View view_dialog = getLayoutInflater().inflate(R.layout.dialog_new_objective, null);

        Button cancell = view_dialog.findViewById(R.id.cancel_action_ob);
        Button add = view_dialog.findViewById(R.id.add_ob);
        final RadioGroup radioGroup = view_dialog.findViewById(R.id.rg2);
        //RadioButton low = view_dialog.findViewById(R.id.low_important);

        final EditText editObjective = view_dialog.findViewById(R.id.objective);
        final EditText edityears = view_dialog.findViewById(R.id.years_ob);
        final EditText editmonths = view_dialog.findViewById(R.id.months_ob);
        final EditText editdays = view_dialog.findViewById(R.id.days_ob);
        final EditText editmotiv = view_dialog.findViewById(R.id.motiv_ob);

        final String objective = editObjective.toString();
        String years = edityears.toString();
        String months = editmonths.toString();
        String days = editdays.toString();
        final String motiv = editmotiv.toString();
        final String[] imp = new String[1];

        if (years.equals("")){years = "0";}
        if (months.equals("")){months = "0";}
        if (days.equals("")){days = "0";}

        builder.setView(view_dialog);
        final AlertDialog dialog = builder.create();
        cancell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final String finalYears = years;
        final String finalMonths = months;
        final String finalDays = days;
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int checkedButtonId = radioGroup.getCheckedRadioButtonId();
                switch (checkedButtonId) {
                    case R.id.rblow_ob:
                        imp[0] = "Вспомогательная";
                        break;
                    case R.id.rbmedium_ob:
                        imp[0] = "Важная";
                        break;
                    case R.id.rbhard_ob:
                        imp[0] = "Главная";
                }
                String important = imp[0];

                writeToDb(objective, finalYears, finalMonths, finalDays, motiv, important);
                dialog.dismiss();

            }
        });
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
    }
    private void writeToDb(String name, String year, String month, String day, String motiv, String important) {
        DBHelper helper = new DBHelper(ObjectiveActivity.this);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBHelper.OBJECTIVE_NAME, name);
        cv.put(DBHelper.YEAR_OB, year);
        cv.put(DBHelper.MONTH_OB, month);
        cv.put(DBHelper.DAY_OB, day);
        cv.put(DBHelper.MOTIV_OB,motiv);
        cv.put(DBHelper.IMPORTANT_OB, important);
        db.insert(DBHelper.OBJECTIVE_TABLE, null, cv);
        db.close();
        helper.close();
    }
    private void ReadFromAdapter(){
        RecyclerView rv = (RecyclerView)findViewById(R.id.objective_recycle);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        //Error NullPointer execption
        ObjectiveAdapter adapter = new ObjectiveAdapter(getApplicationContext());
        //end error
        rv.setAdapter(adapter);
    }

}
