import org.apache.hadoop.io.Text;


public class FaceAttributes {
	
	private String 	tfid;
	private String 	statefips;
	private String	cousub;
	
	private FaceAttributes(String tfid, String statefips, String cousub){
		this.tfid=tfid;
		this.statefips=statefips;
		this.cousub=cousub;
	}
	public String getTFID(){
		return this.tfid;
	}
	
	public String getStateFips(){
		return this.statefips;
	}
	public String getCousub(){
		return this.cousub;
	}
	
	public static FaceAttributes parse(String str){
		if(isFaces(str)){
			String[] inputLine = str.split("\t");
			String[] values=inputLine[1].split("\\|");
			String tfid		= inputLine[0].trim();
			String statefips	= values[0];
			String cousub		= values[1];
			
			return new FaceAttributes(tfid,statefips,cousub);
		}
		else		
			return null;
	}
	
	public static boolean isFaces(String str){
		String[] inputLine = str.split("\t");
		String[] values=inputLine[1].split("\\|");
		
		if (values.length > 2) {
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public Text getFaceInfo(){
		StringBuffer s = new StringBuffer();
		try{
			s.append(this.tfid).append("\t");
			s.append(this.statefips).append("|");
			s.append(this.cousub);

		} catch (NullPointerException e) {
			e.printStackTrace();
			System.exit(1);
		}

		return new Text(s.toString());
	}

	
}
