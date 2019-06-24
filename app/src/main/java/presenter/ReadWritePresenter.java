package presenter;

import android.content.Context;

import base.BaseMvpPresenter;
import base.BaseObserver;
import contract.ReadWriteContract;
import entry.XmlList;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import module.DataBaseDao;
import util.XmlPullParserUtil;

public class ReadWritePresenter extends BaseMvpPresenter implements ReadWriteContract.Presenter {
    private ReadWriteContract.View mView;
    private Context mContext;
    DataBaseDao dao;
    public ReadWritePresenter(Context context,ReadWriteContract.View view){
        mContext=context;
        mView=view;
        dao=DataBaseDao.getInstance(context);
    }

    @Override
    public void partWriteToDB(XmlList xmlList) {
        dao.addAll(xmlList);
    }

    @Override
    public void readFromXML() {
        BaseObserver observer=new BaseObserver<XmlList>(mCompositeDisposable){
            @Override
            public void onNext(XmlList xmlList) {
                if(isDisposed()) return;
                if(mView != null) {
                    mView.getAllDB(xmlList);
                }
            }
        };
        Observable.create(new ObservableOnSubscribe<XmlList>() {
            @Override
            public void subscribe(ObservableEmitter<XmlList> emitter) throws Exception {
                XmlList xmlList=  XmlPullParserUtil.getAllWord(mContext.getResources().getAssets()
                        .open("wordDB.xml"));
                emitter.onNext(xmlList);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    @Override
    public void writeToXml() {
        BaseObserver observer=new BaseObserver<Boolean>(mCompositeDisposable){
            @Override
            public void onNext(Boolean aBoolean) {
                if(isDisposed()) return;
                if(mView != null) {
                    mView.showResult(aBoolean);
                }
            }
        };

        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                Boolean isSuccess=XmlPullParserUtil.writeXmlFromDB(mContext);
                e.onNext(isSuccess);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }


}
