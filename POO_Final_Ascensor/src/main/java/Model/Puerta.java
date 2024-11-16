
package Model;


public class Puerta {
    private boolean abierta;

    public Puerta() {
        this.abierta = false;
    }


    public void abrir() {
        abierta = true;
        Logger.log("Evento EVP1: Puerta abierta automáticamente.");
    }


    public void cerrar() {
        abierta = false;
        Logger.log("Evento EVP2: Puerta cerrada automáticamente.");
    }


    public void aperturaManual() {
        abierta = true;
        Logger.log("Evento EVP3: Puerta abierta manualmente.");
    }


    public void cierreManual() {
        abierta = false;
        Logger.log("Evento EVP4: Puerta cerrada manualmente.");
    }


    public void falla() {
        Logger.log("Evento EVP0: Puerta fallida.");
    }


    public void sinEnergia() {
        Logger.log("Evento EVP10: Puerta sin energía.");
    }

    public boolean isAbierta() {
        return abierta;
    }
}
