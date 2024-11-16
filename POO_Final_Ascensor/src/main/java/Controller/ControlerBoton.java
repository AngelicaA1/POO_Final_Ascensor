package Controller;

import Model.Boton;
import Model.Logger;

public class ControlerBoton {

    private Boton boton;


    public ControlerBoton(Boton boton) {
        this.boton = boton;
    }


    public void presionar(int piso) {
        boton.presionar(piso); 
        Logger.log("Evento: Botón '" + boton.getTipo() + "' presionado en piso " + piso);
    }


    public void resetear(int piso) {
        boton.resetear(piso); 
        Logger.log("Evento: Botón '" + boton.getTipo() + "' reseteado en piso " + piso);
    }
}
