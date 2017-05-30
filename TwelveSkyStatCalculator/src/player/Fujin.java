package player;

import java.io.FileReader;
import java.io.IOException;
import com.opencsv.CSVReader;

public class Fujin extends Player {
	
	private static String[] shadowDamage;
	
	public Fujin() throws IOException{
		super();
		setFaction("Fujin");
		setEleType("Shadow");
		setBaseDmg(51);
		setHpPerPoint(14.29);
		setChiPerPoint(17.14);
		setOffenseDmgPerPoint(3.1700001);
		setDefenseDmgPerPoint(2.71);
		setRangeDmgPerPoint(2.9400001);
		setHitRatePerPoint(4.5700002);
		setDodgePerPoint(2.29);
	}
	
	public Fujin(Player p) throws IOException{
		this();
		try {
			pullValuesFromCSV();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		setLevel(p.getLevel());
		setVit(1);
		setDex(1);
		setStr(1);
		setStatPoints(p.getStatPoints());
		setChiPoints(1);
		setWeapon(p.getWeapon());
		setDamage(getDamage());
		setEleDamage(getShadowDmgByLevel(p.getLevel()));
	}
	
	@Override
	protected void pullValuesFromCSV()  throws IOException{
		if(null == shadowDamage || null == baseHpValues || null == baseChiValues){
			shadowDamage = new String[MAX_LEVEL];
			baseHpValues = new String[MAX_LEVEL];
			baseChiValues = new String[MAX_LEVEL];
			super.pullValuesFromCSV();
			CSVReader reader = new CSVReader(new FileReader("stat_constants.csv"));
	        String [] nextLine;
	        int i = 0;
	        while ((nextLine = reader.readNext()) != null) {
	        	if(nextLine[0].equals("Level")){ //ignore the title row
	        		continue;
	        	}
	        	shadowDamage[i] = nextLine[19];
	            baseHpValues[i] = nextLine[22];
	            baseChiValues[i] = nextLine[25];
	            
	            i++;
	        }
	        reader.close();
		}
	}
	public int getShadowDmgByLevel(int level){
		int index = level - 1;
		return Integer.parseInt(shadowDamage[index]);
	}
	

}
