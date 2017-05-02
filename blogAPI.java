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
		return API(_data, _Ddata);
	}
	
	public APIAnswer API_domains () {
		String[] _data = {"c","domains", "m", "list","sid", sid, "request_id", "API_domains"};
		String[] _Ddata = {};
		return API(_data,_Ddata);
	}
	
	public APIAnswer API_articles(String domain) {
		String[] _data = {"c","articles", "m", "list","sid", sid, "request_id", "API_domains"};
		String[] _Ddata = {"domain", domain};
		return API(_data,_Ddata);
	}

	public APIAnswer API_article(String domain, String postId) {
		String[] _data = {"c","articles", "m", "data","sid", sid, "request_id", "API_domains"};
		String[] _Ddata = {"domain", domain, "post_id", postId};
		return API(_data,_Ddata);
	}

	private APIAnswer API (String[] _data, String[] _Ddata) {
		String _cookie = "";
		try {
			return doPOST(_data, _Ddata, _cookie);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			APIAnswer err = new APIAnswer("").error();
			return err;
		}
	}

}