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
import entry.UsageMethod;

public class UsageMethodAdapter extends BaseRecyclerViewAdapter<UsageMethod, UsageMethodAdapter.UsageMethodViewHolder> {
    /**
     * 构造方法
     *
     * @param context 关联活动
     */
    public UsageMethodAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public UsageMethodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.worddetial_item,viewGroup,false);
        UsageMethodViewHolder viewHolder=new UsageMethodViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsageMethodViewHolder usageMethodViewHolder, int i) {
        UsageMethod item=mDataSource.get(i);
        if(item==null)return;
        usageMethodViewHolder.Chinese.setText(item.getUseChinese());
        usageMethodViewHolder.English.setText(item.getUseWord());
    }

    class UsageMethodViewHolder extends RecyclerView.ViewHolder{
        TextView Chinese;
        TextView English;

        public UsageMethodViewHolder(@NonNull View itemView) {
            super(itemView);
            Chinese=itemView.findViewById(R.id.tv_detail__sample_chinese);
            English=itemView.findViewById(R.id.tv_detail_sample_english);
        }
    }
}
