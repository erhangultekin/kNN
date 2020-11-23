public class CiceklerNesne {
    private int No;
    private double CanakYU=0.0d;
    private double CanakYG=0.0d;
    private double TacYU=0.0d;
    private double TacYG=0.0d;
    private String Tur;
    private double Distance=0.0d;

    public CiceklerNesne(int No) {
        this.No = No;
    }
    private String YeniTur;

    public String getYeniTur() {
        return YeniTur;
    }

    public void setYeniTur(String YeniTur) {
        this.YeniTur = YeniTur;
    }
    
    
    public double getDistance() {
        return Distance;
    }

    public void setDistance(double Distance) {
        this.Distance = Distance;
    }

    public CiceklerNesne(int No, double CanakYU, double CanakYG, double TacYU, double TacYG) {
        this.No = No;
        this.CanakYU = CanakYU;
        this.CanakYG = CanakYG;
        this.TacYU = TacYU;
        this.TacYG = TacYG;
        
    }

    public int getNo() {
        return No;
    }

    public double getCanakYU() {
        return CanakYU;
    }

    public double getCanakYG() {
        return CanakYG;
    }

    public double getTacYU() {
        return TacYU;
    }

    public double getTacYG() {
        return TacYG;
    }

    public String getTur() {
        return Tur;
    }

    public void setNo(int No) {
        this.No = No;
    }

    public void setCanakYU(double CanakYU) {
        this.CanakYU = CanakYU;
    }

    public void setCanakYG(double CanakYG) {
        this.CanakYG = CanakYG;
    }

    public void setTacYU(double TacYU) {
        this.TacYU = TacYU;
    }

    public void setTacYG(double TacYG) {
        this.TacYG = TacYG;
    }

    public void setTur(String Tur) {
        this.Tur = Tur;
    }

    @Override
    public String toString() {
        return "Canak Yaprak Uzunluğu=" + CanakYU + ", Çanak Yaprak Genişliği=" + CanakYG + ", "
                + "Taç Yaprak Uzunluğu=" + TacYU + ", Taç Yaprak Genişliği=" + TacYG + ", Tür=" + Tur + '}';
    }
    
}
