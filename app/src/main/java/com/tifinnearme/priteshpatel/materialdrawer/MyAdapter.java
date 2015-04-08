package com.tifinnearme.priteshpatel.materialdrawer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by pritesh.patel on 08-04-15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private ClikListener clikListener;
    List<DataList> data= Collections.emptyList();//take care of the null objext reference
//get data from DataList class in the form of array
    public MyAdapter(Context context,List<DataList> data){
        this.context=context;
        inflater= LayoutInflater.from(context);//Get the context for layout of row
        this.data=data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view=inflater.inflate(R.layout.custom_row,parent,false);
        //view represent the root of custom Layout(xml) i.e, Linearlayout
        MyViewHolder myholder=new MyViewHolder(view);// pass view to holder class
        Log.d("onBindViewHolder", String.valueOf(i));
        return myholder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {//This method contains the actual data which will be shown in recycler view
        DataList currentObject=data.get(position);
        Log.d("onBindViewHolder", String.valueOf(position));
        viewHolder.icon.setImageResource(currentObject.iconId);
        viewHolder.text.setText(currentObject.iconName);

    }

    public void setOnClikListener(ClikListener clikListener){
        this.clikListener=clikListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView text;
        ImageView icon;
        public MyViewHolder(View itemView) {//Here, we will get the recycler view items/data in the form of icon, text
            super(itemView);
            itemView.setOnClickListener(this);
            text= (TextView)itemView.findViewById(R.id.listText);
            icon=(ImageView) itemView.findViewById(R.id.listIcon);
            text.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if(clikListener!=null)
            {
                clikListener.itemClicked(v,getPosition());
            }
        }
    }
    public interface ClikListener{
        public void itemClicked(View view,int position);
    }
}
