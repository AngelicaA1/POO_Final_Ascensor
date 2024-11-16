package Controller;

import Model.*;
import View.VentanaPrincipal;
import java.awt.Color;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ControllerSistema {
    private List<Ascensor> ascensores;
    private List<Piso> pisos;
    private List<ControlerBoton> controladoresBotones;
    private List<ControllerPuerta> controladoresPuertas;
    private VentanaPrincipal vistaPrincipal;

    public ControllerSistema(int totalAscensores, int totalPisos, VentanaPrincipal vista) {
        ascensores = new ArrayList<>();
        pisos = new ArrayList<>();
        controladoresBotones = new ArrayList<>();
        controladoresPuertas = new ArrayList<>();
        this.vistaPrincipal = vista;

        for (int i = 0; i < totalAscensores; i++) {
            Ascensor ascensor = new Ascensor(i, totalPisos);
            ascensores.add(ascensor);
            controladoresPuertas.add(new ControllerPuerta(ascensor.getPuerta()));
        }

        for (int i = 0; i < totalPisos; i++) {
            Piso piso = new Piso(i);
            pisos.add(piso);
            controladoresBotones.add(new ControlerBoton(piso.getBotonSubida()));
            controladoresBotones.add(new ControlerBoton(piso.getBotonBajada()));
        }
    }


    public void abrirPuertaManual() {
        for (ControllerPuerta puerta : controladoresPuertas) {
            puerta.abrir();
        }
        vistaPrincipal.actualizarEstadoPuerta("Abierta");
        vistaPrincipal.agregarLogSimulacion("Puerta abierta manualmente.");
    }


    public void cerrarPuertaManual() {
        for (ControllerPuerta puerta : controladoresPuertas) {
            puerta.cerrar();
        }
        vistaPrincipal.actualizarEstadoPuerta("Cerrada");
        vistaPrincipal.agregarLogSimulacion("Puerta cerrada manualmente.");
    }

    public void llamarAscensor(int pisoDestino, String direccion) {
    if (pisoDestino < 0 || pisoDestino >= pisos.size()) {
        vistaPrincipal.agregarLogSimulacion("Error: El piso " + pisoDestino + " está fuera de rango.");
        return;
    }

    Ascensor ascensor = encontrarAscensorCercano(pisoDestino, direccion);


    if (ascensor != null && ascensor.getPisoActual() == pisoDestino) {
        vistaPrincipal.agregarLogSimulacion("Ascensor " + (ascensor.getId() + 1) + ": Ya estás en el piso " + pisoDestino + ".");
        return;
    }

    if (direccion.equals("subir") && ascensor != null && ascensor.getPisoActual() == pisos.size() - 1) {
        vistaPrincipal.agregarLogSimulacion("Error: Ya estás en el piso más alto (" + (pisos.size() - 1) + ").");
        return;
    }
    if (direccion.equals("bajar") && ascensor != null && ascensor.getPisoActual() == 0) {
        vistaPrincipal.agregarLogSimulacion("Error: Ya estás en el piso más bajo (0).");
        return;
    }

    if (ascensor != null) {
        
        if (Math.random() < 0.2) {
            activarEventoPesoExcedido();
            return; 
        }

        vistaPrincipal.agregarLogSimulacion("Ascensor " + (ascensor.getId() + 1) + " asignado. Dirección: " + direccion + ".");
        ascensor.solicitarPiso(pisoDestino);
        ascensor.cambiarDireccion(direccion);

  
        vistaPrincipal.setBotonesActivo(false);
        vistaPrincipal.actualizarEstadoAscensor("Moviéndose hacia " + direccion, ascensor.getPisoActual());
        vistaPrincipal.cambiarColorFondo(new Color(255, 102, 102)); 

        Timer movimientoTimer = new Timer(1000, new ActionListener() {
            int pisoActual = ascensor.getPisoActual();

            @Override
            public void actionPerformed(ActionEvent e) {
                if ((direccion.equals("subir") && pisoActual < pisoDestino) ||
                    (direccion.equals("bajar") && pisoActual > pisoDestino)) {
                    pisoActual += direccion.equals("subir") ? 1 : -1;
                    ascensor.setPisoActual(pisoActual);
                    vistaPrincipal.actualizarEstadoAscensor("Moviéndose hacia " + direccion, pisoActual);
                    vistaPrincipal.agregarLogSimulacion("Ascensor " + (ascensor.getId() + 1) + " en piso " + pisoActual);
                } else {
                    ((Timer) e.getSource()).stop();
                    ascensor.setPisoActual(pisoDestino);
                    ascensor.cambiarDireccion("parado");
                    vistaPrincipal.actualizarEstadoAscensor("En espera", pisoDestino);
                    vistaPrincipal.cambiarColorFondo(new Color(135, 206, 250)); 
                    vistaPrincipal.setBotonesActivo(true); 
                    abrirPuertaAutomatica(ascensor);
                }
            }
        });
        movimientoTimer.start();
    } else {
        vistaPrincipal.agregarLogSimulacion("Error: No se encontró un ascensor disponible para la dirección '" + direccion + "'.");
    }
}

    private void activarEventoPesoExcedido() {
        vistaPrincipal.cambiarColorFondo(new Color(169, 169, 169)); 
        String mensaje = "EVP13: Excediendo el peso permitido";
        vistaPrincipal.agregarLogSimulacion(mensaje);
        Logger.log(mensaje);


        Timer timerPesoExcedido = new Timer(5000, (ActionEvent e) -> {
            vistaPrincipal.cambiarColorFondo(new Color(135, 206, 250)); 
            vistaPrincipal.agregarLogSimulacion("EVP13: El evento de peso excedido ha finalizado.");
            Logger.log("EVP13: El evento de peso excedido ha finalizado.");
        });
        timerPesoExcedido.setRepeats(false);
        timerPesoExcedido.start();
    }


    public void simularFalla() {
        vistaPrincipal.setBotonesActivo(false); 
        vistaPrincipal.actualizarEstadoAscensor("FALLA", -1);
        vistaPrincipal.cambiarColorFondo(new Color(255, 69, 0)); 
        vistaPrincipal.agregarLogSimulacion("EVP7: Llamando bomberos y ambulancias...");

        
        Logger.log("EVP7: Llamando bomberos y ambulancias. Ascensor bloqueado por falla.");

        
        Timer timerFalla = new Timer(7000, (ActionEvent e) -> {
            vistaPrincipal.actualizarEstadoAscensor("En espera", -1);
            vistaPrincipal.cambiarColorFondo(new Color(135, 206, 250)); 
            vistaPrincipal.setBotonesActivo(true); 
            vistaPrincipal.agregarLogSimulacion("Ascensor operativo nuevamente.");
            
         
            Logger.log("Ascensor operativo nuevamente tras el bloqueo de 7 segundos.");
        });
        timerFalla.setRepeats(false);
        timerFalla.start();
    }




    private void abrirPuertaAutomatica(Ascensor ascensor) {
        ControllerPuerta puertaController = controladoresPuertas.get(ascensor.getId());
        puertaController.abrir();
        vistaPrincipal.actualizarEstadoPuerta("Abierta");
        vistaPrincipal.agregarLogSimulacion("Ascensor " + ascensor.getId() + ": Puerta abierta automáticamente en el piso " + ascensor.getPisoActual());

        Timer cierreTimer = new Timer(2000, e -> cerrarPuertaAutomatica(ascensor));
        cierreTimer.setRepeats(false);
        cierreTimer.start();
    }


    private void cerrarPuertaAutomatica(Ascensor ascensor) {
        ControllerPuerta puertaController = controladoresPuertas.get(ascensor.getId());
        puertaController.cerrar();
        vistaPrincipal.actualizarEstadoPuerta("Cerrada");
        vistaPrincipal.agregarLogSimulacion("Ascensor " + ascensor.getId() + ": Puerta cerrada automáticamente.");
    }

    private Ascensor encontrarAscensorCercano(int piso, String direccion) {
        for (Ascensor ascensor : ascensores) {
            if (ascensor.getDireccion().equals(direccion) || ascensor.getDireccion().equals("parado")) {
                return ascensor;
            }
        }
        return null;
    }
    

    public void llamarMantenimiento() {
        vistaPrincipal.cambiarColorFondo(new Color(255, 255, 102)); 
        vistaPrincipal.agregarLogSimulacion("EVP11: Llamando al personal de mantenimiento...");
        Logger.log("EVP11: Llamando al personal de mantenimiento...");


        Timer timerMantenimiento = new Timer(2000, (ActionEvent e) -> {
            vistaPrincipal.cambiarColorFondo(new Color(135, 206, 250)); 
            vistaPrincipal.agregarLogSimulacion("EVP11: Mantenimiento finalizado. Ascensor sigue operativo.");
            Logger.log("EVP11: Mantenimiento finalizado. Ascensor sigue operativo.");
        });
        timerMantenimiento.setRepeats(false);
        timerMantenimiento.start();
    }
}
