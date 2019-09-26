package com.example.myapplication.customLayoutManager;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomLayoutManagerActivity extends AppCompatActivity {

    @BindView(R.id.id_recyclerview)
    RecyclerView id_recyclerview;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_layout_manager);
        ButterKnife.bind(this);

        id_recyclerview.setLayoutManager(new CardLayoutManager(this));
        id_recyclerview.setAdapter(new MyAdapter());

    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        private int[] icons = {R.mipmap.header_icon_1,R.mipmap.header_icon_2,R.mipmap.header_icon_3,R.mipmap.header_icon_4};
        private int[] bgs = {R.mipmap.bg_1,R.mipmap.bg_2,R.mipmap.bg_3,R.mipmap.bg_4};
        private String[] nickNames = {"左耳近心","凉雨初夏","稚久九栀","半窗疏影"};
        private String[] descs = {
                "回不去的地方叫故乡 没有根的迁徙叫流浪...",
                "人生就像迷宫，我们用上半生找寻入口，用下半生找寻出口",
                "原来地久天长，只是误会一场",
                "不是故事的结局不够好，而是我们对故事的要求过多",
                "只想优雅转身，不料华丽撞墙"
        };
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_echelon,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.icon.setImageResource(icons[position%4]);
            holder.nickName.setText(nickNames[position%4]);
            holder.desc.setText(descs[position%5]);
            holder.bg.setImageResource(bgs[position%4]);
        }


        @Override
        public int getItemCount() {
            return 10;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView icon;
        ImageView bg;
        TextView nickName;
        TextView desc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.img_icon);
            bg = itemView.findViewById(R.id.img_bg);
            nickName = itemView.findViewById(R.id.tv_nickname);
            desc = itemView.findViewById(R.id.tv_desc);
        }

    }

}
