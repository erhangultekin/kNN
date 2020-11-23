
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class kNN {
    private static final ArrayList<CiceklerNesne> TumCicekler=new ArrayList<CiceklerNesne>();
    private static final ArrayList<CiceklerNesne> İslenmemisVeri=new ArrayList<CiceklerNesne>();
    private static final ArrayList<String> satirlarDosyasi=new ArrayList<String>();
    public static int k ;
    public static int ilkDosyaBoyu;
    public static int no=0; //Örnek No temsil eder.
    public static void main(String[] args) throws FileNotFoundException, IOException {
            BufferedReader br=new BufferedReader(new FileReader("CiceklerDB.txt"));
            String lines;
            
            while(null!=(lines=br.readLine())){
                satirlarDosyasi.add(lines);
                String [] LineList=lines.split(",");  //Her Satırdaki Çiçek Verilerini tutar.
                CiceklerNesne Cicek=new CiceklerNesne(no,Double.parseDouble(LineList[0]),Double.parseDouble(LineList[1]),
                        Double.parseDouble(LineList[2]), Double.parseDouble(LineList[3]));
                Cicek.setTur(LineList[4]);
                TumCicekler.add(Cicek); //Tüm Çiçeklerin tutulduğu geniş liste
                İslenmemisVeri.add(Cicek); //Verilerle işlem yapılırken kullanılacak olan işlem görmemiş liste
                no++; 
            }
            ilkDosyaBoyu=satirlarDosyasi.size();
            br.close();
            Scanner sc=new Scanner(System.in);
            System.out.println("Hangi özelliklere göre yakınlık testi yapılsın: \n"
                    + "1)Çanak Yaprak \n"
                    + "2)Taç Yaprak \n"
                    + "3)Hepsi \n");
            int option=sc.nextInt();
            System.out.println("K gir ");
            k=sc.nextInt();
            CiceklerNesne yeniCicek = new CiceklerNesne(no);
            if(option==1){
                System.out.println("Çanak Yaprak Uzunluğu gir ");
                double CanakYU=sc.nextDouble();
                System.out.println("Çanak Yaprak Genişliği gir ");
                double CanakYG=sc.nextDouble();
                yeniCicek.setCanakYG(CanakYG);
                yeniCicek.setCanakYU(CanakYU);
                DistanceCanak(CanakYU,CanakYG,TumCicekler);
            }
            else if(option==2) {   
                System.out.println("Taç Yaprak Uzunluğu gir ");
                double TacYU=sc.nextDouble();
                System.out.println("Taç Yaprak Genişliği ");
                double TacYG=sc.nextDouble();
                yeniCicek.setTacYG(TacYG);
                yeniCicek.setTacYU(TacYU);
                DistanceTac(TacYU, TacYG, TumCicekler);
            }
            else {
                System.out.println("Çanak Yaprak Uzunluğu gir ");
                double CanakYU=sc.nextDouble();
                System.out.println("Çanak Yaprak Genişliği gir ");
                double CanakYG=sc.nextDouble();
                System.out.println("Taç Yaprak Uzunluğu gir ");
                double TacYU=sc.nextDouble();
                System.out.println("Taç Yaprak Genişliği ");
                double TacYG=sc.nextDouble();
                yeniCicek.setTacYG(TacYG);
                yeniCicek.setTacYU(TacYU);
                yeniCicek.setCanakYG(CanakYG);
                yeniCicek.setCanakYU(CanakYU);
                DistanceHesapla(CanakYU, CanakYG, TacYU, TacYG,TumCicekler);
            }
            
            Sırala(TumCicekler);
            String tur=TurHesapla(k,TumCicekler);
            yeniCicek.setTur(tur);
            
            System.out.println("EN YAKIN k ADET BİTKİ VE ÖZELLİKLERİ: ");
            for(int j=0;j<k;j++){  
                System.out.println(TumCicekler.get(j));
                
            }
            
            no++;
            System.out.println("YENİ BİTKİNİN ÖZELLİKLERİ VE TAHMİN EDİLMİŞ TÜRÜ ");
            System.out.println(yeniCicek);
            
            BasariHesapla();
            
            
            Scanner sc3=new Scanner(System.in);
            System.out.println("Silme işlemi yapmak istiyor musunuz? Evet e/E Hayır h/H");
            String cevap=sc3.nextLine();
            if ((cevap.equals("e")) || (cevap.equals("E"))){
                System.out.println("Hepsini mi yoksa birini mi silmek istiyorsunuz? Tümü T/t Biri B/b");
                String cevap1=sc3.nextLine();
                if((cevap1.equals("T")) || (cevap1.equals("t"))){
                    HepsiniSil();
                    System.out.println("Tüm veriler silinmiştir.");
                }
                else if((cevap1.equals("B")) || (cevap1.equals("b"))){
                    Sil();
                    System.out.println("İndeksi verilen veri silinmiştir.");       
                }
            }
            System.out.println("Ekleme işlemi yapmak istiyor musunuz? Evet e/E Hayır h/H");
            String cevap2=sc3.nextLine();
            if (cevap2.equals("e") || cevap2.equals("E")){
                Ekle();
                System.out.println("Dosyaya veri ekleme işleminiz tamamlanmıştır.");
            }
    }
    

    public static void DistanceHesapla(double CanakYU,double CanakYG,double TacYU,double TacYG,ArrayList<CiceklerNesne> liste){
        for(CiceklerNesne i : liste){
                double distance1=(double) Math.pow((CanakYU-i.getCanakYU()),2)+
                        (double) Math.pow((CanakYG-i.getCanakYG()),2)+
                        (double) Math.pow((TacYU    -i.getTacYU()),2)+
                        (double) Math.pow((TacYG-i.getTacYG()),2);
                double distance=Math.sqrt(distance1);
                i.setDistance(distance);    
                //System.out.println(i.getDistance());
            }
    }
    public static void DistanceCanak(double CanakYU, double CanakYG,ArrayList<CiceklerNesne> liste){
        for(CiceklerNesne i: liste){
            double distance=(double) Math.pow((CanakYU-i.getCanakYU()),2)+
                    (double) Math.pow((CanakYG-i.getCanakYG()), 2);
            distance=Math.sqrt(distance);
            i.setDistance(distance);
        }
    }
    public static void DistanceTac(double TacYU, double TacYG,ArrayList<CiceklerNesne> liste){
        for(CiceklerNesne i: liste){
            double distance= (double) Math.pow((TacYU-i.getTacYU()),2)+
                        (double) Math.pow((TacYG-i.getTacYG()),2);
            distance=Math.sqrt(distance);
            i.setDistance(distance);
        } 
    }
    
    public static void Sırala(ArrayList<CiceklerNesne> liste){
        CiceklerNesne tmp;
            for (int i=0; i<liste.size();i++){
                for(int j=1;j<(liste.size()-i);j++){
                    if (liste.get(j-1).getDistance()>liste.get(j).getDistance()){
                        tmp=liste.get(j-1);
                        liste.set(j-1, liste.get(j));
                        liste.set(j, tmp);
                    }
                }
            }
    }
    
    public static String TurHesapla(int k , ArrayList<CiceklerNesne> liste){
            String tur;
            ArrayList <CiceklerNesne> Setosa=new ArrayList<>();
            ArrayList <CiceklerNesne> Versicolor=new ArrayList<>();
            ArrayList <CiceklerNesne> Virginica=new ArrayList<>();
            for(int a=0;a<k;a++){
                if ("Iris-setosa".equals(liste.get(a).getTur())){
                    Setosa.add(liste.get(a));
                }
                else if("Iris-versicolor".equals(liste.get(a).getTur())){
                    Versicolor.add(liste.get(a));
                }
                else {
                    Virginica.add(liste.get(a));
                }
            }
            int x=Setosa.size();
            int y=Versicolor.size();
            int z=Virginica.size();
            
            if (x>y && x>z){
                tur="Iris-setosa";
            }
            else if (y>x && y>z){
                tur="Iris-versicolor";
            }
            else if (z>x && z>y){
                tur="Iris-virginica";
            }
            else{
                tur=liste.get(0).getTur();
            }
            /*
            else if (x==y){
                if(Setosa.get(0).getDistance()<Versicolor.get(0).getDistance()){
                    tur="Iris-setosa";
                }
                tur="Iris-versicolor";
            }
            else if(x==z){
                if(Setosa.get(0).getDistance()<Virginica.get(0).getDistance()){
                    tur="Iris-setosa";
                }
                tur="Iris-virginica";
            }
            else if(z==y){
                if(Versicolor.get(0).getDistance()<Virginica.get(0).getDistance()){
                    tur="Iris-versicolor";
                }
                tur="Iris-virginica";
            }
            else {
                if(Setosa.get(0).getDistance()<Versicolor.get(0).getDistance() &&
                    Setosa.get(0).getDistance()<Virginica.get(0).getDistance()    ){
                    tur="Iris-setosa";
                }
                else if(Versicolor.get(0).getDistance()<Setosa.get(0).getDistance() &&
                        Versicolor.get(0).getDistance()<Virginica.get(0).getDistance()){
                    tur="Iris-versicolor";
                }
                else {
                    tur="Iris-virginica";
                }
                
            }*/
            return tur;
    }
    
    public static void BasariHesapla(){
        ArrayList <CiceklerNesne> SetosaList=new ArrayList<>();
        ArrayList <CiceklerNesne> VersicolorList=new ArrayList<>();
        ArrayList <CiceklerNesne> VirginicaList=new ArrayList<>();
        ArrayList <CiceklerNesne> EksikListe= İslenmemisVeri;
        
        for(CiceklerNesne i : İslenmemisVeri){
            if(i.getTur().equals("Iris-setosa")){
                SetosaList.add(i);
            }
            else if(i.getTur().equals("Iris-versicolor")){
                VersicolorList.add(i);
            }
            else{
                VirginicaList.add(i);
            }
        }
        
        ArrayList <CiceklerNesne> TestVerileri=new ArrayList<>();
        
        for(int i=SetosaList.size(); SetosaList.size()-10<i;i--){
            TestVerileri.add(SetosaList.get(i-1));
            EksikListe.remove(SetosaList.get(i-1));
            
        }
        for(int j=VersicolorList.size(); VersicolorList.size()-10<j;j--){
            TestVerileri.add(VersicolorList.get(j-1));
            EksikListe.remove(VersicolorList.get(j-1));
        }
        for(int a=VirginicaList.size(); VirginicaList.size()-10<a;a--){
            TestVerileri.add(VirginicaList.get(a-1));
            EksikListe.remove(VirginicaList.get(a-1));
        }
        
        for(CiceklerNesne p : TestVerileri){
            if(p.getCanakYG()==0.0d && p.getCanakYU()==0.0d){
                DistanceTac(p.getTacYU(), p.getTacYG(), EksikListe);
            }
            else if(p.getTacYG()==0.0d && p.getTacYU()==0.0d){
                DistanceCanak(p.getCanakYU(), p.getCanakYG(), EksikListe);
            }
            else {
                DistanceHesapla(p.getCanakYU(), p.getCanakYG(),p.getTacYU(), p.getTacYG(),EksikListe);
            }
            
            Sırala(EksikListe);
            p.setYeniTur(TurHesapla(k, EksikListe));
            
            System.out.println("Test Sonucu Tür : " + p.getYeniTur());
            System.out.println("Testten Önceki Tür : " + p.getTur());
            System.out.println("--------------------------------------"); 
        }
        double dogruTurSayisi=0;
        for(CiceklerNesne z : TestVerileri){
            if(z.getTur().equals(z.getYeniTur())){
                dogruTurSayisi++;
            }
        }
        double basariOrani;
        basariOrani=((double)dogruTurSayisi/TestVerileri.size());
        System.out.println("Başarı Oranı : " +basariOrani);
    }
    
    public static void Sil() throws IOException{
        Scanner sc=new Scanner(System.in);
        System.out.println("Silmek istediğiniz satırı giriniz :");
        int index=sc.nextInt();
        satirlarDosyasi.remove(index);
        
        BufferedWriter bw1=new BufferedWriter(new FileWriter("CiceklerDB.txt"));
        for(String i:satirlarDosyasi){
            bw1.write(i);
            bw1.newLine();
            
        }
        for (int i =satirlarDosyasi.size(); i <ilkDosyaBoyu; i++) {
            bw1.write("");
        }
        bw1.close();
        
    }
    public static void HepsiniSil() throws IOException{
        BufferedWriter bw=new BufferedWriter(new FileWriter("CiceklerDB.txt"));
        for (int i=0;i<satirlarDosyasi.size();i++){
            bw.write("");
        }
        bw.close();
    }
    public static void Ekle() throws IOException{
        System.out.println("Eklemek çiçeğin özelliklerini giriniz :");
        Scanner sc4=new Scanner(System.in);
        System.out.println("Çanak yaprak uzunluğunu giriniz :");
        float canakYU=sc4.nextFloat();
        System.out.println("Çanak yaprak genişliğini giriniz :");
        float canakYG=sc4.nextFloat();
        System.out.println("Taç yaprak uzunluğunu giriniz :");
        float tacYU=sc4.nextFloat();
        System.out.println("Taç yaprak genişliğini giriniz :");
        float tacYG=sc4.nextFloat();
        sc4.nextLine();
        System.out.println("Çiçeğin hangi türden olduğunu giriniz  (Iris-setosa/Iris-versicolor/Iris-virginica) :");
        String tur=sc4.nextLine();
        if((tur.equals("Iris-setosa")) || (tur.equals("Iris-versicolor")) || (tur.equals("Iris-virginica"))){
            satirlarDosyasi.add(canakYU+","+canakYG+","+tacYU+","+tacYG+","+tur);
            BufferedWriter bw3=new BufferedWriter(new FileWriter("CiceklerDB.txt"));
            for(String i:satirlarDosyasi){
                bw3.write(i);
                bw3.newLine();
        }
            bw3.close();   
        }
        else {
            System.out.println("Uygun olmayan bir bilgi girdiniz :");
            Ekle();
        }       
    }   
}
