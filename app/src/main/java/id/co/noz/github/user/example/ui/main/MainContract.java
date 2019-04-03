package id.co.noz.github.user.example.ui.main;

public interface MainContract {
    interface View {
        void navigateToSearch();
        void navigateToLoadMore();
    }

    interface Presenter{
        void handleToSearch();
        void handleToLoadMore();
    }
}
