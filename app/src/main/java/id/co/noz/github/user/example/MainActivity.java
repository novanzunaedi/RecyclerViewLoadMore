package id.co.noz.github.user.example;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import id.co.noz.github.user.example.ui.loadmore.LoadMoreActivity;
import id.co.noz.github.user.example.ui.main.MainContract;
import id.co.noz.github.user.example.ui.main.MainPresenter;
import id.co.noz.github.user.example.ui.search.SearchActivity;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);

        findViewById(R.id.rlSearchUsername).setOnClickListener(v -> presenter.handleToSearch());
        findViewById(R.id.rlLoadMore).setOnClickListener(v -> presenter.handleToLoadMore());
    }

    @Override
    public void navigateToSearch() {
        startActivity(new Intent(this, SearchActivity.class));
    }

    @Override
    public void navigateToLoadMore() {
        startActivity(new Intent(this, LoadMoreActivity.class));
    }
}

