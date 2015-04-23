package com.tifinnearme.priteshpatel.materialdrawer.tvdetails;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.tifinnearme.priteshpatel.materialdrawer.R;
import com.tifinnearme.priteshpatel.materialdrawer.animation.AnimationUtils;
import com.tifinnearme.priteshpatel.materialdrawer.dialogs.CustomDialog;
import com.tifinnearme.priteshpatel.materialdrawer.fragments.FragmentUpcoming;
import com.tifinnearme.priteshpatel.materialdrawer.network.VolleySingleTon;
import com.tifinnearme.priteshpatel.materialdrawer.pojo.Movie;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by PRITESH on 12-04-2015.
 */
public class AdapterTv extends RecyclerView.Adapter<AdapterTv.ViewHolderBoxOffice> {
    public ArrayList<TV> listmovies = new ArrayList<>();
    private LayoutInflater inflater;
    private VolleySingleTon singleTon;
    private ImageLoader imageLoader;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private int previousPosition = 0;
    Context context;
    private CustomDialog dialog;
    public String imageUrl = null;
     int pos=0;
    private MovieClickListener movieClickListener;

    public AdapterTv(Context context) {
        inflater = LayoutInflater.from(context);
        singleTon = VolleySingleTon.getsInstance();
        imageLoader = singleTon.getImageLoader();
        this.context = context;

    }

    @Override
    public ViewHolderBoxOffice onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_movie_box_office, parent, false);
        ViewHolderBoxOffice viewHolderBoxOffice = new ViewHolderBoxOffice(view, context);
        return viewHolderBoxOffice;
    }

    public void setMovieList(ArrayList<TV> listmovies) {
        this.listmovies = listmovies;
        notifyItemRangeChanged(0, listmovies.size());
    }

    @Override
    public void onBindViewHolder(final ViewHolderBoxOffice holder, int position) {
        TV currentMovie = listmovies.get(position);

        holder.movie_name.setText(currentMovie.getTitle());

        if (currentMovie.getVotes() / 20.0F == 0.0) {

            holder.ratings.setRating(0.0F);
            holder.ratings.setAlpha(0.4F);
            /*holder.counts.setVisibility(View.VISIBLE);
            holder.counts.setText("No Ratings Available");*/
        } else {
            //holder.counts.setVisibility(View.GONE);
            holder.ratings.setRating(currentMovie.getVotes() / 20.0F);
            holder.ratings.setAlpha(1.0F);
        }

        if (currentMovie.getReleaseDate() != null) {
            String formatedDate = dateFormat.format(currentMovie.getReleaseDate());

            holder.release_date.setText("Release Date: " + formatedDate);
        } else {
            holder.release_date.setText("NA");
        }
        if (position > previousPosition) {
            AnimationUtils.animate(holder, true);
        } else {
            AnimationUtils.animate(holder, false);
        }
        imageUrl = currentMovie.getUrlthumbNail();
        //this.imageUrl=imageUrl;
        if (imageUrl != null) {
            imageLoader.get(imageUrl, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.movie_poster.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        } else if (imageUrl == null) {
            holder.movie_poster.setImageResource(R.drawable.no_image);
        }

        pos= (int) holder.getItemId();

        //holder.movie_poster.setOnClickListener(this);

    }
    public void setMovieClickListener(MovieClickListener movieClickListener){
            this.movieClickListener=movieClickListener;
    }

    @Override
    public int getItemCount() {
        return listmovies.size();
    }


    class ViewHolderBoxOffice extends RecyclerView.ViewHolder implements View.OnClickListener{
        //private ArrayList<Movie> listmovies = new ArrayList<>();
        private ImageView movie_poster;
        private TextView movie_name, counts, release_date;
        private RatingBar ratings;
        private Context context;
        private FragmentUpcoming fragmentUpcoming = new FragmentUpcoming();
        CustomDialog dialog;
        private Context mcotext;
        private ImageLoader imageLoaderDialog;
        private VolleySingleTon singleTon;
        private ViewHolderBoxOffice vholder;
        private Bitmap bitmap;

        public ViewHolderBoxOffice(View itemView, Context context) {
            super(itemView);
            movie_poster = (ImageView) itemView.findViewById(R.id.movie_image);
            movie_name = (TextView) itemView.findViewById(R.id.movie_name);
            counts = (TextView) itemView.findViewById(R.id.counts);
            ratings = (RatingBar) itemView.findViewById(R.id.ratingBar);
            release_date = (TextView) itemView.findViewById(R.id.release_date);
            this.mcotext = context;
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {

        if(movieClickListener!=null)
        {
            movieClickListener.movieClicked(v,getPosition());
        }
        }
    }
    public interface MovieClickListener{
        public void movieClicked(View view, int position);
    }
}
