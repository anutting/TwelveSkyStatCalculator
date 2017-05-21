package calculator;

import java.io.IOException;
import java.text.DecimalFormat;

import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import player.*;
import player.exception.FullStatPointsException;
import player.exception.NoAvailableStatPointsException;

public class BootCalc extends Application {
	//window
	private final GridPane grid = new GridPane();
	Scene scene = new Scene(new Group(), 220, 410); //500,320
	DecimalFormat df = new DecimalFormat("#");
	//layout
	private final HBox menuBox = new HBox();
	private final VBox statsDisplay = new VBox();
	private final HBox statsBox = new HBox();
	//buttons
	private final Button btnAddStr = new Button();
	private final Button btnAddDex = new Button();
	private final Button btnAddVit = new Button();
	private final Button btnAddChi = new Button();
	private final Button btnSubStr = new Button();
	private final Button btnSubDex = new Button();
	private final Button btnSubVit = new Button();
	private final Button btnSubChi = new Button();
	private final Button btnBloodClear = new Button();
	//boxes
	private final ChoiceBox<String> cbLevel = new ChoiceBox<String>();
	private final ChoiceBox<String> cbFaction = new ChoiceBox<String>();
	private final ChoiceBox<String> cbWeapon = new ChoiceBox<String>();
	//fields
	private final TextField strPoints = new TextField();
	private final TextField dexPoints = new TextField();
	private final TextField chiPoints = new TextField();
	private final TextField vitPoints = new TextField();
	private final TextField hp = new TextField();
	private final TextField chi = new TextField();
	private final TextField dmg = new TextField();
	private final TextField def = new TextField();
	private final TextField hitRate = new TextField();
	private final TextField dodge = new TextField();
	private final TextField element = new TextField();
	//labels
	private final Label statPoints = new Label();
	Label HP = new Label("HP");
	Label CHI = new Label("CHI");
	Label Defense = new Label("Defense");
	Label Damage = new Label("Damage");
	Label Dodge = new Label("Dodge");
	Label HitRate = new Label("Hit Rate");
	Label ele = new Label("Element");
	//Objects
	Player character;

