package base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView基础适配器<BR>
 *
 * @param <E>  数据对象
 * @param <VH> ViewHolder
 */
public abstract class BaseRecyclerViewAdapter<E, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    /**
     * Item点击事件监听<BR>
     */
    public interface OnItemClickListener {
        /**
         * Item点击回调<BR>
         *
         * @param view     点击视图
         * @param position 点击位置
         */
        void onItemClick(View view, int position);

        void onItemDelete(View view,int position);

        void onItemLongClick(View view,int position);
    }

    /**
     * 上下文
     */
    protected Context mContext;

    /**
     * 数据源
     */
    protected List<E> mDataSource = new ArrayList<E>();

    /**
     * 外部点击事件
     */
    protected OnItemClickListener mOuterListener;

    /**
     * 构造方法
     *
     * @param context 关联活动
     */
    public BaseRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    /**
     * 设置数据源<BR>
     *
     * @param dataSource 数据源
     */
    public void setDataSource(List<E> dataSource) {
        mDataSource.clear();
        if (!dataSource.isEmpty()) {
            mDataSource.addAll(dataSource);
        }
    }

    public void addData(E data,int size) {
        mDataSource.add(data);
        notifyItemInserted(size);
        notifyItemRangeChanged(0, mDataSource.size());
    }

    public void removeData(E data,int position) {
        mDataSource.remove(data);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0, mDataSource.size());
    }

    public void updata(E data,E newData){
        mDataSource.remove(data);
        mDataSource.add(newData);
        notifyDataSetChanged();
    }

    public List<E> getDataSource() {
        return mDataSource;
    }

    /**
     * 设置Item点击事件回调<BR>
     *
     * @param listener Item点击事件回调
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        mOuterListener = listener;
    }

    @Override
    public int getItemCount() {
        return mDataSource.isEmpty() ? 0 : mDataSource.size();
    }
}
