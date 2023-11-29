package Client;

import java.io.IOException;
import java.net.Socket;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class test {

	public static void main(String[] args) throws IOException, NullPointerException {
		String api = "https://api-generator.retool.com/cEQCXR/movieSchedule/1";
		
		Document doc = Jsoup.connect(api)
                .ignoreContentType(true).ignoreHttpErrors(true)
                .header("Content-Type", "application/json")
                .method(Connection.Method.GET).execute().parse();
        JSONObject jsonObject = new JSONObject(doc.text());
        System.out.println(jsonObject.get("ip"));
		
	}

}
