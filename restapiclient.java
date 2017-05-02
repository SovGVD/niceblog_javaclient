import java.util.ArrayList;
import java.util.List;

import java.net.CookieHandler;
import java.net.CookieManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;


public class restapiclient {
	private final String USER_AGENT = "blogJava/1";
	private String APIURL = "https://nz.sovgvd.info/admin_api.php";
	private final boolean APIDEBUG = true;

	public APIAnswer doPOST(String[] _data, String[] _Ddata, String _cookie) throws Exception {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(APIURL);
		
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();

		int size = _data.length;
		int Dsize = _Ddata.length;
		if (Dsize>0) {
	        for (int i=0; i<Dsize; i=i+2) {
	        	urlParameters.add(new BasicNameValuePair("d[".concat(_Ddata[i]).concat("]"),_Ddata[i+1]));
	        }
		}
        for (int i=0; i<size; i=i+2) {
        	urlParameters.add(new BasicNameValuePair(_data[i],_data[i+1]));
        }

		post.setHeader("User-Agent", USER_AGENT);
		post.setHeader("Connection", "keep-alive");
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");

		
		if (!_cookie.isEmpty()) {
			CookieHandler.setDefault(new CookieManager());
			post.setHeader("Cookie", "sid=".concat(_cookie));
		}
		 
		post.setEntity(new UrlEncodedFormEntity(urlParameters));
		
		HttpResponse response = client.execute(post);
		if (APIDEBUG) {
			System.out.println("\nSending 'POST' request to URL : " + APIURL);
			System.out.println("Post parameters : " + post.getEntity());
			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		}

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		String answer = result.toString();
		if (APIDEBUG) {
			System.out.println("RAW ANSWER");
			System.out.println(answer);
		}
		
		APIAnswer ret = new APIAnswer(answer);
		return ret;
	}
}