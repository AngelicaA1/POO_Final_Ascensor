
package Model;


public class Piso {
    private int numero;
    private Boton botonSubida;
    private Boton botonBajada;


    public Piso(int numero) {
        this.numero = numero;
        this.botonSubida = new Boton("Subida");
        this.botonBajada = new Boton("Bajada");
    }

    public int getNumero() {
        return numero;
    }

    public Boton getBotonSubida() {
        return botonSubida;
    }

    public Boton getBotonBajada() {
        return botonBajada;
    }
}
