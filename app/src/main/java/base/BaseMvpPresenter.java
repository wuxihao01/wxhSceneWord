package base;

import io.reactivex.disposables.CompositeDisposable;

public class BaseMvpPresenter {
    public CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public void unSubscription(){
        mCompositeDisposable.clear();
    }

    public void cance() {
        unSubscription();
    }
}
