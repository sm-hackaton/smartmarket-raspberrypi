package mobile.smartmarket.smartmarket.javacmd.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class HTTPClientHelperImp implements HTTPClientHelper {
	private Logger logger = LogManager.getLogger(HTTPClientHelperImp.class.getName());
	
	private static HTTPClientHelperImp instance = null;
	
	public final String STR_URL_MYSERVER_GCM = "http://manriqueweb.com/smartmarket/gcmSendFinal.php";
	public final String USER_AGENT = "Mozilla/5.0";

	/**
	 * 
	 */
	private HTTPClientHelperImp() {
	}

	public static synchronized HTTPClientHelperImp getInstance() {
		if (instance==null){
			instance = new HTTPClientHelperImp();
		}
		
		return instance;
	}

	private void stackTraceToString(Throwable e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e.getMessage());
        sb.append(" \n ");
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append(element.toString());
            sb.append(" \n ");
        }
		logger.error("error: \n ".concat(sb.toString()));
    }

	private String getUrl(String idgcm, String idcard, String vendedor, Double monto){
		StringBuffer urlBuf = new StringBuffer();

		try {
			urlBuf.append(STR_URL_MYSERVER_GCM);
			urlBuf.append("?idgcm=");
			urlBuf.append(idgcm);
			urlBuf.append("&idcard=");
			urlBuf.append(URLEncoder.encode(idcard, "UTF-8"));
			urlBuf.append("&vendedor=");
			urlBuf.append(URLEncoder.encode(vendedor, "UTF-8"));
			urlBuf.append("&monto=");
			urlBuf.append(monto);
		} catch (UnsupportedEncodingException e) {
			stackTraceToString(e);
		}

		return urlBuf.toString();
	}

	public int sendPush(String idgcm, String idcard, String vendedor, Double monto) {
		String url = getUrl(idgcm, idcard, vendedor, monto);
		StringBuffer response;
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			//add request header
			con.setRequestProperty("User-Agent", USER_AGENT);

			int responseCode = con.getResponseCode();
			if(logger.isDebugEnabled()) logger.debug("\nSending 'GET' request to URL : " + url);
			if(logger.isDebugEnabled()) logger.debug("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			//print result
			if(logger.isDebugEnabled()) logger.debug(response.toString());
			
		} catch (MalformedURLException e) {
			stackTraceToString(e);
		} catch (ProtocolException e) {
			stackTraceToString(e);
		} catch (IOException e) {
			stackTraceToString(e);
		}

		
		return 0;
	}

}
