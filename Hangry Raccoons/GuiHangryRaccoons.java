/*
 * Last updated: March 8, 2018
 * File: GuiHangryRaccoons.java
 * 
 * This program is used to construct the GUI that can 
 * be used in the Hangry Raccoons game. 
 */

//Imports
import java.util.Random;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.Line;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.io.FileInputStream;

public class GuiHangryRaccoons extends Application{

//================= Declare Global Variables=========================
	//********************** Global GUI Nodes *****************
	
	private double primaryStageWidth = 1000.0;
	private double primaryStageHeight = 1000.0;
	private double btnOpacity = .5;
	private double btnWidth = 100.0;
	private double btnHeight = 30.0;
	private Scene scene;
	//Background pane to hold everything and go in the scene
	private StackPane backgroundPane;
	
	//Global nodes for the main menu
	private MainMenuPane mainMenuPane;
	
	//Global nodes for the levels
	private LevelPane levelPane;
	private StackPane gameScreenPane;
	//Header for levels
	private Text clickCountText = new Text();
	private Text levelTitleText = new Text();
	private Text countDownText = new Text();
	//Narrative and gameplay texts
	private Text narrative =  new Text();
	private Text gameplay = new Text();
	//Clickable character
	private ImageView characterImageView;
	private ImageView sickCharacterImageView;
	private Button btCharacter = new Button();

	//End of level text
	private EndOfLevelText endOfLevelText = new EndOfLevelText();
	//Cursor Image 
	private Image[] imageCursorArray;
	private Background background;
	private int hotSpotDiv = 2; 
	
	//Global nodes for the map
	private MapPane mapPane = new MapPane();
	//Map buttons
	private Button btRevelle;
	private Button btMuir;
	private Button btMarshall;
	private Button btERC;
	private Button btWarren;
	private Button btSixth;
	private Button btGeisel;
	
	//******************** End Global GUI Nodes****************
	
	
	//************ Global Event Handler Variables **************
	
	//Create the EventHandler objects
	private PlayHandler playHandler = new PlayHandler();
	private QuitHandler quitHandler = new QuitHandler();
	private ClickCountHandler clickCountHandler = new ClickCountHandler();
	private RepeatMovementHandler repeatMovementHandler = 
			new RepeatMovementHandler();
	
	//********** End Global Event Handler Variables ************
	
	
	//****************** Other Global Variables ***************
	
	//Click variables
	private int clicksRequired;
	private int clickCount = 0;
	private int clicksLeft;
	//Time variables
	private int timeLimit;
	private int currentTimeLeft = 0;
	//Level variables
	private Level level;
	private int currentLevel;
	private int levelsUnlocked = 1;
	private final boolean WON_LEVEL = true;
	private final boolean LOST_LEVEL = false;
	//Count down variables
	private CountDown countDown;
	private boolean hasStarted = false;
	private boolean isDetergentPod = false;
	//Movement variables
	private PathTransition transition = new PathTransition();
	private Random isRandom = new Random();
	private int randomX, randomY;

	
	//************** End Other Global Variables ****************	
//======================End of Global Variables======================
	
//============================= Stage ================================
	/*
	 * Name: start(Stage primaryStage)
	 * 
	 * Purpose: The purpose of this method
	 * is to override the Application start
	 * method to construct the GUI for the 
	 * HangryRaccoons game. This will only 
	 * initialize the GUI and other methods
	 * will be used to update the GUI.
	 * 
	 * Return: void
	 */
	public void start(Stage primaryStage) {
		//Prepare the primaryStage
		setUpStage(primaryStage);

		//Start background music
		music();
		
		//Initialize to the main menu
		showMainMenu();
		
		//Show the primaryStage
		primaryStage.show();
	}
	
	/*
	 * Name: setUpStage(primaryStage)
	 * 
	 * Purpose: This is called by the call
	 * method to initialize the stage conditions.
	 * 
	 * Return: void
	 */
	public void setUpStage(Stage primaryStage) {
		//Create the backgroundPane
		backgroundPane = new StackPane();
		
		//Set up the scene for the backgroundPane
		scene = new Scene(backgroundPane);
		
		//Set up the primaryStage conditions
		primaryStage.setTitle("Hangry Raccoons!");
		primaryStage.setScene(scene);
		primaryStage.setHeight(primaryStageHeight);
		primaryStage.setWidth(primaryStageWidth);
	}
//========================== End Stage ===============================

//========================= Main Menu ===============================
	/*
	 * Name: showMainMenu()
	 * 
	 * Purpose: This is called whenever the GUI
	 * needs to change to that of the main menu.
	 * 
	 * Return: void
	 */
	public void showMainMenu() {
		//Clear the children of backgroundPane
		backgroundPane.getChildren().clear();
		
		//Instantiate mainMenuPane
		mainMenuPane = new MainMenuPane();

        // Initizalize Gif background to set to main menu.
		Image cloudVideo = new Image("cloud9.gif", primaryStageWidth,
					                primaryStageHeight, true, false);
		ImageView menuVideo = new ImageView(cloudVideo);
			                        backgroundPane.getChildren().add(menuVideo);
		backgroundPane.getChildren().add(mainMenuPane);
	}

