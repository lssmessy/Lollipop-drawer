package com.tifinnearme.priteshpatel.materialdrawer.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tifinnearme.priteshpatel.materialdrawer.R;
import com.tifinnearme.priteshpatel.materialdrawer.adapters.AdapterBoxOffice;
import com.tifinnearme.priteshpatel.materialdrawer.logging.L;
import com.tifinnearme.priteshpatel.materialdrawer.material_test.MyApplication;
import com.tifinnearme.priteshpatel.materialdrawer.network.VolleySingleTon;
import com.tifinnearme.priteshpatel.materialdrawer.pojo.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tifinnearme.priteshpatel.materialdrawer.extras.Keys.EndPointKeys.*;
import static com.tifinnearme.priteshpatel.materialdrawer.api_links.Api_Links.*;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentUpcoming#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentUpcoming extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private VolleySingleTon volleySingleTon;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private RecyclerView recycler_movies_list;
    public ArrayList<Movie> movie_array=new ArrayList<>();
    private AdapterBoxOffice adapterBoxOffice;

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
    public static FragmentUpcoming newInstance(String param1, String param2) {
        FragmentUpcoming fragment = new FragmentUpcoming();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentUpcoming() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        volleySingleTon=VolleySingleTon.getsInstance();
        requestQueue=volleySingleTon.getRequestQueue();
        sendJsonRequest();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_upcoming, container, false);

        recycler_movies_list=(RecyclerView)view.findViewById(R.id.movies_list);
        recycler_movies_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBoxOffice=new AdapterBoxOffice(getActivity());
        recycler_movies_list.setAdapter(adapterBoxOffice);
        sendJsonRequest();
        return view;
    }
    public static String getRequestURL(int limit){
        return API_URL+API_URL_UPCOMING+"?api_key="+ MyApplication.API_KEY+"&limit="+limit;
    }

    public void sendJsonRequest(){
        JsonObjectRequest request=
                new JsonObjectRequest(Request.Method.GET,getRequestURL(10),
                        (String)null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                movie_array=parSeJsonResponse(response);
                                adapterBoxOffice.setMovieList(movie_array);

                            }
                        },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(request);
    }
    public ArrayList<Movie> parSeJsonResponse(JSONObject response){
        ArrayList<Movie> movie_array=new ArrayList<>();
        if(response!=null || response.length()!=0)
        {


            try {
                if(response.has(RESULTS)) {
                    StringBuilder data=new StringBuilder();
                    long id=0;
                    String title="";
                    String image=null;
                    String releaseDate=null;
                    long public_count=0;

                    JSONArray resultsArray = response.getJSONArray(RESULTS);
                    for(int i=0; i<resultsArray.length(); i++){
                        JSONObject jsonObject=resultsArray.getJSONObject(i);
                        title=jsonObject.getString(MOVIES_TITLE);


                        if(!jsonObject.isNull(MOVIES_POSTER)) {
                            boolean result=jsonObject.isNull(MOVIES_POSTER);
                            image = IMAGE_URL + jsonObject.getString(MOVIES_POSTER);
                        }
                        else{
                            image=null;

                        }
                        id=jsonObject.getInt("id");

                        releaseDate=jsonObject.getString(RELEASE_DATE);
                        public_count=jsonObject.getInt(MOVIES_VOTE_COUNT);


                        Movie movie=new Movie();
                        movie.setId(id);
                        movie.setTitle(title);
                        movie.setUrlthumbNail(image);
                        movie.setReleaseDate("Release Date:"+releaseDate);
                        movie.setVotes(public_count);

                        movie_array.add(movie);

                    }
                    L.t(getActivity(), data.toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return movie_array;
    }


}

