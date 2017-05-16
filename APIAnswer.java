import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;

public class APIAnswer {
	private String RAWanswer;
	private JsonObject JSONanswer;
	private boolean ready;
	private boolean hasError;
	private boolean networkError;
	
	public APIAnswer(String j) {
		hasError=false;
		networkError=false;
		if (j!="") set(j);
	}
	
	public void setResult(String j) {
		RAWanswer = j;
		//networkError = false;
		if (j!="") {
			try {
				JSONanswer = new JsonParser().parse(j).getAsJsonObject();
				ready = true;
				if (JSONanswer.get("r").getAsString().equals("ok")) {
					hasError = false;
				} else {
					hasError = true;
				}
			} catch (NullPointerException e) {
				//networkError = true;	// Actually not working like that...
				hasError = true;
			}
		} else {
			hasError = true;
		}		
	}
	
	public void set(String j) {
		setResult(j);
		if (!isNetworkOK()) {
			blogclient.bGUI.showError("Network or host unreachable.");
		}
	}
	
	public JsonObject get() {
		return JSONanswer;
	}
	
	public boolean isOK () {
		return !hasError;
	}

	public boolean isNetworkOK () {
		return !networkError;
	}

	public String value(String v) {
		return JSONanswer.getAsJsonObject("d").get(v).getAsString();
	}
	
	public String[] getArray(String v) {
		List<String> vals = new ArrayList<String>();
		String tmp = "";
		
		int i=0;
		JsonArray results = JSONanswer.getAsJsonArray("d");
		for (JsonElement result : results) {
			vals.add(result.getAsJsonObject().get(v).getAsString());
			i++;
		}
		String[] tmp2 = new String[ vals.size() ];
		vals.toArray( tmp2 );
		return tmp2;
	}
	
	public APIAnswer error() {
		this.hasError=true;
		return this;
	}	
	public APIAnswer errorAPI() {
		this.hasError=true;
		return this;
	}	
	public APIAnswer errorNetwork() {
		this.networkError=true;
		this.hasError=true;
		return this;
	}	

}