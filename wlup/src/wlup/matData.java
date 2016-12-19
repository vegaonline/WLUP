/*
 * JWLUP - Java Based WLUP
 * Developed by Abhijit Bhattacharyya,
 * Nuclear Data Physics Centre of India (NDPCI),
 *  Bhabha Atomic Research Centre, Mumbai, 400 085, INDIA
 */
package wlup;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author vega
 */
public class matData {
    private final SimpleStringProperty matID;
    private final SimpleStringProperty matSymbol;
    private final SimpleStringProperty matFlag;
    private final SimpleStringProperty matName;
    
    public matData(String id, String symbol, String flag, String name){
        this.matID = new SimpleStringProperty(id);
        this.matSymbol = new SimpleStringProperty(symbol);
        this.matFlag = new SimpleStringProperty(flag);
        this.matName = new SimpleStringProperty(name);
    }
    
    public String getID() {
        return matID.get ();
    }
    
    public void setID(String id){
        matID.set (id);
    }
    
    public String getSymbol() {
        return matSymbol.get ();
    }
    
    public void setSymbol(String symbol) {
        matSymbol.set (symbol);
    }
    
    public String getFlag(){
        return matFlag.get ();
    }
    
    public void setFlag(String flag) {
        matFlag.set (flag);
    }
    
    public String getName(){
        return matName.get ();
    }
    
    public void setName(String name) {
        matName.set (name);
    }    
}
