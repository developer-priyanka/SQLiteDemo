package my.assignment.sqlitedemo;

/**
 * Created by root on 7/20/16.
 */

public class Customer {
    private int id;
    private String fname;
    private String lname;


    public Customer(int id,String fname,String lname){
        this.id=id;
        this.fname=fname;
        this.lname=lname;
    }
    public Customer(String fname,String lname){
        this.fname=fname;
        this.lname=lname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
}
