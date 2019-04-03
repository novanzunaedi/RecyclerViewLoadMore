package id.co.noz.github.user.example.ui.search;

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

import java.util.List;

import id.co.noz.github.user.example.R;
import id.co.noz.github.user.example.model.Items;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Items> items;
    private Context context;

    public Adapter(List<Items> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(items.get(position).getLogin());
        Glide.with(context)
                .load(items.get(position).getAvatarUrl())
                .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_github)
                )
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView avatar;
        MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            avatar = itemView.findViewById(R.id.avatar);
        }
    }
}
