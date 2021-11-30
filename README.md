# AGE-ROYAL
Once we have downloaded the .zip file from the eStudy package, we start IntelliJ and open the project located in the program folder.
The database has been made with the MySql Workbench, it is necessary to initialise it and compile it for the correct functioning of the practice.

Before doing anything else, locate the config.json file in assets, open it and change/update all the data, and insert the data from your own database.

Once these two steps are done, we compile the IntelliJ project.

The first thing that appears is a menu with two options, if you are a new user you have to click on the register option, then a screen will open where you will have to insert the requested data, in case there is any error when registering, you will be asked to do so.
If you make a mistake when registering or logging in, an error will appear on the screen indicating that you have made a mistake, and you can try again.

Once you have entered these, press the submit button to send the information to the database. Once you have done this, you will access the main menu of the game and you will be able to play your first game, check the ranking, the history or the options menu.

If it is not the first time you enter the game, skip the previous steps and go directly to login, and enter your data to access your session, if there is any problem, the game will notify you. 

In both cases, once you have entered the data you are asked to login, click on "submit", if the data are correct you will access the main menu.

The main menu is divided into 4 different sections:
	
	-New Game: Press this option if you want to start a new game. Click on the troops and invoke them on the table to start the game. Once the game is over, i.e. one of the main towers has been destroyed, you will see a 
	           destroyed, you will see a window where you will have to save the game with the name you want

	-Career: In this option we will show all the user's history sorted by dates from earliest to latest. For each game we keep the date, the name of the game and if we have won or lost.
		 In this same view each user can watch any of the games and play it by clicking on the play button. Also if you want to return to the main menu, press the "Back" button.


	-Options: There are three main buttons, one to close the session and delete the account from the database called "Delete Account", one to close the session called "Logout" and finally one to go back to the main menu.
		  Both the delete account button and the logout button return to the main menu of the program.
	
	-Ranquing: It consists of a JTable with all the information that we have to show as it is required in the statement of the practice, a JScorll to be able to scroll in case the table is very extensive and finally two JButtons
		   one that returns to the main menu and the other that shows the history of the selected player. 

Translated with www.DeepL.com/Translator (free version)
