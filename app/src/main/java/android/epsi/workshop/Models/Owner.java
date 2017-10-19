package android.epsi.workshop.Models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Simon on 19/10/2017.
 */

public class Owner
{
    public String name;
    public Owner(JSONObject object)
    {
        Log.d("OwnerLOG", object.toString());
        try
        {
            name = object.get("firstname").toString();
            name += " " + object.get("lastname").toString();
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }
}
