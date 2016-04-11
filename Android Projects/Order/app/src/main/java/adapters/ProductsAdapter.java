package adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.order.app.order.R;

import java.util.Arrays;
import java.util.List;

import lists.ProductList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>{

    List<ProductList> products;
    private int lastPosition = -1;
    private Context context;

    public ProductsAdapter(List<ProductList> products, Context context){
        this.products = products;
        this.context = context;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_item, parent, false);
        ProductViewHolder pvh = new ProductViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        initView(position, holder.productName, products.get(position).getName());
        holder.productPrice.setText(products.get(position).getPrice() + " €");
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
        LinearLayout linearLayout;

        ProductViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            productName = (TextView)itemView.findViewById(R.id.textView_name_product);
            productPrice = (TextView)itemView.findViewById(R.id.textView_price_product);
            productImage = (ImageView)itemView.findViewById(R.id.imageView_product);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.product_container);
        }
    }

    private void initView(int position, TextView holderTextView, String text) {
        Paint paint = new Paint();
        float width = paint.measureText(text);
        int maxLength = 5; // put whatever length you need here
        if (width > maxLength) {
            List<String> arrayList = null;
            String[] array = (text.split("\\s"));
            arrayList = Arrays.asList(array);
            int seventyPercent = (int) (Math.round(arrayList.size() * 0.10)); // play with this if needed
            String linebreak = arrayList.get(seventyPercent) + "\n";
            arrayList.set(seventyPercent, linebreak);
            text = TextUtils.join(" ", arrayList);
            text.replace(",", " ");
        }
        holderTextView.setText(text);
    }


    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition){
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_up);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

}
