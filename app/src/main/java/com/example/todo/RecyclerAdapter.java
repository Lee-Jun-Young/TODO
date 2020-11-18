package com.example.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private List<TodoList> items = new ArrayList<>();
    private Context mContext;
    private TodoDatabase db;

    public RecyclerAdapter(TodoDatabase db) {
        this.db = db;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<TodoList> getItems() {return items;}

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_item, viewGroup, false);
        mContext = viewGroup.getContext();
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, int position) {

        viewHolder.onBind(items.get(position),position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_todo;
        private TextView tv_memo;
        private Button btnSave;
        private int index;

        public ViewHolder(View view) {
            super(view);

            tv_todo = view.findViewById(R.id.tv_todo);
            tv_memo = view.findViewById(R.id.tv_memo);
            btnSave = view.findViewById(R.id.btnSave);
            btnSave.setOnClickListener(v -> editData(tv_memo.getText().toString()));

        }
        public void onBind(TodoList todoList, int position){
            index = position;
            tv_todo.setText(todoList.getTodoName());
            tv_memo.setText(todoList.getTodoMemo());
        }

        public void editData(String contents){
            new Thread(() -> {
                items.get(index).setTodoMemo(contents);
                db.todoDao().update(items.get(index));
            }).start();
            Toast.makeText(mContext,"저장완료", Toast.LENGTH_SHORT).show();
        }

    }

    public void setItem(List<TodoList> data) {
        items = data;
        notifyDataSetChanged();
    }

}