	/*
	 * Name: MainMenuPane
	 * 
	 * Purpose: The purpose of this class is to create
	 * a MainMenuPane to show the main menu and hold
	 * the main menu buttons
	 */
	public class MainMenuPane extends BorderPane{	
		//Constructor
		public MainMenuPane() {

			//Creates path for the play and quit,
			// buttons to move on
			PathTransition transitionPlay = new PathTransition();
			PathTransition transitionQuit = new PathTransition();

			//Create and customize the main menu title text
			Text mainMenuTitleText = new Text("Hangry Raccoons");
			mainMenuTitleText.setFont(Font.font("Goudy Stout", 
					FontWeight.BOLD, 30));
			mainMenuTitleText.setFill(Color.rgb(1, 3, 150));

			//Create an GridPane to hold the main menu title
			GridPane mainMenuTitlePane = new GridPane();
			mainMenuTitlePane.add(mainMenuTitleText, 0, 0);
			mainMenuTitlePane.setAlignment(Pos.CENTER);
			mainMenuTitlePane.setPadding(new Insets(10, 10, 10, 10));
			//Add the main menu title pane to the header of the main menu
			this.setTop(mainMenuTitlePane);
			
			//Create main menu buttons to put in the center
			Button btPlay = new Button();
			Button btQuit = new Button();

			/* Clears the background, 
			 * the play and quit button
			 */ 

		    Color clear = new Color(0,0,0,0);	
			CornerRadii cornerRadii = new CornerRadii(0.5);
			BackgroundFill backgroundFill = new BackgroundFill(clear, 
			                                    cornerRadii, new Insets(0,0,0,0));
			background = new Background(backgroundFill);
			

			//Play cloud image implementation 
			Image PlayCloud = new Image("PlayCloud.png", primaryStageWidth,
					                     primaryStageHeight, true, false);
     		ImageView PlayButton = new ImageView(PlayCloud);
			btPlay.setGraphic(PlayButton);
			btPlay.setBackground(background);
			PlayButton.setFitHeight(80);
			PlayButton.setFitWidth(150);

			//Quit cloud image implementation 
			Image QuitCloud = new Image("QuitCloud.png", primaryStageWidth,
					                     primaryStageHeight, true, false);
			ImageView QuitButton = new ImageView(QuitCloud);
			btQuit.setGraphic(QuitButton);
			btQuit.setBackground(background);
			QuitButton.setFitHeight(80);
			QuitButton.setFitWidth(150);

			//Create a stack pane to hold the buttons
			GridPane mainMenuCenterPane = new GridPane();
			mainMenuCenterPane.setAlignment(Pos.CENTER);
			mainMenuCenterPane.setVgap(100);
			mainMenuCenterPane.add(btPlay, 0, 0);
			mainMenuCenterPane.add(btQuit, 0, 1);
			//Add the main menu center pane to the center of the main menu
			this.setCenter(mainMenuCenterPane);
			
			//Set on action calls for the buttons
			btPlay.setOnAction(playHandler);
			btQuit.setOnAction(quitHandler);

			//Set Play button to line
			// so the cloud moves in a line path
			Line PlayLine = new Line();
			PlayLine.setStartX(-500);
			PlayLine.setStartY(0);
			PlayLine.setEndX(100);
			PlayLine.setEndY(0);
			transitionPlay.setNode(btPlay);
			transitionPlay.setPath(PlayLine);
			transitionPlay.setDuration(Duration.seconds(5));
			transitionPlay.play();

			//Adding Quit button to Line
			// so the cloud moves in a line path
			Line QuitLine = new Line();
			QuitLine.setStartX(-500);
			QuitLine.setStartY(0);
			QuitLine.setEndX(100);
			QuitLine.setEndY(0);
			transitionQuit.setNode(btQuit);
			transitionQuit.setPath(QuitLine);
			transitionQuit.setDuration(Duration.seconds(5));
			transitionQuit.play();
		}
	}
//========================= End Main Menu ===========================
	
//============================= Level ===============================
	
	/*
	 * Name: loadLevel(int levelNumber)
	 * 
	 * Purpose: This is called whenever the GUI
	 * needs to change to that of a particular level.
	 * 
	 * Return: void
	 */
	public void loadLevel(int levelNumber) {
		setValues(levelNumber);
		
		//Clear the children of backgroundPane
		backgroundPane.getChildren().clear();
		
		//Create level
		createLevel(levelNumber);
		
		//Add the levelPane to the backgroundPane
		backgroundPane.getChildren().add(levelPane);
	}
	
	/*
	 * Name: createLevel(int levelNumber)
	 * 
	 * Purpose: This is called to set levelPane
	 * to the a Pane dependent on the levelNumber.
	 */
	public void createLevel(int levelNumber) {
		switch(levelNumber) {
		case 1:
			levelPane = new LevelOnePane();
			break;
		case 2:
			levelPane = new LevelTwoPane();
			break;
		case 3:
			levelPane = new LevelThreePane();
			break;
		case 4:
			levelPane = new LevelFourPane();
			break;
		case 5:
			levelPane = new LevelFivePane();
			break;
		case 6:
			levelPane = new LevelSixPane();
			break;
		case 7:
			levelPane = new LevelSevenPane();
			break;
		//If it reaches here, something went wrong
		default: levelPane = new LevelPane();
		}
		
		//Show the clicks required and the time limit for the level
		clickCountText.setText("Clicks Left: " + 
				Integer.toString(clicksRequired));
		countDownText.setText("Time Left: " +
				Integer.toString(timeLimit));
		
		//Make Character not clickable and not visible until start is pressed
		btCharacter.setDisable(true);
		btCharacter.setOpacity(0);
	}

	/*
	 * Name: beginLevel(int levelNumber)
	 * 
	 * Purpose: This is called whenever the level
	 * needs to start.
	 * 
	 * Return: void
	 */
	public void beginLevel(int levelNumber) {
		loadLevel(levelNumber);
		narrative.setText("");

		//Make btCharacter visable and clickable
		btCharacter.setOpacity(1);
		btCharacter.setDisable(false);
		//Start the countDown if the level has not been started
		if(!hasStarted) {
			startCountDown();
			levelPane.movement();
		}
	}
	
	/*
	 * Name: setValues(int levelNumber)
	 * 
	 * Purpose: This is called to set/reset values 
	 * dependent on the loaded level
	 */
	public void setValues(int levelNumber) {
		isDetergentPod = false;
		currentLevel = levelNumber;
		clickCount = 0;
		
		Level level = new Level(levelNumber);
		clicksRequired = level.getClicksRequired();
		timeLimit = level.getTimeLimit();
	}

