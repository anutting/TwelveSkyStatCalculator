package player;

import java.io.FileReader;
import java.io.IOException;
import com.opencsv.CSVReader;

public class Guanyin extends Player {
	
	private static String[] lightDamage;
	
	public Guanyin() throws IOException{
		super();
		setFaction("Guanyin");
		setEleType("Light");
		setBaseDmg(62);
		setHpPerPoint(15.31);
		setChiPerPoint(20.00);
		setOffenseDmgPerPoint(4.3);
		setDefenseDmgPerPoint(3.8599990);
		setRangeDmgPerPoint(4.0799990);
		setHitRatePerPoint(5.1399999);
		setDodgePerPoint(2.5699999);
	}
	
	public Guanyin(Player p) throws IOException{
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
		setChiPoints(1);
		setWeapon(p.getWeapon());
		setDamage(getDamage());
		setEleDamage(getLightDmgByLevel(p.getLevel()));
	}
	
	
	@Override
	protected void pullValuesFromCSV()  throws IOException{
		if(null == lightDamage || null == baseHpValues || null == baseChiValues){
			
		
			//super.pullValuesFromCSV();
			lightDamage = new String[145];
			baseHpValues = new String[MAX_LEVEL];
			baseChiValues = new String[MAX_LEVEL];
			CSVReader reader = new CSVReader(new FileReader("stat_constants.csv"));
	        String [] nextLine;
	        int i = 0;
	        while ((nextLine = reader.readNext()) != null) {
	        	if(nextLine[0].equals("Level")){ //ignore the title row
	        		continue;
	        	}
	        	lightDamage[i] = nextLine[18];
	            baseHpValues[i] = nextLine[21];
	            baseChiValues[i] = nextLine[24];
	            
	            i++;
	        }
	        reader.close();
		}
	}
	public int getLightDmgByLevel(int level){
		int index = level - 1;
		return Integer.parseInt(lightDamage[index]);
	}
}
