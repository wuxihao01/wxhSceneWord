package base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
    protected OnPopEventListener listener;

    protected boolean attached = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof  OnPopEventListener) {
            listener = (OnPopEventListener) context;
        }
        attached = true;
    }

    public boolean isAttached() {
        return attached;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        attached = false;
    }

    /**
     * 是否自行拦截处理返回按键
     * @return true 拦截
     */
    public boolean handleBackKeyEvent() {
        return false;
    }

    /**
     * 分发传递数据
     * @param from 传递类型
     * @param bundle 传递参数
     */
    public void dispatchResultData(String from, Bundle bundle) {

    }

    /**
     * 切换TAB
     * @param hidden 是否隐藏
     * @param requestData 是否请求数据
     */
    public void hiddenChanged(boolean hidden, boolean requestData) {

    }

    public interface OnPopEventListener {
        /**
         * 在父容器中添加界面
         * @param fragment
         */
        void addFragment(BaseFragment fragment);

        /**
         * fragment出栈，并传递数据
         * @param from 来源
         * @param bundle 数据源
         * @return
         */
        boolean backToPop(String from ,Bundle bundle);

        /**
         * fragment出栈
         * @return
         */
        boolean backToPop();

        /**
         * 返回栈顶
         */
        void backToTop();

    }
}
