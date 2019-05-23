package com.fabricio.challenge.view.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fabricio.challenge.R;
import com.fabricio.challenge.control.DownloadImageTask;
import com.fabricio.challenge.model.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ViewHolder> {

    private static final String TAG = ProductRecyclerAdapter.class.getSimpleName();

    private Context context;

    private List<Product> products = new ArrayList<>();

    public ProductRecyclerAdapter(Context context){
        this.context = context;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public GridLayout layout;
        public ImageView image;
        public TextView description;
        public TextView price;
        public TextView offer;
        public ImageView indicator;

        public ViewHolder(GridLayout layout) {
            super(layout);
            this.layout = layout;
            this.image = ((ImageView)layout.findViewById(R.id.product_image));
            this.description = ((TextView)layout.findViewById(R.id.product_description));
            this.price = ((TextView)layout.findViewById(R.id.product_original_price));
            this.offer = ((TextView)layout.findViewById(R.id.product_discount_price));
            this.indicator = ((ImageView)layout.findViewById(R.id.indicator));

            price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                indicator.setImageTintList(null);
//            }
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        GridLayout layout = (GridLayout) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_item, viewGroup, false);
        Product product = products.get(i);
//        ((TextView)layout.findViewById(R.id.product_description)).setText(product.getDescription());
        ViewHolder vh = new ViewHolder(layout);
        return vh;

//        View view = views.get(i);
//        viewGroup.addView(view);
//        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        try {
            ;
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            viewHolder.description.setText(
                context.getResources().getString(
                        R.string.product_name_and_description,
                        products.get(i).getName(),
                        products.get(i).getDescription()
                )
            );
            new DownloadImageTask(viewHolder.image).execute(products.get(i).getUrlImage());
            viewHolder.price.setText(context.getResources().getString(
                    R.string.price,
                    context.getResources().getString(R.string.oritinal_price),
                    products.get(i).getPriceOriginal()
            ));
            viewHolder.offer.setText( context.getResources().getString(
                    R.string.price,
                    context.getResources().getString(R.string.discount_price),
                    products.get(i).getPriceDiscount()
            ));
        } catch (Exception e) {
            Log.e(TAG, "Could not update product info", e);
        }
//        holder.textView.setText(mDataset[position]);
    }


    @Override
    public int getItemCount() {
        return products.size();
    }

//    public void addProduct(View view){
//        views.add(view);
//        notifyDataSetChanged();
//    }

    public void addProduct(Product product){
        products.add(product);
        notifyItemInserted(products.size()-1);
//        notifyDataSetChanged();
    }
}
