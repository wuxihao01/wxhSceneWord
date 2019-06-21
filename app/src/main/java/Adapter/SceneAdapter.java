package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wxh.wxhsceneword.R;

import base.BaseRecyclerViewAdapter;
import entry.Scene;

public class SceneAdapter extends BaseRecyclerViewAdapter<Scene, SceneAdapter.SceneViewHolder> {

    /**
     * 构造方法
     *
     * @param context 关联活动
     */
    public SceneAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public SceneViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item,viewGroup,false);
        SceneViewHolder viewHolder=new SceneViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SceneViewHolder sceneViewHolder, final int i) {
        Scene item=mDataSource.get(i);
        if(item==null)return;
        sceneViewHolder.textView.setText(item.getSceneName());
        sceneViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOuterListener!=null){
                    mOuterListener.onItemClick(v,i);
                }
            }
        });
        sceneViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mOuterListener!=null){
                    mOuterListener.onItemLongClick(v,i);
                }
                return false;
            }
        });
        sceneViewHolder.delImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOuterListener!=null){
                    mOuterListener.onItemDelete(v,i);
                }
            }
        });
    }

    class SceneViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView delImg;
        public SceneViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.rv_text);
            delImg=itemView.findViewById(R.id.item_del);
        }
    }
}
