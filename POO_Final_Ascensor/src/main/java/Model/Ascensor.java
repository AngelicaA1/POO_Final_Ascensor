package Model;

public class Ascensor {
    private int id;
    private int pisoActual;
    private String direccion; 
    private boolean[] pisosSolicitados;
    private Puerta puerta; 


    public Ascensor(int id, int totalPisos) {
        this.id = id;
        this.pisoActual = 0;
        this.direccion = "parado";
        this.pisosSolicitados = new boolean[totalPisos];
        this.puerta = new Puerta(); 
    }


    public void solicitarPiso(int piso) {
        if (piso >= 0 && piso < pisosSolicitados.length) {
            pisosSolicitados[piso] = true;
            Logger.log("Ascensor " + id + ": Solicitud de piso " + piso + " registrada.");
        }
    }

    public void mover() {
        if (direccion.equals("subiendo")) {
            for (int i = pisoActual + 1; i < pisosSolicitados.length; i++) {
                if (pisosSolicitados[i]) {
                    pisoActual = i;
                    pisosSolicitados[i] = false;
                    Logger.log("Ascensor " + id + ": Movido al piso " + pisoActual + " (Subiendo).");
                    return;
                }
            }
            direccion = "parado";
        } else if (direccion.equals("bajando")) {
            for (int i = pisoActual - 1; i >= 0; i--) {
                if (pisosSolicitados[i]) {
                    pisoActual = i;
                    pisosSolicitados[i] = false;
                    Logger.log("Ascensor " + id + ": Movido al piso " + pisoActual + " (Bajando).");
                    return;
                }
            }
            direccion = "parado";
        }
    }


    public void cambiarDireccion(String nuevaDireccion) {
        this.direccion = nuevaDireccion;
        Logger.log("Ascensor " + id + ": Dirección cambiada a '" + nuevaDireccion + "'.");
    }


    public int getPisoActual() {
        return pisoActual;
    }


    public void setPisoActual(int pisoActual) {
        this.pisoActual = pisoActual;
        Logger.log("Ascensor " + id + ": Piso actual actualizado a " + pisoActual + ".");
    }


    public String getDireccion() {
        return direccion;
    }


    public int getId() {
        return id;
    }


    public Puerta getPuerta() {
        return puerta;
    }
}
