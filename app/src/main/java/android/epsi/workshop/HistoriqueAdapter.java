package android.epsi.workshop;

import android.content.Context;
import android.database.DataSetObserver;
import android.epsi.workshop.Models.Balance;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Simon on 19/10/2017.
 */

public class HistoriqueAdapter extends ArrayAdapter<Balance> implements Adapter
{


    public List<Balance> balances;
    public Context context;

    public HistoriqueAdapter(@NonNull Context context, @LayoutRes int resource)
    {
        super(context, resource);
    }


    @Override
    public void registerDataSetObserver(DataSetObserver observer)
    {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer)
    {

    }

    @Override
    public int getCount()
    {
        return balances.size();
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        LayoutInflater inflater = (LayoutInflater.from(this.context));
        View view = inflater.inflate(R.layout.history_cell, parent);

        TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
        TextView tvBalance = (TextView) view.findViewById(R.id.tvBalance);

        tvDate.setText(balances.get(position).datetime.toString());
        tvBalance.setText(balances.get(position).balance.toString());

        return view;
    }

    @Override
    public int getItemViewType(int position)
    {
        return 1;
    }

    @Override
    public int getViewTypeCount()
    {
        return 1;
    }

    @Override
    public boolean isEmpty()
    {
        return false;
    }
}
