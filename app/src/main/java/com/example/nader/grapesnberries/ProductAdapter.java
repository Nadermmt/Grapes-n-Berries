package com.example.nader.grapesnberries;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView price, desc;
        ImageView productImage;
        RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            price = (TextView)itemView.findViewById(R.id.price_view);
            desc = (TextView)itemView.findViewById(R.id.desc_view);
            productImage = (ImageView)itemView.findViewById(R.id.image_view);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relative_layout);

        }
    }

    private List<Product> productsList;

    public ProductAdapter(List<Product> productsList) {
        this.productsList = productsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int pos) {
        if(productsList !=  null) {
            Product product = productsList.get(pos);
            holder.price.setText(String.valueOf(product.getPrice()) + "$");
            holder.desc.setText(product.getProductDescription());
            Picasso.with(holder.productImage.getContext()).load(product.getImage().getUrl()).into(holder.productImage);
            holder.productImage.setMaxHeight(product.getImage().getHeight());
        }
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }
}
