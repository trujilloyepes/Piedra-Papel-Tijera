package model.enums;
public enum Movimiento {
    PIEDRA ("Piedra", "Shoryuken"),
    PAPEL ("Papel", "Hadouken"),
    TIJERA ("Tijera", "Tatsumaki");

    private final String nombre;
    private final String golpe;

    Movimiento(String nombre, String golpe) {
        this.nombre = nombre;
        this.golpe = golpe;
    }

    public String golpe (){
        return this.golpe;
    }

    /**
     * Método que devuelve un boolean si ese movimiento gana al otro
     * @param otro El otro movimiento que se produce durante la batalla
     * @return Devuelve true si este movimiento gana al otro
     */
    public boolean vence (Movimiento otro){
        return (this == PIEDRA && otro == TIJERA
        || (this == PAPEL && otro == PIEDRA) || (this == TIJERA && otro == PAPEL));
    }

    @Override
    public String toString() {
        return this.nombre + " (" + this.golpe + ")";
    }
}