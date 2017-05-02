import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;

public class APIAnswer {
	private String RAWanswer;
	private JsonObject JSONanswer;
	private boolean ready;
	private boolean hasError;
	
	public APIAnswer(String j) {
		set(j);
	}
	
	public void set(String j) {
		RAWanswer = j;
		JSONanswer = new JsonParser().parse(j).getAsJsonObject();
		ready = true;
		if (JSONanswer.get("r").getAsString().equals("ok")) {
			hasError = true;
		} else {
			hasError = false;
		}
	}
	
	public JsonObject get() {
		return JSONanswer;
	}
	
	public boolean isOK () {
		return hasError;
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
		hasError=true;
		return this;
	}	
}