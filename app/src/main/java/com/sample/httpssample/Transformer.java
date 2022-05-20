package com.sample.httpssample;

import android.app.Dialog;

import androidx.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class Transformer {
    public Transformer() {
    }

    public static <T> ObservableTransformer<T, T> switchSchedulers() {
        return switchSchedulers((Dialog) null);
    }

    public static <T> ObservableTransformer<T, T> switchSchedulers(final Dialog dialog) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        if (dialog != null) {
                            dialog.show();
                        }

                    }
                }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (dialog != null) {
                            dialog.dismiss();
                        }

                    }
                });
            }
        };
    }
}
