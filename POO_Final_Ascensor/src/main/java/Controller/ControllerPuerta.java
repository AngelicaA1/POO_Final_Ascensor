package Controller;

import Model.Puerta;
import Model.Logger;

public class ControllerPuerta {

    private Puerta puerta;


    public ControllerPuerta(Puerta puerta) {
        this.puerta = puerta;
    }


    public void abrir() {
        puerta.abrir();
        Logger.log("Evento: Puerta abierta automáticamente.");
    }

    public void cerrar() {
        puerta.cerrar();
        Logger.log("Evento: Puerta cerrada automáticamente.");
    }

    public void aperturaManual() {
        puerta.aperturaManual();
        Logger.log("Evento: Puerta abierta manualmente.");
    }


    public void cierreManual() {
        puerta.cierreManual();
        Logger.log("Evento: Puerta cerrada manualmente.");
    }


    public void falla() {
        puerta.falla();
        Logger.log("Evento: Falla en la puerta detectada.");
    }


    public void sinEnergia() {
        puerta.sinEnergia();
        Logger.log("Evento: Puerta sin energía.");
    }
}
