package id.co.noz.github.user.example.api;

import java.util.ArrayList;
import java.util.List;

import id.co.noz.github.user.example.model.Itemsp;
import id.co.noz.github.user.example.model.Users;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("search/users")
    Call<Users> getUsers(@Query("q") String keyword);

    @GET("users")
    Call<List<Itemsp>> getperPageList(@Query("per_page") int per_page);
}
