package com.fabricio.challenge.view.adapters;

import android.app.Activity;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fabricio.challenge.R;
import com.fabricio.challenge.control.DownloadImageTask;
import com.fabricio.challenge.control.eventbus.MessageCode;
import com.fabricio.challenge.control.eventbus.MessageEvent;
import com.fabricio.challenge.model.Product;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ViewHolder> {

    private static final String TAG = ProductRecyclerAdapter.class.getSimpleName();

    private Activity activity;

    private List<Product> products = new ArrayList<>();

    public ProductRecyclerAdapter(Activity activity){
        this.activity = activity;
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

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        // create a new view
        final GridLayout layout = (GridLayout) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_item, viewGroup, false);
        ViewHolder vh = new ViewHolder(layout);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        try {
            final Product product = products.get(i);

            viewHolder.description.setText(
                activity.getResources().getString(
                        R.string.format_product_name,
                        products.get(i).getName(),
                        products.get(i).getDescriptionFormatted()
                )
            );
            new DownloadImageTask(viewHolder.image).execute(products.get(i).getUrlImage());
            viewHolder.price.setText(activity.getResources().getString(
                    R.string.format_price,
                    activity.getResources().getString(R.string.oritinal_price),
                    products.get(i).getPriceOriginal()
            ));
            viewHolder.offer.setText( activity.getResources().getString(
                    R.string.format_price,
                    activity.getResources().getString(R.string.discount_price),
                    products.get(i).getPriceDiscount()
            ));

            // Manage click action to open the product description page

            viewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlphaAnimation clickAnim = new AlphaAnimation(0.5F, 1F);
                    clickAnim.setDuration(500);
                    viewHolder.layout.startAnimation(clickAnim);

                    // Callback
                    EventBus.getDefault().post(new MessageEvent(MessageCode.PRODUCT_SELECT_EVENT, product));
                }
            });
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
