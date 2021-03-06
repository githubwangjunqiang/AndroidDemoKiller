package com.max.tang.demokiller.utils;

import com.max.tang.demokiller.utils.log.Logger;
import com.max.tang.demokiller.view.SortView;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
/**
 * Created by zhihuitang on 2016-11-29.
 */

public class SortAlgo<E> {
    private static final String TAG = "SortAlgo";


    public <E extends Comparable<E>> void bubbleSort( final WeakReference<SortView> view, final List<E> data) {
        /*
        for( int i = 0; i < data.size(); i++ ){
            for (int j = i+1; j < data.size(); j++) {
                if(data.get(i) > data.get(j)) {
                    int tmp = data.get(i);
                    data.set(i, data.get(j));
                    data.set(j, tmp);
                    view.updateUI(data);
                }

            }
        }
        */

        Observable.range(0, data.size())
            .concatMap(new Func1<Integer, Observable<Integer>>() {
                @Override public Observable<Integer> call(Integer i) {
                    return Observable.range(0, data.size()- i );
                }
            })
            .zipWith(Observable.interval(50, TimeUnit.MILLISECONDS), new Func2<Integer, Long, Integer>() {
                @Override public Integer call(Integer i, Long x2) {
                    return i;
                }
            })
            .map(new Func1<Integer, Integer>() {
                @Override public Integer call(Integer j) {
                    if( j+1 < data.size() && data.get(j).compareTo(data.get(j+1)) > 0 ){
                    //if( j+1 < data.size() && data.get(j) > data.get(j+1)) {
                        swap(data, j, j+1 );
                    }
                    return j;
                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<Integer>() {
                @Override public void onCompleted() {
                    Logger.i(TAG, "onCompleted: sort finished");
                    if( view.get() != null )
                        view.get().finish("Bubble Sort");
                }

                @Override public void onError(Throwable e) {
                    Logger.i(TAG, "onCompleted: sort error");
                    if( view.get() != null )
                        view.get().finish("Bubble Sort failed");
                    e.printStackTrace();
                }

                @Override public void onNext(Integer integer) {
                    //Logger.i(TAG, "onCompleted: sort next");
                    if( view.get() != null )
                        view.get().updateUI(data);

                }
            });
    }

    static private <E> void swap(final List<E> data, final int i, final int j) {
        E tmp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, tmp);
    }

    public void cancel() {

    }
}
