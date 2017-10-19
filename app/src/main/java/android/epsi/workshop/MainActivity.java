package android.epsi.workshop;

import android.epsi.workshop.Models.Account;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements BlockchainAPI.BlockchainAPIInterface {

    TextView tvId ;
    TextView tvOwnerName ;
    TextView tvPayee ;
    TextView tvBalance ;
    TextView tvRate ;
    TextView tvConso ;

    Button btnRefresh ;
    Button btnHisto ;

    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BlockchainAPI.init(this);

        String id = getIntent().getExtras().getString("objectID");
        Boolean bool = getIntent().getExtras().getBoolean("type");
        BlockchainAPI.getInstance().getAccountFromID(id, bool);

        tvId = (TextView) findViewById(R.id.textId);
        tvOwnerName = (TextView) findViewById(R.id.textName);
        tvPayee = (TextView) findViewById(R.id.textPayee);
        tvBalance = (TextView) findViewById(R.id.textBalance);
        tvRate = (TextView) findViewById(R.id.textRate);
        tvConso = (TextView) findViewById(R.id.textConso);

        btnRefresh = (Button) findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(OnClickBtnRefresh);
        btnHisto = (Button) findViewById(R.id.btnHistory);
        btnHisto.setOnClickListener(OnClickBtnHisto);


        getAccount();
        RefreshConso();


    }

    public View.OnClickListener OnClickBtnRefresh = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            RefreshConso();
        }
    };

    public View.OnClickListener OnClickBtnHisto = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            //Do something
        }
    };

    public void getAccount(){
        // récupération des données du compte
    }

    public void LoadDisplayInfos(Account account)
    {
        // utiliser les données
        tvId.setText(account.id);
        if(account.owner != null)
            tvOwnerName.setText(account.owner.name);
        if(account.provider != null)
            tvPayee.setText(account.provider.name);
        tvBalance.setText(account.balances.get(account.balances.size() - 1).toString());
        tvRate.setText(account.rate.toString());
    }

    public void RefreshConso(){
        // Refresh de la conso
        BlockchainAPI.getInstance().getMeter();
        //tvConso.setText("13,14 m3");
    }


    @Override
    public void OnMeterReceived(Double meter)
    {
        tvConso.setText(String.valueOf(meter));
    }

    @Override
    public void OnObjectReceived(JSONObject jsonObject)
    {
        account = new Account(jsonObject);
    }

    @Override
    public void OnObjectReceivedFailed()
    {

    }

    @Override
    public void OnAccountFullyLoaded()
    {
        LoadDisplayInfos(account);
    }
}
