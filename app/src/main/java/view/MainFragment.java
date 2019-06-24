package view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wxh.wxhsceneword.R;

import java.util.Stack;

import base.BaseFragment;
import util.StringUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment {
    private Stack<BaseFragment> fragmentStack = new Stack<>();
    private Bundle bundle;
    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * 创建栈底Fragment
     * @return
     */
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String type=bundle.getString("type");
        Bundle putbundle=new Bundle();
        BaseFragment fragment = null;
        switch (type){
            case "normal":{
                fragment=new PartFragment();
            }break;
            case "scene":{
                fragment=new WordListFragment();
                putbundle.putString("type","fromscene");
                putbundle.putString("scene",bundle.getString("values"));
                fragment.setArguments(putbundle);
            }break;
            case "english":{
                fragment=new WordFragment();
                putbundle.putString("word",bundle.getString("values"));
                fragment.setArguments(putbundle);
            }break;
            case "chinese":{
                fragment=new WordListFragment();
                putbundle.putString("type","chinese");
                putbundle.putString("searchChinese",bundle.getString("values"));
                fragment.setArguments(putbundle);
            }break;
            default:break;
        }
        addFragment(fragment);
    }

    private void addFragmentToContent(BaseFragment fragment) {
        FragmentTransaction transaction =  getChildFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container,fragment).commit();
        transaction.addToBackStack(null);
        fragmentStack.add(fragment);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public void addFragment(BaseFragment fragment) {
        if(fragment != null) {
            addFragmentToContent(fragment);
        }
    }

    /**
     * 处理上层fragment出栈的逻辑
     * @param from
     * @param bundle
     * @return
     */
    public boolean backToPop(String from, Bundle bundle) {
        int count = getChildFragmentManager().getBackStackEntryCount();
        if(count > 1) {
            getChildFragmentManager().popBackStackImmediate();
            fragmentStack.pop();
            BaseFragment fragment = fragmentStack.peek();
            if(fragment != null && fragment.isAttached()) {
                if(!StringUtils.isEmpty(from)) {
                    fragment.dispatchResultData(from, bundle);
                }
                getChildFragmentManager().beginTransaction().show(fragment).commit();
            }
            return true;
        }
        return false;
    }

    /**
     * 返回顶层界面
     */
    public void backToTop() {
        int count = fragmentStack.size();
        if(count > 1) {
            while (fragmentStack.size() > 1) {
                fragmentStack.pop();
                getChildFragmentManager().popBackStackImmediate();
            }
            BaseFragment curFragment = fragmentStack.peek();
            if(curFragment != null && curFragment.isAttached()) {
                getChildFragmentManager().beginTransaction().show(curFragment).commit();
            }
        }
    }

    public void setBundle(Bundle getBundle){
        bundle=getBundle;
    }
}
