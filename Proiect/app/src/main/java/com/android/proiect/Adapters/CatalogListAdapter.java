package com.android.proiect.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.proiect.Database.AppDatabase;
import com.android.proiect.Database.Entities.Catalog;
import com.android.proiect.Database.Entities.Product;
import com.android.proiect.R;

import java.util.List;

public class CatalogListAdapter extends ArrayAdapter<Catalog> {

    private Context context;

    private int resource;

    public CatalogListAdapter(Context context, int resource, List<Catalog> catalogs) {
        super(context, resource, catalogs);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int id = getItem(position).productId;
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        Product product = appDatabase.productDao().getById(id);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView tvName = convertView.findViewById(R.id.name);
        TextView tvDescription = convertView.findViewById(R.id.description);
        TextView tvPrice = convertView.findViewById(R.id.price);

        tvName.setText(product.productName);
        tvDescription.setText(product.description);
        tvPrice.setText(product.price.toString());

        return convertView;
    }
}
