package model;
public class RankingJugador {
    private final String nombre;
    private final int combates;
    private final int victorias;

    public RankingJugador(String nombre, int combates, int victorias){
        this.nombre = nombre;
        this.combates = combates;
        this.victorias = victorias;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCombates() {
        return combates;
    }

    public int getVictorias() {
        return victorias;
    }

    public int getDerrotas () {
        return this.combates - this.victorias;
    }

    public double getPorcentaje () {
        return this.combates == 0 ? 0 : (this.victorias * 100.0) / this.combates;
    }
}