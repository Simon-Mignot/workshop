package android.epsi.workshop.Models;

import android.icu.text.DateFormat;
import android.icu.text.RelativeDateTimeFormatter;
import android.icu.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by Simon on 19/10/2017.
 */

public class Balance
{
    Date datetime;
    Double balance;

    public Balance(JSONObject object)
    {
        try
        {
            balance = Double.valueOf(object.get("balanceValue").toString());

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            datetime = dateFormat.parse(object.get("balanceDate").toString());
        }
        catch(JSONException | ParseException e)
        {
            e.printStackTrace();
        }
    }
}
