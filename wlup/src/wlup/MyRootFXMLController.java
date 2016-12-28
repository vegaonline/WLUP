/*
 * JWLUP - Java based WLUP
 * Developed by Abhijit Bhattacharyya,
 * Nuclear Data Physics Centre of India (NDPCI),
 *  Bhabha Atomic Research Centre, Mumbai, 400 085, INDIA
 */
package wlup;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * FXML Controller class
 *
 * @author vega
 */
public class MyRootFXMLController implements Initializable {

    // Variables etc.
    // File related
    JFileChooser fileChooser = new JFileChooser();
    private File WIMSFileRead;
    private File MATFileRead;
    private File WIMSFileWrite;
    private File matFileWrite;
    private File recordsDir;
    private String dataPathDir = "../DATA/matList_and_WIMSDLIST";
    private String fNameWIMS = "";
    private String fNameMAT = "";
    private Integer filemark = 999999999;

    // Data Related
    int nucDataNum = 0;
    private final ObservableList<matData> nucData
            = FXCollections.observableArrayList();

    //UI related
    private final Stage myDialog = new Stage();
    private Scene myDialogScene;

    // Parameter for WIMS
    int NEL, NG, NG0, NG1, NG2, NG3, NG12, NNFD, NNFPD;
    int NDAT1, NDAT2;
    int IDINT, IZ, NF, NT, NR;
    Double AW;
    private final ObservableList<Integer> wimsID
            = FXCollections.observableArrayList();
    private final ObservableList<Double> wimsEG
            = FXCollections.observableArrayList();
    private final ObservableList<Double> wimsCHI
            = FXCollections.observableArrayList();
    private final ObservableList<Double> wimsMTF
            = FXCollections.observableArrayList();
    private final ObservableList<Integer> wimsYLD
            = FXCollections.observableArrayList();
    private final ObservableList<Double> wimsXP
            = FXCollections.observableArrayList();
    private final ObservableList<Double> wimsXX
            = FXCollections.observableArrayList();
    private final ObservableList<Double> wimsXT
            = FXCollections.observableArrayList();
    private final ObservableList<Double> wimsXA
            = FXCollections.observableArrayList();
    private final ObservableList<Double> wimsXQ
            = FXCollections.observableArrayList();
    private final ObservableList<Double> wimsGL
            = FXCollections.observableArrayList();
    private final ObservableList<Double> wimsXF
            = FXCollections.observableArrayList();
    private final ObservableList<Double> wimsVF
            = FXCollections.observableArrayList();
    private final ObservableList<Double> wimsXSCF
            = FXCollections.observableArrayList();

