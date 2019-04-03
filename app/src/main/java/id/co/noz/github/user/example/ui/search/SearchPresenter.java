package id.co.noz.github.user.example.ui.search;

import android.support.annotation.NonNull;

import id.co.noz.github.user.example.api.ApiClient;
import id.co.noz.github.user.example.api.ApiInterface;
import id.co.noz.github.user.example.model.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.InitView initView;
    public SearchPresenter(SearchContract.InitView initView){
        this.initView = initView;
    }

    @Override
    public void getUserList(final String keyword) {
        initView.showLoading();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Users> call = apiInterface.getUsers(keyword);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(@NonNull Call<Users> call, @NonNull Response<Users> response) {

                initView.hideLoading();
                initView.userList(response.body().getItems());
                int totalCount = response.body().getTotalCount();

                if (!response.isSuccessful() || response.body().getItems() == null || totalCount == 0) {
                    initView.userListFailure("No Result for '" + keyword + "'", "Try Searching for Other Users");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Users> call, @NonNull Throwable t) {
                initView.userListFailure("Error Loading For '" + keyword+ "'", t.toString());
                initView.hideLoading();
                t.printStackTrace();
            }
        });
    }
}
