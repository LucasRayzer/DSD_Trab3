/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsd_t3;

import java.util.Date;

public class Time implements java.io.Serializable {
    
    private long h;
    private Date utc;

    public Time(long h, Date utc) {
        this.h = h;
        this.utc = utc;
    }

    public long getH() {
        return h;
    }

    public Date getUtc() {
        return utc;
    }
    
}
