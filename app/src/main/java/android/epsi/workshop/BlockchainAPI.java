package android.epsi.workshop;

import android.app.Activity;
import android.content.Context;
import android.epsi.workshop.Models.Account;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vodka on 18/10/2017.
 */

public class BlockchainAPI
{

    static private BlockchainAPI instance = null;

    public Account account;

    public interface BlockchainAPIInterface
    {
        void OnMeterReceived(Double meter);
        void OnObjectReceived(JSONObject jsonObject);
        void OnObjectReceivedFailed();
        void OnAccountFullyLoaded();
    }

    public interface LoginInterface
    {
        void OnLogin();
        void OnLoginFailed();
    }

    public interface AccountInterface
    {
        void OnEntityReceived(JSONObject object);
    }
    private BlockchainAPIInterface bcInterface;
    private LoginInterface loginInterface;
    private AccountInterface accountInterface;

    private RequestQueue queue = null;
    private String address ="http://148.100.99.84:3000/api/";

    private BlockchainAPI(Activity activity)
    {
        queue = Volley.newRequestQueue(activity);
    }

    public static BlockchainAPI init(Object activity)
    {
        if (instance == null)
            instance = new BlockchainAPI((Activity)activity);
        instance.setInterface(activity);
        return instance;
    }

    private void setInterface(Object activity)
    {
        if(activity instanceof LoginActivity)
            loginInterface = (LoginInterface)activity;
        else if(activity instanceof MainActivity)
            bcInterface = (BlockchainAPIInterface)activity;
        else
            accountInterface = (AccountInterface)activity;
    }

    public static BlockchainAPI getInstance(){
        return instance;
    }

    public void getMeter()
    {
        String url = address + "org.acme.anothertest.GetMeterReading";
        // Request a string response from the provided URL.
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        try
                        {
                            JSONObject obj = response.getJSONObject(response.length()-1);
                            bcInterface.OnMeterReceived(obj.getDouble("statementValue"));

                        } catch(JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.d("RESPONSE", error.toString());
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(request);
    }

    public void getEntityFromID(String id, String type)
    {
        String url = address + "org.acme.anothertest." + type + "/" + id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        accountInterface.OnEntityReceived(response);
                        bcInterface.OnAccountFullyLoaded();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        bcInterface.OnObjectReceivedFailed();
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(request);
    }

    public void checkObjectID(String id, Boolean owner)
    {
        String url = address + "org.acme.anothertest." + (owner ? "Owner" : "Account") + "/" + (owner ? "ownerId:" : "accountId:") + id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        loginInterface.OnLogin();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        loginInterface.OnLoginFailed();
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(request);

    }
    public void getAccountFromID(String id, final Boolean owner)
    {
        String url = address + "org.acme.anothertest." + (owner ? "Owner" : "Account") + "/" + (owner ? "ownerId:" : "accountId:") + id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        if(!owner)
                            bcInterface.OnObjectReceived(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        bcInterface.OnObjectReceivedFailed();
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(request);

    }
}
