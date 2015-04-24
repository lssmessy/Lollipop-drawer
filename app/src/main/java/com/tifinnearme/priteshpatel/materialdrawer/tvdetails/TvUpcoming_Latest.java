package com.tifinnearme.priteshpatel.materialdrawer.tvdetails;


import android.app.ProgressDialog;
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
import com.tifinnearme.priteshpatel.materialdrawer.interfaces.SortListener;
import com.tifinnearme.priteshpatel.materialdrawer.logging.L;
import com.tifinnearme.priteshpatel.materialdrawer.material_test.MyApplication;
import com.tifinnearme.priteshpatel.materialdrawer.network.VolleySingleTon;
import com.tifinnearme.priteshpatel.materialdrawer.pojo.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.tifinnearme.priteshpatel.materialdrawer.api_links.Api_Links.IMAGE_URL;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link TvUpcoming_Latest#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TvUpcoming_Latest extends Fragment implements SortListener, SwipeRefreshLayout.OnRefreshListener, AdapterTv.MovieClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private VolleySingleTon volleySingleTon;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private RecyclerView recycler_movies_list;
    public ArrayList<TV> movie_array = new ArrayList<>();
    public ArrayList<Movie> movie_array_for_id = new ArrayList<>();
    private AdapterTv adapterBoxOffice;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private TextView errorText;
    private TVSorter movieSorter = new TVSorter();
    public SwipeRefreshLayout refreshButtonUpcoming;
    long id = 0;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    static String movie_overView = "NOthing";
    private ArrayList<String> credits = new ArrayList<>();
    private StringBuilder stringBuilder=new StringBuilder();
    private ProgressDialog pdial;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBoxOffice.
     */
    // TODO: Rename and change types and number of parameters
    public static TvUpcoming_Latest newInstance(String param1, String param2) {
        TvUpcoming_Latest fragment = new TvUpcoming_Latest();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TvUpcoming_Latest() {
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
        imageLoader = volleySingleTon.getImageLoader();
        sendJsonRequest();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        errorText = (TextView) view.findViewById(R.id.errorText);
        refreshButtonUpcoming = (SwipeRefreshLayout) view.findViewById(R.id.refreshButtonUpcoming);
        refreshButtonUpcoming.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN, Color.RED);
        refreshButtonUpcoming.setOnRefreshListener(this);

        recycler_movies_list = (RecyclerView) view.findViewById(R.id.movies_list);
        recycler_movies_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBoxOffice = new AdapterTv(getActivity());
        adapterBoxOffice.setMovieClickListener(this);
        recycler_movies_list.setAdapter(adapterBoxOffice);
        pdial=new ProgressDialog(getActivity());
        pdial.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdial.setMessage("Loading TV shows...");
        pdial.show();
        sendJsonRequest();
        /*if(movie_array.size()!=0) {
            for (int i = 0; i < movie_array.size(); i++) {
                long movie_id=movie_array.get(i).getId();
                sendJsonRequest_for_Id(movie_id);
            }
        }*/
        return view;
    }

    private void sendJsonRequest_for_Id(long movie_id, final int position) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        final TV_CustomDialog dialog = new TV_CustomDialog(getActivity(), R.style.cust_dialog);
        dialog.setTitle(movie_array.get(position).getTitle());

        final String formatedDate = dateFormat.format(movie_array.get(position).getReleaseDate());
        JsonObjectRequest jrequest = new JsonObjectRequest(Request.Method.GET, getRequestURLforCredits(movie_id), (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                stringBuilder = parSeJsonResponseforCredits(response);
                /*L.t(getActivity(), credits.toString());*/
                TV_CustomDialog.actors.setText(stringBuilder.toString());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleVolleyErrors(error);
            }
        });

        JsonObjectRequest request =
                new JsonObjectRequest(Request.Method.GET, getRequestURLforID(movie_id),
                        (String) null,
                        new Response.Listener<JSONObject>() {


                            @Override
                            public void onResponse(JSONObject response) {

                                errorText.setVisibility(View.GONE);
                                movie_overView = parSeJsonResponseforID(response);
                                String current = movie_array.get(position).getUrlthumbNail();
                                if (current != null) {
                                    imageLoader.get(current, new ImageLoader.ImageListener() {
                                        @Override
                                        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                                            TV_CustomDialog.imageView.setImageBitmap(response.getBitmap());
                                        }

                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    });
                                } else if (current == null) {
                                    TV_CustomDialog.imageView.setImageResource(R.drawable.no_image);
                                }
                                if (movie_overView != "null")
                                    TV_CustomDialog.textView.setText(movie_overView);
                                else if (movie_overView == "null")
                                    TV_CustomDialog.textView.setText("Not available");
                                //TV_CustomDialog.actors.setText(credits.toString());
                                TV_CustomDialog.first_air_date.setText(""+formatedDate.toString());
                                progressDialog.dismiss();
                                dialog.show();

                                //adapterBoxOffice.setMovieList(movie_array_for_id);


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleVolleyErrors(error);

                    }
                });


        requestQueue.add(jrequest);
        requestQueue.add(request);


    }

    private StringBuilder parSeJsonResponseforCredits(JSONObject response) {
        //ArrayList<String> credits = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        if (response != null || response.length() != 0) {
            try {
                JSONArray castArray = null;

                castArray = response.getJSONArray(Keys_Tv.EndPointKeys.CAST);

                if (castArray.length()!=0) {


                    for (int i = 0; i < castArray.length(); i++) {
                        JSONObject job_actor = castArray.getJSONObject(i);
                        String actor = job_actor.getString(Keys_Tv.EndPointKeys.ACTOR_NAME);
                        if (i == castArray.length() - 1)
                            stringBuilder.append(actor + "");
                        else
                            stringBuilder.append(actor + ",");

                    }
                }
                else {
                    stringBuilder.append("Not available");
                }
                //L.t(getActivity(),stringBuilder.toString());
                return stringBuilder;
                //L.t(getActivity(),""+credits);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return stringBuilder;
    }


    public static String getRequestURL(int limit) {
        return Api_Links_Tv.API_URL + Api_Links_Tv.API_URL_TODAY + "?api_key=" + MyApplication.API_KEY ;
    }

    public static String getRequestURLforID(long id) {
        return Api_Links_Tv.API_URL + "/" + id + "?api_key=" + MyApplication.API_KEY;
    }

    public static String getRequestURLforCredits(long id) {
        return Api_Links_Tv.API_URL + "/" + id + Api_Links_Tv.CREDITS + "?api_key=" + MyApplication.API_KEY;
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
                                pdial.dismiss();

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

    public ArrayList<TV> parSeJsonResponse(JSONObject response) {
        ArrayList<TV> movie_array = new ArrayList<>();
        if (response != null || response.length() != 0) {
            try {
                if (response.has(Keys_Tv.EndPointKeys.RESULTS)) {

                    JSONArray resultsArray = response.getJSONArray(Keys_Tv.EndPointKeys.RESULTS);
                    for (int i = 0; i < resultsArray.length(); i++) {

                        String title = "";
                        String image = null;
                        String releaseDate = null;
                        long public_count = -1;


                        JSONObject jsonObject = resultsArray.getJSONObject(i);
                        title = jsonObject.getString(Keys_Tv.EndPointKeys.MOVIES_TITLE);


                        if (!jsonObject.isNull(Keys_Tv.EndPointKeys.MOVIES_POSTER) && jsonObject.has(Keys_Tv.EndPointKeys.MOVIES_POSTER)) {

                            image = IMAGE_URL + jsonObject.getString(Keys_Tv.EndPointKeys.MOVIES_POSTER);
                        } else {
                            image = null;

                        }
                        this.id = jsonObject.getInt("id");
                        if (jsonObject.has(Keys_Tv.EndPointKeys.RELEASE_DATE) && !jsonObject.isNull(Keys_Tv.EndPointKeys.RELEASE_DATE)) {
                            releaseDate = jsonObject.getString(Keys_Tv.EndPointKeys.RELEASE_DATE);
                        } else {
                            releaseDate = "NA";
                        }
                        if (jsonObject.has(Keys_Tv.EndPointKeys.MOVIES_VOTE_COUNT))
                            public_count = jsonObject.getInt(Keys_Tv.EndPointKeys.MOVIES_VOTE_COUNT);
                        TV movie = new TV();
                        movie.setId(id);
                       /* if(id != 0) {
                            sendJsonRequest_for_Id(id);
                           // movie_array.get(i).setOverview(movie_overView);
                            //movie.setOverview();
                        }*/

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

    public String parSeJsonResponseforID(JSONObject response) {

        String movie_overView = null;
        if (response != null || response.length() != 0) {
            try {

                movie_overView = response.getString(Keys_Tv.EndPointKeys.OVERVIEW);
                if (movie_overView != null) {
                    movie_overView = response.getString(Keys_Tv.EndPointKeys.OVERVIEW);
                    /*Movie movie=new Movie();
                    movie.setOverview(movie_overView);*/

                }
                return movie_overView;

            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
        return movie_overView;
    }


    @Override
    public void onSortByName() {
        L.t(MyApplication.getContext(), "Box Upcoming by name");
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
        if(new MyApplication().isInternetAvailable()==true) {
            L.t(getActivity(), "Updating Upcoming");
            new RefreshData().execute();
            //sendJsonRequest();
            adapterBoxOffice.notifyDataSetChanged();
        }else
        {
            L.t(getActivity(),"No internet available");
            refreshButtonUpcoming.setRefreshing(false);

        }

    }

    @Override
    public void movieClicked(View view, int position) {
        sendJsonRequest_for_Id(movie_array.get(position).getId(), position);

       // new Overview_Data().execute();



    }

    class Overview_Data extends AsyncTask<Integer, Void, Void> {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());

        @Override
        protected Void doInBackground(Integer... params) {
            sendJsonRequest_for_Id(movie_array.get(params[0]).getId(), params[0]);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }
    }


    class RefreshData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... params) {
            sendJsonRequest();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (refreshButtonUpcoming.isRefreshing()) {

                refreshButtonUpcoming.setRefreshing(false);
            }
        }
    }
}

