public class user {
	boolean userLogin = false;
	String userCredentials;
	
	
	public user () {
		
	}
	
	public String getUserPassword () {
		return blogclient.bCipher.decrypt(blogclient.bDB.get("user", "password"));
	}

	public String getUserLogin () {
		return blogclient.bCipher.decrypt(blogclient.bDB.get("user", "login"));
	}

	
	public boolean isUserLogedIn () {
		return userLogin;
	}
	
	public boolean userSID (String sid) {
		if (sid.equals("") || sid.equals("ERROR")) {
			userLogin=false;
			userCredentials="";
		} else {
			userLogin=true;
			userCredentials=sid;
			blogclient.bAPI.setCredentials(sid);
			blogclient.bDomains.set(blogclient.bAPI.API_domains());
			
			System.out.println("SID: ".concat(userCredentials));
		}
		return userLogin;
	}
	
	public boolean login (String login, String password) {
		APIAnswer sid=blogclient.bAPI.API_login(login, password);
		return userSID(sid.isOK()?sid.value("sid"):"");
	}
}