package dsd_t3;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ivens
 */
public class ServerTime implements Serializable{
    private static final long serialVersionUID = -5399605122490343339L;
    
    private long H;
    private Date utc;

    public ServerTime(long H, Date utc) {    
        this.utc = utc;
        this.H = System.currentTimeMillis() - H;
    }   

    public Date getUtc() {
        return utc;
    }
    
    public long getH(){
        return H;
    }
}
