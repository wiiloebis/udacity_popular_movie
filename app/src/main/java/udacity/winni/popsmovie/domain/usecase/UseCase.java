package udacity.winni.popsmovie.domain.usecase;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by winniseptiani on 6/26/17.
 */

public abstract class UseCase<T> {

    protected abstract Observable<T> buildUseCaseObservable();

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    /**
     * Executes the current use case.
     *
     * @param useCaseSubscriber The guy who will be listen to the observable build
     *                          with {@link #buildUseCaseObservable()}.
     */
    public void execute(DisposableObserver useCaseSubscriber) {
        compositeDisposable.add(buildUseCaseObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(useCaseSubscriber));
    }

    /**
     * Unsubscribes from current {@link rx.Subscription}.
     */
    public void unsubscribe() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }

    }
}

