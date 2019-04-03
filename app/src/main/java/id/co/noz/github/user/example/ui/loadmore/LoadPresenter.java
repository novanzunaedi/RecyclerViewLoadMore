package id.co.noz.github.user.example.ui.loadmore;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import id.co.noz.github.user.example.api.ApiClient;
import id.co.noz.github.user.example.api.ApiInterface;
import id.co.noz.github.user.example.model.Itemsp;
import id.co.noz.github.user.example.model.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadPresenter implements LoadContract.Presenter {

    private LoadContract.InitView initView;

    public LoadPresenter(LoadContract.InitView initView){
        this.initView = initView;
    }

    @Override
    public void getLoadUser(int perPage) {
        initView.showLoading();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Itemsp>> call = apiInterface.getperPageList(perPage);
        call.enqueue(new Callback<List<Itemsp>>() {
            @Override
            public void onResponse(@NonNull Call<List<Itemsp>> call, @NonNull Response<List<Itemsp>> response) {

                initView.hideLoading();
                initView.userperPageList(response.body());
                int totalCount = response.body().size();

                if (!response.isSuccessful() || response.body() == null || totalCount == 0) {
                    initView.userperPageListFailure("No Result for '" + perPage + "'", "Try Searching for Other Users");
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Itemsp>> call, @NonNull Throwable t) {
                initView.userperPageListFailure("Error Loading For '" + perPage+ "'", t.toString());
                initView.hideLoading();
                t.printStackTrace();
            }
        });
    }

    public void getLoadNextPage(int perPage){
        System.out.println("PERPAGENYA " + perPage);
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Itemsp>> call = apiInterface.getperPageList(perPage);
        call.enqueue(new Callback<List<Itemsp>>() {
            @Override
            public void onResponse(@NonNull Call<List<Itemsp>> call, @NonNull Response<List<Itemsp>> response) {

                initView.hideLoading();
                initView.userNextperPageList(response.body());
                int totalCount = response.body().size();

                if (!response.isSuccessful() || response.body() == null || totalCount == 0) {
                    initView.userperPageListFailure("No Result for '" + perPage + "'", "Try Searching for Other Users");
                }

                System.out.println("REQUEST SUCCESS " + response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Itemsp>> call, @NonNull Throwable t) {
                initView.userperPageListFailure("Error Loading For '" + perPage+ "'", t.toString());
                initView.hideLoading();
                t.printStackTrace();
                System.out.println("REQUEST FAILED " + t.toString());
            }
        });
    }
}
