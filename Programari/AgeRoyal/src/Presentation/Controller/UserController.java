package Presentation.Controller;

import Business.Model;
import Business.Entities.Usuari;
import Presentation.View.MainView;

/**
 * Controlador personalitzar que controla les parts del programa que permeten a l'usuari entrar i/o registrar-se al programa
 * @version 04/07/21
 * @author Narcís Cisquella, Lluis Gumbau, Joan LLobet, Marc Postils
 */
public class UserController {
    private Model model;
    private MainView mainView;
    public static final int ERROR_CORREU=1;
    public static final int ERROR_FORMAT_PASSWORD=2;

    public static final int ERROR_EQUALS_PASSWORD=3;
    public static final int ERROR_USUARI=4;

    /**
     * Constructor del controller
     * @param model el model de dades
     */
    public UserController(Model model,MainView mainView){
        this.model = model;
        this.mainView = mainView;
    }

    /**
     * Funció que registra l'usuari en la base de dades
     * @param contrasenya la contrasenya del usuari
     * @param confirmation la contrasenya del usuari de nou, per confirmar-la
     * @param nom el nom de l'usuari
     * @param correu el correu del usuari
     * @return boolean que controla si s'ha pogut registrar correctament
     */
    public boolean registerUsuari(String contrasenya, String confirmation, String nom, String correu){
        if(contrasenya.equals(confirmation)){
            Usuari usuari = new Usuari(nom,correu);
            this.model.updateUsuari(usuari);
            int error = this.model.getUserLogic().register(contrasenya);
              if(error == 0){
                  return true;

              }else{
                  mainView.registerError(error);
              }


        }else{

            mainView.registerError(ERROR_EQUALS_PASSWORD);
        }
        return false;
    }
    /**
     * Funció que permet a l'usuari entrar al programa
     * @param constrasenya contrasenya del usuari
     * @param nomCorreu correu de l'usuari
     * @return boolean que indica si l'usuari ha pogut entrar amb éxit
     */
    public boolean loginUsuari(String constrasenya, String nomCorreu){

        return model.getUserLogic().login( constrasenya, nomCorreu);
    }
}
