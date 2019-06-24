package view;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Toast;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.wxh.wxhsceneword.MainActivity;
import com.wxh.wxhsceneword.R;
import java.util.List;

import Adapter.PartAdapter;
import base.BaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import contract.ReadWriteContract;
import contract.ShowFragmentContract;
import entry.Part;
import entry.XmlList;
import es.dmoral.toasty.Toasty;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import presenter.ReadWritePresenter;
import presenter.ShowFragmentPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PartFragment extends BaseFragment implements ShowFragmentContract.PartView, PartAdapter.OnItemClickListener {

    @BindView(R.id.part_back)
    ImageView imageView;
    @BindView(R.id.part_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.part_add)
    ImageView addPartView;
    private ShowFragmentPresenter mPresenter;
    private List<Part> mDataList;
    private PartAdapter adapter;
    private Unbinder unbinder;
    private View view;
    public PartFragment() {
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
        view=inflater.inflate(R.layout.fragment_part, container, false);
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
        adapter=new PartAdapter(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new SlideInLeftAnimator());
        recyclerView.setAdapter(new AlphaInAnimationAdapter(adapter));
        mPresenter.getPartList(this);
    }

    @Override
    public void setPartList(List<Part> partList) {
        mDataList=partList;
        adapter.setDataSource(partList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        Part part=adapter.getDataSource().get(position);
        if (listener != null) {
            getChildFragmentManager().beginTransaction().hide(this).commit();
            BaseFragment fragment = new ChapterFragment();
            Bundle bundle = new Bundle();
            bundle.putString("part",part.getPartID());
            fragment.setArguments(bundle);
            listener.addFragment(fragment);
        }
    }

    @Override
    public void onItemDelete(View view, final int position) {
        final Part part=adapter.getDataSource().get(position);
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this part!")
                .setConfirmText("Yes,delete it!")
                .setCancelText("No,cancel plx!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        adapter.removeData(part, position);
                        mPresenter.removePart(part.getPartID());
                        mDataList.remove(part);
                        sDialog
                                .setTitleText("Deleted!")
                                .setContentText("part has been deleted!")
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
        LinearLayout chineseLayout=(LinearLayout) layout.findViewById(R.id.layout_chinese);
        chineseLayout.setVisibility(View.GONE);
        new AlertDialog.Builder(getContext()).setTitle("修改part")
                .setView(layout)
                .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText=(EditText)layout.findViewById(R.id.et_input_name);
                        String name = editText.getText().toString();
                        Part part=adapter.getDataSource().get(position);
                        mPresenter.updataPart(part,name);
                        Part newPart=part;
                        newPart.setPartID(name);
                        adapter.updata(part,newPart);
                        mDataList.set(position,newPart);
                    }
                })
                .setNegativeButton("取消", null).show();
    }


    @OnClick(R.id.part_back)
    void backAndPop(){
        Intent intent=new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.part_add)
    void addPart(){
        YoYo.with(Techniques.Tada)
                .duration(700)
                .repeat(0)
                .playOn(view.findViewById(R.id.part_add));
        final View layout=LayoutInflater.from(getContext()).inflate(R.layout.dialog_part,null,false);
        LinearLayout chineseLayout=(LinearLayout) layout.findViewById(R.id.layout_chinese);
        chineseLayout.setVisibility(View.GONE);
        new AlertDialog.Builder(getContext()).setTitle("添加part")
                .setView(layout)
                .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText=(EditText)layout.findViewById(R.id.et_input_name);
                        String name = editText.getText().toString();
                        Part part=new Part();
                        part.setPartID(name);
                        if(mPresenter.querryPary(name)){
                            Toasty.error(getContext(), "该Part已存在，请重新输入！", Toast.LENGTH_SHORT, true).show();
                        }else {
                            Toasty.success(getContext(), "添加成功！", Toast.LENGTH_SHORT, true).show();
                            mPresenter.addPart(part);
                            mDataList.add(part);
                            adapter.addData(part,mDataList.size());
                        }
                    }
                })
                .setNegativeButton("取消", null).show();
    }

}
