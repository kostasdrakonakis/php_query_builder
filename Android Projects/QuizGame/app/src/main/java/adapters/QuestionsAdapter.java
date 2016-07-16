package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.library.quizgame.R;

import java.util.List;

import lists.CategoryList;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder>{

    List<CategoryList> questions;
    private Context context;

    public QuestionsAdapter(List<CategoryList> questions, Context context){
        this.questions = questions;
        this.context = context;
    }

    @Override
    public QuestionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_categories, parent, false);
        QuestionsViewHolder pvh = new QuestionsViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(QuestionsViewHolder holder, int position) {
        holder.button.setText(questions.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class QuestionsViewHolder extends RecyclerView.ViewHolder {
        Button button;

        QuestionsViewHolder(View itemView) {
            super(itemView);
            button = (Button) itemView.findViewById(R.id.row_cat_button);
        }
    }
}
