package Business.Entities;

/**
 * Classe on inicialitzem totes les topes
 * @version 23/05/21
 * @author Narcís Cisquella, Lluis Gumbau, Joan LLobet, Marc Postils
 */
public class Tropa implements Cloneable{
    private Integer life;
    private Integer damage;
    private Boolean offensive;
    private Boolean tower;
    private Integer tropa;
    private Integer reach;
    private Boolean maquina;
    private String name;
    private int numFila;
    private int numColumna;
    private int cost;
    private static final int EMPTY = -1;
    private static final int TOWER = 1;
    private static final int OFENSIVE = 2;
    private static final int DEFENSIVE = 3;
    private String path;

    /**
     * Constructor de la classe Tropa
     * @param tropa la tropa
     */
    public Tropa(Tropa tropa){
        this.life = tropa.life;
        this.damage = tropa.damage;
        this.offensive = tropa.offensive;
        this.tower = tropa.tower;
        this.tropa = tropa.tropa;
        this.reach = tropa.reach;
        this.maquina = tropa.maquina;
        this.name = tropa.name;
        this.numFila = tropa.numFila;
        this.numColumna = tropa.numColumna;
        this.cost = tropa.cost;
        this.path = tropa.path;

    }

    /**
     * Constructor de la classe Tropa
     * @param numFila el nombre de la fila
     * @param numColumna el nombre de la columna
     */
    public Tropa(int numFila, int numColumna){
        tropa = EMPTY;
        offensive = false;
        tower = false;
        maquina = false;
        this.numFila = numFila;
        this.numColumna = numColumna;
    }

    /**
     * Constructor de la classe Tropa
     * @param tropa la tropa
     * @param maquina si es controlat per la maquina o no
     * @param numFila el nombre de la fila
     * @param numColumna el nombre de la columna
     */
    public Tropa(Integer tropa, Boolean maquina, int numFila, int numColumna){
        this.tropa = tropa;
        if(tropa == TOWER){
            life = 500;
            offensive = false;
            tower = true;
            this.maquina = maquina;
            this.numFila = numFila;
            this.numColumna = numColumna;
            this.name = "Tower";
        }
    }


    /**
     * Constructor de la classe Tropa
     * @param life la vida
     * @param damage la vida que treu al atacar
     * @param offensive si la tropa es ofensiva o no
     * @param tower si es una torre
     * @param tropa quina tropa es
     * @param reach fins a on arriba el seu atac
     * @param maquina si está controlada per la maquina
     * @param name el nom de la tropa
     * @param cost el cost de la tropa
     * @param path el path a la imtge de la tropa
     * @param numFila la fila en la que esta
     * @param numColumna la columna en la que esta
     */
    public Tropa(Integer life, Integer damage, Boolean offensive, Boolean tower, Integer tropa, Integer reach, Boolean maquina, String name, int cost, String path, int numFila, int numColumna) {
        this.life = life;
        this.damage = damage;
        this.offensive = offensive;
        this.tower = tower;
        this.tropa = tropa;
        this.reach = reach;
        this.maquina = maquina;
        this.name = name;
        this.cost = cost;
        this.path = path;
        this.numFila = numFila;
        this.numColumna = numColumna;
    }

    /**
     * Getter de l'atribut Topa
     * @return la tropa
     */
    public Integer getTropa() {
        return tropa;
    }

    /**
     * Getter de l'atribut isOffensive
     * @return si es ofensiva o no
     */
    public Boolean isOffensive(){
        return offensive;
    }

    /**
     * Getter de l'atribut isTower
     * @return si es una torre o no
     */
    public Boolean isTower(){
        return tower;
    }

    /**
     * Getter de l'atribut Maquina, ens diu si és la màquina o el pplayer
     * @return si es de la maquina o del usuari
     */
    public Boolean isMaquina() {
        return maquina;
    }

    /**
     * Getter de l'atribut getPath
     * @return el path de la foto de la tropa
     */
    public String getPath() {
        return path;
    }

    /**
     * Getter de l'atribut cost
     * @return el cost de la tropa
     */
    public int getCost() {
        return cost;
    }

    /**
     * Getter de l'atribut màquina
     * @param maquina si la tropa pertany a la maquina
     */
    public void setMaquina(Boolean maquina) {
        this.maquina = maquina;
    }

    /**
     * Getter per comprovar si una casella esta buida
     * @return si la casella esta buida
     */
    public Boolean isEmpty(){
        return this.tropa == -1;
    }

    /**
     * Getter de l'atribut numFila
     * @return el nombre de la fila
     */
    public int getNumFila() {
        return numFila;
    }

    /**
     * Stter de l'atribut numFila
     * @param numFila el nombre de la fila
     */
    public void setNumFila(int numFila) {
        this.numFila = numFila;
    }

    /**
     * Getter de l'atribut numClolumna
     * @return el nombre de la columna
     */
    public int getNumColumna() {
        return numColumna;
    }

    /**
     * Setter de l'atribut numColumna
     * @param numColumna el nombre de la columna
     */
    public void setNumColumna(int numColumna) {
        this.numColumna = numColumna;
    }

    /**
     * Getter de l'atribut Life
     * @return agafa la vida
     */
    public Integer getLife() {
        return life;
    }

    /**
     * Setter de l'atribut life
     * @param life la vida
     */
    public void setLife(Integer life) {
        this.life = life;
    }

    /**
     * Getter de l'atribut Damage
     * @return el dany que causa
     */
    public Integer getDamage() {
        return damage;
    }

    /**
     * Getter de l'atribut Reach
     * @return fins a on arriba l'atac
     */
    public Integer getReach() {
        return reach;
    }


}

