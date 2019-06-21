package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wxh.wxhsceneword.R;

import base.BaseRecyclerViewAdapter;
import entry.ExampleSentence;

public class SentenceAdapter extends BaseRecyclerViewAdapter<ExampleSentence, SentenceAdapter.SentenceViewHolder> {
    /**
     * 构造方法
     *
     * @param context 关联活动
     */
    public SentenceAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public SentenceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.worddetial_item,viewGroup,false);
        SentenceViewHolder viewHolder=new SentenceViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SentenceViewHolder sentenceViewHolder, int i) {
        ExampleSentence item=mDataSource.get(i);
        if(item==null)return;
        sentenceViewHolder.Chinese.setText(item.getSentenceC());
        sentenceViewHolder.English.setText(item.getSentenceE());
    }

    class SentenceViewHolder extends RecyclerView.ViewHolder{
        TextView Chinese;
        TextView English;

        public SentenceViewHolder(@NonNull View itemView) {
            super(itemView);
            Chinese=itemView.findViewById(R.id.tv_detail__sample_chinese);
            English=itemView.findViewById(R.id.tv_detail_sample_english);
        }
    }
}
