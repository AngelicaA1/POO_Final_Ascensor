package View;

import Controller.ControllerSistema;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private JLabel lblEstadoPuerta;
    private JLabel lblEstadoAscensor;
    private JLabel lblPisoActual;
    private JTextArea txtLogSimulacion; 
    private JButton btnAbrirPuerta;
    private JButton btnCerrarPuerta;
    private JButton btnLlamarAscensor; 
    private ControllerSistema controlador; 

    public VentanaPrincipal() {
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Sistema de Control de Ascensores");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(135, 206, 250)); 
    }

    private void inicializarComponentes() {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(10, 10, 10, 10);


    btnLlamarAscensor = new JButton("Llamar Ascensor");
    btnLlamarAscensor.addActionListener(e -> {
        if (controlador != null) {
            String pisoStr = JOptionPane.showInputDialog(this, "Ingrese el piso:");
            String direccion = JOptionPane.showInputDialog(this, "Ingrese la dirección (subir/bajar):");
            try {
                int piso = Integer.parseInt(pisoStr);
                controlador.llamarAscensor(piso, direccion);
            } catch (NumberFormatException ex) {
                agregarLogSimulacion("Error: Entrada inválida. Intente de nuevo.");
            }
        }
    });
    gbc.gridx = 0;
    gbc.gridy = 0;
    add(btnLlamarAscensor, gbc);


    btnAbrirPuerta = new JButton("Abrir Puerta");
    btnAbrirPuerta.addActionListener(e -> {
        if (controlador != null) {
            controlador.abrirPuertaManual();
        }
    });
    gbc.gridx = 1;
    gbc.gridy = 0;
    add(btnAbrirPuerta, gbc);


    btnCerrarPuerta = new JButton("Cerrar Puerta");
    btnCerrarPuerta.addActionListener(e -> {
        if (controlador != null) {
            controlador.cerrarPuertaManual();
        }
    });
    gbc.gridx = 2;
    gbc.gridy = 0;
    add(btnCerrarPuerta, gbc);


    JButton btnSimularFalla = new JButton("Simular Falla");
    btnSimularFalla.addActionListener(e -> {
        if (controlador != null) {
            controlador.simularFalla();
        }
    });
    gbc.gridx = 0;
    gbc.gridy = 5;
    add(btnSimularFalla, gbc);

    JButton btnLlamarMantenimiento = new JButton("\u260E Llamar Mantenimiento"); 
    btnLlamarMantenimiento.addActionListener(e -> {
        if (controlador != null) {
            controlador.llamarMantenimiento();
        }
    });
    gbc.gridx = 1;
    gbc.gridy = 5;
    add(btnLlamarMantenimiento, gbc);

    lblPisoActual = new JLabel("Piso Actual: En espera", SwingConstants.CENTER);
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 3;
    add(lblPisoActual, gbc);

    lblEstadoAscensor = new JLabel("Estado Ascensor: En espera", SwingConstants.CENTER);
    gbc.gridy = 2;
    add(lblEstadoAscensor, gbc);

    lblEstadoPuerta = new JLabel("Estado Puerta: Cerrada", SwingConstants.CENTER);
    gbc.gridy = 3;
    add(lblEstadoPuerta, gbc);

    txtLogSimulacion = new JTextArea(10, 30);
    txtLogSimulacion.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(txtLogSimulacion);
    gbc.gridy = 4;
    add(scrollPane, gbc);
}


    public void actualizarEstadoAscensor(String estado, int piso) {
        lblPisoActual.setText("Piso Actual: " + piso);
        lblEstadoAscensor.setText("Estado Ascensor: " + estado);

        if (estado.contains("subiendo") || estado.contains("bajando")) {
            cambiarColorFondo(new Color(255, 102, 102)); 
            setBotonesActivo(false);
        } else if (estado.equals("En espera")) {
            cambiarColorFondo(new Color(135, 206, 250)); 
            setBotonesActivo(true); 
        }
    }


    public void actualizarEstadoPuerta(String estado) {
        lblEstadoPuerta.setText("Estado Puerta: " + estado);

        if (estado.equals("Abierta")) {
            cambiarColorFondo(new Color(102, 255, 102)); 
        } else if (estado.equals("Cerrada")) {
            cambiarColorFondo(new Color(135, 206, 250)); 

        }
    }
    
    

    public void cambiarColorFondo(Color color) {
        getContentPane().setBackground(color);
        repaint();
    }


    public void agregarLogSimulacion(String mensaje) {
        txtLogSimulacion.append(mensaje + "\n");
        txtLogSimulacion.setCaretPosition(txtLogSimulacion.getDocument().getLength());
    }


    public void setBotonesActivo(boolean activo) {
        btnLlamarAscensor.setEnabled(activo);
        btnAbrirPuerta.setEnabled(activo);
        btnCerrarPuerta.setEnabled(activo);
    }

   
    public void setControlador(ControllerSistema controlador) {
        this.controlador = controlador;
    }


    public void mostrar() {
        setVisible(true);
    }
}
