package com.wxh.wxhsceneword;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;


import com.spark.submitbutton.SubmitButton;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    //读写权限
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //请求状态码
    private static int REQUEST_PERMISSION_CODE = 1;


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
    @BindView(R.id.writeToXml)
    SubmitButton writeToXml;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initEvent();
        unbinder= ButterKnife.bind(this);
        initView();
        showPresenter=new ShowFragmentPresenter(getApplicationContext());
        mPresenter=new ReadWritePresenter(getApplicationContext(),this);
    }

    private void initView() {
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

    private void initEvent() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                Log.i("MainActivity", "申请的权限为：" + permissions[i] + ",申请结果：" + grantResults[i]);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.readFromXML)
    public void getWord(){
        fb1.setEnabled(false);
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
        write.setEnabled(false);
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

    @OnClick(R.id.writeToXml)
    void writOutXml(){
        writeToXml.setEnabled(false);
        mPresenter.writeToXml();
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

    @Override
    public void showResult(Boolean isSuccess) {
        if(isSuccess){
            Toasty.success(getApplicationContext(), "Xml成功生成！", Toast.LENGTH_SHORT, true).show();
        }
        else{
            Toasty.error(getApplicationContext(), "Xml生成失败", Toast.LENGTH_SHORT, true).show();
        }
    }


}
