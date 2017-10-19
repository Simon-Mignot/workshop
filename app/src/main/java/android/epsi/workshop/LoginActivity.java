package android.epsi.workshop;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.epsi.workshop.Models.Account;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements BlockchainAPI.LoginInterface
{

    TextView text_objectID;
    CheckBox checkbox_owner;
    Button button_connexion;

    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        BlockchainAPI.init(this);

        ctx = this;

        text_objectID = (TextView)findViewById(R.id.objectIDInput);
        checkbox_owner = (CheckBox)findViewById(R.id.checkBox_owner);
        button_connexion = (Button)findViewById(R.id.button_connexion);

        button_connexion.setOnClickListener(OnClickButtonConnection);
    }

    public View.OnClickListener OnClickButtonConnection = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            String id = text_objectID.getText().toString();
            if(!id.isEmpty())
                BlockchainAPI.getInstance().checkObjectID(id, checkbox_owner.isChecked());
            else
                Toast.makeText(ctx, "Veuillez entrer un ID.", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void OnLogin()
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("objectID", text_objectID.getText().toString());
        intent.putExtra("type", checkbox_owner.isChecked());
        startActivity(intent);
    }

    @Override
    public void OnLoginFailed()
    {
        text_objectID.setText("");
        Toast.makeText(ctx, "ID inconnu.", Toast.LENGTH_SHORT).show();
    }
}
