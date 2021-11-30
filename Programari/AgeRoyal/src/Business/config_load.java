package Business;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Classe que utilitzem per llegir les dades del Workbench del usuari, llegim el config.json.
 * @version 23/05/21
 * @author Narcís Cisquella, Lluis Gumbau, Joan LLobet, Marc Postils
 */
public class config_load {
    private final String config_path = "assets/config.json";
    private Config configuracio;

    /**
     * Mètode que utilitzem per llegir les dades del config.json.
     */
    public void llegirDades() {
        Gson gson = new Gson();
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(config_path));
            this.configuracio = gson.fromJson(reader, Config.class);


        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }
    /**
     * Getter del atribut configuració.
     * @return Retorna l'atribut configuració.
     */
    public Config getConfiguracio() {
        return configuracio;
    }
}
