package id.co.noz.github.user.example.ui.search;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import id.co.noz.github.user.example.R;
import id.co.noz.github.user.example.model.Items;

public class SearchActivity extends AppCompatActivity implements SearchContract.InitView {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SearchPresenter searchPresenter;
    private RelativeLayout emptyView;
    private TextView errorTitle, errorMessage;
    private Adapter adapter;
    private List<Items> users;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
        searchPresenter = new SearchPresenter(this);
    }

    private void init() {

        emptyView = findViewById(R.id.empty_view);
        errorTitle = findViewById(R.id.errorTitle);
        errorMessage = findViewById(R.id.errorMessage);
        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu );

        final MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("Search Github Users");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                if (users != null){
                    users.clear();
                }
                errorView(View.VISIBLE, "Tittle", "Search Github Users");
                return true;
            }
        });

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchPresenter.getUserList(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) { return false; }
        });


        return true;
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        errorView(View.INVISIBLE, "", "");
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void userList(List<Items> items) {
        if (users != null){
            users.clear();
        }
        users = items;
        adapter = new Adapter(users, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void userListFailure(String errorMessage, String keyword) {
        errorView(View.VISIBLE,  errorMessage, keyword);
    }

    private void errorView(int visibility, String title, String message){
        emptyView.setVisibility(visibility);
        errorTitle.setText(title);
        errorMessage.setText(message);
    }
}

