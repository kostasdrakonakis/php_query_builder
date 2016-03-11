package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.library.quizgame.R;

import java.util.List;

import lists.SingleCategories;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>{

    List<SingleCategories> categories;
    private Context context;

    public CategoriesAdapter(List<SingleCategories> categories, Context context){
        this.categories = categories;
        this.context = context;
    }

    @Override
    public CategoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_categories, parent, false);
        CategoriesViewHolder pvh = new CategoriesViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(CategoriesViewHolder holder, int position) {
        holder.button.setText(categories.get(position).getCategoryName());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class CategoriesViewHolder extends RecyclerView.ViewHolder {
        Button button;

        CategoriesViewHolder(View itemView) {
            super(itemView);
            button = (Button) itemView.findViewById(R.id.row_cat_button);
        }
    }
}
