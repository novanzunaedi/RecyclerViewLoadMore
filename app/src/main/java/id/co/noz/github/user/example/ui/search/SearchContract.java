package id.co.noz.github.user.example.ui.search;

import java.util.List;

import id.co.noz.github.user.example.model.Items;

public interface SearchContract {

    interface InitView {
        void showLoading();
        void hideLoading();
        void userList(List<Items> users);
        void userListFailure(String errorMessage, String keyword);
    }

    interface Presenter { void getUserList(String keyword); }
}
