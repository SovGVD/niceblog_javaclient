//import java.util.ArrayList;

//import com.google.gson.*;

public class blogDomains {
	private String[] domains;
	private String currentDomain;
	
	public blogDomains () {
		
	}
	
	public void set(APIAnswer a) {
		if (a.isOK()) {
			domains = a.getArray("http");
		}
	}
	
	public void setCurrent(String domain) {
		currentDomain = domain;
	}
	
	public String current() {
		return currentDomain;
	}
	
	public void changeDomain(String host) {
		
	}
	
	public String[] list () {
		return domains;
	}
}
