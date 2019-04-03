package id.co.noz.github.user.example.ui.loadmore;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    LinearLayoutManager layoutManager;

    public PaginationScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        System.out.println("ISL LOADING " + isLoading() );
        System.out.println("ISL LAST PAGE " + isLastPage());

        if (!isLoading() && !isLastPage()) {
            System.out.println("ILS JALAN");
            System.out.println("ILS JALAN V " + visibleItemCount);
            System.out.println("ILS JALAN F " + firstVisibleItemPosition);
            System.out.println("ILS JALAN TI " + totalItemCount);
            System.out.println("ILS JALAN GT " + getTotalPageCount());

//            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
//                    && firstVisibleItemPosition >= 0
//                    && totalItemCount >= getTotalPageCount()) {
            if (totalItemCount <= 70){
                System.out.println("ILS LOAD MORE A ");
                loadMoreItems();
            }
        }

    }

    protected abstract void loadMoreItems();

    public abstract int getTotalPageCount();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();

}
