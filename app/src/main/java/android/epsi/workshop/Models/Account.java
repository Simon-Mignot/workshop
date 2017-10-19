package android.epsi.workshop.Models;

import android.epsi.workshop.BlockchainAPI;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 19/10/2017.
 */

public class Account implements BlockchainAPI.AccountInterface
{
    public String id;
    public List<Balance> balances;
    public Double rate;
    public String periodicity;

    public Owner owner;
    public Provider provider;
    public Customer customer;
    //Meter meter;

    public Account(JSONObject object)
    {
        BlockchainAPI.init(this);
        balances = new ArrayList<>();
        Log.d("Account", "ctor");
        try
        {
            // ID
            id = object.get("accountId").toString();

            // rate
            rate = Double.valueOf(object.get("rate").toString());

            // periodicity
            periodicity = object.get("periodicity").toString();

            // balance
            JSONArray array = object.getJSONArray("balances");
            for(int i = 0; i < array.length(); ++i)
                balances.add(new Balance(array.getJSONObject(i)));


            BlockchainAPI api = BlockchainAPI.getInstance();
            api.getEntityFromID(extractID(object.get("owner").toString()), "Owner");
            api.getEntityFromID(extractID(object.get("provider").toString()), "Provider");
            api.getEntityFromID(extractID(object.get("customer").toString()), "Customer");

        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }

    String extractID(String namespace)
    {
        return namespace.split("\\#")[1];
    }


    @Override
    public void OnEntityReceived(JSONObject object)
    {
        try
        {
            String className = object.get("$class").toString().split("\\.")[3];
            Log.d("OnEntityReceived", className);
            switch(className)
            {
                case "Owner":
                    owner = new Owner(object);

                    break;
                case "Customer":
                    customer = new Customer(object);
                    break;
                case "Provider":
                    provider = new Provider(object);
                    break;
            }

        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }
}
