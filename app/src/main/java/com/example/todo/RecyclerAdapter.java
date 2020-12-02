package com.example.todo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.RoomDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private List<TodoList> todoList;
    private Activity context;
    private TodoDatabase database;

    public RecyclerAdapter(Activity context, List<TodoList> todoList){
        this.context = context;
        this.todoList = todoList;
        notifyDataSetChanged();
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

        database = TodoDatabase.getInstance(context);

        holder.tv_todo.setText(data.getTodo_content());

        holder.iv_delete.setOnClickListener(view -> {
            TodoList d = todoList.get(holder.getAdapterPosition());
            database.todoDao().delete(d);

            int position1 = holder.getAdapterPosition();
            todoList.remove(position1);
            notifyItemRemoved(position1);
            notifyItemRangeChanged(position1, todoList.size());
        });

        holder.todo_check.setOnClickListener(v -> {
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
        CheckBox todo_check;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_todo = itemView.findViewById(R.id.tv_todo);
            iv_delete = itemView.findViewById(R.id.iv_delete);
            todo_check = itemView.findViewById(R.id.todo_check);
        }
    }
}