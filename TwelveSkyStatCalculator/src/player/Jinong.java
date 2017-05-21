package player;

import java.io.FileReader;
import java.io.IOException;
import com.opencsv.CSVReader;

public class Jinong extends Player {
	
	private static String[] darkDamage;
	
	public Jinong() throws IOException{
		super();
		setFaction("Jinong");
		setEleType("Dark");
	}
	public Jinong(Player p) throws IOException{
		this();
		try {
			pullValuesFromCSV();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		setLevel(p.getLevel());
		setVit(p.getVit());
		setDex(p.getDex());
		setStr(p.getStr());
		setChiPoints(p.getChiPoints());
		setStatPoints(p.getStatPoints());
		setWeapon(p.getWeapon());
		setBaseDmg(87);
		setHpPerPoint(16.33);
		setChiPerPoint(22.29);
		setOffenseDmgPerPoint(5.6100001);
		setDefenseDmgPerPoint(5.3899999);
		setRangeDmgPerPoint(5.1700001);
		setHitRatePerPoint(5.71);
		setDodgePerPoint(2.8599999);
		setDamage(getDamage());
	}
	
	@Override
	protected void pullValuesFromCSV()  throws IOException{
		if(null == darkDamage || null == baseHpValues || null == baseChiValues){
			super.pullValuesFromCSV();
			darkDamage = new String[145];
			baseHpValues = new String[MAX_LEVEL];
			baseChiValues = new String[MAX_LEVEL];
			CSVReader reader = new CSVReader(new FileReader("stat_constants.csv"));
	        String [] nextLine;
	        int i = 0;
	        while ((nextLine = reader.readNext()) != null) {
	        	if(nextLine[0].equals("Level")){ //ignore the title row
	        		continue;
	        	}
	        	darkDamage[i] = nextLine[20];
	            baseHpValues[i] = nextLine[23];
	            baseChiValues[i] = nextLine[26];
	            
	            i++;
	        }
	        reader.close();
		}
	}
}