	/*
	 * Name: endLevel()
	 * 
	 * Purpose: This is called whenever a 
	 * level ends and updates the GUI depending
	 * on if the level was won or lost
	 */
	public void endLevel(boolean win) {
		
		//Cancel the countDown task
		if(countDown.isRunning()) {
			countDown.cancel();
		}
		transition.stop();
		
		//Update the gameScreenPane
		gameScreenPane.getChildren().clear();
		if(win) {
			gameScreenPane.getChildren().add(endOfLevelText.win());
			//Unlock next level if current level is the highest unlocked
			if(currentLevel == levelsUnlocked) {
				levelsUnlocked++;
				updateMap();
			}
		}
		else {
			gameScreenPane.getChildren().add(endOfLevelText.lose());
		}
		scene.setCursor(null);
		//Clear the countDownText and clickCountText
		countDownText.setText("");
		clickCountText.setText("");
		hasStarted = false;
	}
	/*
	 * Name: changeCursor
	 * creates a list of Images for food objects,
	 * plus laundry detergent, and then creates ImageView
	 * that will be randomly assigned to the cursor.
     * The if/else statements in the bottom, handle 
     * adding laundry detergent as a toxic food item as
     * the level's difficulty inccrease (currentLevel > 4). 
	 */	
	public void changeCursor() {

		/* Creates an array for, 
		 * cursor implementation. 
		 */

		imageCursorArray = new Image[] {	
				new Image("Burger.png"), new Image("Taco.png"),
				new Image("Pizza.png"), new Image("Fries.png"), 
				new Image("IceCream.png"), new Image("Candy.png"),
				new Image("Soda.png"), new Image("Waffles.png"), 
				new Image("Cheese.png"), new Image("DetergentPod.png"),
			    new Image("DetergentPod.png"), new Image("DetergentPod.png"),
			    new Image("DetergentPod.png"), new Image("DetergentPod.png"),
			    new Image("DetergentPod.png"), new Image("DetergentPod.png")
			};

	    int randomIndex = 0;  
        Image imageNow = new Image("Burger.png");
        ImageCursor imageCursor =  new ImageCursor(imageNow, 
		   	                                       imageNow.getWidth()/hotSpotDiv, 
		   	                                       imageNow.getHeight()/hotSpotDiv);

        if(currentLevel < 4){
		  randomIndex = isRandom.nextInt(imageCursorArray.length - 7);
	      imageNow = imageCursorArray[randomIndex];
	      imageCursor = new ImageCursor(imageNow, 
		   	                         imageNow.getWidth()/hotSpotDiv, 
		   	                         imageNow.getHeight()/hotSpotDiv);
	      scene.setCursor(imageCursor);

		} else {
			int indexChange = currentLevel % 7;
			if (isDetergentPod == false){
		        randomIndex = isRandom.nextInt(imageCursorArray.length - 
		        		indexChange);
		        imageNow = imageCursorArray[randomIndex];

		        if(imageNow == imageCursorArray[9] ||
		           imageNow== imageCursorArray[10] ||
		           imageNow == imageCursorArray[11]){
		           isDetergentPod = true;
		        }
		    }else{ 
		      imageNow = imageCursorArray[9];     
		    }   
		    imageCursor = new ImageCursor(imageNow, 
		   	                              imageNow.getWidth()/hotSpotDiv, 
		   	                              imageNow.getHeight()/hotSpotDiv);
		    scene.setCursor(imageCursor);
		}



	}

	/*
	 * Name: characterClicked
	 * 
	 * Purpose: This method is ran when
	 * the character is clicked. This will
	 * increment the clickCount and update 
	 * the clickCountText
	 */
	public void characterClicked() {
		//Increment clickCount and update the Text for it
	  if(isDetergentPod==false){	
		clickCount += 1;
		clicksLeft =  clicksRequired - clickCount;
		clickCountText.setText("Clicks Left: " + 
				Integer.toString(clicksLeft));
		if(btCharacter.getGraphic() != characterImageView) {
			btCharacter.setGraphic(characterImageView);
		}
	  }
	  else {
	  	clickCount -= 1;
	  	clicksLeft =  clicksRequired - clickCount;
		clickCountText.setText("Clicks Left: " + 
				Integer.toString(clicksLeft));
		if(btCharacter.getGraphic() != sickCharacterImageView) {
			btCharacter.setGraphic(sickCharacterImageView);
		}		
	  }	
	}

	/*
	 * Name: EndOfLevelText
	 * 
	 * Purpose: To create a Text that will display
	 * a message base on if the user wins the level
	 * or not
	 */
	public class EndOfLevelText extends Text{
		//Constructor
		public EndOfLevelText() {
			this.setFont(Font.font("Elephant",
					FontWeight.SEMI_BOLD, 20));
			this.setFill(Color.rgb(20, 39, 98));
			this.setWrappingWidth(0.75 * primaryStageWidth);
		}
		
		//Set winner text
		public Text win() {
			if(currentLevel == 7) {
				this.setText("Congratulations! You have beaten"
						+ " the game. We hope you have enjoyed"
						+ " your adventure to feed our beloved"
						+ " trash bandits");
				return this;
			}
			else {
				String string = Integer.toString(currentLevel);
				this.setText("Congratulations! You have"
						+ " won level " + string + " and"
						+ " can move onto the next level."
						+ " Go back to the map and the next"
						+ " level will be unlocked.");
				return this;
			}
		}
		
		//Set loser text
		public Text lose() {
			this.setText("Oops! You lost, please click the"
					+ " \"Start\" button to try again.");
			return this;
		}
	}
	
	/*
	 * Name: LevelPane
	 * 
	 * Purpose: The purpose of this class is to create
	 * a LevelPane to show the level being played
	 */
	public class LevelPane extends BorderPane {
	
