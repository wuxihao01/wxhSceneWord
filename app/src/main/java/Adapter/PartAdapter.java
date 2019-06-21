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
import entry.Part;

public class PartAdapter extends BaseRecyclerViewAdapter<Part, PartAdapter.PartViewHolder> {



    /**
     * 构造方法
     *
     * @param context 关联活动
     */
    public PartAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public PartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item,viewGroup,false);
        PartViewHolder viewHolder=new PartViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PartViewHolder partViewHolder, final int i) {
        Part item=mDataSource.get(i);
        if(item==null)return;
        partViewHolder.textView.setText(item.getPartID());
        partViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOuterListener!=null){
                    mOuterListener.onItemClick(v,i);
                }
            }
        });
        partViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mOuterListener!=null){
                    mOuterListener.onItemLongClick(v,i);
                    return true;
                }
                return false;
            }
        });
        partViewHolder.delImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOuterListener!=null){
                    mOuterListener.onItemDelete(v,i);
                }
            }
        });
    }

    class PartViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView delImg;
        public PartViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.rv_text);
            delImg=itemView.findViewById(R.id.item_del);
        }
    }
}
