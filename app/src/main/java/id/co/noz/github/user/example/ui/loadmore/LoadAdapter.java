package id.co.noz.github.user.example.ui.loadmore;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import id.co.noz.github.user.example.R;
import id.co.noz.github.user.example.model.Itemsp;

public class LoadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private List<Itemsp> itemsps;
    private Context context;
    private boolean isLoadingAdded = false;


    public LoadAdapter(List<Itemsp> itemsps, Context context) {
        this.itemsps = itemsps;
        this.context = context;
    }

    public LoadAdapter(Context context) {
        this.context = context;
        this.itemsps = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View view2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(view2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View view1 = inflater.inflate(R.layout.item, parent, false);
        viewHolder = new MyViewHolder(view1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Itemsp itemsp = itemsps.get(position); // Movie

        switch (getItemViewType(position)) {
            case ITEM:
                final MyViewHolder holder = (MyViewHolder) viewHolder;

                holder.name.setText(itemsp.getLogin());
                Glide.with(context)
                        .load(itemsp.getAvatarUrl())
                        .apply(
                                RequestOptions.placeholderOf(R.drawable.ic_github)
                        )
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(holder.avatar);
                break;
            case LOADING:

                break;

        }
    }


    @Override
    public int getItemCount() {
        return itemsps == null ? 0 : itemsps.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == itemsps.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


     /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(Itemsp r) {
        itemsps.add(r);
        notifyItemInserted(itemsps.size() - 1);
    }

    public void addAll(List<Itemsp> itemResults) {
        for (Itemsp result : itemResults) {
            add(result);
        }
    }

    public void remove(Itemsp r) {
        int position = itemsps.indexOf(r);
        if (position > -1) {
            itemsps.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Itemsp());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = itemsps.size() - 1;
        Itemsp result = getItem(position);

        if (result != null) {
            itemsps.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Itemsp getItem(int position) {
        return itemsps.get(position);
    }


    /*
    View Holders
    _________________________________________________________________________________________________
     */

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView avatar;
        MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            avatar = itemView.findViewById(R.id.avatar);
        }
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

}
