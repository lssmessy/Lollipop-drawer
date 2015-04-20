package com.tifinnearme.priteshpatel.materialdrawer.pojo;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.tifinnearme.priteshpatel.materialdrawer.R;
import com.tifinnearme.priteshpatel.materialdrawer.material_test.MyApplication;
import com.tifinnearme.priteshpatel.materialdrawer.network.VolleySingleTon;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tifinnearme.priteshpatel.materialdrawer.api_links.Api_Links.API_URL;
import static com.tifinnearme.priteshpatel.materialdrawer.extras.Keys.EndPointKeys.OVERVIEW;

/**
 * Created by pritesh.patel on 20-04-15.
 */
public class Show_Movie_Details extends ActionBarActivity {
    Toolbar toolbar;
    private RequestQueue requestQueue;
    private TextView errorText;
    public ArrayList<Movie> movie_array_for_id = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        toolbar=(Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        VolleySingleTon volleySingleTon=VolleySingleTon.getsInstance();
        requestQueue=volleySingleTon.getRequestQueue();



    }


    private void sendJsonRequest_for_Id(long movie_id) {

        JsonObjectRequest request =
                new JsonObjectRequest(Request.Method.GET, getRequestURLforID(movie_id),
                        (String) null,
                        new Response.Listener<JSONObject>() {


                            @Override
                            public void onResponse(JSONObject response) {

                                errorText.setVisibility(View.GONE);
                                movie_array_for_id = parSeJsonResponseforID(response);

                                //adapterBoxOffice.setMovieList(movie_array_for_id);


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleVolleyErrors(error);

                    }
                });
        requestQueue.add(request);
    }
    public static String getRequestURLforID(long id) {
        return API_URL + "/" + id + "?api_key=" + MyApplication.API_KEY;
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
    public ArrayList<Movie> parSeJsonResponseforID(JSONObject response) {
        ArrayList<Movie> movie_array_for_id=new ArrayList<Movie>();
        String movie_overView=null;
        if (response != null || response.length() != 0) {
            try {

                movie_overView=response.getString(OVERVIEW);
                if(movie_overView!=null)
                {
                    movie_overView = response.getString(OVERVIEW);
                    Movie movie=new Movie();
                    movie.setOverview(movie_overView);
                    movie_array_for_id.add(movie);
                }
                return movie_array_for_id;

            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
        return movie_array_for_id;
    }



    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id==android.R.id.home)
        {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
