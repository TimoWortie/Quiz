package db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DatabaseHandler {

  private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  public static ArrayList<Score> readJsonFromUrl() throws IOException, JSONException {
    InputStream is = new URL("http://corebull.com/getScores.php").openStream();
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      ArrayList<Score> scoreList = new ArrayList<Score>();
      
      JSONArray itemArray = new JSONArray(jsonText);
      
      for(int i = 0; i<itemArray.length(); i++){
          JSONObject itemObject = itemArray.getJSONObject(i);
          Score score = new Score(
                  itemObject.getString("Name"),
                  itemObject.getInt("Score")
          );
          
          scoreList.add(score);
      }
      
      return scoreList;
    } finally {
      is.close();
    }
  }
  
  public static void postScoreToUrl(Score score) throws IOException {
	  
	    URL url = new URL("http://corebull.com/insertScore.php");
	    
	    Map<String,Object> params = new LinkedHashMap<>();
	    params.put("NAME", score.getName());
	    params.put("SCORE", String.valueOf(score.getScore()));

	    StringBuilder postData = new StringBuilder();
	    
	    for (Map.Entry<String,Object> param : params.entrySet()) {
	        if (postData.length() != 0) postData.append('&');
	        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
	        postData.append('=');
	        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
	    }

	    // Convert string to byte array, as it should be sent
	    byte[] postDataBytes = postData.toString().getBytes("UTF-8");

	    // Connect, easy
	    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	    // Tell server that this is POST and in which format is the data
	    conn.setRequestMethod("POST");
	    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	    conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
	    conn.setDoOutput(true);
	    conn.getOutputStream().write(postDataBytes);
	    
	    
	    
	    // This gets the output from your server
	    Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

	    for (int c; (c = in.read()) >= 0;)
	        System.out.print((char)c);
  }
  
  public static void main(String[] args) throws IOException, JSONException {
	    ArrayList<Score> newList = readJsonFromUrl();
	    postScoreToUrl(new Score("Flo",999));
	  }
}