package id.co.noz.github.user.example.ui.loadmore;

import java.util.List;

import id.co.noz.github.user.example.model.Items;
import id.co.noz.github.user.example.model.Itemsp;

public interface LoadContract {

    interface InitView {
        void showLoading();
        void hideLoading();
        void userperPageList(List<Itemsp> users);
        void userNextperPageList(List<Itemsp> users);
        void userperPageListFailure(String errorMessage, String keyword);
    }

    interface Presenter { void getLoadUser(int per_page); }
}

