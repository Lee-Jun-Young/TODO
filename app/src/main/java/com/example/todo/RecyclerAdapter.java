package com.example.todo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.RoomDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private List<TodoList> todoList;
    private Context context;
    private TodoDatabase database;

    public RecyclerAdapter(List<TodoList> todoList){
        this.todoList = todoList;
        notifyDataSetChanged();
    }

    public Context getContext(){
        return context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TodoList data = todoList.get(position);
        database = TodoDatabase.getInstance(getContext());
        holder.tv_todo.setText(data.getTodo_content());
        holder.todo_check.setOnCheckedChangeListener(null);

        holder.todo_check.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (holder.todo_check.isChecked()) {
                holder.tv_todo.setPaintFlags(holder.tv_todo.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            }
            else {
                holder.tv_todo.setPaintFlags(0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_todo;
        ImageView iv_delete;
        ImageView iv_edit;
        CheckBox todo_check;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_todo = itemView.findViewById(R.id.tv_todo);
            iv_delete = itemView.findViewById(R.id.iv_delete);
            todo_check = itemView.findViewById(R.id.todo_check);
            iv_edit = itemView.findViewById(R.id.iv_edit);
        }
    }
}