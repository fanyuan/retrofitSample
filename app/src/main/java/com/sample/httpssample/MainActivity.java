package com.sample.httpssample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sample.httpssample.databinding.ActivityMainBinding;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
    }

    public void request(View view) {
        ProgressDialog dialog = new ProgressDialog(this);
        //dialog.show();
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        //创建网络请求接口对象实例
        Api api = retrofit.create(Api.class);
        //对发送请求进行封装
        Call<Data<Info>> dataCall = api.getJsonData("新歌榜", "json");
        dataCall.enqueue(new Callback<Data<Info>>() {
            @Override
            public void onResponse(Call<Data<Info>> call, Response<Data<Info>> response) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "get回调成功:异步执行", Toast.LENGTH_SHORT).show();
                Data<Info> body = response.body();
                if (body == null){ return;}
                Info info = body.getData();
                if (info == null) {return;}
                binding.text.append("\n\n");
                binding.text.append("返回的数据：" + "\n\n" + info.getName() + "\n" + info.getPicurl());
            }

            @Override
            public void onFailure(Call<Data<Info>> call, Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "回调失败：" + t.getMessage() + "," + t.toString());
                Toast.makeText(MainActivity.this, "回调失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void requestRxJava(View view) {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.show();
        Retrofit retrofit = RetrofitUtil.getRetrofitObservable();
        //创建网络请求接口对象实例
        Api api = retrofit.create(Api.class);
        api.getJsonDataObservable("新歌榜", "json")
        .compose(Transformer.switchSchedulers(dialog))
        .subscribe(new Observer<Data<Info>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                LogHelper.d("---request2  onSubscribe---");
            }

            @Override
            public void onNext(@NonNull Data<Info> infoData) {
                Info info = infoData.getData();
                String msg = "返回的数据：" + "\n\n" + info.getName() + "\n" + info.getPicurl();
                binding.text.append("\n\n");
                binding.text.append(msg);
                LogHelper.d("---request2  onNext---" + msg);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                LogHelper.d("---request2  onError---" + e.getMessage());
            }

            @Override
            public void onComplete() {
                LogHelper.d("---request2  onComplete---");
            }
        });
    }
}