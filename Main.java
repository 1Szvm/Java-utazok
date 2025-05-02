import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.SplittableRandom;
import java.util.TreeMap;

public class Main {
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

    public Main(){
        betolt("utazok.csv");
        //0.feladat
        System.out.printf("0) %d utazó adata beolvasva\n",list.size());

        //1.feladat
        TreeMap<String,Integer> varosok= new TreeMap<>();
        for(Utazas ut:list){
            if(!varosok.containsKey(ut.varos)){
                varosok.put(ut.varos,1);
            }else{
                varosok.put(ut.varos,varosok.get(ut.varos)+1);
            }
        }
        System.out.printf("1) Összesen %d városba utaztak\n",varosok.size());

        String randCity=list.get((int)(Math.random()*list.size())).varos;
        System.out.printf("   Közülük egy véletlenszerűen kiválasztott: %s\n",randCity);
        System.out.printf("   Ebbe a városba %d utazó érkezett\n",varosok.get(randCity));

        //2.feladat
        String earlyest=list.get(0).indulas;
        for(Utazas ut:list){
            String[] max=earlyest.split(":");
            String[] next=ut.indulas.split(":");
            if(Integer.parseInt(max[0])>=Integer.parseInt(next[0])&&Integer.parseInt(max[1])>Integer.parseInt(next[1])){
                earlyest=ut.indulas;
            }
        }
        System.out.printf("Legkorábbi indulás: %s\n",earlyest);

        int countMorningRouts=0;
        for(Utazas ut:list){
            if (Integer.parseInt(ut.indulas.split(":")[0])<12) countMorningRouts++;
        }
        System.out.printf("Összesen %d utazás kezdődött délelőtt\n",countMorningRouts);

        //3.faladat
        TreeMap<String,Integer> routsPerMonth= new TreeMap<>();
        for(Utazas ut:list){
            String month=ut.daum.split("\\.")[0];
            if(!routsPerMonth.containsKey(month)){
                routsPerMonth.put(month,1);
            }else{
                routsPerMonth.put(month,routsPerMonth.get(month)+1);
            }
        }
        System.out.println("3) Utazások száma hónaponként:");
        for (String month:routsPerMonth.keySet()){
            System.out.printf("   %s.hó : %d utazás\n",month,routsPerMonth.get(month));
        }

        //4.faladat
        TreeMap<String,Integer> lastNames= new TreeMap<>();
        for(Utazas ut:list){
            String last=ut.nev.split(" ")[0];
            if(!lastNames.containsKey(last)){
                lastNames.put(last,1);
            }else{
                lastNames.put(last,lastNames.get(last)+1);
            }
        }
        System.out.println("4) Többször szereplő vezetéknevek:");
        System.out.print("   ");
        for (String lastName:lastNames.keySet()){
            if(lastNames.get(lastName)>1){
                System.out.printf("%s ",lastName);
            }
        }
        System.out.println();

        //5.faladat
        TreeMap<String,Integer> routPerDay= new TreeMap<>();
        for(Utazas ut:list){
            if(!routPerDay.containsKey(ut.daum)){
                routPerDay.put(ut.daum,1);
            }else{
                routPerDay.put(ut.daum,routPerDay.get(ut.daum)+1);
            }
        }

        System.out.print("5) Ugyanazon a napon kettőnél több utazás: ");
        for (String day:routPerDay.keySet()){
            if(routPerDay.get(day)>2){
                System.out.printf("%s(%d) ",day,routPerDay.get(day));
            }
        }
        System.out.print("");

        //7.feldat
        PrintWriter ki = null;
        try{
            ki = new PrintWriter(new File("szeged.txt"),"utf-8");
            for(Utazas ut:list){
                if(ut.varos.equals("Szeged")){
                    ki.printf("%s (%s %s)\r\n",ut.nev,ut.daum,ut.indulas);
                }
            }
        }catch (UnsupportedEncodingException e){
            System.out.println(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if(ki !=null) ki.close();
        }
        System.out.print("6) Szegedre utazók kiírva a szeged.txt fájlba");
    }

    private void betolt(String FajNev){
        Scanner be = null;
        try{
            be = new Scanner(new File(FajNev),"utf-8");
            while (be.hasNextLine()){
                list.add(new Utazas(be.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(be != null) be.close();
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}