    // FXML elements
    @FXML
    private BorderPane rootPane;
    @FXML
    private MenuBar rootMenuBar;
    @FXML
    private Menu rootMenuFile;
    @FXML
    private MenuItem rootMenuFileNew;
    @FXML
    private MenuItem rootMenuFileSave;
    @FXML
    private MenuItem rootMenuFileClose;
    @FXML
    private Menu rootMenuPlot;
    @FXML
    private MenuItem rootMenuPlotPlot;
    @FXML
    private MenuItem rootPlotChLib;
    @FXML
    private MenuItem rootMenuPlotDelete;
    @FXML
    private Menu rootMenuHelp;
    @FXML
    private MenuItem rootMenuHelpAbout;
    @FXML
    private ToolBar toolBar;
    @FXML
    private Label matLBotTool;
    @FXML
    private ComboBox<String> matCBBotTool = new ComboBox<String>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fNameMAT
                = "/home/vega/NetBeansProjects/WLUP/DATA/matList_and_WIMSDLIST/endfb6.lst";
        fNameWIMS
                = "/home/vega/NetBeansProjects/WLUP/DATA/matList_and_WIMSDLIST/endfb6.lib";
        readLibs();
        initArray2zero();
    }

    // To Quit to the System
    @FXML
    private void goExit(ActionEvent event) {
        System.gc();
        System.exit(0);
    }

    // Start a new session
    @FXML
    private void doStartNew(ActionEvent event) {
        setDefaultDirExt(dataPathDir);
        loadLibs(); // select and Load the data files
        readLibs(); // read the already selected data file
    }

    // Select material from the bottom toolBar
    @FXML
    private void doSelectMat(ActionEvent event) {
    }

    // methods
    // Initialize arrays to zeros
    private void initArray2zero() {
        wimsID.setAll(0);
        wimsYLD.setAll(0);
        wimsEG.setAll(Double.parseDouble("0.0"));
        wimsCHI.setAll(Double.parseDouble("0.0"));
        wimsMTF.setAll(Double.parseDouble("0.0"));
        wimsXP.setAll(Double.parseDouble("0.0"));
        wimsXX.setAll(Double.parseDouble("0.0"));
        wimsXT.setAll(Double.parseDouble("0.0"));
        wimsXA.setAll(Double.parseDouble("0.0"));
        wimsXQ.setAll(Double.parseDouble("0.0"));
        wimsGL.setAll(Double.parseDouble("0.0"));
    }

    /*
     * Read WIMS and material libraries @author Abhijit Bhattacharyya
     */
    private void loadLibs() {
        int ret = 0;
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);

        // Load Material Library ************        
        fileChooser.setDialogTitle("Load Material List library");
        FileNameExtensionFilter matLibFilter = new FileNameExtensionFilter(
                "Material Libraries (*.lst)", "lst"
        ); // add filters
        fileChooser.addChoosableFileFilter(matLibFilter);
        fileChooser.setFileFilter(matLibFilter);
        ret = fileChooser.showOpenDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION) {
            MATFileRead = fileChooser.getSelectedFile();
        }
        if (!MATFileRead.exists()) {
            System.gc();
            System.out.println("File does not exists");
            System.exit(99); // file read error
        }
        fNameMAT = MATFileRead.getAbsolutePath().toString();

        // SETUP corresponding WIMS Library **********        
        fNameWIMS = fNameMAT.substring(0, fNameMAT.length() - 3) + "lib";
        System.out.println("WIMS->" + fNameWIMS);
        System.out.println("MAT->" + fNameMAT);
    }

    // Setting the default  Directory        
    private void setDefaultDirExt(String fPath) {
        recordsDir = new File(fPath);
        if (!recordsDir.exists()) {
            recordsDir.mkdirs();
        }
        fileChooser.setCurrentDirectory(recordsDir);
    }

    // Read already loaded data files
    private void readLibs() {
        BufferedReader brWIMS;
        BufferedReader brMAT;
        int i = 0;
        int j = 0;
        int k = 0;
        int l = 0;

        // first Loading Material file
        try {
            brMAT = new BufferedReader(new FileReader(fNameMAT));
            String line;
            while ((line = brMAT.readLine()) != null) {
                String[] fRead;
                if (line.length() > 3) {
                    fRead = line.split("\\s+");
                    nucData.add(nucDataNum++,
                            new matData(fRead[0], fRead[1],
                                    fRead[2], fRead[3]));

                }
            }
            brMAT.close();
            System.gc();
            System.out.println(fNameMAT + " file reading successful....");
        } catch (Exception e) {
            System.out.println("File " + fNameMAT + " reading error");
            System.exit(99);
        }

        // Loading the WIMS file
        try {
            brWIMS = new BufferedReader(new FileReader(fNameWIMS));
            String line;
            int lCount = 0;
            while ((line = brWIMS.readLine()) != null) {
                String[] fRead = line.split("\\s+");
                int fReadLen = fRead.length;
                if (lCount++ > 1032) {
                    System.out.println("# " + lCount + "->" + line);
                }
                if (i == 0) {
                    NEL = Integer.parseInt(fRead[1]);
                    NG = Integer.parseInt(fRead[2]);
                    NG0 = Integer.parseInt(fRead[3]);
                    NG1 = Integer.parseInt(fRead[4]);
                    NG2 = Integer.parseInt(fRead[5]);
                    NG12 = NG1 + NG2;
                    for (int ii = 0; ii < NG1; ii++) {
                        wimsXP.add(Double.parseDouble("0.0"));
                        wimsXX.add(Double.parseDouble("0.0"));
                        wimsXQ.add(Double.parseDouble("0.0"));
                        wimsGL.add(Double.parseDouble("0.0"));
                    }
                    ++i;
                } else if (i == 1) {
                    NG3 = Integer.parseInt(fRead[1]);
                    NNFD = Integer.parseInt(fRead[2]);
                    NNFPD = Integer.parseInt(fRead[3]);
                    ++i;
                } else if (i == 2) {
                    if (j <= NEL) { // carry on till ID(NEL)
                        for (int ii = 1; ii <= fReadLen - 1; ii++) {
                            wimsID.add(Integer.parseInt(fRead[ii]));
                        }
                    }
                    j += (fReadLen - 1); // add length for each slot of 5 iints
                    if (j == NEL) { // when NEL IDs read, go to next var
                        j = 0; // initialize to zero
                        ++i;
                    }
                } else if (i == 3) {
                    if (j <= NG + 1) { // till EG(NG+1)
                        for (int ii = 1; ii <= fReadLen - 1; ii++) {
                            wimsEG.add(Double.parseDouble(fRead[ii]));
                        }
                    }
                    j += (fReadLen - 1);
                    if (j == NG + 1) {
                        j = 0;
                        ++i;
                    }
                } else if (i == 4) {
                    if (j <= NG0) {
                        for (int ii = 1; ii <= fReadLen - 1; ii++) {
                            wimsCHI.add(Double.parseDouble(fRead[ii]));
                        }
                    }
                    j += (fReadLen - 1);
                    if (j == NG0) {
                        j = 0;
                        ++i;
                    }
                } else if (i == 5) {
                    if (j <= NEL && NDAT1 != filemark) {
                        switch (fReadLen) {
                            case 2:
                                NDAT1 = Integer.parseInt(fRead[1]);
                                break;
                            case 3:
                                if (NDAT1 == 2) {
                                    for (int ii = 1; ii <= NDAT1 / 2; ii++) {
                                        wimsMTF.add(
                                                Double.parseDouble(fRead[1]));
                                        wimsYLD.add(
                                                Integer.parseInt(fRead[2]));
                                    }
                                    j++;
                                }
                                if (NDAT1 == 8) {
                                    wimsMTF.add(Double.parseDouble(fRead[1]));
                                    wimsYLD.add(Integer.parseInt(fRead[2]));
                                    j++;
                                }
                                break;
                            case 7:
                                if (NDAT1 <= 8) {
                                    for (int ii = 1; ii <= NDAT1 - 2; ii += 2) {
                                        wimsMTF.add(
                                                Double.parseDouble(fRead[ii]));
                                        wimsYLD.add(
                                                Integer.parseInt(fRead[ii + 1]));
                                    }
                                } else {
                                    for (int ii = 1; ii <= 6;
                                            ii += 2) {
                                        wimsMTF.add(
                                                Double.parseDouble(fRead[ii]));
                                        wimsYLD.add(
                                                Integer.parseInt(fRead[ii + 1]));
                                    }
                                }
                        }
                        if (NDAT1 == filemark) {
                            j = 0;
                            ++i;
                        }
                    }
                } else if (i == 6) {  // CS series 
                    IDINT = Integer.parseInt(fRead[1]);
                    AW = Double.parseDouble(fRead[2]);
                    IZ = Integer.parseInt(fRead[3]);
                    NF = Integer.parseInt(fRead[4]);
                    NT = Integer.parseInt(fRead[5]);
                    NR = Integer.parseInt(fRead[6]);
                    j = 0;
                    k = 0;
                    ++i;
                } else if (i == 7) {
                    switch (j) {
                        case 0:
                            for (int ii = 1; ii <= 5; ii++) {
                                int jj = NG1 + (ii + k * 5) - 1;
                                wimsXP.add(jj, Double.parseDouble(fRead[ii]));
                            }
                            if (NG2 - (k + 1) * 5 > 5) {
                                ++k;
                            } else {
                                ++j;
                                ++k;
                            }
                            break;
                        case 1:
                            if (k != 0) {
                                for (int ii = 1; ii <= 3; ii++) {
                                    int jj = NG1 + (ii + k * 5) - 1;
                                    wimsXP.add(jj, Double.parseDouble(
                                            fRead[ii]));
                                }
                            }
                            k = 0;
                            for (int ii = 4; ii <= 5; ii++) {
                                int kk = NG1 + (ii - 3) - 1;
                                wimsXX.add(kk, Double.parseDouble(fRead[ii]));
                            }
                            ++i;
                            j = 0;
                            break;
                    }
                } else if (i == 8) {
                    switch (j) {
                        case 0:
                            for (int ii = 1; ii <= 5; ii++) {
                                int jj = (NG1 + 2) + (ii + k * 5) - 1;
                                wimsXX.add(jj, Double.parseDouble(fRead[ii]));
                            }
                            if (NG2 - (k + 1) * 5 > 5) {
                                ++k;
                            } else {
                                ++j;
                                ++k;
                            }
                            break;
                        case 1:
                            if (k != 0) {
                                int jj = (NG1 + 2) + (1 + k * 5) - 1;
                                wimsXX.add(jj, Double.parseDouble(fRead[1]));
                                k = 0;
                            }
                            if (k == 0) {
                                wimsXT.add(Double.valueOf(fRead[2]));
                                wimsXT.add(Double.valueOf(fRead[3]));
                                wimsXT.add(Double.valueOf(fRead[4]));
                                wimsXT.add(Double.valueOf(fRead[5]));
                            }
                            ++i;
                            k = 0;
                            j = 0;
                            break;
                    }
                } else if (i == 9) {
                    switch (j) {
                        case 0:
                            for (int ii = 1; ii <= 5; ii++) {
                                int jj = 3 + ii + (k * 5);
                                wimsXT.add(jj, Double.valueOf(fRead[ii]));
                            }
                            if (NG12 - 4 - (k + 1) * 5 > 5) {
                                ++k;
                            } else {
                                ++j;
                                ++k;
                            }
                            break;
                        case 1:
                            if (k != 0) {
                                for (int ii = 1; ii <= 3; ii++) {
                                    int jj = (NG12 - 4) + ii;
                                    wimsXT.add(jj,
                                            Double.valueOf(fRead[ii]));
                                }
                                k = 0;
                            }
                            if (k == 0) {
                                for (int ii = 4; ii <= 5; ii++) {
                                    wimsXA.add(Double.valueOf(fRead[ii]));
                                    //System.out.println (wimsXA.get (ii - 4));
                                }
                            }
                            ++i;
                            j = 0;
                            break;
                    }
                } else if (i == 10) {
                    switch (j) {
                        case 0:
                            for (int ii = 1; ii <= 5; ii++) {
                                int jj = 1 + ii + (k * 5);
                                wimsXA.add(jj, Double.valueOf(fRead[ii]));
                            }
                            if (NG12 - 2 - (k + 1) * 5 >= 5) {
                                ++k;
                            } else if (NG12 - 2 - (k + 1) * 5 == 0) {
                                ++i;
                                j = 0;
                                k = 0;
                            } else {
                                ++j;
                                ++k;
                            }
                            break;

                        case 1:
                            ++i;
                            j = 0;
                            k = 0;
                            break;
                    }
                } else if (i == 11) {
                    switch (j) {
                        case 0:
                            for (int ii = 1; ii <= 5; ii++) {
                                int jj = NG1 + (ii + k * 5) - 1;
                                wimsXQ.add(jj, Double.parseDouble(fRead[ii]));
                            }
                            if (NG2 - (k + 1) * 5 > 5) {
                                ++k;
                            } else {
                                ++j;
                                ++k;
                            }
                            break;
                        case 1:
                            if (k != 0) {
                                for (int ii = 1; ii <= 3; ii++) {
                                    int jj = NG1 + (ii + k * 5) - 1;
                                    wimsXQ.add(jj, Double.parseDouble(
                                            fRead[ii]));
                                }
                            }
                            k = 0;
                            for (int ii = 4; ii <= 5; ii++) {
                                int jj = NG1 + (ii - 3) - 1;
                                wimsGL.add(jj, Double.parseDouble(fRead[ii]));
                            }
                            ++i;
                            j = 0;
                            break;
                    }
                } else if (i == 12) {
                    switch (j) {
                        case 0:
                            for (int ii = 1; ii <= 5; ii++) {
                                int jj = (NG1 + 2) + (ii + k * 5) - 1;
                                wimsGL.add(jj, Double.parseDouble(fRead[ii]));
                                System.out.println(jj + " " + k + " GL->"
                                        + wimsGL.get(jj));
                            }
                            if (NG2 - (k + 1) * 5 > 5) {
                                ++k;
                            } else {
                                ++j;
                                ++k;
                            }
                            break;
                        case 1:
                            if (k != 0) {
                                int jj = (NG1 + 2) + (1 + k * 5) - 1;
                                wimsGL.add(jj, Double.parseDouble(fRead[1]));
                                System.out.println(jj + " " + k + " GL->"
                                        + wimsGL.get(jj));
                                k = 0;
                            }
                            ++i;
                            j = 0;
                            break;
                    }
                } else if (i == 13) {
                    NDAT2 = Integer.parseInt(fRead[1]);
                    ++i;
                    j = 0;
                    k = 0;
                } else if (i == 14) {
                    switch (j) {
                        case 0:
                            for (int ii = 1; ii <= 5; ii++) {
                                int jj = ii + k * 5 - 1;  // check for all prior data 
                                wimsXSCF.add(jj, Double.parseDouble(fRead[ii]));
                            }
                            if (NDAT2 - (k + 1) * 5 > 5) {
                                ++k;
                            } else {
                                //++k; <- check for all priot  data
                                ++j;
                            }
                            break;
                        case 1:
                            if (k != 0) {
                                for (int ii = 1; ii <= 4; ii++) {
                                    int jj = ii + (k + 1) * 5 - 1;
                                    wimsXSCF.add(jj, Double.parseDouble(fRead[ii]));
                                }
                                k = 0;
                            }
                            j = 0;
                            ++i;
                            break;
                    }
                } else if (i == 15) {

                }
            }
            brWIMS.close();
            System.gc();
            System.out.println(fNameWIMS + " file reading successful...."
                    + i + " " + j);
        } catch (Exception e) {
            System.out.println("File " + fNameWIMS + " Reading error "
                    + i + " " + j + " " + NEL);
            System.exit(99);
        }

        // Loading the bottom toolbar ComboBox
        matCBBotTool.getItems()
                .clear();
        for (matData myData : nucData) {
            String tmp = myData.getSymbol().toString()
                    + "  ("
                    + myData.getName().toString() + ")";
            matCBBotTool.getItems().add(tmp);
        }
    }

} // *********  END OF CONTROLLER
