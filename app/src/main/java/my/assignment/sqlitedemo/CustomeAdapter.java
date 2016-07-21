package my.assignment.sqlitedemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by root on 7/21/16.
 */

public class CustomeAdapter extends BaseAdapter {
    ArrayList<Customer>data;
    Context context;
    LayoutInflater inflater;

    public CustomeAdapter(Context c,ArrayList<Customer> list){
        context=c;
        data=list;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class Holder
    {
        TextView tv1;
        TextView tv2;
        TextView tv3;

    }
    public void addCustomerTolist(Customer c){
        data.add(c);
        notifyDataSetChanged();
    }
    public void updateCustomerList(ArrayList<Customer> list){
        data=list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder=new Holder();
        View rowView;
        String id=data.get(i).getId()+"";
        rowView = inflater.inflate(R.layout.list_view, null);
        holder.tv1=(TextView) rowView.findViewById(R.id.fname);
        holder.tv2=(TextView) rowView.findViewById(R.id.lname);
        holder.tv3=(TextView)rowView.findViewById(R.id.custid);

        holder.tv1.setText(data.get(i).getFname());
        holder.tv2.setText(data.get(i).getLname());
        holder.tv3.setText(id);



        return rowView;


    }
}
