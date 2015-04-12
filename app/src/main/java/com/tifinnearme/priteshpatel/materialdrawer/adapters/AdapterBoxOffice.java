package com.tifinnearme.priteshpatel.materialdrawer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.tifinnearme.priteshpatel.materialdrawer.R;
import com.tifinnearme.priteshpatel.materialdrawer.network.VolleySingleTon;
import com.tifinnearme.priteshpatel.materialdrawer.pojo.Movie;

import java.util.ArrayList;

/**
 * Created by PRITESH on 12-04-2015.
 */
public class AdapterBoxOffice  extends RecyclerView.Adapter<AdapterBoxOffice.ViewHolderBoxOffice> {
    private ArrayList<Movie> listmovies=new ArrayList<>();
    private LayoutInflater inflater;
    private VolleySingleTon singleTon;
    private ImageLoader imageLoader;

    public AdapterBoxOffice(Context context){
        inflater=LayoutInflater.from(context);
        singleTon=VolleySingleTon.getsInstance();
        imageLoader=singleTon.getImageLoader();
    }
    @Override
    public ViewHolderBoxOffice onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custom_movie_box_office,parent,false);
        ViewHolderBoxOffice viewHolderBoxOffice=new ViewHolderBoxOffice(view);
        return viewHolderBoxOffice;
    }
        public void setMovieList(ArrayList<Movie> listmovies){
            this.listmovies=listmovies;
            notifyItemRangeChanged(0,listmovies.size());
        }
    @Override
    public void onBindViewHolder(final ViewHolderBoxOffice holder, int position) {
        Movie currentMovie=listmovies.get(position);
        holder.movie_name.setText(currentMovie.getTitle());
        holder.counts.setText("User ratings: "+currentMovie.getVotes());
        holder.release_date.setText(currentMovie.getReleaseDate());
        String imageUrl=currentMovie.getUrlthumbNail();
        if (imageUrl!=null){
            imageLoader.get(imageUrl, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.movie_poster.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
        else if(imageUrl==null){
          holder.movie_poster.setImageResource(R.drawable.no_image);
        }

    }

    @Override
    public int getItemCount() {
        return listmovies.size();
    }
    static class ViewHolderBoxOffice extends RecyclerView.ViewHolder{

        private ImageView movie_poster;
        private TextView movie_name,counts,release_date;

        public ViewHolderBoxOffice(View itemView) {
            super(itemView);
            movie_poster=(ImageView)itemView.findViewById(R.id.movie_image);
            movie_name=(TextView)itemView.findViewById(R.id.movie_name);
            counts=(TextView)itemView.findViewById(R.id.counts);
            release_date=(TextView)itemView.findViewById(R.id.release_date);

        }
    }
}
