package view;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wxh.wxhsceneword.R;

import java.util.List;

import Adapter.SentenceAdapter;
import Adapter.UsageMethodAdapter;
import base.BaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import contract.ShowFragmentContract;
import entry.ExampleSentence;
import entry.UsageMethod;
import entry.Word;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import presenter.ShowFragmentPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class WordFragment extends BaseFragment implements ShowFragmentContract.WordView {

    @BindView(R.id.tv_detail_chinese)
    TextView mWordChinese;
    @BindView(R.id.tv_detail_English)
    TextView mWordEnglish;
    @BindView(R.id.sentence_recyclerview)
    RecyclerView mSentenceRecyclerView;
    @BindView(R.id.usage_recyclerview)
    RecyclerView mUsageRecyclerView;
    @BindView(R.id.sound)
    ImageView sound;


    private String word;
    private Unbinder unbinder;
    private ShowFragmentPresenter mPresenter;
    private UsageMethodAdapter mUsageAdapter;
    private SentenceAdapter mSentenceAdapter;
    private int currentProgress = 0;
    private Handler handle=new Handler();

    public WordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter=new ShowFragmentPresenter(getContext());
        initView();
        initEvent();
    }

    private void initEvent() {

    }

    private void initView() {
        mUsageAdapter=new UsageMethodAdapter(getContext());
        mSentenceAdapter=new SentenceAdapter(getContext());
        Bundle bundle=getArguments();
        word=bundle.getString("word");
        mWordEnglish.setText(word);
        mSentenceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mUsageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSentenceRecyclerView.setItemAnimator(new SlideInLeftAnimator());
        mUsageRecyclerView.setItemAnimator(new SlideInLeftAnimator());
        mSentenceRecyclerView.setAdapter(new AlphaInAnimationAdapter(mSentenceAdapter));
        mUsageRecyclerView.setAdapter(new AlphaInAnimationAdapter(mUsageAdapter));
        mPresenter.getWordDetail(this,word);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_word, container, false);
        unbinder= ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void setUsageMethodList(List<UsageMethod> usageMethodList) {
        mUsageAdapter.setDataSource(usageMethodList);
        mUsageAdapter.notifyDataSetChanged();
    }

    @Override
    public void setSentenceList(List<ExampleSentence> sentenceList) {
        mSentenceAdapter.setDataSource(sentenceList);
        mUsageAdapter.notifyDataSetChanged();
    }

    @Override
    public void setWord(List<Word> word) {
        Word getWord=word.get(0);
        mWordChinese.setText(getWord.getChinese());
    }


    @OnClick(R.id.sound)
    void readWord(){
        Toast.makeText(getContext(),"录音开始播放",Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.word_back)
    void backToPop(){
        if(listener!=null){
            listener.backToPop();
        }
    }

}
