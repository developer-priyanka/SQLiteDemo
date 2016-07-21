package my.assignment.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.ContextMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 7/20/16.
 */

public class DBHelper extends SQLiteOpenHelper {
    public String log="DatabaseLog";
    private static final int DB_VERSION=1;
    private static final String DB_NAME="CustomerDB";
    private static final String DB_TABLE="customer";
    private static final String CUST_ID="id";
    private static final String CUST_FNAME="fname";
    private static final String CUST_LNAME="lname";
    private static final String CREATE_CUST="CREATE TABLE "+DB_TABLE+" ("+CUST_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+CUST_FNAME+" TEXT NOT NULL,"+CUST_LNAME+" TEXT NOT NULL)";
    private String [] columns=new String[]{CUST_ID,CUST_FNAME,CUST_LNAME};

    public DBHelper(Context c){
        super(c,DB_NAME,null,DB_VERSION);


    }
    public void onCreate(SQLiteDatabase db){
        Log.d(log,CREATE_CUST);
        db.execSQL(CREATE_CUST);
    }
    public void onUpgrade(SQLiteDatabase db,int v1,int v2){
        db.execSQL("DROP TABLE IF EXISTS "+DB_TABLE);
        this.onCreate(db);

    }


    public void insertCust(Customer c){
      SQLiteDatabase db=getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put("fname",c.getFname());
        cv.put("lname",c.getLname());
        db.insert(DB_TABLE,null,cv);
        db.close();
        Log.d(log,"Record Created");


    }

    public int updateCust(Customer customer){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("fname",customer.getFname());
        cv.put("lname",customer.getLname());
        int i =db.update(DB_TABLE,cv,CUST_ID+" = ?",new String[]{String.valueOf(customer.getId())});
        db.close();
        Log.d(log,"Record Updated");
        return i;

    }
    public void deleteCust(Customer customer){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("delete from "+DB_TABLE+" where id="+customer.getId());
        db.close();
        Log.d(log,"Record Deleted");
    }

    public ArrayList<Customer> getAllCustmor(){
        ArrayList<Customer>cList=new ArrayList<Customer>();
      SQLiteDatabase  db= getReadableDatabase();
        Cursor cursor=db.query(true,DB_TABLE,columns,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{

            cList.add(new Customer(cursor.getInt(cursor.getColumnIndex(CUST_ID)),cursor.getString(cursor.getColumnIndex(CUST_FNAME)),cursor.getString(cursor.getColumnIndex(CUST_LNAME))));
            }while (cursor.moveToNext());
        }
        db.close();

        return cList;
    }

    public Customer getCustomerById(int id){
        Customer c;
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from customer where id=?",new String[]{String.valueOf(id)});
        if(cursor.moveToNext()){
            return(new Customer(cursor.getInt(cursor.getColumnIndex(CUST_ID)),cursor.getString(cursor.getColumnIndex(CUST_FNAME)),
                    cursor.getString(cursor.getColumnIndex(CUST_LNAME))));
        }
        return null;
    }
    public int getLastinsertedId(){
        SQLiteDatabase db=getReadableDatabase();
        Cursor c;
        int id=0;
        c=db.rawQuery("select max(id) last_id from customer",null);
        if(c.moveToNext())
            id=Integer.parseInt(c.getString(0));

        return id;
    }
}

