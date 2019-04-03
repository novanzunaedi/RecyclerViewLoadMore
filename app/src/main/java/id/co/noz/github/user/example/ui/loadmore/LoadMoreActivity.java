package id.co.noz.github.user.example.ui.loadmore;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

import id.co.noz.github.user.example.R;
import id.co.noz.github.user.example.model.Items;
import id.co.noz.github.user.example.model.Itemsp;
import retrofit2.Response;

public class LoadMoreActivity extends AppCompatActivity implements LoadContract.InitView {

    private LoadPresenter presenter;
    private ProgressBar pb;
    private RecyclerView recyclerView;
    private RelativeLayout emptyView;
    private TextView errorTitle, errorMessage;
    private LoadAdapter loadAdapter;
    private List<Itemsp> itemsp;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES = 50;
//    private int currentPage = PAGE_START;
    private int iPerPage = 10;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadmore);

        presenter = new LoadPresenter(this);
        initView();

        presenter.getLoadUser(iPerPage);
    }

    private void initView(){
        pb = findViewById(R.id.pbLoading);
        recyclerView = findViewById(R.id.rv);
        emptyView = findViewById(R.id.empty_view);
        errorTitle = findViewById(R.id.errorTitle);
        errorMessage = findViewById(R.id.errorMessage);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        loadAdapter = new LoadAdapter(this);

        recyclerView.setAdapter(loadAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new PaginationScrollListener(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;

                iPerPage = iPerPage + 10;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage(iPerPage);
                    }
                }, 2500);
            }

            @Override
            public int getTotalPageCount() {
                return iPerPage;
            }

            @Override
            public boolean isLastPage() {
//                Toast.makeText(LoadMoreActivity.this, "IS LAST PAGE ", Toast.LENGTH_SHORT).show();
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
//                Toast.makeText(LoadMoreActivity.this, "IS LOADING ", Toast.LENGTH_SHORT).show();
                return isLoading;
            }
        });

    }
    @Override
    public void showLoading() {
        pb.setVisibility(View.VISIBLE);
        errorView(View.INVISIBLE, "", "");
    }

    @Override
    public void hideLoading() {
        pb.setVisibility(View.INVISIBLE);
    }


    @Override
    public void userperPageList(List<Itemsp> itemsps) {
        if (itemsp != null){
            itemsp.clear();
        }
        itemsp = itemsps;

        List<Itemsp> liTemsps = fetchResults(itemsps);
        loadAdapter.addAll(liTemsps);

        if (iPerPage <= TOTAL_PAGES) loadAdapter.addLoadingFooter();
        else isLastPage = true;
    }

    @Override
    public void userNextperPageList(List<Itemsp> itemsps) {
        loadAdapter.removeLoadingFooter();
        isLoading = false;

        List<Itemsp> listItemsp = fetchResults(itemsps);

//        int adaSize = loadAdapter.getItemCount();
//        List<Itemsp> listItemsp = itemsps;
//        int removed = 0; // Setup the variable for removal counting
//
//        while (removed < Math.min(listItemsp.size(), iPerPage)) { // While we still haven't removed 5 entries OR second list size
//            listItemsp.remove(listItemsp.size() - 1); // Remove the last entry of the list
//            removed++; // Increases 'removed' count
//        }
//
//        System.out.println("LIST SIZE NOW " + listItemsp.size());

        loadAdapter.addAll(listItemsp);

        if (iPerPage != TOTAL_PAGES) loadAdapter.addLoadingFooter();
        else isLastPage = true;
    }

//    private List<Itemsp> removeList(List<Itemsp> listItemsp){
//        Iterator<Itemsp> it = listItemsp.iterator();
//        while(it.hasNext()) {
//            if() {
//                it.remove();
//            }
//        }
//    }

    @Override
    public void userperPageListFailure(String errorMessage, String keyword) {
        errorView(View.VISIBLE,  errorMessage, keyword);
    }

    private void errorView(int visibility, String title, String message){
        emptyView.setVisibility(visibility);
        errorTitle.setText(title);
        errorMessage.setText(message);
    }

    private void loadNextPage(int nextPerpage){
        presenter.getLoadNextPage(nextPerpage);
    }

    private List<Itemsp> fetchResults(List<Itemsp> itemsps) {
        List<Itemsp> itemsp = itemsps;
        return itemsp;
    }
}
