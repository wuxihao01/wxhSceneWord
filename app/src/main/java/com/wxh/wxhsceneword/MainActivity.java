package com.wxh.wxhsceneword;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.spark.submitbutton.SubmitButton;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.Unbinder;
import contract.ReadWriteContract;
import entry.Chapter;
import entry.ExampleSentence;
import entry.Part;
import entry.Scene;
import entry.UsageMethod;
import entry.Word;
import entry.XmlList;
import es.dmoral.toasty.Toasty;
import presenter.ReadWritePresenter;
import presenter.ShowFragmentPresenter;

public class MainActivity extends AppCompatActivity implements ReadWriteContract.View {

    private Unbinder unbinder;
    private ReadWriteContract.Presenter mPresenter;
    private ShowFragmentPresenter showPresenter;
    public XmlList mXmlList;
    private String choose="Scene";
    List<Part> partList;
    List<Chapter> chapterList;
    List<Scene> sceneList;
    List<Word> wordList;
    List<UsageMethod> usageMethodList;
    List<ExampleSentence> sentenceList;

    @BindView(R.id.write)
    SubmitButton write;
    @BindView(R.id.show)
    Button show;
    @BindView(R.id.readFromXML)
    SubmitButton fb1;
    @BindView(R.id.btn_search)
    Button searchBtn;
    @BindView(R.id.ed_search)
    EditText searchEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initEvent();
        unbinder= ButterKnife.bind(this);
        initView();
        showPresenter=new ShowFragmentPresenter(getApplicationContext());
        mPresenter=new ReadWritePresenter(getApplicationContext(),this);
    }

    private void initView() {
    }

    private void initEvent() {
        NiceSpinner niceSpinner=(NiceSpinner) findViewById(R.id.nice_spinner);
        List<String> dataset = new LinkedList<>(Arrays.asList("Scene","English","Chinese"));
        niceSpinner.attachDataSource(dataset);
        niceSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                choose=(String) parent.getItemAtPosition(position);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.readFromXML)
    public void getWord(){
        mPresenter.readFromXML();
    }

    @OnClick(R.id.show)
    public void readFromDB(){
        Intent intent=new Intent(MainActivity.this,WordShowActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("type","normal");
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }

    @OnClick(R.id.write)
    public void writeToDB(){
        mPresenter.partWriteToDB(mXmlList);
    }

    @OnClick(R.id.btn_search)
    public void jumpToSearch(){
        Intent intent=new Intent(MainActivity.this,WordShowActivity.class);
        String values=searchEdit.getText().toString();
        Bundle bundle=new Bundle();
        bundle.putString("values",values);
        switch (choose){
            case "Scene":{
                if(!showPresenter.querryScene(values)){
                    Toasty.error(getApplicationContext(), "该Sence不存在", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                bundle.putString("type","scene");
                intent.putExtra("bundle",bundle);
            }break;
            case "English":{
                if(!showPresenter.querryWord(values)){
                    Toasty.error(getApplicationContext(), "该单词不存在", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                bundle.putString("type","english");
                intent.putExtra("bundle",bundle);
            }break;
            case "Chinese":{
                if (!showPresenter.querryWordByChinese(values)){
                    Toasty.error(getApplicationContext(), "该中文不存在", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                bundle.putString("type","chinese");
                intent.putExtra("bundle",bundle);
            }break;
            default:break;
        }
        startActivity(intent);
    }

    @Override
    public void getAllDB(XmlList xmlList) {
        mXmlList=xmlList;
        partList=xmlList.getPartList();
        chapterList=xmlList.getChapterList();
        sceneList=xmlList.getSceneList();
        wordList=xmlList.getWordList();
        usageMethodList=xmlList.getUsageMethodList();
        sentenceList=xmlList.getExampleSentenceList();
        for(Part part:partList){
            Log.d("ummmmm","part:"+part.getPartID());
        }
        for(Chapter chapter:chapterList){
            Log.d("ummmmm","chaptername:"+chapter.getChapterName()+"   chapterbelong:"+chapter.getBelongPart());
        }
        for(Scene item:sceneList){
            Log.d("ummmmm","sencename:"+item.getSceneName()+"   sencebelong:"+item.getBelongChapter());
        }
        for(Word item:wordList){
            Log.d("ummmmm","wordname:"+item.getEnglish()+"   wordChinese:"+item.getChinese()+"    wordbelong:"+item.getBelongScene());
        }
        for(UsageMethod item:usageMethodList){
            Log.d("ummmmm","usagename:"+item.getUseWord()+"      usageChinese:"+item.getUseChinese()+"   usagebelong:"+item.getBelongWord());
        }
        for(ExampleSentence item:sentenceList){
            Log.d("ummmmm","sentencename:"+item.getSentenceE()+"      sentenceChinese:"+item.getSentenceC()+"   sentencebelong:"+item.getBelongWord());
        }
    }

}
