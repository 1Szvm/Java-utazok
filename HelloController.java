package com.example.utazokgui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class HelloController {

    @FXML private ListView<String> lsVarosok;
    @FXML private ListView<String> lsUtazasok;

    private FileChooser fc = new FileChooser();

    public void initialize(){
        fc.setInitialDirectory(new File("./"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Fájlok","*.csv"));
    }


    @FXML private void onMegnyitasClick(){
        File fbe=fc.showOpenDialog(lsVarosok.getScene().getWindow());
        if(fbe!=null){
            list.clear();
            lsVarosok.getItems().clear();
            betolt(fbe);
            TreeSet<String> varosok = new TreeSet<>();
            for(Utazas ut:list){
                varosok.add(ut.varos);
            }
            for (String varos:varosok){
                lsVarosok.getItems().add(varos);
            }
        }
    }

    public void renderUtazasok(){
        lsUtazasok.getItems().clear();
        for(Utazas ut:list){
            if (lsVarosok.getSelectionModel().getSelectedItem().equals(ut.varos)){
                lsUtazasok.getItems().add(String.format("%s (%s %s)",ut.nev,ut.daum,ut.indulas));
            }
        }
    }

    private void betolt(File Fajl){
        Scanner be = null;
        try{
            be= new Scanner(Fajl,"utf-8");
            while (be.hasNextLine()){
                list.add(new Utazas(be.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(be != null) be.close();
        }
    }

    public class Utazas{
        String nev;
        String varos;
        String daum;
        String indulas;

        public Utazas(String sor) {
            String[] s=sor.split(";");
            this.nev = s[0];
            this.varos =s[1];
            this.daum = s[2];
            this.indulas = s[3];
        }

    }

    public ArrayList<Utazas> list = new ArrayList<>();

    @FXML private void onKilépesClick(){
        Platform.exit();
    }

    @FXML private void onNevjegyzekClikc(){
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Névjegy");
        info.setHeaderText(null);
        info.setContentText("Utazók v1.0.0\n(C) Kandó");
        info.showAndWait();
    }
}