		double BUTTON_WIDTH = 50.0;
		final double FOOTER_BUTTON_HGAP = 
				primaryStageWidth / 5;
		Button btStart = new Button("Start");
		Button btMap = new Button("Map");
		//Constructor
		public LevelPane() {
			//Clear Background to be used for characters
			//Transparent color
			Color clear = new Color(0,0,0,0);
			//CornerRadii that does not matter since the color will be clear
			CornerRadii cornerRadii = new CornerRadii(0.5);
			BackgroundFill backgroundFill = new BackgroundFill(clear, 
					                cornerRadii, new Insets(0,0,0,0));
			background = new Background(backgroundFill);
			
			//Set up level header
			//Customize the levelTitleText
			levelTitleText.setFont(Font.font("Impact",
					FontWeight.BOLD, 40));
			levelTitleText.setFill(Color.rgb(20, 39, 98));
			//Customize the click count text
			clickCountText.setText("Clicks Left: ");
			clickCountText.setFont(Font.font("Impact",
					FontWeight.BOLD, 20));
			clickCountText.setFill(Color.rgb(255, 186, 1));
			//Customize the countDown Text
			countDownText.setText("Time Left: ");
			countDownText.setFont(Font.font("Impact",
					FontWeight.BOLD, 20));
			countDownText.setFill(Color.rgb(255, 186, 1));
			//Create a GridPane to hold the header
			GridPane levelHeader = new GridPane();
			levelHeader.add(countDownText, 0, 0);
			levelHeader.add(levelTitleText, 1, 0);
			levelHeader.add(clickCountText, 2, 0);
			levelHeader.add(narrative, 0, 1);
			levelHeader.setColumnSpan(narrative, 3);
			ColumnConstraints columnIn = new ColumnConstraints();
			ColumnConstraints columnOut = new ColumnConstraints();
			columnIn.setPercentWidth(60);
			columnIn.setHalignment(HPos.CENTER);
			columnOut.setPercentWidth(20);
			columnOut.setHalignment(HPos.CENTER);
			narrative.setWrappingWidth(primaryStageHeight * 0.80);
			narrative.setFill(Color.BLACK);
			narrative.setFont(Font.font("Impact",
					FontWeight.BOLD, 20));
			
			

			levelHeader.getColumnConstraints().addAll(columnOut,
					columnIn, columnOut);
			//Add the levelHeader to the top of the levelPane
			this.setTop(levelHeader);
			
			//Set up level game screen
			//Set instantiate character image
			Image character = new Image("Raccoon.png");
			Image sickCharacter = new Image("SickRaccoon.png");
			//Create an ImageView to hold the character image
			characterImageView = new ImageView(character);
			characterImageView.setFitHeight(50);
			characterImageView.setFitWidth(50);
			sickCharacterImageView = new ImageView(sickCharacter);
			sickCharacterImageView.setFitHeight(50);
			sickCharacterImageView.setFitWidth(50);
			//Create a Button and set the graphic to the character
			btCharacter = new Button();
			btCharacter.setGraphic(characterImageView);
			btCharacter.setBackground(background);
			//Create a StackPane to hold the btnCharacter
			gameScreenPane = new StackPane();
			gameScreenPane.getChildren().addAll(btCharacter);
			//Add the gameScreenPane to the Center of the LevelPane
			this.setCenter(gameScreenPane);
			gameScreenPane.setOnMouseEntered(new CursorEnterGameHandler());
			gameScreenPane.setOnMouseExited(new CursorExitGameHandler());
			
			//Set up the level footer
			//Set button widths
			btStart.setMinWidth(BUTTON_WIDTH);
			btMap.setMinWidth(BUTTON_WIDTH);
			//Create a FlowPane to hold the buttons
			FlowPane levelFooterPane = new FlowPane();
			levelFooterPane.getChildren().add(btStart);
			levelFooterPane.getChildren().add(btMap);
			levelFooterPane.setHgap(FOOTER_BUTTON_HGAP);
			levelFooterPane.setAlignment(Pos.CENTER);
			//Add the levelFooterPane to the Bottom of the LevelPane
			this.setBottom(levelFooterPane);
			
			/*
			 * setOnAction() calls for the btMap and btCharacter. The 
			 * setOnAction() call for the btStart is dependent on the
			 * levelNumber so it is within each level's pane.
			 */
			btMap.setOnAction(playHandler);
			btCharacter.setOnAction(clickCountHandler);
		}
		
		public void movement() {
		}
	}
	

	/* Name: LevelOnePane
	 * 
	 * Purpose: Creates the logic for, 
	 * level one, also adds narrative
	 * 
	 */ 
	public class LevelOnePane extends LevelPane{
		//Constructor
		public LevelOnePane() {
            // Change the Level's Background
			Image revelle = new Image("Revelle.png", primaryStageWidth,
					primaryStageHeight, true, false);
			ImageView collegeBackground = new ImageView(revelle);
			collegeBackground.setFitHeight(primaryStageHeight);
			collegeBackground.setFitWidth(primaryStageWidth);
			backgroundPane.getChildren().add(collegeBackground);
            //Level header 
			levelTitleText.setText("Level One");
			//Narration for the level 
			narrative.setText("Our beloved trash bandits are getting very"
					+ " hangry and it is up to you, a brave triton, to feed"
					+ " them. Start in Revelle college where the hangry"
					+ " raccoons are the most calm. Now be a true hero"
					+ " and start feeding these raccons around campus!"
					+ " (To play click the \"Start\" button and click the"
					+ " raccoon until \"Click Count\" is zero. Make sure"
					+ " to do this in the given time limit)");
			
			//Set on action calls for the buttons
			btStart.setOnAction(new StartHandler(1));
		}
			//Creates a path for the character to move on 
			public void movement(){
		
				Rectangle path = new Rectangle(350,200);
				randomX = 300 - isRandom.nextInt(600);
				randomY = 300 - isRandom.nextInt(600);
				path.setTranslateX(randomX);
				path.setTranslateY(randomY);
				transition.setNode(btCharacter);
				transition.setDuration(Duration.seconds(10));
				transition.setPath(path);
				transition.play();
				transition.setOnFinished(repeatMovementHandler);
			}
		}
	/* Name: LevelTwoPane
	 * 
	 * Purpose: Creates the logic for,
	 * level two. 
	 * 
	 */ 
	public class LevelTwoPane extends LevelPane{
		//Constructor
		public LevelTwoPane() {

			// Change the Level's Background
			Image muir = new Image("Muir.png", primaryStageWidth,
					               primaryStageHeight, true, false);
			ImageView collegeBackground = new ImageView(muir);
			collegeBackground.setFitHeight(primaryStageHeight);
			collegeBackground.setFitWidth(primaryStageWidth);
			backgroundPane.getChildren().add(collegeBackground);

			levelTitleText.setText("Level Two");
			
			narrative.setText("You have successfully fed the raccoons"
					+ " in Revelle and have moved on to feeding the"
					+ " ones in Muir. However, these ones seem to be"
					+ " scared of humans and are hiding in their "
					+ " bushes. Search the bushes and find the raccoon"
					+ " to feed.");
			
			//set on action calls for buttons
			btStart.setOnAction(new StartHandler(2));
		}
		// Randomized movement in shape of circle
		
