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
import entry.Word;

public class WordAdapter extends BaseRecyclerViewAdapter<Word, WordAdapter.WordViewHolder> {

    /**
     * 构造方法
     *
     * @param context 关联活动
     */
    public WordAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item,viewGroup,false);
        WordViewHolder viewHolder=new WordViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder wordViewHolder, final int i) {
        Word item=mDataSource.get(i);
        if(item==null)return;
        wordViewHolder.textView.setText(item.getEnglish());
        wordViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOuterListener!=null){
                    mOuterListener.onItemClick(v,i);
                }
            }
        });
        wordViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mOuterListener!=null){
                    mOuterListener.onItemLongClick(v,i);
                }
                return false;
            }
        });
        wordViewHolder.delImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOuterListener!=null){
                    mOuterListener.onItemDelete(v,i);
                }
            }
        });
    }



    class WordViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView delImg;
        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.rv_text);
            delImg=itemView.findViewById(R.id.item_del);
        }
    }
}
