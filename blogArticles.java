import com.google.gson.*;

public class blogArticles {
	private String [] postIds;
	private String currentId;
			
	public blogArticles () {
		
	}
	
	public void setCurrent(String id) {
		currentId=id;
	}
	
	public String current() {
		return currentId;
	}
	
	public String postIdByRow(int row) {
		String ret = "0";
		try {
			ret=postIds[row];
			System.out.println("ID="+ret);
			return ret;
		} catch (NullPointerException e) {
			System.out.println("no post");
			return "0";
		}
	}
	
	public void article(APIAnswer a) {
		JsonObject result = a.get().getAsJsonObject("d");
		blogclient.bGUI.showArticle(
				result.get("title").getAsString(),
				result.get("text_src").getAsString()
				);
	}
	
	public void list(APIAnswer a) {
		String [] columNames= {"Title", "Date", "Edit"};
		
		int i=0;
		JsonArray results = a.get().getAsJsonArray("d");
		int v=results.size();
		Object[][] data = new Object[v][3];
		postIds = new String[v];
		for (JsonElement result : results) {
			postIds[i]=result.getAsJsonObject().get("post_id").getAsString();
			data[i][0]=result.getAsJsonObject().get("title").getAsString();
			data[i][1]=result.getAsJsonObject().get("dt").getAsString();
			data[i][2]="Edit";
			i++;
		}

		blogclient.bGUI.showArticlesList(data, columNames);
	}
	
}