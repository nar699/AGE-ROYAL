package Business;

/**
 * Classe que representa un parell de coordenades(una posiciÃ³ del tauler)
 * @param <T> Integer coordenada x
 * @param <U> Integer coordenada y
 * @version 23/05/21
 * @author Narcís Cisquella, Lluis Gumbau, Joan LLobet, Marc Postils
 */
public class Pair<T, U> {
    public T getT() {
        return t;
    }

    public U getU() {
        return u;
    }

    private final T t;
    private final U u;

    /**
     * constructor de la parella
     * @param t Integer coordenada x
     * @param u Integer coordenada y
     */
    public Pair(T t, U u) {
        this.t= t;
        this.u= u;
    }
}