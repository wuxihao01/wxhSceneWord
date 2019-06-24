package view;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.wxh.wxhsceneword.MainActivity;
import com.wxh.wxhsceneword.R;

import java.util.List;

import Adapter.WordAdapter;
import base.BaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import contract.ShowFragmentContract;
import entry.Scene;
import entry.Word;
import es.dmoral.toasty.Toasty;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import presenter.ShowFragmentPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class WordListFragment extends BaseFragment implements ShowFragmentContract.WordListView, WordAdapter.OnItemClickListener {
    @BindView(R.id.word_back)
    ImageView imageView;
    @BindView(R.id.word_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.word_add)
    ImageView addWordView;
    private ShowFragmentPresenter mPresenter;
    private WordAdapter adapter;
    public String type;
    private List<Word> mDataList;
    private Unbinder unbinder;
    private View view;
    private String belong;
    public WordListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_word_list, container, false);
        unbinder= ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter=new ShowFragmentPresenter(getContext());
        initView();
        initEvent();
    }

    private void initEvent() {
        adapter.setOnItemClickListener(this);
    }

    private void initView() {
        adapter=new WordAdapter(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new SlideInLeftAnimator());
        recyclerView.setAdapter(new AlphaInAnimationAdapter(adapter));
        Bundle bundle=getArguments();
        type=bundle.getString("type");
        switch (type){
            case "normal":{
                belong=bundle.getString("scene");
                mPresenter.getWordList(this,belong);
            }break;

            case "fromscene":{
                belong=bundle.getString("scene");
                mPresenter.getWordList(this,belong);
            }break;

            case "chinese":{
                String chinese=bundle.getString("searchChinese");
                mPresenter.getWordListByChinese(this,chinese);
            }break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Word item=adapter.getDataSource().get(position);
        if(listener!=null){
            getChildFragmentManager().beginTransaction().hide(this).commit();
            BaseFragment fragment=new WordFragment();
            Bundle bundle=new Bundle();
            bundle.putString("word",item.getEnglish());
            fragment.setArguments(bundle);
            listener.addFragment(fragment);
        }
    }

    @Override
    public void onItemDelete(View view, final int position) {
        final Word item=adapter.getDataSource().get(position);
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this word!")
                .setConfirmText("Yes,delete it!")
                .setCancelText("No,cancel plx!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        adapter.removeData(item, position);
                        mPresenter.removeWord(item.getEnglish());
                        mDataList.remove(item);
                        sDialog
                                .setTitleText("Deleted!")
                                .setContentText("Word has been deleted!")
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
    public void onItemLongClick(View view, final int position) {
        final View layout=LayoutInflater.from(getContext()).inflate(R.layout.dialog_part,null,false);
        TextView englishText=(TextView)layout.findViewById(R.id.ed_name);
        englishText.setText("English Name:");
        TextView chineseText=(TextView)layout.findViewById(R.id.ed_chinese);
        chineseText.setText("Chinese:");
        new AlertDialog.Builder(getContext()).setTitle("添加Word")
                .setView(layout)
                .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText=(EditText)layout.findViewById(R.id.et_input_name);
                        EditText editText2=(EditText)layout.findViewById(R.id.et_input_chinese);
                        String name = editText.getText().toString();
                        String chinese=editText2.getText().toString();
                        Word item=adapter.getDataSource().get(position);
                        mPresenter.updataWord(item,name,chinese);
                        Word newItem=item;
                        newItem.setEnglish(name);
                        newItem.setChinese(chinese);
                        adapter.updata(item,newItem);
                        mDataList.set(position,newItem);

                    }
                })
                .setNegativeButton("取消", null).show();
    }


    @Override
    public void setWordList(List<Word> wordList) {
        mDataList=wordList;
        Log.d("ummmmm",String.valueOf(mDataList.size()));
        if(mDataList.size()>0){
            adapter.setDataSource(wordList);
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.word_back)
    void backAndPop(){
        if(type.equals("normal")){
            if (listener!=null){
                listener.backToPop();
            }
        }else{
            Intent intent=new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.word_add)
    void addWord(){
        YoYo.with(Techniques.Tada)
                .duration(700)
                .repeat(0)
                .playOn(view.findViewById(R.id.word_add));
        final View layout=LayoutInflater.from(getContext()).inflate(R.layout.dialog_part,null,false);
        TextView englishText=(TextView)layout.findViewById(R.id.ed_name);
        englishText.setText("English Name:");
        TextView chineseText=(TextView)layout.findViewById(R.id.ed_chinese);
        chineseText.setText("Chinese:");
        new AlertDialog.Builder(getContext()).setTitle("添加Word")
                .setView(layout)
                .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText=(EditText)layout.findViewById(R.id.et_input_name);
                        EditText editText2=(EditText)layout.findViewById(R.id.et_input_chinese);
                        String name = editText.getText().toString();
                        String chinese=editText2.getText().toString();
                        Word item=new Word();
                        item.setBelongScene(belong);
                        item.setEnglish(name);
                        item.setChinese(chinese);
                        if(mPresenter.querryWord(name)){
                            Toasty.error(getContext(), "该Word已存在，请重新输入！", Toast.LENGTH_SHORT, true).show();
                        }else {
                            if(mPresenter.querryScene(belong)){
                                Toasty.success(getContext(), "添加成功！", Toast.LENGTH_SHORT, true).show();
                                mPresenter.addWord(item);
                                mDataList.add(item);
                                adapter.addData(item,mDataList.size());
                            }
                            else {
                                Toasty.error(getContext(), "所属的Scene不存在，请查阅后再添加！", Toast.LENGTH_SHORT, true).show();
                            }
                        }
                    }
                })
                .setNegativeButton("取消", null).show();
    }

}