		public void movement(){
			Image bushRaccoon = new Image("bushRaccoon.png");
			ImageView bushRaccoonImageView = new ImageView(bushRaccoon);

			bushRaccoonImageView.setFitHeight(100);
			bushRaccoonImageView.setFitWidth(100);

			randomX = 300 - isRandom.nextInt(600);
			randomY = 300 - isRandom.nextInt(600);

			btCharacter.setTranslateX(randomX);
			btCharacter.setTranslateY(randomY);
			btCharacter.setGraphic(bushRaccoonImageView);
			btCharacter.setBackground(background);

			// Create Bushes in Random locations for the Raccoon to hide behind
			for (int i = 0; i <= 15;i++){	
			  Image bush = new Image("bush.png");
			  ImageView bushImageView = new ImageView(bush);

			  randomX = 350 - isRandom.nextInt(700);
			  randomY = 350 - isRandom.nextInt(700);
              //Creates path for Raccoons 
              bushImageView.setTranslateX(randomX);
			  bushImageView.setTranslateY(randomY);
			  bushImageView.setFitHeight(100);
			  bushImageView.setFitWidth(100);
			  gameScreenPane.getChildren().add(bushImageView);
			}

			
		}
	}
	/* Name: LevelThreePane
	 * 
	 * Purpose: Creates logic for, 
	 * level three 
	 *  
	 */
	public class LevelThreePane extends LevelPane{
		//Constructor
		public LevelThreePane() {
		    // Change the Level's Background
			Image marshall = new Image("Marshall.png", primaryStageWidth,
					                    primaryStageHeight, true, false);
			ImageView collegeBackground = new ImageView(marshall);
			collegeBackground.setFitHeight(primaryStageHeight);
			collegeBackground.setFitWidth(primaryStageWidth);
			backgroundPane.getChildren().add(collegeBackground);

			//Adds narrative for level three
			levelTitleText.setText("Level Three");
			
			narrative.setText("You head on to Marshall where the hangry"
					+ " raccoons have broken into OVT and raided the"
					+ " coffee and tea reserves. They are now super hyper"
					+ " and are running all around in circles. Feed them"
					+ " to calm them down and put them into food comas"
					+ " before they cause any more damage!");

			btStart.setOnAction(new StartHandler(3));
		}
			//Creates movement for level 3
		public void movement(){
			
		    Circle path = new Circle(100);
		
			randomX = 300 - isRandom.nextInt(600);
			randomY = 300 - isRandom.nextInt(600);
			
			path.setTranslateY(randomY);
			path.setTranslateX(randomX);
			
			transition.setNode(btCharacter);
			transition.setDuration(Duration.seconds(3));
			transition.setPath(path);
			transition.play();
			transition.setOnFinished(repeatMovementHandler);
		
        }
	}
	/* Name:LevelFourPane
	 * 
	 * Purpose: creates the logic for
	 * level four
	 */
	public class LevelFourPane extends LevelPane{
			//Constructor
		public LevelFourPane() {
		    // Change the Level's Background
		  Image erc = new Image("ERC.png", primaryStageWidth,
			                     primaryStageHeight, true, false);
		  ImageView collegeBackground = new ImageView(erc);
		  collegeBackground.setFitHeight(primaryStageHeight);
		  collegeBackground.setFitWidth(primaryStageWidth);
		  backgroundPane.getChildren().add(collegeBackground);
          
          levelTitleText.setText("Level Four");
          	//Narration for level 4 
          narrative.setText("You now set your sights on ERC,"
          		+ " unfortunately your food reserves have been"
          		+ " spiked with a few detergent Pods. These are very"
          		+ " poisonous to the hangry raccoons and will "
          		+ " make them even more hangry! You MUST dispose of"
          		+ " the detergent pods in the laundry machines!");
          
          btStart.setOnAction(new StartHandler(4));
		}
		
		public void movement(){
		  Image washer = new Image("Washer.png");
		  ImageView washerImageView = new ImageView(washer);

		  washerImageView.setFitHeight(100);
		  washerImageView.setFitWidth(100);
		  //Creates washer button 
		  Button btWasher = new Button();
		  btWasher.setGraphic(washerImageView);
		  btWasher.setBackground(background);
		  randomX = 350 - isRandom.nextInt(700);
		  randomY = 350 - isRandom.nextInt(700);
		  btWasher.setTranslateX(randomX);
		  btWasher.setTranslateY(randomY);
		  gameScreenPane.getChildren().add(btWasher);
		  btWasher.setOnAction(new WasherHandler());
		}	
	}
			
	/* Name:LevelFivePane
	 * 
	 * Purpose: creates the logic for
	 * level five
	 */
	public class LevelFivePane extends LevelPane{
		//Constructor
		public LevelFivePane() {
		  // Change the Level's Background
		  Image warren = new Image("Warren.png", primaryStageWidth,
			                     primaryStageHeight, true, false);
		  ImageView collegeBackground = new ImageView(warren);
		  collegeBackground.setFitHeight(primaryStageHeight);
		  collegeBackground.setFitWidth(primaryStageWidth);
		  backgroundPane.getChildren().add(collegeBackground);
		  //Level Narrative 
		  levelTitleText.setText("Level Five");
		  btStart.setOnAction(new StartHandler(5));
		  
          narrative.setText("You run down the hill to Warren! The"
          		+ " raccoons keep getting more and more HANGRY!!"
          		+ " KEEP GOING!");
		  //Adds washer image
		  Image washer = new Image("Washer.png");
		  ImageView washerImageView = new ImageView(washer);

		  washerImageView.setFitHeight(100);
		  washerImageView.setFitWidth(100);
		  //Creates a button for a washer
		  Button btWasher = new Button();
		  btWasher.setGraphic(washerImageView);
		  btWasher.setBackground(background);
		  randomX = 350 - isRandom.nextInt(700);
		  randomY = 350 - isRandom.nextInt(700);
		  btWasher.setTranslateX(randomX);
		  btWasher.setTranslateY(randomY);
		  gameScreenPane.getChildren().add(btWasher);
		  btWasher.setOnAction(new WasherHandler());
		}

		public void movement(){			  
		    Circle path = new Circle(100);
			
			randomX = 300 - isRandom.nextInt(600);
			randomY = 300 - isRandom.nextInt(600);
			
			path.setTranslateY(randomY);
			path.setTranslateX(randomX);
			
			transition.setNode(btCharacter);
			transition.setDuration(Duration.seconds(3));
			transition.setPath(path);
			transition.play();
			transition.setOnFinished(repeatMovementHandler);
		}	
	}
	/* Name: LevelSixPane
	 * 
	 * Purpose: Creates the logic for, 
	 * level six
	 * 
	 */
	public class LevelSixPane extends LevelPane{
		ImageView bushRaccoonImageView;
		
