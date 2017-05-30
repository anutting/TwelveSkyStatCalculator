package player;

import java.io.FileReader;
import java.io.IOException;
import com.opencsv.CSVReader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import player.exception.FullStatPointsException;
import player.exception.NoAvailableStatPointsException;


//base class for the three different types of players.
//Stores all of the constant values and player attributes
//most of the business logic is handled here based on the instance of Player
public class Player {
	
		public enum Weapon {OFFENSIVE,DEFENSIVE,RANGED};
		
		protected final int MAX_LEVEL = 145;
		protected String faction;
		private int level;
		private int statPoints;
		private int maxStatPoints;
		private int vit;
		private int dex;
		private int chiPoints;
		private int str;
		private int defense;
		private Double damage;
		private Double dodge;
		private Double hp;
		private Double chi;
		private Double hitRate;
		private int hpElixers;
		private int chiElixers;
		private int eleDamage;
		private String eleType;
		
		//constant values representing stats pulled from a csv.
		//level = index + 1;
		protected static String[] defenseValues;
		protected static String[] expValues;
		protected static  String[] baseHpValues;
		protected static String[] baseChiValues;
		
		private Double hpPerPoint;
		private  Double chiPerPoint;
		private int baseDmg;
		private Double offenseDmgPerPoint;
		private Double defenseDmgPerPoint;
		private Double rangeDmgPerPoint;
		private Double hitRatePerPoint;
		private Double dodgePerPoint;
		
		private Weapon weapon;
		
		
		public Player() throws IOException{
			this.level = 1; // setLevel() has dependencies that haven't loaded yet, so the variable is set manually.
			setVit(1);
			setDex(1);
			setStr(1);
			setChiPoints(1);
			setDodge(0.0);
			setHitRate(0.0);
			setHpElixers(0);
			setChiElixers(0);
			setEleDamage(0);
			setDefense(1);
			setStatPoints(0);
			setMaxStatPoints(0);
			setHpPerPoint(0d);
			setChiPerPoint(0d);
			pullValuesFromCSV();
			//defaulting to offensive wep
			setWeapon(Weapon.OFFENSIVE);
		}
		
		//only retrieves the values needed for all types of players. overloaded in subclasses to retrieve info needed for that particular player
		protected void pullValuesFromCSV() throws IOException{
			defenseValues = new String[MAX_LEVEL];
    		expValues = new String[MAX_LEVEL];
			CSVReader reader = new CSVReader(new FileReader("stat_constants.csv"));
	        String [] nextLine;
	        int i = 0;
	        while ((nextLine = reader.readNext()) != null) {
	        	if(nextLine[0].equals("Level")){ //ignore the title row
	        		continue;
	        	}
	        	
	    		defenseValues[i] = nextLine[9];
	            expValues[i] = nextLine[27];
	            
	            i++;
	        }
	        reader.close();
		}
		
		public static ObservableList<String> getLevels(){
			ObservableList<String> levels = FXCollections.observableArrayList ();
	        int mLvl = 1;
	        for(Integer i = 1; i <= 145; i++){
	        	String strLvl = Integer.toString(i);
	        	if(i >= 113){
	        		strLvl = ("M"+mLvl);
	        		mLvl++;
	        	}
	        	levels.add(strLvl);
	        }
			return levels;
		}
		
		
		
		public Double getHitRatePerPoint() {
			return hitRatePerPoint;
		}


		public void setHitRatePerPoint(Double hitRatePerPoint) {
			this.hitRatePerPoint = hitRatePerPoint;
		}


		public Double getDodgePerPoint() {
			return dodgePerPoint;
		}


		public void setDodgePerPoint(Double dodgePerPoint) {
			this.dodgePerPoint = dodgePerPoint;
		}


		public Weapon getWeapon() {
			return (Weapon) weapon;
		}

		public void setWeapon(Weapon weapon) {
			this.weapon = weapon;
		}

		public int getBaseDmg() {
			return baseDmg;
		}

		public void setBaseDmg(int baseDmg) {
			this.baseDmg = baseDmg;
		}

		public Double getOffenseDmgPerPoint() {
			return offenseDmgPerPoint;
		}

		public void setOffenseDmgPerPoint(Double offenseDmgPerPoint) {
			this.offenseDmgPerPoint = offenseDmgPerPoint;
		}

		public Double getDefenseDmgPerPoint() {
			return defenseDmgPerPoint;
		}

		public void setDefenseDmgPerPoint(Double defenseDmgPerPoint) {
			this.defenseDmgPerPoint = defenseDmgPerPoint;
		}

		public Double getRangeDmgPerPoint() {
			return rangeDmgPerPoint;
		}

		public void setRangeDmgPerPoint(Double rangeDmgPerPoint) {
			this.rangeDmgPerPoint = rangeDmgPerPoint;
		}

		public void setHpPerPoint(Double d){
			this.hpPerPoint = d;
		}
		public Double getHpPerPoint() {
			return hpPerPoint;
		}
		
		public void setChiPerPoint(Double d){
			this.chiPerPoint = d;
		}
		
		public Double getChiPerPoint() {
			return chiPerPoint;
		}

		public int getMaxStatPoints() {
			return maxStatPoints;
		}


		public void setMaxStatPoints(int maxStatPoints) {
			this.maxStatPoints = maxStatPoints;
		}


		public int getStatPoints() {
			return statPoints;
		}
		
		public void setStatPoints(int statPoints) {
			this.statPoints = statPoints;
			setMaxStatPoints(statPoints);
		}
		
