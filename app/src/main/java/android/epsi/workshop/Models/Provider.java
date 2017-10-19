package android.epsi.workshop.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Simon on 19/10/2017.
 */

public class Provider
{
    public String name;
    public Provider(JSONObject object)
    {
        try
        {
            name = object.get("name").toString();
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }
}
