package android.epsi.workshop;

import android.epsi.workshop.Models.Account;
import android.epsi.workshop.Models.Balance;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class HistoryActivity extends AppCompatActivity
{

    Account account;
    ListView listeHisto;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        account = BlockchainAPI.getInstance().account;

        listeHisto = (ListView) findViewById(R.id.historyList);

        HistoriqueAdapter adapter = new HistoriqueAdapter(this,R.layout.history_cell);
        adapter.balances = account.balances;
        adapter.context = this;
        listeHisto.setAdapter(adapter);

    }
}
