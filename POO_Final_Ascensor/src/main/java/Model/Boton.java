
package Model;


public class Boton {
    private String tipo; 
    private boolean estado; 


    public Boton(String tipo) {
        this.tipo = tipo;
        this.estado = false;
    }

    public void presionar(int piso) {
        estado = true;
        Logger.log("Botón '" + tipo + "' presionado en el piso " + piso);
    }


    public void resetear(int piso) {
        estado = false;
        Logger.log("Botón '" + tipo + "' reseteado en el piso " + piso);
    }

    public boolean isEncendido() {
        return estado;
    }

    public String getTipo() {
        return tipo;
    }
}
