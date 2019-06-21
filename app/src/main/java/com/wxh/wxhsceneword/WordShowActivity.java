package com.wxh.wxhsceneword;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import base.BaseFragment;
import view.MainFragment;

public class WordShowActivity extends FragmentActivity implements BaseFragment.OnPopEventListener {
    private MainFragment mCurFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_show);
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("bundle");
        mCurFragment=MainFragment.newInstance();
        mCurFragment.setBundle(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.container, mCurFragment).commit();

    }

    @Override
    public void addFragment(BaseFragment fragment) {
        mCurFragment.addFragment(fragment);
    }

    @Override
    public boolean backToPop(String from, Bundle bundle) {
        return mCurFragment.backToPop(from, bundle);
    }

    @Override
    public boolean backToPop() {
        return backToPop(null, null);
    }

    @Override
    public void backToTop() {
        mCurFragment.backToTop();
    }

}