		public void spendStatPoint(String stat) throws NoAvailableStatPointsException{
			if(statPoints > 0){
				this.statPoints--;
				switch(stat){
					case "Vit":{
						addHp(getHpPerPoint());
						break;
					}
					case "Chi":{
						addChiValue(getChiPerPoint());
						break;
					}
				}
			}else{
				throw new NoAvailableStatPointsException("All available stat points are spent.");
			}
		}
		
		public void refundStatPoint(String stat) throws FullStatPointsException{
			if(statPoints < maxStatPoints){
				this.statPoints++;
				switch(stat){
					case "Vit":{
						subHp(getHpPerPoint());
						break;
					}
					case "Chi":{
						subChiValue(getChiPerPoint());
						break;
					}
				}
			}else{
				throw new FullStatPointsException("Spend stat points before refund.");
			}
		}

		public String getEleType() {
			return eleType;
		}

		public void setEleType(String eleType) {
			this.eleType = eleType;
		}

	
		public String getFaction() {
			return faction;
		}

		public void setFaction(String faction) {
			this.faction = faction;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			int statPoints = 0;
			
			for(int i = 2; i <= level;i++){
				if(i < 99){
					statPoints += 5;
				}

				if(i > 112){
					statPoints += 30;
				}
			}
			
			this.level = level;
			setStatPoints(statPoints);
			setMaxStatPoints(statPoints);
			setVit(1);
			setDex(1);
			setChiPoints(1);
			setStr(1);
			setHp(getHpByLevel(level));
			setChi(getChiByLevel(level));
			setDefense(getDefenseByLevel(level));
		}

		public int getVit() {
			return vit;
		}

		public void setVit(int vit) {
			this.vit = vit;
		}
		
		public void addVit(){
			this.vit++;
		}
		public void subVit(){
			this.vit--;
		}

		public int getDex() {
			return dex;
		}

		public void setDex(int dex) {
			this.dex = dex;
		}
		
		public void addDex(){
			this.dex++;
		}
		public void subDex(){
			this.dex--;
		}

		public int getChiPoints() {
			return chiPoints;
		}

		public void setChiPoints(int chiPoints) {
			this.chiPoints = chiPoints;
		}
		public void subChi(){
			this.chiPoints--;
		}
		
		public void addChi(){
			this.chiPoints++;
		}

		public int getStr() {
			return str;
		}

		public void setStr(int str) {
			this.str = str;
		}
		
		public void addStr(){
			this.str++;
		}
		
		public void subStr(){
			this.str--;
		}

		public int getDefense() {
			return defense;
		}

		public void setDefense(int defense) {
			this.defense = defense;
		}

		public Double getDamage() {
			Double dmgPerPoint = 0.0;
			switch(getWeapon()){
				case OFFENSIVE:{
					dmgPerPoint = getOffenseDmgPerPoint();
					break;
				}
				case DEFENSIVE:{
					dmgPerPoint = getDefenseDmgPerPoint();
					break;
				}
				case RANGED:{
					dmgPerPoint = getRangeDmgPerPoint();
					break;
				}
			}
			Double dmg =  ( getBaseDmg() + (getStr() * dmgPerPoint) );
			setDamage(dmg);
			return dmg;
		}

		public void setDamage(Double damage) {
			this.damage = damage;
		}

		public Double getDodge() {
			Double dodge = (getDex() * getDodgePerPoint());
			setDodge(dodge);
			return dodge;
		}

		public void setDodge(Double dodge) {
			this.dodge = dodge;
		}

		public Double getHp() {
			return hp;
		}

		public void setHp(Double hp) {
			this.hp = hp;
		}
		
		public void addHp(Double hp){
			this.hp += hp;
		}
		
		public void subHp(Double hp){
			this.hp -= hp;
		}

		public Double getChi() {
			return chi;
		}

		public void setChi(Double d) {
			this.chi = d;
		}
		
		public void addChiValue(Double d){
			this.chi += d;
		}
		
		public void subChiValue(Double d){
			this.chi -= d;
		}

		public Double getHitRate() {
			Double hr = (getDex() * getHitRatePerPoint());
			setHitRate(hr);
			return hr;
		}

		public void setHitRate(Double hitRate) {
			this.hitRate = hitRate;
		}

		public int getHpElixers() {
			return hpElixers;
		}

		public void setHpElixers(int hpElixers) {
			this.hpElixers = hpElixers;
		}

		public int getChiElixers() {
			return chiElixers;
		}

		public void setChiElixers(int chiElixers) {
			this.chiElixers = chiElixers;
		}

		public int getEleDamage() {
			return eleDamage;
		}

		public void setEleDamage(int eleDamage) {
			this.eleDamage = eleDamage;
		}
		
		public void addEleDamage(int dmg){
			this.eleDamage += dmg;
		}
		
		public void printPlayerInfo(){
			System.out.println("Faction: " + getFaction());
			System.out.println("Level: " + getLevel());
			System.out.println("Stat Points: " + getStatPoints());
			System.out.println("");
		}
		
		
		public Double getHpByLevel(int level){
			int index = level - 1;
			return Double.parseDouble(baseHpValues[index]);
			
		}
		
		public Double getChiByLevel(int level){
			int index = level - 1;
			return Double.parseDouble(baseChiValues[index]);
		}

		protected int getDefenseByLevel(int level){
			int index = level - 1;
			return Integer.parseInt(defenseValues[index]);
		}
		
		public int getExp(){
			int index = level - 1;
			return Integer.parseInt(expValues[index]);
		}
		
}
