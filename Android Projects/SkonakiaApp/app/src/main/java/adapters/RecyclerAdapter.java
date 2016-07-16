package adapters;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import matrix.skonakigr.kostas.skonakiaapp.R;

import java.util.List;

import lists.CustomList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.TropoiViewHolder>{

    List<CustomList> tropoi;

    public RecyclerAdapter(List<CustomList> tropoi){
        this.tropoi = tropoi;
    }

    @Override
    public TropoiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent ,false);
        TropoiViewHolder holder = new TropoiViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TropoiViewHolder holder, int position) {
        holder.name.setText(tropoi.get(position).getItemName());
        holder.image.setImageResource(tropoi.get(position).getImageId());
    }

    @Override
    public int getItemCount() {
        return tropoi.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class TropoiViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        ImageView image;
        TextView name;

        public TropoiViewHolder(View itemView){
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cardView);
            image = (ImageView)itemView.findViewById(R.id.imageTropoi);
            name = (TextView)itemView.findViewById(R.id.textTropoi);
        }
    }
}
