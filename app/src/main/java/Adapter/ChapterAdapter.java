package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.wxh.wxhsceneword.R;

import org.w3c.dom.Text;

import base.BaseRecyclerViewAdapter;
import entry.Chapter;

public class ChapterAdapter extends BaseRecyclerViewAdapter<Chapter, ChapterAdapter.ChapterViewHolder> {

    /**
     * 构造方法
     *
     * @param context 关联活动
     */
    public ChapterAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item,viewGroup,false);
        ChapterViewHolder viewHolder=new ChapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ChapterViewHolder chapterViewHolder, final int i) {
        Chapter item=mDataSource.get(i);
        if(item==null)return;
        chapterViewHolder.textView.setText(item.getChapterName());
        chapterViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOuterListener!=null){
                    mOuterListener.onItemClick(v,i);
                }
            }
        });
        chapterViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mOuterListener!=null){
                    mOuterListener.onItemLongClick(v,i);
                    return true;
                }
                return false;
            }
        });
        chapterViewHolder.delImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOuterListener!=null){
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .repeat(1)
                            .playOn(chapterViewHolder.delImg);
                    mOuterListener.onItemDelete(v,i);
                }
            }
        });
    }

    class ChapterViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView delImg;
        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.rv_text);
            delImg=itemView.findViewById(R.id.item_del);
        }
    }
}
