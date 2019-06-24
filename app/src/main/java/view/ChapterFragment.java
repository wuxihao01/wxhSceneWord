package view;


import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.wxh.wxhsceneword.R;

import java.util.List;

import Adapter.ChapterAdapter;
import base.BaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import contract.ShowFragmentContract;
import entry.Chapter;
import es.dmoral.toasty.Toasty;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import presenter.ShowFragmentPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChapterFragment extends BaseFragment implements ShowFragmentContract.ChapterView,  ChapterAdapter.OnItemClickListener {

    @BindView(R.id.chapter_back)
    ImageView imageView;
    @BindView(R.id.chapter_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.chapter_add)
    ImageView addChapterView;
    private ShowFragmentPresenter mPresenter;
    private List<Chapter> mDataList;
    private ChapterAdapter adapter;
    private String belong;
    private View view;
    private Unbinder unbinder;
    public ChapterFragment() {
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
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_chapter, container, false);
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
        adapter=new ChapterAdapter(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        Bundle bundle=getArguments();
        belong=bundle.getString("part");
        recyclerView.setItemAnimator(new SlideInLeftAnimator());
        recyclerView.setAdapter(new AlphaInAnimationAdapter(adapter));
        mPresenter.getChapterList(this,belong);
    }

    @Override
    public void setChapterList(List<Chapter> chapterList) {
        mDataList=chapterList;
        adapter.setDataSource(chapterList);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(View view, int position) {
        Chapter item=adapter.getDataSource().get(position);
        if(listener!=null){
            getChildFragmentManager().beginTransaction().hide(this).commit();
            BaseFragment fragment=new SceneFragment();
            Bundle bundle=new Bundle();
            bundle.putString("chapter",item.getChapterName());
            fragment.setArguments(bundle);
            listener.addFragment(fragment);
        }
    }

    @Override
    public void onItemDelete(View view, final int position) {
        final Chapter item=adapter.getDataSource().get(position);
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this chapter!")
                .setConfirmText("Yes,delete it!")
                .setCancelText("No,cancel plx!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        adapter.removeData(item, position);
                        mPresenter.removeChapter(item.getChapterName());
                        mDataList.remove(item);
                        sDialog
                                .setTitleText("Deleted!")
                                .setContentText("Chapter has been deleted!")
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
        TextView textView1=(TextView)layout.findViewById(R.id.ed_name);
        textView1.setText("Chapter Name");
        LinearLayout chineseLayout=(LinearLayout)layout.findViewById(R.id.layout_chinese);
        chineseLayout.setVisibility(View.GONE);
        new AlertDialog.Builder(getContext()).setTitle("修改Chapter")
                .setView(layout)
                .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText=(EditText)layout.findViewById(R.id.et_input_name);
                        String name = editText.getText().toString();
                        Chapter item=adapter.getDataSource().get(position);
                        mPresenter.updataChapter(item,name);
                        Chapter newItem=item;
                        newItem.setChapterName(name);
                        adapter.updata(item,newItem);
                        mDataList.set(position,newItem);
                    }
                })
                .setNegativeButton("取消", null).show();
    }


    @OnClick(R.id.chapter_back)
    void backAndPop(){
        if (listener!=null){
            listener.backToPop();
        }
    }

    @OnClick(R.id.chapter_add)
    void addChapter(){
        YoYo.with(Techniques.Tada)
                .duration(700)
                .repeat(0)
                .playOn(view.findViewById(R.id.chapter_add));
        final View layout=LayoutInflater.from(getContext()).inflate(R.layout.dialog_part,null,false);
        TextView textView1=(TextView)layout.findViewById(R.id.ed_name);
        textView1.setText("Chapter Name");
        LinearLayout chineseLayout=(LinearLayout)layout.findViewById(R.id.layout_chinese);
        chineseLayout.setVisibility(View.GONE);
        new AlertDialog.Builder(getContext()).setTitle("添加Chapter")
                .setView(layout)
                .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText=(EditText)layout.findViewById(R.id.et_input_name);
                        String name = editText.getText().toString();
                        Chapter chapter=new Chapter();
                        chapter.setBelongPart(belong);
                        chapter.setChapterName(name);
                        if(mPresenter.querryChapter(name)){
                            Toasty.error(getContext(), "该Chapter已存在，请重新输入！", Toast.LENGTH_SHORT, true).show();
                        }else {
                            if(mPresenter.querryPary(belong)){
                                Toasty.success(getContext(), "添加成功！", Toast.LENGTH_SHORT, true).show();
                                mPresenter.addChapter(chapter);
                                mDataList.add(chapter);
                                adapter.addData(chapter,mDataList.size());
                            }
                            else {
                                Toasty.error(getContext(), "所属的Part不存在，请查阅后再添加！", Toast.LENGTH_SHORT, true).show();
                            }
                        }
                    }
                })
                .setNegativeButton("取消", null).show();
    }
}

