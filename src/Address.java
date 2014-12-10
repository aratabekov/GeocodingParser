
public class Address {
	
	//specific to addrfeat
	private String 	TLID;		
	private String 	TFIDL;
	private String 	TFIDR;
	private String 	fullName;
	private String	lfromhn;
	private String	ltohn;
	private String	rfromhn;
	private String 	rtohn;
	private String	zipl;
	private String	zipr;
	
	//specific to faces
	private String 	tfid;
	private String 	statefips;
	private String	cousub;
	
	public Address() {
		
	}
	
	public Address(String addressInfo) {
		
		// Split the line for every tab
		String[] inputLine = addressInfo.split("\t");
		String tlid = "";
		
		//We now have a TLID and either TFIDL... or fromhn...
		try {
			this.TLID = inputLine[0];
			
			//Determine whether or TFIDL or fromhn
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		
		
		
	}

	public String getTLID() {
		return TLID;
	}

	public void setTLID(String tLID) {
		TLID = tLID;
	}

	public String getTFIDL() {
		return TFIDL;
	}

	public void setTFIDL(String tFIDL) {
		TFIDL = tFIDL;
	}

	public String getTFIDR() {
		return TFIDR;
	}

	public void setTFIDR(String tFIDR) {
		TFIDR = tFIDR;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getLfromhn() {
		return lfromhn;
	}

	public void setLfromhn(String lfromhn) {
		this.lfromhn = lfromhn;
	}

	public String getLtohn() {
		return ltohn;
	}

	public void setLtohn(String ltohn) {
		this.ltohn = ltohn;
	}

	public String getRfromhn() {
		return rfromhn;
	}

	public void setRfromhn(String rfromhn) {
		this.rfromhn = rfromhn;
	}

	public String getRtohn() {
		return rtohn;
	}

	public void setRtohn(String rtohn) {
		this.rtohn = rtohn;
	}

	public String getZipl() {
		return zipl;
	}

	public void setZipl(String zipl) {
		this.zipl = zipl;
	}

	public String getZipr() {
		return zipr;
	}

	public void setZipr(String zipr) {
		this.zipr = zipr;
	}

	public String getTfid() {
		return tfid;
	}

	public void setTfid(String tfid) {
		this.tfid = tfid;
	}

	public String getStatefips() {
		return statefips;
	}

	public void setStatefips(String statefips) {
		this.statefips = statefips;
	}

	public String getCousub() {
		return cousub;
	}

	public void setCousub(String cousub) {
		this.cousub = cousub;
	}

	

}
