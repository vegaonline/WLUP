/*
 * INDEXFOR - Indian EXFOR Compiler
 * Developed by Abhijit Bhattacharyya,
 * Nuclear Data Physics Centre of India (NDPCI),
 *  Bhabha Atomic Research Centre, Mumbai, 400 085, INDIA
 */
package wlup;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;

/**
 *
 * @author vega
 */
public class nuclide {

    private final SimpleStringProperty nucName;
    private int NINT;
    private final ObservableList<Float> abs = FXCollections.
            observableArrayList ();
    private final ObservableList<Float> trans = FXCollections.
            observableArrayList ();
    private final ObservableList<Float> fis = FXCollections.
            observableArrayList ();
    private final ObservableList<Float> nufis = FXCollections.
            observableArrayList ();
    private final ObservableList<Float> scat = FXCollections.
            observableArrayList ();
    private final ObservableList<Float> temp = FXCollections.
            observableArrayList ();

    public nuclide(
            String name, int nintData, Float absData, Float transData,
            Float fisData, Float nufisData, Float scatData, Float tempData
    ) {
        this.nucName = new SimpleStringProperty (name);
        this.NINT = nintData;
        this.abs.add (absData);
        this.trans.add (transData);
        this.fis.add (fisData);
        this.nufis.add (nufisData);
        this.scat.add (scatData);
        this.temp.add (tempData);
    }

    public String getName() {
        return nucName.get ();
    }

    public void setName(String name) {
        nucName.set (name);
    }

    public int getNINT() {
        return this.NINT;
    }

    public void setNINT(int nint) {
        this.NINT = nint;
    }

    public ObservableList<Float> getABS() {
        return this.abs;
    }

    public void addABS(Float abs) {
        this.abs.add (abs);
    }

    public ObservableList<Float> getTRANS() {
        return this.trans;
    }

    public void addTRANS(Float trans) {
        this.trans.add (trans);
    }

    public ObservableList<Float> getFIS() {
        return this.fis;
    }

    public void addFIS(Float fis) {
        this.fis.add (fis);
    }

    public ObservableList<Float> getNUFIS() {
        return this.nufis;
    }

    public void addNUFIS(Float nufis) {
        this.nufis.add (nufis);
    }

    public ObservableList<Float> getSCAT() {
        return this.scat;
    }

    public void addSCAT(Float scat) {
        this.scat.add (scat);
    }

    public ObservableList<Float> getTEMP() {
        return this.temp;
    }

    public void addTEMP(Float temp) {
        this.temp.add (temp);
    }
}
