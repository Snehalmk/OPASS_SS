package com.example.admin.opass;

/**
 * Created by dell on 16/02/2017.
 */

public class DataProvider {
    private String dname;
    private String demail;
    private String dpass;
    public DataProvider(String name,String email,String password)
    {
        this.dname=name;
        this.demail=email;
        this.dpass=password;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getDemail() {
        return demail;
    }

    public void setDemail(String demail) {
        this.demail = demail;
    }

    public String getDpass() {
        return dpass;
    }

    public void setDpass(String dpass) {
        this.dpass = dpass;
    }


}
