package base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class BaseObserver<T> extends DisposableObserver<T> {
    public BaseObserver(CompositeDisposable compositeDisposable) {
        compositeDisposable.add(this);
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T t) {

    }
}
