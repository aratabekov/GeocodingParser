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
		
		//We now have a TFIDL/TFIDR or a tfid
		try {
			if (inputLine.length > 2) {		// more than 3 tabs so TFIDL/TFIDR
				this.TLID 		= inputLine[0];
				this.TFIDL 		= inputLine[1];
				this.TFIDR 		= inputLine[2];
				this.fullName 	= inputLine[3];
				this.lfromhn 	= inputLine[4];
				this.ltohn		= inputLine[5];
				this.rfromhn	= inputLine[6];
				this.rtohn		= inputLine[7];
				this.zipl		= inputLine[8];
				this.zipr		= inputLine[9];
				this.isAddrFeat	= true;
				
			} else {						// less than 3 tabs so tfid
				this.tfid		= inputLine[0];
				this.statefips	= inputLine[1];
				this.cousub		= inputLine[2];
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
				s.append(this.TFIDL).append("\t");
				s.append(this.TFIDR).append("\t");
				s.append(this.fullName).append("\t");
				s.append(this.lfromhn).append("\t");
				s.append(this.ltohn).append("\t");
				s.append(this.rfromhn).append("\t");
				s.append(this.rtohn).append("\t");
				s.append(this.zipl).append("\t");
				s.append(this.zipr);
			} else {
				s.append(this.tfid).append("\t");
				s.append(this.statefips).append("\t");
				s.append(this.cousub);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return new Text(s.toString());
	}
			
	

}
