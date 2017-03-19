package com.example.nader.grapesnberries;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.io.ByteArrayOutputStream;
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
    private Activity context;

    public ProductAdapter(Activity context, List<Product> productsList) {
        this.context = context;
        this.productsList = productsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int pos) {
        if(productsList !=  null) {
            Product product = productsList.get(pos);
            holder.price.setText(String.valueOf(product.getPrice()) + "$");
            holder.desc.setText(product.getProductDescription());
            Picasso.with(holder.productImage.getContext()).load(product.getImage().getUrl()).into(holder.productImage);
            holder.productImage.setMaxHeight(product.getImage().getHeight());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.FragmentManager fm = context.getFragmentManager();
                PopUpWindow popUpFragment = new PopUpWindow();
                Bundle bundle = new Bundle();

                bundle.putCharSequence("description", holder.desc.getText());

                Bitmap bitmap = ((BitmapDrawable)holder.productImage.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bitmapData = stream.toByteArray();
                bundle.putByteArray("image",bitmapData);

                popUpFragment.setArguments(bundle);
                popUpFragment.show(fm,"Fragment");
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }
}
