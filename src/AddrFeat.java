import org.apache.hadoop.io.Text;


public class AddrFeat {
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
	
	
	
	
	private AddrFeat(String tlid, String tfidl,String tfidr, String fullname, String lfromhn,String ltohn,String rfromhn, String rtohn,String zipl, String zipr) {
		this.TLID=tlid;
		this.TFIDL=tfidl;
		this.TFIDR=tfidr;
		this.fullName=fullname;
		this.lfromhn=lfromhn;
		this.ltohn=ltohn;
		this.rfromhn=rfromhn;
		this.rtohn=rtohn;
		this.zipl=zipl;
		this.zipr=zipr;
	}
	
	public static AddrFeat prase(String addressInfo){
		if(isAddrFeat(addressInfo)){
			String[] inputLine = addressInfo.split("\t");
			String[] values=inputLine[1].split("\\|");
			
			String TLID 		= inputLine[0].trim();
			String TFIDL 		= values[0].trim();
			String TFIDR 		= values[1].trim();
			String fullName 	= values[2];
			String lfromhn 	= values[3];
			String ltohn		= values[4];
			String rfromhn	= values[5];
			String rtohn		= values[6];
			String zipl		= values[7];
			String zipr		= values[8];
			
			return new AddrFeat(TLID,TFIDL,TFIDR,fullName,lfromhn,ltohn,rfromhn,rtohn,zipl,zipr);
		}
			
		return null;
	}
	public static boolean isAddrFeat(String addressInfo){
		String[] inputLine = addressInfo.split("\t");
		String[] values=inputLine[1].split("\\|");		
		if (values.length > 2) 
			return true;
		else
			return false;
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
		return fullName.trim();
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

	public Text getAddressInfo() {
		StringBuffer s = new StringBuffer();
		try {
			
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
			
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return new Text(s.toString());
	}
			

}
