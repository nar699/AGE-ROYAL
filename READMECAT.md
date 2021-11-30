# AGE-ROYAL
Un cop descarregat el .zip del pou de l'eStudy, iniciem el IntelliJ i obrim el projecte ubicat a la carpeta programari.
La base de dades l'hem fet amb el MySql Workbench, es necessari inicialitzar-la i compilar-la pel funcionament correcte de la pràctica.

Abans de fer res, localitza a assets el fitxer config.json, obre'l i canvia/actualitza totes les dades, i inserta les de la teva pròpia base de dades.

Un cop fets aquest dos pasos, compilem el projecte del IntelliJ.

El primer que ens apareix és un menú amb dues opcions, si ets un usuari nou has de fer click a l'opció de register, a continuació se't obrirà una pantalla on hauràs d'inserir les dades que et demanen, en el cas que hi hagi algún error a
l'hora de registar-te o de fer login, sortira l'error per pantalla indicant que has fet malament, i ho podràs tornar a intentar.

Un cop aquestes introduides, prem el botó de submit per enviar la informació a la base de dades. Fet això accedirem al menú principal del joc i ja podràs fer la teva primera partida, consulta el ranking , l'historial o el menú d'opcions.

En cas de no ser el primer cop que entres al joc, saltat els pasos anteriors i ves directament a login, i introdueix les teves dades per accedir a la teva sessió, si hi ha algun problema, el joc t'ho notificarà. 

En els dos casos, un cop introduides les dades que et demanen a login, fes click a "submit", si les dades son correctes accediràs al menú principal.

El menú principal es divideix en 4 apartats diferents:
	
	-New Game: Prem aquesta opció si vols iniciar una nova partida. Fes click a les tropas i invoca-les sobre el taulell i que començi la partida. Un cop acabi la partida, és a dir, que alguna de les torres principals hagi estat 
	           destruida, apreixerà una finestra on hauràs de guardar la partida amb el nom que tu vulguis

	-Career: En aquesta opció mostrem tot l'historial de l'usuari ordenat per dates de més propera a més llunyana. Per cada partida guardem la data, el nom de la partida i si hem guanyat o perdut.
		 En aquesta mateixa view cada usuari pot prémer qualsevol de les partides i reproduir-lo clicant el botó de reproduir. També si es vol pot tornar al menú principal prement el botó "Atras"


	-Options: Consta de tres botons principals, un per tancar sessió i borrar el compte de la base de dades anomenat "Delete Account", un per tancar sessió anomenat "Logout" i finalment un que tira enrere cap al menu principal.
		  Tant el botó de delete account com el de logout, retornen a al menu principal del programa.
	
	-Rànquing: Consta d'una JTable amb tota la informació que hem de mostrar tal i com se'ns requereix a l'enunciat de la pràctica, un JScorll per poder fer scroll en cas que la taula sigui molt extensa i finalment dos JButtons
		   un que retorna al menú principal i l'altra et mostra l'historial del jugador seleccionat. 