        //Constructor
        public LevelSixPane() {
           Image sixth = new Image("Sixth.png", primaryStageWidth,
			                     primaryStageHeight, true, false);
		  ImageView collegeBackground = new ImageView(sixth);
		  collegeBackground.setFitHeight(primaryStageHeight);
		  collegeBackground.setFitWidth(primaryStageWidth);
		  backgroundPane.getChildren().add(collegeBackground);

          levelTitleText.setText("Level Six");
          btStart.setOnAction(new StartHandler(6));
          
          Image washer = new Image("Washer.png");
		  ImageView washerImageView = new ImageView(washer);

		  washerImageView.setFitHeight(100);
		  washerImageView.setFitWidth(100);
		  //Creates button for washing machine 
		  Button btWasher = new Button();
		  btWasher.setGraphic(washerImageView);
		  btWasher.setBackground(background);
		  randomX = 350 - isRandom.nextInt(700);
		  randomY = 350 - isRandom.nextInt(700);
		  btWasher.setTranslateX(randomX);
		  btWasher.setTranslateY(randomY);
		  gameScreenPane.getChildren().add(btWasher);
		  btWasher.setOnAction(new WasherHandler());
		  //Narration for level six 
          narrative.setText("You are now in the home of the"
          		+ " trash bandits, Sixth College. There are "
          		+ " many napping in the bushes go and try"
          		+ " to feed the ones that want to be fed."
          		+ " BEWARE, waking a sleeping trash bandit"
          		+ " will scare them and they will run! But"
          		+ " feed them anyways, they must eat!");
          
          Image bushRaccoon = new Image("bushRaccoon.png");
          bushRaccoonImageView = new ImageView(bushRaccoon);
          bushRaccoonImageView.setFitHeight(100);
          bushRaccoonImageView.setFitWidth(100);
          randomX = 300 - isRandom.nextInt(600);
          randomY = 300 - isRandom.nextInt(600);
          btCharacter.setTranslateX(randomX);
          btCharacter.setTranslateY(randomY);
          btCharacter.setGraphic(bushRaccoonImageView);
          btCharacter.setBackground(background);

          Image bush = new Image("bush.png");
 
          for (int i = 0; i <= 16;i++){
            if ( i <= 9){
               ImageView bushImageView = new ImageView(bush);
               randomX = 350 - isRandom.nextInt(700);
               randomY = 350 - isRandom.nextInt(700);
               bushImageView.setTranslateX(randomX);
               bushImageView.setTranslateY(randomY);
               bushImageView.setFitHeight(100);
               bushImageView.setFitWidth(100);
               gameScreenPane.getChildren().add(bushImageView);
            }
            else
              bush = new Image("bushRaccoon.png");
              ImageView bushImageView = new ImageView(bush);
              randomX = 350 - isRandom.nextInt(700);
              randomY = 350 - isRandom.nextInt(700);
              bushImageView.setTranslateX(randomX);
              bushImageView.setTranslateY(randomY);
              bushImageView.setFitHeight(100);
              bushImageView.setFitWidth(100);
              gameScreenPane.getChildren().add(bushImageView);
            }
        }    
        
        public void movement(){
        	
        	double circleRadius;
        	if(btCharacter.getGraphic() != bushRaccoonImageView) {
        		circleRadius = 100;
    			randomX = 300 - isRandom.nextInt(600);
    			randomY = 300 - isRandom.nextInt(600);
        	}
        	else {
        		circleRadius = 0;
        		randomX = 0;
        		randomY = 0;
        	}
        	Circle path = new Circle(circleRadius);
        	
			
			path.setTranslateY(randomY);
			path.setTranslateX(randomX);
			
			transition.setNode(btCharacter);
			transition.setDuration(Duration.seconds(5));
			transition.setPath(path);
			transition.play();
			transition.setOnFinished(repeatMovementHandler);
        }
    }
	/* Name: LevelSevenPane
	 * 
	 * Purpose: Creates the pane, 
	 * for level seven. 
	 * 
	 */
	public class LevelSevenPane extends LevelPane{
		ImageView bushRaccoonImageView;
		//Constructor
		public LevelSevenPane() {
	           Image sixth = new Image("Geisel.png", primaryStageWidth,
	                     primaryStageHeight, true, false);
			ImageView collegeBackground = new ImageView(sixth);
			collegeBackground.setFitHeight(primaryStageHeight);
			collegeBackground.setFitWidth(primaryStageWidth);
			backgroundPane.getChildren().add(collegeBackground);
			
			levelTitleText.setText("Level Seven");
			btStart.setOnAction(new StartHandler(7));
			
			Image washer = new Image("Washer.png");
			ImageView washerImageView = new ImageView(washer);
			
			washerImageView.setFitHeight(100);
			washerImageView.setFitWidth(100);
			
			Button btWasher = new Button();
			btWasher.setGraphic(washerImageView);
			btWasher.setBackground(background);
			randomX = 350 - isRandom.nextInt(700);
			randomY = 350 - isRandom.nextInt(700);
			btWasher.setTranslateX(randomX);
			btWasher.setTranslateY(randomY);
			gameScreenPane.getChildren().add(btWasher);
			btWasher.setOnAction(new WasherHandler());
			
			narrative.setText("You have now fed all of the raccoons"
					+ " in all of the colleges, but some have set up "
					+ " camp at Geisel. Feed the last of the hangry "
					+ " raccoons and save this campus!");
			
			Image bushRaccoon = new Image("bushRaccoon.png");
			bushRaccoonImageView = new ImageView(bushRaccoon);
			bushRaccoonImageView.setFitHeight(100);
			bushRaccoonImageView.setFitWidth(100);
			randomX = 300 - isRandom.nextInt(600);
			randomY = 300 - isRandom.nextInt(600);
			btCharacter.setTranslateX(randomX);
			btCharacter.setTranslateY(randomY);
			btCharacter.setGraphic(bushRaccoonImageView);
			btCharacter.setBackground(background);
			
			Image bush = new Image("bush.png");
			
			for (int i = 0; i <= 30;i++){
			  if ( i <= 15){
			     ImageView bushImageView = new ImageView(bush);
			     randomX = 350 - isRandom.nextInt(700);
			     randomY = 350 - isRandom.nextInt(700);
			     bushImageView.setTranslateX(randomX);
			     bushImageView.setTranslateY(randomY);
			     bushImageView.setFitHeight(100);
			     bushImageView.setFitWidth(100);
			     gameScreenPane.getChildren().add(bushImageView);
			  }
			  else
			    bush = new Image("bushRaccoon.png");
			    ImageView bushImageView = new ImageView(bush);
			    randomX = 350 - isRandom.nextInt(700);
			    randomY = 350 - isRandom.nextInt(700);
			    bushImageView.setTranslateX(randomX);
			    bushImageView.setTranslateY(randomY);
			    bushImageView.setFitHeight(100);
			    bushImageView.setFitWidth(100);
			    gameScreenPane.getChildren().add(bushImageView);
			  }
			}    
			
