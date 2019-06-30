package view;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
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
import es.dmoral.toasty.Toasty;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import presenter.ShowFragmentPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class WordFragment extends BaseFragment implements ShowFragmentContract.WordView,UsageMethodAdapter.delUsageListener,SentenceAdapter.delListener{

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
    @BindView(R.id.tv_word_top)
    TextView topText;
    @BindView(R.id.tv_word_buttom)
    TextView buttomText;

    private String word;
    private Unbinder unbinder;
    private ShowFragmentPresenter mPresenter;
    private UsageMethodAdapter mUsageAdapter;
    private SentenceAdapter mSentenceAdapter;
    private List<UsageMethod> mUsageMethodList;
    private List<ExampleSentence> mSentenceList;

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
        initView(view);
        initEvent();
    }

    private void initEvent() {
        mUsageAdapter.setmDelListener(this);
        mSentenceAdapter.setmDelListener(this);
    }

    private void initView(View view) {
        YoYo.with(Techniques.ZoomInDown)
                .duration(900)
                .repeat(0)
                .playOn(view.findViewById(R.id.tv_word_top));
        YoYo.with(Techniques.ZoomInDown)
                .duration(900)
                .repeat(0)
                .playOn(view.findViewById(R.id.tv_word_buttom));
        mUsageAdapter=new UsageMethodAdapter(getContext());
        mSentenceAdapter=new SentenceAdapter(getContext());
        Bundle bundle=getArguments();
        word=bundle.getString("word");
        mWordEnglish.setText(word);
        YoYo.with(Techniques.FlipInX)
                .duration(900)
                .repeat(0)
                .playOn(view.findViewById(R.id.tv_detail_English));
        YoYo.with(Techniques.FlipInY)
                .duration(900)
                .repeat(0)
                .playOn(view.findViewById(R.id.tv_detail_chinese));
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
        mUsageMethodList=usageMethodList;
        mUsageAdapter.setDataSource(mUsageMethodList);
        mUsageAdapter.notifyDataSetChanged();
    }

    @Override
    public void setSentenceList(List<ExampleSentence> sentenceList) {
        mSentenceList=sentenceList;
        mSentenceAdapter.setDataSource(mSentenceList);
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

    @OnClick(R.id.tv_word_top)
    void addUsageMethod(){
        final View layout=LayoutInflater.from(getContext()).inflate(R.layout.dialog_part,null,false);
        TextView textView=(TextView) layout.findViewById(R.id.ed_name);
        textView.setText("UsageMethod");
        TextView textView1=(TextView) layout.findViewById(R.id.ed_chinese);
        textView1.setText("UsageMethod Chinese");
        new AlertDialog.Builder(getContext()).setTitle("添加part")
                .setView(layout)
                .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText=(EditText)layout.findViewById(R.id.et_input_name);
                        String name = editText.getText().toString();
                        EditText editText1=(EditText)layout.findViewById(R.id.et_input_chinese);
                        String chinese=editText1.getText().toString();
                        UsageMethod usageMethod=new UsageMethod();
                        usageMethod.setBelongWord(mWordEnglish.getText().toString());
                        usageMethod.setUseChinese(chinese);
                        usageMethod.setUseWord(name);
                        if(mPresenter.querryUsageMethod(name)){
                            Toasty.error(getContext(), "该UsageMethod已存在，请重新输入！", Toast.LENGTH_SHORT, true).show();
                        }else {
                            Toasty.success(getContext(), "添加成功！", Toast.LENGTH_SHORT, true).show();
                            mPresenter.addUsageMethod(usageMethod);
                            mUsageMethodList.add(usageMethod);
                            mUsageAdapter.addData(usageMethod,mUsageMethodList.size());
                        }
                    }
                })
                .setNegativeButton("取消", null).show();
    }

    @OnClick(R.id.tv_word_buttom)
    void addSentence(){
        final View layout=LayoutInflater.from(getContext()).inflate(R.layout.dialog_part,null,false);
        TextView textView=(TextView) layout.findViewById(R.id.ed_name);
        textView.setText("Sentence");
        TextView textView1=(TextView) layout.findViewById(R.id.ed_chinese);
        textView1.setText("Sentence Chinese");
        new AlertDialog.Builder(getContext()).setTitle("添加Sentence")
                .setView(layout)
                .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText=(EditText)layout.findViewById(R.id.et_input_name);
                        String name = editText.getText().toString();
                        EditText editText1=(EditText)layout.findViewById(R.id.et_input_chinese);
                        String chinese=editText1.getText().toString();
                        ExampleSentence sentence=new ExampleSentence();
                        sentence.setBelongWord(mWordEnglish.getText().toString());
                        sentence.setSentenceC(chinese);
                        sentence.setSentenceE(name);
                        if(mPresenter.querrySentence(name)){
                            Toasty.error(getContext(), "该Sentence已存在，请重新输入！", Toast.LENGTH_SHORT, true).show();
                        }else {
                            Toasty.success(getContext(), "添加成功！", Toast.LENGTH_SHORT, true).show();
                            mPresenter.addSentence(sentence);
                            mSentenceList.add(sentence);
                            mSentenceAdapter.addData(sentence,mSentenceList.size());
                        }
                    }
                })
                .setNegativeButton("取消", null).show();
    }

    @Override
    public void onItemDel(View view, final int position) {
        final ExampleSentence sentence=mSentenceAdapter.getDataSource().get(position);
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this ExampleSentence!")
                .setConfirmText("Yes,delete it!")
                .setCancelText("No,cancel plx!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        mSentenceAdapter.removeData(sentence, position);
                        mPresenter.removeSentence(sentence.getSentenceE());
                        mSentenceList.remove(sentence);
                        sDialog
                                .setTitleText("Deleted!")
                                .setContentText("ExampleSentence has been deleted!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .show();
    }

    @Override
    public void delUsage(View view, final int position) {
        final UsageMethod usageMethod=mUsageAdapter.getDataSource().get(position);
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this UsageMethod!")
                .setConfirmText("Yes,delete it!")
                .setCancelText("No,cancel plx!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        mUsageAdapter.removeData(usageMethod, position);
                        mPresenter.removeUsageMethod(usageMethod.getUseWord());
                        mUsageMethodList.remove(usageMethod);
                        sDialog
                                .setTitleText("Deleted!")
                                .setContentText("UsageMethod has been deleted!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .show();
    }
}