    @Override
    public void start(Stage primaryStage) throws Exception {
    	character = new Player();
    
    	grid.setHgap(10);
       	grid.setVgap(10);
        grid.setPadding(new Insets(5,10,5,10));
        
        buildStatsDisplay();
        buildMenuBox();
        buildStatBox();
        VBox rows = new VBox();
        rows.setSpacing(5);
        rows.setPrefWidth(200);
        statsBox.setSpacing(10);
        rows.getChildren().addAll(menuBox,statPoints,statsBox,statsDisplay);
        grid.add(rows,0,0);
        
    	//set events
        cbFaction.setOnAction(onFactionChange);
        cbLevel.setOnAction(onLevelChange);
        cbWeapon.setOnAction(onWeaponChange);
        
        btnSubStr.setOnAction(subStatHandler);
        btnSubDex.setOnAction(subStatHandler);
        btnSubVit.setOnAction(subStatHandler);
        btnSubChi.setOnAction(subStatHandler);
        btnAddStr.setOnAction(addStatHandler);
        btnAddDex.setOnAction(addStatHandler);
        btnAddVit.setOnAction(addStatHandler);
        btnAddChi.setOnAction(addStatHandler);
        
        btnBloodClear.setOnAction(onBloodClear);
        
        //preselect boxes and trigger the above events
        cbFaction.getSelectionModel().select(0);
        cbLevel.getSelectionModel().select(0);
        cbWeapon.getSelectionModel().select(0);
        
        //establish the grid
        Group root = (Group)scene.getRoot();
        root.getChildren().add(grid);
        
        primaryStage.setTitle("TwelveSky Stat Calculator");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    private void buildStatBox(){
    	
    	VBox left = new VBox();
    	VBox right = new VBox();
    	Label str = new Label("Str");
     	Label dex = new Label("Dex");
     	Label chi = new Label("Chi");
     	Label vit = new Label("Vit");
     	
     	
     	strPoints.setPrefWidth(45);
     	strPoints.setEditable(false);
     	
     	dexPoints.setPrefWidth(45);
     	dexPoints.setEditable(false);
     	
     	chiPoints.setPrefWidth(45);
     	dexPoints.setEditable(false);
     	
     	vitPoints.setPrefWidth(45);
     	vitPoints.setEditable(false);
     	
     	VBox strBox = new VBox(); //contains label with hbox underneath
     	HBox strHBox = new HBox(); //contains textfield with button next to it
     	
     	
     	btnAddStr.setText("+");
     	btnAddStr.setId("Str");
     	btnSubStr.setText("-");
     	btnSubStr.setId("Str");
     	strHBox.getChildren().addAll(strPoints,btnAddStr,btnSubStr);
     	strBox.getChildren().addAll(str,strHBox);
     	
     	VBox dexBox = new VBox();
     	HBox dexHBox = new HBox();
     	
     	
     	btnAddDex.setText("+");
     	btnAddDex.setId("Dex");
     	btnSubDex.setText("-");
     	btnSubDex.setId("Dex");
     	dexHBox.getChildren().addAll(dexPoints,btnAddDex,btnSubDex);
     	dexBox.getChildren().addAll(dex,dexHBox);
     	
     	VBox chiBox = new VBox();
     	HBox chiHBox = new HBox();
     	
     	
     	btnAddChi.setText("+");
     	btnAddChi.setId("Chi");
     	btnSubChi.setText("-");
     	btnSubChi.setId("Chi");
     	chiHBox.getChildren().addAll(chiPoints,btnAddChi,btnSubChi);
     	chiBox.getChildren().addAll(chi,chiHBox);
     	
     	VBox vitBox = new VBox();
     	HBox vitHBox = new HBox();
     	
     	
     	btnAddVit.setText("+");
     	btnAddVit.setId("Vit");
     	btnSubVit.setText("-");
     	btnSubVit.setId("Vit");
     	vitHBox.getChildren().addAll(vitPoints,btnAddVit,btnSubVit);
     	vitBox.getChildren().addAll(vit,vitHBox);
    	
     	left.getChildren().addAll(vitBox,strBox);
     	right.getChildren().addAll(chiBox,dexBox);
     	statsBox.getChildren().addAll(left,right);
    	
    }
    
    private void buildMenuBox(){
    	
    	menuBox.setPadding(new Insets(0,10,0,0));
    	menuBox.setSpacing(10);
    	menuBox.setPrefWidth(120);
    	
        
        cbLevel.getItems().addAll(Player.getLevels());
        cbWeapon.getItems().addAll(FXCollections.observableArrayList("Offensive","Defensive","Ranged"));
        cbFaction.getItems().addAll(FXCollections.observableArrayList("Guanyin","Fujin","Jinong"));
        
        
        Label lblFaction = new Label("Faction");
        Label lblLevel = new Label("Level");
        Label lblWeapon = new Label("Weapon");
        
        VBox vbFaction = new VBox();
        vbFaction.getChildren().addAll(lblFaction, cbFaction);
        cbFaction.setPrefWidth(100);
        
        VBox vbLevel = new VBox();
        vbLevel.getChildren().addAll(lblLevel,cbLevel);
        cbLevel.setPrefWidth(100);
        
        VBox vbWeapon = new VBox();
        vbWeapon.getChildren().addAll(lblWeapon,cbWeapon);
        cbWeapon.setPrefWidth(100);
        
    	btnBloodClear.setText("Blood Clear");
    	btnBloodClear.setPrefWidth(100);
    	
    	
    	VBox left = new VBox();
    	VBox right = new VBox();
    	
    	right.setSpacing(17);
    	
    	left.getChildren().addAll(vbFaction,vbLevel);
    	right.getChildren().addAll(vbWeapon,btnBloodClear);
        
        //vbLeftMenu.getChildren().addAll(vbFaction,vbLevel,vbWeapon,statPoints,strBox,dexBox,vitBox,chiBox,btnBloodClear);
        menuBox.getChildren().addAll(left,right);
    	
    }
  
   
    
    public void buildStatsDisplay(){
    
    	
    	
    	//row 1
    	HBox row1 = new HBox();
    	VBox hpBox = new VBox();
    	VBox chiBox = new VBox();
    	
    	hp.setEditable(false);
    	hpBox.getChildren().addAll(HP,hp);
    	hpBox.setPrefWidth(90);
    	chi.setEditable(false);
    	chiBox.getChildren().addAll(CHI,chi);
    	chiBox.setPrefWidth(90);
    	
    	row1.getChildren().addAll(hpBox,chiBox);
    	row1.setSpacing(10);
    	
    	//row 2
    	HBox row2 = new HBox();
    	VBox dmgBox = new VBox();
    	VBox defBox = new VBox();
    	
    	dmg.setEditable(false);
    	dmgBox.getChildren().addAll(Damage,dmg);
    	dmgBox.setPrefWidth(90);
    	
    	def.setEditable(false);
    	defBox.getChildren().addAll(Defense,def);
    	defBox.setPrefWidth(90);
    	
    	row2.getChildren().addAll(dmgBox,defBox);
    	row2.setSpacing(10);
    	
    	//row 3
    	HBox row3 = new HBox();
    	VBox hitRateBox = new VBox();
    	VBox dodgeBox = new VBox();
    	
    	hitRate.setEditable(false);
    	hitRateBox.getChildren().addAll(HitRate,hitRate);
    	hitRateBox.setPrefWidth(90);
    	
    	dodge.setEditable(false);
    	dodgeBox.getChildren().addAll(Dodge,dodge);
    	dodgeBox.setPrefWidth(90);
    	
    	row3.getChildren().addAll(hitRateBox,dodgeBox);
    	row3.setSpacing(10);
    	
    	//row 4
    	HBox row4 = new HBox();
    	VBox eleBox = new VBox();
    	
    	element.setEditable(false);
    	eleBox.getChildren().addAll(ele,element);
    	eleBox.setPrefWidth(90);
    	
    	row4.getChildren().add(eleBox);
    	row4.setSpacing(10);
    	
    	statsDisplay.getChildren().addAll(row1,row2,row3,row4);
    	statsDisplay.setSpacing(7);
    	
    }
    
    private void refreshFields(){
    	
		hp.setText(df.format((character.getHp())));
		chi.setText(df.format((character.getChi())));
		dmg.setText(df.format(character.getDamage()));
		def.setText( Integer.toString(character.getDefense()));
		dodge.setText((df.format(character.getDodge())));
		hitRate.setText(df.format(character.getHitRate()));
		element.setText(Integer.toString(character.getEleDamage()));
		ele.setText(character.getEleType());
		
		chiPoints.setText( Integer.toString(character.getChiPoints()) );
    	vitPoints.setText( Integer.toString(character.getVit()) );
    	dexPoints.setText( Integer.toString(character.getDex()) );
    	strPoints.setText( Integer.toString(character.getStr()) );
    	
    	statPoints.setText( "Stat Points: " + Integer.toString(character.getStatPoints()) );
    }
    
    //Event handlers
    private EventHandler<ActionEvent> onFactionChange = new EventHandler<ActionEvent>(){
    	
		@Override
		public void handle(ActionEvent event) {
			
			String selectedValue = cbFaction.getValue();
			switch(selectedValue){
				case "Guanyin":{
					try {
						character = new Guanyin(character);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				case "Fujin":{
					try {
						character = new Fujin(character);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				case "Jinong":{
					try {
						character = new Jinong(character);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
			}
			refreshFields();
			character.printPlayerInfo();
		}
    	
    };
    
    private EventHandler<ActionEvent> onLevelChange = new EventHandler<ActionEvent>(){

		@Override
		public void handle(ActionEvent event) {
			
			Integer selectedLevel = cbLevel.getSelectionModel().getSelectedIndex() +1;
			
			character.setLevel(selectedLevel);
			String selectedValue = cbFaction.getValue();
			switch(selectedValue){
				case "Guanyin":{
					try {
						character = new Guanyin(character);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				case "Fujin":{
					try {
						character = new Fujin(character);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				case "Jinong":{
					try {
						character = new Jinong(character);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
			}
			character.printPlayerInfo();
			
			refreshFields();
		}
    	
    };
    

    private EventHandler<ActionEvent> addStatHandler = new EventHandler<ActionEvent>(){

		@Override
		public void handle(ActionEvent event) {
			Button source = (Button)event.getSource();
			String stat = source.getId();
			try{
				switch(stat){
					case "Str":{
						character.spendStatPoint(stat);
						character.addStr();
						break;
					}
					case "Dex":{
						character.spendStatPoint(stat);
						character.addDex();
						break;
					}
					case "Chi":{
						character.spendStatPoint(stat);
						character.addChi();
						break;
					}
					case "Vit":{
						character.spendStatPoint(stat);
						character.addVit();
						break;
					}
				}
				refreshFields();
			}catch(NoAvailableStatPointsException ex){
				System.out.println(ex.getMessage());
			}
			
			
		}
    	
    };
    
    private EventHandler<ActionEvent> subStatHandler = new EventHandler<ActionEvent>(){

		@Override
		public void handle(ActionEvent event) {
			Button source = (Button)event.getSource();
			String stat = source.getId();
			try{
				switch(stat){
					case "Str":{
						character.refundStatPoint(stat);
						character.subStr();
						break;
					}
					case "Dex":{
						character.refundStatPoint(stat);
						character.subDex();
						break;
					}
					case "Chi":{
						character.refundStatPoint(stat);
						character.subChi();
						break;
					}
					case "Vit":{
						character.refundStatPoint(stat);
						character.subVit();
						break;
					}
				}
				refreshFields();
			}catch(FullStatPointsException ex){
				System.out.println(ex.getMessage());
			}
			
			statPoints.setText( "Stat Points: " + Integer.toString(character.getStatPoints()) );
		}
			
    	
    };
    
    private EventHandler<ActionEvent> onWeaponChange = new EventHandler<ActionEvent>(){

		@Override
		public void handle(ActionEvent event) {
			String wep = cbWeapon.getValue();
			
			switch(wep){
				case "Offensive":{
					character.setWeapon(Player.Weapon.OFFENSIVE);
					break;
				}
				case "Defensive":{
					character.setWeapon(Player.Weapon.DEFENSIVE);
					break;
				}
				case "Ranged":{
					character.setWeapon(Player.Weapon.RANGED);
					break;
				}
				
			}
			
			
			
			refreshFields();
			
		}
    	
    };
    
    private EventHandler<ActionEvent> onBloodClear = new EventHandler<ActionEvent>(){

		@Override
		public void handle(ActionEvent event) {
			
			character.setLevel(character.getLevel());
			
			refreshFields();
			
		}
    	
    };
    

}
