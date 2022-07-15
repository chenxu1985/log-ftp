package cn.ac.gsa.utility;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/*******************************************************************
 * this class used to generate a http request 
 * 
 * @author sweeter
 *
 */
public class HttpRequestUtil {
	  public static final String CHARSET_UTF_8 = "utf-8";
	


	public static String doHttpPostResponseJson(String url,String body) throws Exception{
		String resultString = "";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", "application/json");
		httpPost.setEntity(new StringEntity(body));
		CloseableHttpResponse response = httpClient.execute(httpPost);
		if (response.getStatusLine().getStatusCode() == 200) {
			resultString = EntityUtils.toString(response.getEntity(), CHARSET_UTF_8);
		}else{
			String tmpstr = EntityUtils.toString(response.getEntity(), CHARSET_UTF_8);
			System.out.println(tmpstr);
		}
		return resultString;

	}
	
}
