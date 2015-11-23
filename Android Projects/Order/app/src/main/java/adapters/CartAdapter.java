package adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.order.app.order.R;

import java.util.List;

import lists.CartList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{

    List<CartList> cartItems;

    public CartAdapter(List<CartList> cartItems){
        this.cartItems = cartItems;

    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_row_item, parent, false);
        CartViewHolder pvh = new CartViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        holder.cartProductName.setText(cartItems.get(position).getName());
        holder.cartProductprice.setText(cartItems.get(position).getPrice());
        holder.pref1.setText(cartItems.get(position).getPreferation1());
        holder.pref2.setText(cartItems.get(position).getPreferation2());
        holder.pref3.setText(cartItems.get(position).getPreferation3());
        holder.pref4.setText(cartItems.get(position).getPreferation4());
        holder.quantity.setText(cartItems.get(position).getQuantity());
        Ion.with(holder.cartProductImage).placeholder(R.mipmap.placeholder).error(R.mipmap.placeholder).load(cartItems.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        CheckBox checkBox;
        TextView cartProductName, cartProductprice, pref1, pref2, pref3, pref4, quantity;
        ImageView cartProductImage;
        LinearLayout linearLayout;

        CartViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.card_view);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.rootLinear);
            checkBox = (CheckBox)itemView.findViewById(R.id.deleteCheckCartItem);
            cartProductName = (TextView)itemView.findViewById(R.id.product_name_cart);
            cartProductprice = (TextView)itemView.findViewById(R.id.product_price_cart);
            pref1 = (TextView)itemView.findViewById(R.id.pref1_text_cart);
            pref2 = (TextView)itemView.findViewById(R.id.pref2_text_cart);
            pref3 = (TextView)itemView.findViewById(R.id.pref3_text_cart);
            pref4 = (TextView)itemView.findViewById(R.id.pref4_text_cart);
            quantity = (TextView)itemView.findViewById(R.id.quantity_text_cart);
            cartProductImage = (ImageView)itemView.findViewById(R.id.product_image_cart);


        }
    }

}
