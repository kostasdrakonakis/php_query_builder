package adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.order.app.order.R;

import java.util.List;

import lists.ProductList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>{

    List<ProductList> products;

    public ProductsAdapter(List<ProductList> products){
        this.products = products;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.productlist_row_item, parent, false);
        ProductViewHolder pvh = new ProductViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.productName.setText(products.get(position).getName());
        holder.productPrice.setText(products.get(position).getPrice());
        Ion.with(holder.productImage).placeholder(R.mipmap.placeholder).error(R.mipmap.placeholder).load(products.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView productName;
        TextView productPrice;
        ImageView productImage;

        ProductViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            productName = (TextView)itemView.findViewById(R.id.product_name);
            productPrice = (TextView)itemView.findViewById(R.id.product_price);
            productImage = (ImageView)itemView.findViewById(R.id.product_image);
        }
    }

}
