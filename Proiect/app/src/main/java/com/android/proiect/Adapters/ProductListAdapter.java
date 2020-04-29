package com.android.proiect.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.proiect.Database.AppDatabase;
import com.android.proiect.Database.Entities.Cart;
import com.android.proiect.Database.Entities.Product;
import com.android.proiect.R;

import java.util.List;

public class ProductListAdapter extends ArrayAdapter<Product> {

    private Context context;

    private int resource;

    public ProductListAdapter(Context context, int resource, List<Product> products) {
        super(context, resource, products);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int id = getItem(position).id;
        int categoryId = getItem(position).categoryId;
        String name = getItem(position).productName;
        String description = getItem(position).description;
        Double price = getItem(position).price;

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView tvName = convertView.findViewById(R.id.name);
        TextView tvDescription = convertView.findViewById(R.id.description);
        TextView tvPrice = convertView.findViewById(R.id.price);
        final TextView tvQuantity = convertView.findViewById(R.id.editText);

        tvName.setText(name);
        tvDescription.setText(description);
        tvPrice.setText(price.toString());

        Button button = convertView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity;
                if(tvQuantity.getText().toString().length() > 0)
                    quantity = Integer.parseInt(tvQuantity.getText().toString());
                else
                    quantity = 0;

                if(quantity > 0) {
                    AppDatabase appDatabase = AppDatabase.getInstance(context);
                    Integer currentQuantity = appDatabase.cartDao().getQuantityForProduct(id);
                    if(currentQuantity == null || currentQuantity == 0) {
                        Integer newId = appDatabase.cartDao().getMaxId();
                        if(newId == null || newId == 0) newId = 0;
                        appDatabase.cartDao().insert(new Cart(newId + 1, id, quantity));
                    }
                    else
                        appDatabase.cartDao().update(quantity + currentQuantity, id);
                }
            }
        });

        return convertView;
    }
}
