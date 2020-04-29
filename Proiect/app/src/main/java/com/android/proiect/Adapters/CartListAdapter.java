package com.android.proiect.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.proiect.CartActivity;
import com.android.proiect.Database.AppDatabase;
import com.android.proiect.Database.Entities.Cart;
import com.android.proiect.Database.Entities.Product;
import com.android.proiect.R;

import java.util.List;

public class CartListAdapter extends ArrayAdapter<Cart> {

    private Context context;

    private int resource;

    private ListView listView;

    public CartListAdapter(Context context, int resource, List<Cart> products, ListView listView) {
        super(context, resource, products);
        this.context = context;
        this.resource = resource;
        this.listView = listView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int id = getItem(position).id;
        int productId = getItem(position).productId;
        int quantity = getItem(position).quantity;

        final AppDatabase appDatabase = AppDatabase.getInstance(context);
        Product product = appDatabase.productDao().getById(productId);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView tvName = convertView.findViewById(R.id.name);
        TextView tvDescription = convertView.findViewById(R.id.description);
        TextView tvPrice = convertView.findViewById(R.id.price);
        TextView tvQuantity = convertView.findViewById(R.id.editText);

        final Cart cart = new Cart(id, productId, quantity);

        tvName.setText(product.productName);
        tvDescription.setText(product.description);
        tvPrice.setText(product.price.toString());
        tvQuantity.setText(Integer.toString(quantity));

        Button button = convertView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appDatabase.cartDao().delete(cart);
                CartActivity.generateList(context, listView);
            }
        });

        return convertView;
    }

}
