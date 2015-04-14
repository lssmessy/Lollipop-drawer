package com.tifinnearme.priteshpatel.materialdrawer.fragments;


import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tifinnearme.priteshpatel.materialdrawer.R;
import com.tifinnearme.priteshpatel.materialdrawer.adapters.AdapterBoxOffice;
import com.tifinnearme.priteshpatel.materialdrawer.interfaces.SortListener;
import com.tifinnearme.priteshpatel.materialdrawer.logging.L;
import com.tifinnearme.priteshpatel.materialdrawer.material_test.MyApplication;
import com.tifinnearme.priteshpatel.materialdrawer.network.VolleySingleTon;
import com.tifinnearme.priteshpatel.materialdrawer.pojo.Movie;
import com.tifinnearme.priteshpatel.materialdrawer.pojo.MovieSorter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.tifinnearme.priteshpatel.materialdrawer.api_links.Api_Links.API_URL;
import static com.tifinnearme.priteshpatel.materialdrawer.api_links.Api_Links.API_URL_NOW_PLAYING;
import static com.tifinnearme.priteshpatel.materialdrawer.api_links.Api_Links.IMAGE_URL;
import static com.tifinnearme.priteshpatel.materialdrawer.extras.Keys.EndPointKeys.MOVIES_POSTER;
import static com.tifinnearme.priteshpatel.materialdrawer.extras.Keys.EndPointKeys.MOVIES_TITLE;
import static com.tifinnearme.priteshpatel.materialdrawer.extras.Keys.EndPointKeys.MOVIES_VOTE_COUNT;
import static com.tifinnearme.priteshpatel.materialdrawer.extras.Keys.EndPointKeys.RELEASE_DATE;
import static com.tifinnearme.priteshpatel.materialdrawer.extras.Keys.EndPointKeys.RESULTS;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBoxOffice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBoxOffice extends Fragment implements SortListener, SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String STATE_MOVIES = "state of movies";
    private VolleySingleTon volleySingleTon;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private RecyclerView recycler_movies_list;
    public ArrayList<Movie> movie_array = new ArrayList<>();
    private AdapterBoxOffice adapterBoxOffice;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private TextView errorText;
    private MovieSorter movieSorter = new MovieSorter();
    public SwipeRefreshLayout refreshButton;

    /*private RefreshData refreshData;*/

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBoxOffice.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBoxOffice newInstance(String param1, String param2) {
        FragmentBoxOffice fragment = new FragmentBoxOffice();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentBoxOffice() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        volleySingleTon = VolleySingleTon.getsInstance();
        requestQueue = volleySingleTon.getRequestQueue();

        //sendJsonRequest();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_box_office, container, false);
        errorText = (TextView) view.findViewById(R.id.errorText);
        refreshButton=(SwipeRefreshLayout)view.findViewById(R.id.refreshButton);
        refreshButton.setColorSchemeColors(Color.CYAN,Color.RED,Color.YELLOW,Color.CYAN,Color.GREEN,Color.RED);
        refreshButton.setOnRefreshListener(this);
        recycler_movies_list = (RecyclerView) view.findViewById(R.id.movies_list);
        recycler_movies_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBoxOffice = new AdapterBoxOffice(getActivity());
        recycler_movies_list.setAdapter(adapterBoxOffice);

        sendJsonRequest();
        /*if (savedInstanceState != null) {
            movie_array = savedInstanceState.getParcelableArrayList(STATE_MOVIES);
            adapterBoxOffice.setMovieList(movie_array);
        } else {
            sendJsonRequest();

        }*/
       return view;
    }

    public static String getRequestURL(int limit) {
        return API_URL + API_URL_NOW_PLAYING + "?api_key=" + MyApplication.API_KEY + "&limit=" + limit;
    }

    public void sendJsonRequest() {



        JsonObjectRequest request =
                new JsonObjectRequest(Request.Method.GET, getRequestURL(10),
                        (String) null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                errorText.setVisibility(View.GONE);
                                movie_array = parSeJsonResponse(response);
                                adapterBoxOffice.setMovieList(movie_array);
                                L.t(getActivity(), movie_array.size() + "rows fetched");

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleVolleyErrors(error);

                    }
                });
        requestQueue.add(request);
    }

    public void handleVolleyErrors(VolleyError error) {
        errorText.setVisibility(View.VISIBLE);
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            errorText.setText("Please check you Internet connection. It may be slow or down right now!");
        } else if (error instanceof AuthFailureError) {
            errorText.setText("Oops..Facing Authentication error!");

        } else if (error instanceof ParseError) {
            errorText.setText("Oops..Facing Parsing error!");

        } else if (error instanceof ServerError) {
            errorText.setText("Oops..Facing Server error!");

        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_MOVIES, movie_array);
    }

    public ArrayList<Movie> parSeJsonResponse(JSONObject response) {
        ArrayList<Movie> movie_array = new ArrayList<>();
        if (response != null || response.length() != 0) {


            try {
                if (response.has(RESULTS)) {


                    JSONArray resultsArray = response.getJSONArray(RESULTS);
                    for (int i = 0; i < resultsArray.length(); i++) {
                        long id = 0;
                        String title = "";
                        String image = null;
                        String releaseDate = null;
                        long public_count = -1;


                        JSONObject jsonObject = resultsArray.getJSONObject(i);
                        title = jsonObject.getString(MOVIES_TITLE);


                        if (!jsonObject.isNull(MOVIES_POSTER) && jsonObject.has(MOVIES_POSTER)) {
                            boolean result = jsonObject.isNull(MOVIES_POSTER);
                            image = IMAGE_URL + jsonObject.getString(MOVIES_POSTER);
                        } else {
                            image = null;

                        }
                        id = jsonObject.getInt("id");
                        if (jsonObject.has(RELEASE_DATE) && !jsonObject.isNull(RELEASE_DATE)) {
                            releaseDate = jsonObject.getString(RELEASE_DATE);
                        } else {
                            releaseDate = "NA";
                        }
                        if (jsonObject.has(MOVIES_VOTE_COUNT))
                            public_count = jsonObject.getInt(MOVIES_VOTE_COUNT);


                        Movie movie = new Movie();
                        movie.setId(id);
                        movie.setTitle(title);
                        movie.setUrlthumbNail(image);
                        Date date = null;
                        try {
                            date = dateFormat.parse(releaseDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        movie.setReleaseDate(date);
                        movie.setVotes(public_count);

                        movie_array.add(movie);

                    }


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return movie_array;
    }


    @Override
    public void onSortByName() {
        L.t(MyApplication.getContext(), "Showing by name");
        movieSorter.searchMovieByName(movie_array);
        adapterBoxOffice.notifyDataSetChanged();

    }

    @Override
    public void onSortByDate() {

        L.t(MyApplication.getContext(), "Sorting by Date");
        movieSorter.searchMovieByDate(movie_array);
        adapterBoxOffice.notifyDataSetChanged();

    }

    @Override
    public void onSortByRatings() {
        L.t(MyApplication.getContext(), "Sorting by Ratings");
        movieSorter.searchMovieByRatings(movie_array);
        adapterBoxOffice.notifyDataSetChanged();

    }

    @Override
    public void onRefresh() {
        L.t(getActivity(),"Refreshing");
        //new RefreshData().execute();
        sendJsonRequest();
        if(refreshButton.isRefreshing())
        {

            refreshButton.setRefreshing(false);
        }


    }

    class RefreshData extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            sendJsonRequest();
            adapterBoxOffice.notifyDataSetChanged();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(refreshButton.isRefreshing())
            {

                refreshButton.setRefreshing(false);
            }
        }
    }

}
