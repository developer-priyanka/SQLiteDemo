package my.assignment.sqlitedemo;

import android.app.LauncherActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;

import static android.R.attr.onClick;

public class MainActivity extends AppCompatActivity {
    DBHelper db;
    CustomeAdapter adapter;
    EditText etfname,etlname;
    ListView lv;
    ArrayList<Customer> customers=new ArrayList<Customer>();
    Button button;
    Customer customer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etfname=(EditText)findViewById(R.id.etFname);
        etlname=(EditText)findViewById(R.id.etLname);
        lv=(ListView)findViewById(R.id.listview);
        button=(Button)findViewById(R.id.button);
        db=new DBHelper(this);
        customers=db.getAllCustmor();
        adapter=new CustomeAdapter(this,customers);
        lv.setAdapter(adapter);
        registerForContextMenu(lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                customer=(Customer)adapter.getItem(i);
                etfname.setText(customer.getFname());
                etlname.setText((customer.getLname()));
                button.setText("Update");

            }
        });

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("MainActivity","Inside button click "+button.getText());
                if(button.getText().equals("Add"))
                    onSubmit();
                if(button.getText().equals("Update")) {
                    Customer c=new Customer(customer.getId(),etfname.getText().toString(),etlname.getText().toString());
                    onUpdate(c);
                }
            }

        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        menu.add(0, view.getId(), 0, "Delete");

    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int i = info.position;
        Customer c = (Customer) adapter.getItem(i);
        int id = c.getId();
        if(item.getTitle()=="Delete"){
            db.deleteCust(c);
            Toast.makeText(this,"Customer Deleted...",Toast.LENGTH_SHORT).show();
            customers=db.getAllCustmor();
            adapter.updateCustomerList(customers);
            return true;
        }
        return false;
    }


    public void onSubmit(){
        Customer c=new Customer(etfname.getText().toString(),etlname.getText().toString());

        db.insertCust(c);
        Toast.makeText(this,"Customer Added...",Toast.LENGTH_SHORT).show();
        etfname.setText("");
        etlname.setText(""); Log.d("updating values",customer.getId()+"");
        c=null;
        c=db.getCustomerById(db.getLastinsertedId());
        adapter.addCustomerTolist(c);
        Log.d("List Count",""+customers.size());

    }
    private void onUpdate(Customer c){
        Log.d("Main Activity:","updating record");
        db.updateCust(c);
        Toast.makeText(this,"Customer Updated...",Toast.LENGTH_SHORT).show();
        customers=db.getAllCustmor();
        adapter.updateCustomerList(customers);
        etfname.setText("");
        etlname.setText("");
        button.setText("Add");

    }

}
