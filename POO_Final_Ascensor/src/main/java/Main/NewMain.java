package Main;

import Controller.ControllerSistema;
import View.VentanaPrincipal;

public class NewMain {
    public static void main(String[] args) {
        int totalAscensores = 3; 
        int totalPisos = 10;  


        VentanaPrincipal ventana = new VentanaPrincipal();
        ControllerSistema controlador = new ControllerSistema(totalAscensores, totalPisos, ventana);


        ventana.setControlador(controlador);

        ventana.mostrar();
    }
}
