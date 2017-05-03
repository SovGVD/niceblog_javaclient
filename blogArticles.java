import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.*;

public class blogArticles {
	private String [] postIds;
	private String currentId;
	private String title;
	private String dt;
	private String text_src;
	private String url;
	private String text_tags;
	private String post_lang;
	private String privacy;
	private String crosspost_it;
			
	public blogArticles () {
		
	}
	
	private String currentDt() {
		Date tdt = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(tdt); 
	}
	
	public void newArticle() {
		// set default values for new post
		dt = currentDt();
		currentId="0";
		title="";
		text_src="";
		url="";
		text_tags="";
		post_lang="USA";
		privacy="0";
		crosspost_it="0";
		
		blogclient.bGUI.showArticle(title, text_src);
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
		// looks not good... TODO
		JsonObject result = a.get().getAsJsonObject("d");
		dt = result.get("dt").getAsString();
		currentId=result.get("post_id").getAsString();
		title=result.get("title").getAsString();
		text_src=result.get("text_src").getAsString();
		url=result.get("url").getAsString();
		text_tags=result.get("text_tags").getAsString();
		post_lang=result.get("post_lang").getAsString();
		privacy=result.get("privacy").getAsString();
		crosspost_it=result.get("crosspost_it").getAsString();

		blogclient.bGUI.showArticle(
				title,
				text_src
				);
	}
	
	public void saveArticle(String stitle, String stext_src) {
		String domain = blogclient.bDomains.current();
		// TODO, update date?
		dt = currentDt();
		System.out.println("string".concat(stext_src));
		blogclient.bAPI.API_article_save(domain, currentId, stitle, stext_src, dt, url, text_tags, post_lang, privacy, crosspost_it);
		// move to articles list
		this.list(blogclient.bAPI.API_articles(domain));
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