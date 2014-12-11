import org.apache.hadoop.io.Text;

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
	private boolean isAddrFeat	= false;
	
	//specific to faces
	private String 	tfid;
	private String 	statefips;
	private String	cousub;
	
	public Address() {
		
	}
	
	public Address(String addressInfo) {
		
		// Split the line for every tab
		String[] inputLine = addressInfo.split("\t");
		String[] values=inputLine[1].split("\\|");
		
		//We now have a TFIDL/TFIDR or a tfid
		try {
			if (values.length > 2) {		// more than 3 tabs so TFIDL/TFIDR
				this.TLID 		= inputLine[0].trim();
				this.TFIDL 		= values[0].trim();
				this.TFIDR 		= values[1].trim();
				this.fullName 	= values[2];
				this.lfromhn 	= values[3];
				this.ltohn		= values[4];
				this.rfromhn	= values[5];
				this.rtohn		= values[6];
				this.zipl		= values[7];
				this.zipr		= values[8];
				this.isAddrFeat	= true;
				
			} else {						// less than 3 tabs so tfid
				this.tfid		= inputLine[0].trim();
				this.statefips	= values[0];
				this.cousub		= values[1];
			}
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

	public boolean isAddrFeat() {
		return isAddrFeat;
	}

	public Text getAddressInfo() {
		StringBuffer s = new StringBuffer();
		try {
			if (isAddrFeat) {
				s.append(this.TLID).append("\t");
				s.append(this.TFIDL).append("|");
				s.append(this.TFIDR).append("|");
				s.append(this.fullName).append("|");
				s.append(this.lfromhn).append("|");
				s.append(this.ltohn).append("|");
				s.append(this.rfromhn).append("|");
				s.append(this.rtohn).append("|");
				s.append(this.zipl).append("|");
				s.append(this.zipr);
			} else {
				s.append(this.tfid).append("\t");
				s.append(this.statefips).append("|");
				s.append(this.cousub);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return new Text(s.toString());
	}
			
	

}
