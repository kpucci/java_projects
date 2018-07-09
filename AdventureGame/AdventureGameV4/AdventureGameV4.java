/*
  Katie Vaughan
  CS401
  Fall 2016
  Assignment 5
*/

import javafx.application.Application;
import javafx.application.*;
import javafx.event.*;
  import javafx.event.ActionEvent;
  import javafx.scene.*;
  import javafx.scene.layout.*;
  import javafx.scene.Scene;
  import javafx.scene.control.*;
  import javafx.scene.control.Control;
  import javafx.scene.control.Button;
  import javafx.scene.control.Label;
  import javafx.scene.control.ChoiceBox;
  import javafx.scene.control.TextField;
  import javafx.scene.control.ListView;
  import javafx.scene.text.*;
  import javafx.stage.*;
  import javafx.stage.Stage;
  import javafx.geometry.*;
  import javafx.scene.image.Image;
  import javafx.scene.image.ImageView;
  import javafx.scene.control.RadioButton;
  import javafx.scene.control.ToggleGroup;
  import javafx.collections.*;
  import java.util.Random;
  import java.io.*;
  import java.util.Scanner;

  public class AdventureGameV4 extends Application
  {
    private Button runButton;

    private RadioButton fourEnemies;
    private RadioButton fiveEnemies;
    private RadioButton sixEnemies;
    private RadioButton oneRound;
    private RadioButton fiveRounds;
    private RadioButton tenRounds;
    private RadioButton twentyRounds;

    private ListView<String> playerList;
    private ListView<String> enemyList;
    private ListView<String> playerWeaponList;
    private ListView<String> enemyWeaponList;

    private ObservableList<String> playerSelection;
    private ObservableList<String> playerWeaponSelection;
    private ObservableList<String> enemySelection;
    private ObservableList<String> enemyWeaponSelection;

    private Image playerImg = null;
    private Image enemyImg = null;
    private Image playerWeaponImg = null;
    private Image enemyWeaponImg = null;

    private ImageView playerImgView;
    private ImageView enemyImgView;
    private ImageView playerWeaponImgView;
    private ImageView enemyWeaponImgView;

    private Stage stage;
    private Scene scene;

    private Player player;
    private Enemy enemy;
    private Weapon playerWeapon;
    private Weapon enemyWeapon;
    private String displayString = null;

    private int numEnemies;
    private int numEnemiesDefeated = 0;
    private int numBattlesWon = 0;
    private int numBattlesLost = 0;
    private int numRounds;

    //Could either use global variables, or set up these variables in an array
    //that's passed to each method they're used in
    private int paladinWins, paladinLosses;
    private int rogueWins, rogueLosses;
    private int jchanWins, jchanLosses;
    private int goblinWins, goblinLosses;
    private int skeletonWins, skeletonLosses;

    @Override
    public void start(Stage primaryStage)
    {
      //Load previous game data, if available
      try
      {
        loadData();
      }
      catch(IOException e)
      {
        MessageBox.show("Error loading data", "ERROR");
      }

      //Set stage
      stage = primaryStage;
      stage.setResizable(false);

      //Initialize text elements and specify font
      Text titleText = new Text("ADVENTURE GAME BATTLE SIMULATOR");
      titleText.setStyle("-fx-font: 24 arial;");

      Text playerText = new Text("PLAYER");
      playerText.setStyle("-fx-font: 16 arial;");

      Text optionsText = new Text("OPTIONS");
      optionsText.setStyle("-fx-font: 16 arial;");

      Text enemyText = new Text("ENEMY");
      enemyText.setStyle("-fx-font: 16 arial;");

      //Initialize labels
      Label numEnemyLabel = new Label("Number of enemies");
      Label numRoundsLabel = new Label("Number of rounds");

      //Initialize buttons and set actions
      Button playerButton = new Button("SELECT PLAYER");
      playerButton.setOnAction(e -> playerButton_Click());

      Button enemyButton = new Button("SELECT ENEMY");
      enemyButton.setOnAction(e -> enemyButton_Click());

      Button exitButton = new Button("EXIT");
      exitButton.setOnAction(e -> exitButton_Click());

      Button randomPlayerButton = new Button("RANDOMIZE PLAYER");
      randomPlayerButton.setOnAction(e -> randomPlayerButton_Click());

      Button randomEnemyButton = new Button("RANDOMIZE ENEMY");
      randomEnemyButton.setOnAction(e -> randomEnemyButton_Click());

      Button recordsButton = new Button("DISPLAY BATTLE RECORDS");
      recordsButton.setOnAction(e -> recordsButton_Click());

      Button resetDataButton = new Button("RESET BATTLE DATA");
      resetDataButton.setOnAction(e -> resetDataButton_Click());

      runButton = new Button("RUN SIMULATION");
      runButton.setDisable(true);
      runButton.setOnAction(e -> runButton_Click());

      //Initialize radio buttons & toggle groups
      fourEnemies = new RadioButton("4 enemies");
      fiveEnemies = new RadioButton("5 enemies");
      sixEnemies = new RadioButton("6 enemies");
      ToggleGroup numEnemyGroup = new ToggleGroup();
      fourEnemies.setSelected(true); //Set default selection
      fourEnemies.setToggleGroup(numEnemyGroup);
      fiveEnemies.setToggleGroup(numEnemyGroup);
      sixEnemies.setToggleGroup(numEnemyGroup);

      oneRound = new RadioButton("1 round");
      fiveRounds = new RadioButton("5 rounds");
      tenRounds = new RadioButton("10 rounds");
      twentyRounds = new RadioButton("20 rounds");
      ToggleGroup numRoundsGroup = new ToggleGroup();
      oneRound.setSelected(true); //Set default selection
      oneRound.setToggleGroup(numRoundsGroup);
      fiveRounds.setToggleGroup(numRoundsGroup);
      tenRounds.setToggleGroup(numRoundsGroup);
      twentyRounds.setToggleGroup(numRoundsGroup);

      //Initialize listviews
      playerList = new ListView<String>();
      playerList.getItems().addAll("Paladin", "Rogue", "Jackie Chan");

      enemyList = new ListView<String>();
      enemyList.getItems().addAll("Goblin", "Skeleton");

      playerWeaponList = new ListView<String>();
      playerWeaponList.getItems().addAll("Mace", "Short Sword", "Long Sword", "Axe");

      enemyWeaponList = new ListView<String>();
      enemyWeaponList.getItems().addAll("Mace", "Short Sword", "Long Sword", "Axe");

      //Initialize image views
      playerImgView = new ImageView();
      enemyImgView = new ImageView();
      playerWeaponImgView = new ImageView();
      enemyWeaponImgView = new ImageView();

      //Initialize panes
      //HBox for top portion of borderpane
      HBox topHBox = new HBox(titleText);
      topHBox.setAlignment(Pos.TOP_CENTER);

      //HBox for bottom portion of borderpane
      HBox bottomHBox = new HBox(50, runButton, exitButton);
      bottomHBox.setAlignment(Pos.CENTER);

      //HBox for SELECT PLAYER and RANDOMIZE PLAYER buttons
      HBox playerButtonHBox = new HBox(10, playerButton,randomPlayerButton);
      playerButtonHBox.setAlignment(Pos.CENTER);

      //HBox for SELECT ENEMY and RANDOMIZE ENEMY buttons
      HBox enemyButtonHBox = new HBox(10, enemyButton, randomEnemyButton);
      enemyButtonHBox.setAlignment(Pos.CENTER);

      //HBox for DISPLAY BATTLE RECORDS button and RESET BATTLE DATA buttons
      HBox dataButtonBox = new HBox(50, recordsButton, resetDataButton);
      dataButtonBox.setAlignment(Pos.CENTER);

      //VBox for the two rows of buttons on bottom of window
      VBox bottomButtonVBox = new VBox(10, bottomHBox, dataButtonBox);
      bottomButtonVBox.setAlignment(Pos.CENTER);

      //VBoxes for radio buttons
      VBox radioVBox1 = new VBox(10, numEnemyLabel, fourEnemies, fiveEnemies, sixEnemies);
      VBox radioVBox2 = new VBox(10, numRoundsLabel, oneRound, fiveRounds, tenRounds, twentyRounds);

      //VBox for left portion of borderpane
      VBox leftVBox = new VBox(10, playerText, playerList, playerImgView, playerWeaponList, playerWeaponImgView, playerButtonHBox);
      leftVBox.setAlignment(Pos.TOP_CENTER);

      //VBox for right portion of borderpane
      VBox rightVBox = new VBox(10, enemyText, enemyList, enemyImgView, enemyWeaponList, enemyWeaponImgView, enemyButtonHBox);
      rightVBox.setAlignment(Pos.TOP_CENTER);

      //VBox for center portion of borderpane
      VBox centerVBox = new VBox(10, optionsText, radioVBox1, radioVBox2);
      centerVBox.setAlignment(Pos.TOP_CENTER);
      centerVBox.setMargin(radioVBox1, new Insets(20));
      centerVBox.setMargin(radioVBox2, new Insets(20));

      //Set portions of borderpane and specify spacing
      BorderPane pane = new BorderPane();
      pane.setPrefHeight(800);
      pane.setTop(topHBox);
      pane.setBottom(bottomButtonVBox);
      pane.setLeft(leftVBox);
      pane.setRight(rightVBox);
      pane.setCenter(centerVBox);
      pane.setMargin(bottomButtonVBox, new Insets(20));
      pane.setMargin(leftVBox, new Insets(20));
      pane.setMargin(rightVBox, new Insets(20));
      pane.setMargin(centerVBox, new Insets(20));

      //Set scene
      scene = new Scene(pane);
      stage.setScene(scene);
      stage.show();
    }

    /**
    The playerButton_Click method is an event handler for the playerButton. It gets the player
    and weapon objects that are selected by the user or by the randomize function.
    */

    public void playerButton_Click()
    {
      playerSelection = playerList.getSelectionModel().getSelectedItems(); //Get selected player from list view
      playerWeaponSelection = playerWeaponList.getSelectionModel().getSelectedItems(); //Get selected weapon from list view

      //If neither the player or weapon were selected, display error message
      if(playerSelection.isEmpty() && playerWeaponSelection.isEmpty())
      {
        MessageBox.show("You must select a player and a weapon.", "ERROR");
      }
      //If the player wasn't selected, display error message
      else if(playerSelection.isEmpty())
      {
        MessageBox.show("You must select a player.", "ERROR");
      }
      //If the weapon wasn't selected, display error message
      else if(playerWeaponSelection.isEmpty())
      {
        MessageBox.show("You must select a weapon.", "ERROR");
      }
      //Only if both the player and weapon are selected, get selections
      else
      {
        player = new Player(playerSelection.get(0)); //Set player object
        playerImg = player.getImageCopy(); //Get image based on selected player
        playerWeapon = new Weapon(playerWeaponSelection.get(0)); //Initialize player's weapon
        player.setWeapon(playerWeapon); //Set player's weapon
        playerWeaponImg = playerWeapon.getImageCopy(); //Get image based on selected weapon

        //Set images in image views
        playerImgView.setImage(playerImg);
        playerWeaponImgView.setImage(playerWeaponImg);

        //If the enemy was already selected, then enable the run simulation button
        /*Note to self: Have to use weapon object here because the enemy object isn't set until
        the run simulation button is pressed (this is so a new enemy object can be created
        for each minion in a loop). Due to the way the enemy selection process is set up, an enemy weapon
        can't exist without the enemy being selected also, or else an error message is displayed.
        So if the enemy object is initialized, then everything is ready for simulation.*/
        if(!(enemyWeapon == null))
          runButton.setDisable(false);
      }
    }

    /**
    The enemyButton_Click() method is an event handler for enemyButton. It gets the selected enemy and weapon objects
    from the user or from the randomize function.
    */
    public void enemyButton_Click()
    {
      enemySelection = enemyList.getSelectionModel().getSelectedItems(); //Get selected enemy from list view
      enemyWeaponSelection = enemyWeaponList.getSelectionModel().getSelectedItems(); //Get selected weapon from list view

      //If neither the enemy nor weapon were selected, display error message
      if(enemySelection.isEmpty() && enemyWeaponSelection.isEmpty())
      {
        MessageBox.show("You must select an enemy and a weapon.", "ERROR");
      }
      //If the enemy wasn't selected, display error message
      else if(enemySelection.isEmpty())
      {
        MessageBox.show("You must select an enemy.", "ERROR");
      }
      //If the weapon wasn't selected, display an error
      else if(enemyWeaponSelection.isEmpty())
      {
        MessageBox.show("You must select a weapon.", "ERROR");
      }
      //Only if both the enemy and weapon are selected, get selections
      else
      {
        enemyImg = Character.getCharacterImage(enemySelection.get(0)); //Get image based on enemy selection
        enemyWeapon = new Weapon(enemyWeaponSelection.get(0)); //Initialize enemy's weapon
        enemyWeaponImg = enemyWeapon.getImageCopy(); //Get image based on selected weapon

        //Set images
        enemyImgView.setImage(enemyImg);
        enemyWeaponImgView.setImage(enemyWeaponImg);

        //Enable the run simulation button only if the player selection was made
        if(!(player == null))
          runButton.setDisable(false);
      }
    }

    /**
    The randomPlayerButton_Click method is an event handler for randomPlayerButton.
    It randomly selects a player and a weapon to use for battle.
    */
    public void randomPlayerButton_Click()
    {
      Random rand = new Random();
      int randPlayer = rand.nextInt(3); //Generate random number between 0-2
      int randWeapon = rand.nextInt(4); //Generate random number between 0-3

      playerList.getSelectionModel().select(randPlayer); //Select a random player on the list
      playerList.getFocusModel().focus(randPlayer); //Highlight the selected player
      player = new Player(playerList.getSelectionModel().getSelectedItem()); //Initialize the player object
      playerImg = player.getImageCopy(); //Get image based on selected player

      playerWeaponList.getSelectionModel().select(randWeapon); //Select a random weapon on the list
      playerWeaponList.getFocusModel().focus(randWeapon); //Highlight the selected weapon
      playerWeapon = new Weapon(playerWeaponList.getSelectionModel().getSelectedItem()); //Initialize player's weapon
      player.setWeapon(playerWeapon); //Set player's weapon
      playerWeaponImg = playerWeapon.getImageCopy(); //Get image based on selected weapon

      //Set images in image views
      playerImgView.setImage(playerImg);
      playerWeaponImgView.setImage(playerWeaponImg);

      //Only enable the run simulation button if the enemy was already selected
      if(!(enemyWeapon == null))
        runButton.setDisable(false);
    }

    /**
    The randomEnemyButton_Click method is an event handler for randomEnemyButton.
    It randomly selects an enemy and weapon to use for battle.
    */
    public void randomEnemyButton_Click()
    {
      Random rand = new Random();
      int randEnemy = rand.nextInt(2); //Generate a random number between 0-1
      int randWeapon = rand.nextInt(4); //Generate a random number between 0-3

      enemyList.getSelectionModel().select(randEnemy); //Select a random enemy on the list
      enemyList.getFocusModel().focus(randEnemy); //Highlight the selected weapon
      enemyImg = Character.getCharacterImage(enemyList.getSelectionModel().getSelectedItem()); //Get image based on enemy selection

      enemyWeaponList.getSelectionModel().select(randWeapon); //Select a random weapon on the list
      enemyWeaponList.getFocusModel().focus(randWeapon); //Highlight the selected weapon
      enemyWeapon = new Weapon(enemyWeaponList.getSelectionModel().getSelectedItem()); //Initialize enemy's weapon
      enemyWeaponImg = enemyWeapon.getImageCopy(); //Get image based on selected weapon

      //Set images in image views
      enemyImgView.setImage(enemyImg);
      enemyWeaponImgView.setImage(enemyWeaponImg);

      //Only enable the run simulation button if player was already selected
      if(!(playerWeapon == null))
        runButton.setDisable(false);
    }

    /**
    The runButton_Click method is an event handler for runButton. It simulates a
    battle sequence between player and a number of enemies and then displays the results.
    */
    public void runButton_Click()
    {
      numEnemies = getNumEnemies(); //Get number of enemies from radio buttons
      numRounds = getNumRounds(); //Get number of rounds from radio buttons
      /*Note to self: One radio button is set by default for each list (4 enemies and 1 round),
      so error handling is implicit in the way this is coded --> Don't need an "if button hasn't been selected" case */
      /*Another note to self: numEnemies and numRounds must be declared outside of all methods because they're used in
      multiple methods that don't have parameters*/

      //For each round
      for(int j = 1; j <= numRounds; j++)
      {
        //For each enemy
        for(int i = 1; i <= numEnemies; i++)
        {
          enemy = new Enemy(enemyList.getSelectionModel().getSelectedItem()); //Initialize enemy object
          enemy.setWeapon(enemyWeapon); //Set enemy's weapon
          player.battleMinion(enemy); //Fight enemy

          //If the player isn't defeated, bring on next enemy
          if(!player.isDefeated())
            numEnemiesDefeated++;
          //If the player was defeated, end this round
          else
          {
            break;
          }
        }

        //If the round is over and the player hasn't been defeated yet, then
        //the player must have beaten all the enemies and won the round
        if(!player.isDefeated())
        {
          numBattlesWon++; //Increase number of rounds won

          if(player.getType() == Character.Type.ROGUE) //If the player is Rogue
            rogueWins++; //Increase total number of wins
          else if(player.getType() == Character.Type.PALADIN) //If the player is Paladin
            paladinWins++; //Increase total number of wins
          else if(player.getType() == Character.Type.JACKIE_CHAN) //If the player is Jackie Chan
            jchanWins++; //Increase total number of wins

          //Since the player won, the enemy lost
          if(enemy.getType() == Character.Type.GOBLIN) //If enemy is Goblin
            goblinLosses++; //Increase total number of losses
          else if(enemy.getType() == Character.Type.SKELETON) //If enemy is Skeleton
            skeletonLosses++; //Increase total number of losses
        }
        //If the round is over and the player has been defeated, then the enemy must have won the round
        else
        {
          numBattlesLost++; //Increase number of rounds lost

          if(player.getType() == Character.Type.ROGUE) //If the player is Rogue
            rogueLosses++; //Increase total number of losses
          else if(player.getType() == Character.Type.PALADIN) //If the player is Paladin
            paladinLosses++; //Increase total number of losses
          else if(player.getType() == Character.Type.JACKIE_CHAN) //If the player is Jackie Chan
            jchanLosses++; //Increase total number of losses

          //Since the player lost, the enemy won
          if(enemy.getType() == Character.Type.GOBLIN) //If the enemy is Goblin
            goblinWins++; //Increase total number of wins
          else if(enemy.getType() == Character.Type.SKELETON) //If the enemy is Skeleton
            skeletonWins++; //Increase total number of wins
        }
        player.resetHP(); //Reset player HP after each round
      }

      //When battle is over, update the battle records with new win/loss data
      try
      {
        overWriteData();
      }
      catch(IOException e)
      {
        MessageBox.show("Error overwriting data", "ERROR");
      }

      //Show the results of the battle
      MessageBox.show(getDisplayString(), "SIMULATION RESULTS");

      //Reset the number of battles won/lost and the number of defeated enemies for next simulation
      numBattlesWon = 0;
      numBattlesLost = 0;
      numEnemiesDefeated = 0;
    }

    /**
    The recordsButton_Click method is an event handler for recordsButton. It shows the
    total number of wins and losses for each player and enemy in a message box.

    Note to self: Since the BattleData file is updated each time the simulation is executed, it
    contains the same data as what's already available in the program. It's unnecessary to open
    the file when the data stored in it is already stored in variables.
    */
    public void recordsButton_Click()
    {
      String recordsString = "Rogue: " + rogueWins + " W, " + rogueLosses + " L\n" +
                             "Paladin: " + paladinWins + " W, " + paladinLosses + " L\n" +
                             "Jackie Chan: " + jchanWins + " W, " + jchanLosses + " L\n" +
                             "Goblin: " + goblinWins + " W, " + goblinLosses + " L\n" +
                             "Skeleton: " + skeletonWins + " W, " + skeletonLosses + " L\n";
      MessageBox.show(recordsString, "BATTLE RECORDS");
    }

    public void resetDataButton_Click()
    {
      Stage stage = new Stage();
  		stage.initModality(Modality.APPLICATION_MODAL);
  		stage.setTitle("WARNING");
  		stage.setMinWidth(250);

  		Label lbl = new Label();
  		lbl.setText("You are about to erase all battle record data!\n" +
                  "Are you sure you want to continue?");

  		Button btnDelete = new Button("Yes, delete data");
  		btnDelete.setOnAction(new EventHandler<ActionEvent>()
                            {
                              public void handle(ActionEvent e)
                              {
                                rogueWins = 0;
                                rogueLosses = 0;
                                paladinWins = 0;
                                paladinLosses = 0;
                                jchanWins = 0;
                                jchanLosses = 0;
                                goblinWins = 0;
                                goblinLosses = 0;
                                skeletonWins = 0;
                                skeletonLosses = 0;

                                try
                                {
                                  overWriteData();
                                }
                                catch(IOException eIO)
                                {
                                  MessageBox.show("Error overwriting data", "ERROR");
                                }
                                stage.close();
                              }
                            });

      Button btnKeep = new Button("No, keep data");
      btnKeep.setOnAction(e -> stage.close());

      HBox buttonBox = new HBox(10, btnDelete, btnKeep);
      buttonBox.setAlignment(Pos.CENTER);

  		VBox pane = new VBox(20);
                  pane.setPadding(new Insets(30,20,30,20));
  		pane.getChildren().addAll(lbl, buttonBox);
  		pane.setAlignment(Pos.CENTER);

  		Scene scene = new Scene(pane);
  		stage.setScene(scene);
  		stage.showAndWait();
    }

    public void exitButton_Click()
    {
      stage.close();
    }

    /**
    The loadData method loads the saved battle data for each player and enemy and stores
    that information in variables.
    @exception IOException Thrown if the file attempted to be opened doesn't exist.
    */
    public void loadData() throws IOException
    {
      String filename = "BattleRecords.txt";
      String nextDataLine = null;
      String[] splitDataLine;
      int[] battleRecordData = new int[10];
      int i = 0;
      Boolean fileCreated = false;

      //Try to open file named BattleRecords.txt
      try
      {
        File recordFile = new File(filename);
        Scanner inputFile = new Scanner(recordFile);

        //While the file still has data available to read
        while(inputFile.hasNext())
        {
          nextDataLine = inputFile.nextLine(); //Get the next line
          splitDataLine = nextDataLine.split(" "); //Split the line at the spaces
          //Example of what's contained in each String array: {Paladin:, 3, W, 4, L}
          battleRecordData[i] = Integer.parseInt(splitDataLine[1]); //Index [1] is the number of wins for that player
          i++; //Go to next index of battleRecordData
          battleRecordData[i] = Integer.parseInt(splitDataLine[3]); //Index [3] is the number of losses for that player
          i++; //Go to next index of battleRecordData for when code loops back through
        }
        inputFile.close();
      }
      catch(FileNotFoundException e)
      {
        //If file doesn't exist, create a new one
        File newFile = new File(filename);
        fileCreated = newFile.createNewFile();
      }

      //Set the win/loss variables for each player and enemy based on their position in the file
      rogueWins = battleRecordData[0]; //Rogue is the first line
      rogueLosses = battleRecordData[1];

      paladinWins = battleRecordData[2]; //Paladin is the second line
      paladinLosses = battleRecordData[3];

      jchanWins = battleRecordData[4]; //Jackie Chan is the third line
      jchanLosses = battleRecordData[5];

      goblinWins = battleRecordData[6]; //Goblin is the fourth line
      goblinLosses = battleRecordData[7];

      skeletonWins = battleRecordData[8]; //Skeleton is the fifth line
      skeletonLosses = battleRecordData[9];
    }

    /**
    The overWriteData method overwrites whatever data is in the BattleRecords.txt file with new data.
    @exception IOException Thrown if there's an issue opening the file for writing.
    */
    public void overWriteData() throws IOException
    {
      String filename = "BattleRecords.txt";
      String recordsString = "Rogue: " + rogueWins + " W, " + rogueLosses + " L\n" +
                             "Paladin: " + paladinWins + " W, " + paladinLosses + " L\n" +
                             "JackieChan: " + jchanWins + " W, " + jchanLosses + " L\n" +
                             "Goblin: " + goblinWins + " W, " + goblinLosses + " L\n" +
                             "Skeleton: " + skeletonWins + " W, " + skeletonLosses + " L\n";

      try
      {
        FileWriter fwriter = new FileWriter(filename, false);
        fwriter.write(recordsString);
        fwriter.close();
      }
      catch(IOException e)
      {
        MessageBox.show("File writer error", "ERROR");
      }
    }



    public int getNumEnemies()
    {
      int numEnemies;

      if(fourEnemies.isSelected())
        numEnemies = 4;
      else if(fiveEnemies.isSelected())
        numEnemies = 5;
      else
        numEnemies = 6;

      return numEnemies;
    }

    public int getNumRounds()
    {
      int numRounds;

      if(oneRound.isSelected())
        numRounds = 1;
      else if(fiveRounds.isSelected())
        numRounds = 5;
      else if(tenRounds.isSelected())
        numRounds = 10;
      else
        numRounds = 20;

      return numRounds;
    }

    public String getDisplayString()
    {
      return "Number of enemies per round: " + numEnemies +
             "\nNumber of rounds: " + numRounds +
             "\nNumber of battles won: " + numBattlesWon +
             "\nNumber of battles lost: " + numBattlesLost +
             "\nTotal number of enemies defeated: " + numEnemiesDefeated;
    }

    public static void main(String[] args) throws IOException
    {
      Application.launch(args);
    }
  }
