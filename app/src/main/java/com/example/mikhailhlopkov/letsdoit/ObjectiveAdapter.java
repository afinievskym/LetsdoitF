package com.example.mikhailhlopkov.letsdoit;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mikhailhlopkov.letsdoit.DB.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ObjectiveAdapter extends RecyclerView.Adapter<ObjectiveAdapter.ObjViewHolder> {

    public ArrayList<String> AddedObjective;
    ArrayList<String> AddedObjectiveTime;
    ArrayList<String> AddedImportantObjective;
    DBHelper dbHelper ;
    SQLiteDatabase database;
    Context context;
    public ObjectiveAdapter(Context context) {
        this.context = context;
        this.AddedObjective = db();
        this.AddedObjectiveTime = ObjTime();
        this.AddedImportantObjective = ImpObj();

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public ArrayList<String> db(){
        //Всегда getApplicationcontext() для баз данных!
        dbHelper = new DBHelper(context);
        ArrayList<String> dbHelperArray = new ArrayList<String>();
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DBHelper.OBJECTIVE_TABLE, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            //int idIndex = cursor.getColumnIndex(DBHelper.ID);
            int taskIndex = cursor.getColumnIndex(DBHelper.OBJECTIVE_NAME);
            do {
                dbHelperArray.add(cursor.getString(taskIndex));

            } while (cursor.moveToNext());
        }
        cursor.close();

        return dbHelperArray;
    }
    public ArrayList<String> ImpObj(){
        //Всегда getApplicationcontext() для баз данных!
        dbHelper = new DBHelper(context);
        ArrayList<String> dbHelperArray = new ArrayList<String>();
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DBHelper.OBJECTIVE_TABLE, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            //int idIndex = cursor.getColumnIndex(DBHelper.ID);
            int taskIndex = cursor.getColumnIndex(DBHelper.IMPORTANT_OB);
            do {
                dbHelperArray.add(cursor.getString(taskIndex));

            } while (cursor.moveToNext());
        }
        cursor.close();

        return dbHelperArray;
    }
    public ArrayList<String> ObjTime(){
        //Всегда getApplicationcontext() для баз данных!
        dbHelper = new DBHelper(context);
        ArrayList<String> dbHelperArray = new ArrayList<String>();
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DBHelper.OBJECTIVE_TABLE, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            //int idIndex = cursor.getColumnIndex(DBHelper.ID);
            int taskIndex = cursor.getColumnIndex(DBHelper.YEAR_OB);
            int taskIndex2 = cursor.getColumnIndex(DBHelper.MONTH_OB);
            int taskIndex3 = cursor.getColumnIndex(DBHelper.DAY_OB);
            do {
                dbHelperArray.add("До её достижения " + cursor.getString(taskIndex) + " YEARS" + cursor.getString(taskIndex2) + " MONTHS" + cursor.getString(taskIndex3) + "DAYS");

            } while (cursor.moveToNext());
        }
        cursor.close();

        return dbHelperArray;
    }
    public class ObjViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView objektive;
        TextView time_objective;
        TextView important_objective;

        public ObjViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv_obj);
            objektive = itemView.findViewById(R.id.tv_obj);
            time_objective = itemView.findViewById(R.id.tv_time_obj);
            important_objective = itemView.findViewById(R.id.imp_obj);

        }
    }

    @Override
    public ObjectiveAdapter.ObjViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.objective_card, parent, false);
        ObjectiveAdapter.ObjViewHolder taskViewHolder = new ObjectiveAdapter.ObjViewHolder(v);
        return taskViewHolder;
    }


    @Override
    public void onBindViewHolder(ObjectiveAdapter.ObjViewHolder holder, int position) {
        holder.objektive.setText(AddedObjective.get(position));
        holder.time_objective.setText(AddedObjectiveTime.get(position));
        holder.important_objective.setText(AddedImportantObjective.get(position));

    }

    @Override
    public int getItemCount() {return AddedObjective.size();}
}