		public void movement(){
			double circleRadius;
			if(btCharacter.getGraphic() != bushRaccoonImageView) {
				circleRadius = 100;
				randomX = 300 - isRandom.nextInt(600);
				randomY = 300 - isRandom.nextInt(600);
			}
			else {
				circleRadius = 0;
				randomX = 0;
				randomY = 0;
			}
			Circle path = new Circle(circleRadius);
			
			
			path.setTranslateY(randomY);
			path.setTranslateX(randomX);
			
			transition.setNode(btCharacter);
			transition.setDuration(Duration.seconds(3));
			transition.setPath(path);
			transition.play();
			transition.setOnFinished(repeatMovementHandler);
		}
	}
		
//=========================== End Level =============================
//=========================== Count Down =============================
	/*
	 * Name: startCountDown();
	 * 
	 * Purpose: This will start the count down
	 * timer for the level.
	 */
	public void startCountDown() {
		//Set the currentTimeLeft
		currentTimeLeft = timeLimit;
		
		//Initialize the countDown task
		countDown = new CountDown();
		
		//Handle if the count down succeeds (counts all the way to zero
		countDown.setOnSucceeded(new NoTimeLeftHandler());
		
		//Initialize and start the count down
		new Thread(countDown).start();
	}
	
	
	/*
	 * Name: CountDown
	 * 
	 * Purpose: This creates a Task to run a count
	 * down concurrently with the Application start()
	 * method. This mimics a count down by iterating
	 * through the timeLimit, sleeping for 1000 milliseconds
	 * and then updating the countDownText string.
	 */
	public class CountDown extends Task<Void> {
		// Override the Task<Void> call() method
		protected Void call() throws Exception{
			hasStarted = true;
			//Iterate through the timeLimit
			//for(int i = timeLimit; i > 0; i--) {
				//Try to sleep and catch the Interrupted Exception
				try {
					for(int i = timeLimit; i > 0; i--) {
					//Pause for 1 seconds
					Thread.sleep(1000);
					currentTimeLeft--;
					changeCursor();
					countDownText.setText("Time Left: " +
							Integer.toString(currentTimeLeft));
				    }

				 }catch(InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			//}
			
			hasStarted = false;
			return null;
		}
	}
//========================== End Count Down ==========================
	
//=============================== Map ===============================
	
	/*
	 * Name: showMap()
	 * 
	 * Purpose: This is called whenever the GUI
	 * needs to be changed to that of the map
	 * 
	 * Return: void
	 */
	public void showMap() {
		//If countDown exists and is running, cancel it
		if(countDown != null) {
			if(countDown.isRunning()) {
				countDown.cancel();
			}
		}
		transition.stop();
		scene.setCursor(null);
		clickCount = 0;
		
		//Clear the children of backgroundPane
		backgroundPane.getChildren().clear();
			
		//Add the mainMenuPane to the backgroundPane
		backgroundPane.getChildren().add(mapPane);
	}
	

	/*
	 * Name: updateMap()
	 * 
	 * Purpose: This is called when a new level
	 * is unlocked so that the map updates and
	 * makes the next highest level button visable
	 * and clickable
	 */
	public void updateMap() {
		switch(levelsUnlocked) {
		case 2: 
		    btMuir.setDisable(false);
		    btMuir.setOpacity(0.9);
		    break;
		case 3: 
		    btMarshall.setDisable(false);
		    btMarshall.setOpacity(0.9);
		    break;
		case 4: 
		    btERC.setDisable(false);
		    btERC.setOpacity(0.9);
		    break;
		case 5: 
		    btWarren.setDisable(false);
		    btWarren.setOpacity(0.9);
		    break;
		case 6: 
		    btSixth.setDisable(false);
		    btSixth.setOpacity(0.9);
		    break;
		case 7: 
		    btGeisel.setDisable(false);
		    btGeisel.setOpacity(0.9);
		    break;
		}
	}

		
	/*
	 * Name: MapPane
	 * 
	 * Purpose: This class is to create a MapPane
	 * to show the map of the levels being played.
	 */
	public class MapPane extends BorderPane{
		
		//Constructor to make the Map
		public MapPane() {
            
            //scene.setCursor(Cursor.type);
			//Create an the background Image and put it in an ImageView
			Image mapOfUCSD = new Image("Map.jpg", primaryStageWidth,
					primaryStageHeight, true, false);
			ImageView mapBackground = new ImageView(mapOfUCSD);
			mapBackground.setFitHeight(primaryStageHeight);
			mapBackground.setFitWidth(primaryStageWidth);
			
			
			//Create a GridPane to hold all the buttons for levels
			GridPane levelButtonsPane = new GridPane();

			//Creates and asigns location for Revelle button
			btRevelle = new Button("Revelle");
		    btRevelle.setStyle("-fx-background-color:white;");
		    btRevelle.setTranslateX(primaryStageWidth * 0.10);
		    btRevelle.setTranslateY(primaryStageHeight * 0.750);
		    btRevelle.setMinWidth(btnWidth);
		    btRevelle.setMinHeight(btnHeight);
		   	btRevelle.setOpacity(0.9);

			//Creates and asigns location for Revelle button
		    btMuir = new Button("Muir");
		    btMuir.setStyle("-fx-background-color:white;");
		    btMuir.setTranslateX(primaryStageWidth * 0.08);
		    btMuir.setTranslateY(primaryStageHeight * 0.50);
		    btMuir.setDisable(true);
		    btMuir.setMinWidth(btnWidth);
		    btMuir.setMinHeight(btnHeight);
		    btMuir.setOpacity(btnOpacity);
			
		    //Creates and asigns location for Revelle button
		    btMarshall = new Button("Marshall");
		    btMarshall.setStyle("-fx-background-color:white;");
		    btMarshall.setTranslateX(primaryStageWidth * 0.10);
		    btMarshall.setTranslateY(primaryStageHeight * 0.25);
		    btMarshall.setDisable(true);
		    btMarshall.setMinWidth(btnWidth);
		    btMarshall.setMinHeight(btnHeight);
		    btMarshall.setOpacity(btnOpacity);
	
			//Creates and asigns location for Revelle button
		    btERC = new Button("ERC");
		    btERC.setStyle("-fx-background-color:white;");
		    btERC.setTranslateX(primaryStageWidth * 0.10);
		    btERC.setTranslateY(primaryStageHeight * 0.05);
		    btERC.setDisable(true);
		    btERC.setMinWidth(btnWidth);
		    btERC.setMinHeight(btnHeight);
		    btERC.setOpacity(btnOpacity);
	
			//Creates and asigns location for Revelle button
		    btWarren = new Button("Warren");
		    btWarren.setStyle("-fx-background-color: white;");
		    btWarren.setTranslateX(primaryStageWidth * 0.55);
		    btWarren.setTranslateY(primaryStageHeight * 0.25);
		    btWarren.setDisable(true);
		    btWarren.setMinWidth(btnWidth);
		    btWarren.setMinHeight(btnHeight);
		    btWarren.setOpacity(btnOpacity);
	
		    //Creates and asigns location for Revelle button
		    btSixth = new Button("Sixth");
		    btSixth.setStyle("-fx-background-color: white;");
		    btSixth.setTranslateX(primaryStageWidth * 0.75);
		    btSixth.setTranslateY(primaryStageHeight * 0.60);
		    btSixth.setDisable(true);
		    btSixth.setMinWidth(btnWidth);
		    btSixth.setMinHeight(btnHeight);
		    btSixth.setOpacity(btnOpacity);
			
			//Creates and asigns location for Revelle button
		    btGeisel = new Button("Geisel");
		    btGeisel.setStyle("-fx-background-color:white;");
		    btGeisel.setTranslateX(primaryStageWidth * 0.37);
		    btGeisel.setTranslateY(primaryStageHeight * 0.35);
		    btGeisel.setDisable(true);
		    btGeisel.setMinWidth(btnWidth);
		    btGeisel.setMinHeight(btnHeight);
		    btGeisel.setOpacity(btnOpacity);
		
		    
		    // put the buttons inside the buttonLayer pane.
		    levelButtonsPane.getChildren().addAll(btRevelle, btMuir,
		    		btMarshall, btERC, btWarren, btSixth, btGeisel);
		    this.getChildren().addAll(mapBackground, levelButtonsPane);
		    
		    //Set on action handlers for buttons
		    btRevelle.setOnAction(new LevelHandler(1));
		    btMuir.setOnAction(new LevelHandler(2));
			btMarshall.setOnAction(new LevelHandler(3));
			btERC.setOnAction(new LevelHandler(4));
			btWarren.setOnAction(new LevelHandler(5));
			btSixth.setOnAction(new LevelHandler(6));
			btGeisel.setOnAction(new LevelHandler(7));
		}
	}

//============================= End Map =============================
	
//========================= Event Handlers ==========================

