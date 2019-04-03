package id.co.noz.github.user.example.ui.main;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    public void handleToSearch(){
        view.navigateToSearch();
    }

    public void handleToLoadMore(){
        view.navigateToLoadMore();
    }
}
