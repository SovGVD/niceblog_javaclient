public class blogAPI extends restapiclient{
	private String sid;
		
	public void main () {
		
	}
	
	public void setCredentials(String s) {
		this.sid=s;
	}
	
	public APIAnswer API_login(String login, String password) {
		String[] _data = {"c","auth","login", login, "password", password, "request_id", "API_login"};
		String[] _Ddata = {};
		String[] _Adata = {};
		return API(_data, _Ddata, _Adata);
	}
	
	public APIAnswer API_domains () {
		String[] _data = {"c","domains", "m", "list","sid", sid, "request_id", "API_domains"};
		String[] _Ddata = {};
		String[] _Adata = {};
		return API(_data,_Ddata, _Adata);
	}
	
	public APIAnswer API_articles(String domain) {
		String[] _data = {"c","articles", "m", "list","sid", sid, "request_id", "API_articles"};
		String[] _Ddata = {"domain", domain};
		String[] _Adata = {};
		return API(_data, _Ddata, _Adata);
	}

	public APIAnswer API_article(String domain, String postId) {
		String[] _data = {"c","articles", "m", "data","sid", sid, "request_id", "API_article"};
		String[] _Ddata = {"domain", domain, "post_id", postId};
		String[] _Adata = {};
		return API(_data, _Ddata, _Adata);
	}
	
	public APIAnswer API_article_save(String domain, String postId, String title, String text_src, String dt, String url, String text_tags, String post_lang, String privacy, String crosspost_it) {
		String[] _data = {"c","articles", "m", "save","sid", sid, "request_id", "API_article_save"};
		String[] _Ddata = {"domain", domain, "post_id", postId};
		String[] _Adata = {"post_id", postId, "title", title, "text_src", text_src, "dt", dt, "url", url, "text_tags", text_tags, "post_lang", post_lang, "privacy", privacy, "crosspost_it", crosspost_it};
		return API(_data, _Ddata, _Adata);
	}

	private APIAnswer API (String[] _data, String[] _Ddata, String[] _Adata) {
		String _cookie = "";
		try {
			return doPOST(_data, _Ddata, _Adata, _cookie);
		} catch (Exception e) {
			//e.printStackTrace();
			APIAnswer err = new APIAnswer("").error();
			return err;
		}
	}

}