	/*
	 * Name: PlayHandler
	 * 
	 * Purpose: This is used to handle the btPlay
	 * action upon clicking
	 */
	class PlayHandler implements EventHandler<ActionEvent>{
		/*
		 * Name: handle(ActionEvent e)
		 * 
		 * Purpose: This will handle the clicking of
		 * the btPlay and bring you to the first level
		 * TODO: Ideally we will want to go to a map first
		 * and have the map take you to the levels
		 */
		public void handle(ActionEvent e) {
			//Show the Level GUI
			showMap();
		}
	}
	
	/*
	 * Name: QuitHandler
	 * 
	 * Purpose: This is used to handle the btQuit
	 * action upon clicking
	 */
	class QuitHandler implements EventHandler<ActionEvent>{
		/*
		 * Name: handle(ActionEvent e)
		 * 
		 * Purpose: This will handle the clicking of
		 * the btQuit and will close everything
		 * TODO: Ideally would will have it open up
		 * a new stage asking if the user is sure 
		 * they want to quit. Yes will close everything
		 * and No will return to the main menu
		 */
		public void handle(ActionEvent e) {
			System.exit(0);
		}
	}

	/*
	 * Name: StartHandler
	 * 
	 * Purpose: This is used to handle the btStart
	 * action upon clicking
	 */
	class StartHandler implements EventHandler<ActionEvent>{
		int levelNumber;
		
		//Constructor
		public StartHandler(int levelNumber) {
			this.levelNumber = levelNumber;
		}
		
		/*
		 * Name: handle(ActionEvent e)
		 * 
		 * Purpose: This will handle the clicking of
		 * the btStart and start the first level
		 */
		public void handle(ActionEvent e) {
			if(!hasStarted) {
				beginLevel(levelNumber);
			}
		}
	}

	/* Name: ClickCountHandler
	 * 
	 * Purpose: This is used to handle the btCharacter
	 * action upon clicking
	 */
	class ClickCountHandler implements EventHandler<ActionEvent>{
		/*
		 * Name: handle(ActionEvent e)
		 * 
		 * Purpose: This will handle the clicking of
		 * the btCharacter and will count the amount
		 * that the character is clicked
		 */
		public void handle(ActionEvent e) {			
			characterClicked();
		
			if(clicksLeft <= 0) {
				endLevel(WON_LEVEL);
			}
		}
	}
	
	class LevelHandler implements EventHandler<ActionEvent>{
		int levelNumber;
		
		//Constructor
		public LevelHandler(int levelNumber) {
			this.levelNumber = levelNumber;
		}
		/*
		 * Name: handle(ActionEvent e)
		 * 
		 * Purpose: This will handle the clicking of
		 * the btCharacter and will count the amount
		 * that the character is clicked
		 */
		public void handle(ActionEvent e) {
			loadLevel(levelNumber);
		}
	}
	
	class NoTimeLeftHandler implements EventHandler<WorkerStateEvent>{
		/*
		 * Name: handle(WorkerStateEvent e)
		 * 
		 * Purpose: This will handle the succeeding of the
		 * CountDown Task
		 */
		public void handle(WorkerStateEvent e) {
			endLevel(LOST_LEVEL);
		}
	}
	
	class RepeatMovementHandler implements EventHandler<ActionEvent>{
		/*
		 * Name: handle(ActionEvent e)
		 * 
		 * Purpose: This will call the levelPane.movement function
		 * again after its transition has completed.
		 */
		public void handle(ActionEvent e) {
			levelPane.movement();
		}
	}
	
	class CursorEnterGameHandler implements EventHandler<MouseEvent>{
		/*
		 * Name: handle(ActionEvent e)
		 * 
		 * Purpose: This will call the levelPane.movement function
		 * again after its transition has completed.
		 */
		public void handle(MouseEvent e) {
			changeCursor();
		}
	}
	
	class CursorExitGameHandler implements EventHandler<MouseEvent>{
		/*
		 * Name: handle(ActionEvent e)
		 * 
		 * Purpose: This will call the levelPane.movement function
		 * again after its transition has completed.
		 */
		public void handle(MouseEvent e) {
			scene.setCursor(null);
		}
	}

	class WasherHandler implements EventHandler<ActionEvent>{
		/*
		 * Name: handle(ActionEvent e)
		 * 
		 * Purpose: 
		 */
		public void handle(ActionEvent e) {
			isDetergentPod = false;
			changeCursor();
		}
	}

	public void music(){
// Convert the song file path to a string
String song = new String(new File("bg.wav").toURI().toString());
// set the path to a media variable
Media music = new Media(song);
// create media player
MediaPlayer bgMusic = new MediaPlayer(music);
//play music
bgMusic.play();

}

	
//====================== End Event Handlers ==========================
}

