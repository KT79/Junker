package uk.co.mcclure_solicitors.wwjmcclure;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by kevantodd on 20/08/2015.
 */
public class FactFind implements Serializable {


    public FactFind(String title, Date date) {
        super();
        this.title = title;
      //  this.FactFindTitle = factFind;
        this.date = date;
    }

    private String title;
   // private String FactFindTitle;
    private Date date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

  /* public String getFactFindTitle() {
        return FactFindTitle;
    }

    public void setFactFindTitle(String factFind) {
        this.FactFindTitle = factFind;
    }*/
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}