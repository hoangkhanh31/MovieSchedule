package Server;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Objects.Cinema;
import Objects.Cineplex;
import Objects.Film;
import Objects.FilmShowTimes;

public class Server {

	private static ArrayList<Cinema> cinemaList = new ArrayList<>();
	private static ArrayList<Film> filmList = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		try (DatagramSocket socket = new DatagramSocket(5000)) {
			System.out.println("Khởi tạo server thành công!");
			PostIpToApi();
			filmList = getFilmData();
//			System.out.println(filmList);
			cinemaList = getCinemas();
			byte[] receiveData = new byte[1048576];
			byte[] sendData = new byte[1048576];

			while (true) {
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

				socket.receive(receivePacket);

				String input = new String(receivePacket.getData(), 0, receivePacket.getLength());
				System.out.println(input);

				InetAddress IPAddress = receivePacket.getAddress();

				int port = receivePacket.getPort();

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);

				String[] splitString = input.split(";");
				switch (splitString[0]) {
				case "getCinemaByCineplex":
					ArrayList<Cinema> cinemaList = getCinemaByCineplex(splitString[1]);

					oos.writeObject(cinemaList);
					break;
				case "getFilmByDay":
					ArrayList<String> output2 = getFilmByDay(Integer.parseInt(splitString[1]), splitString[2]);
					System.out.println(input);
//					   Objects.DataForCinema data2 = new Objects.DataForCinema(output2);

					oos.writeObject(output2);
					break;
				case "getIdFilm":
//					   ArrayList<String> output2 = getFilmByDay(Integer.parseInt(splitString[1]), splitString[2]);
//					   System.out.println(input);
//					   Objects.DataForCinema data2 = new Objects.DataForCinema(output2);
					int output3 = getIdFilm(splitString[1]);

					oos.writeObject(output3);

					break;
				case "getFilmDetails":
					Film output4 = getFilmDetails(Integer.parseInt(splitString[1]));
//					   System.out.println(output4.getTitle());
					oos.writeObject(output4);

					break;
				case "getShowTimes":
					ArrayList<FilmShowTimes> output5 = getShowTimes(Integer.parseInt(splitString[1]), splitString[2]);

					Objects.DataForFilmShowTimes data5 = new Objects.DataForFilmShowTimes(output5);

					oos.writeObject(data5);
					break;
				}

				byte[] dataBytes = baos.toByteArray();
				DatagramPacket packet = new DatagramPacket(dataBytes, dataBytes.length, IPAddress, port);
				socket.send(packet);
			}
		}
	}
	
	private static void PostIpToApi() throws IOException, NullPointerException{
		Socket socket = new Socket("google.com", 80);
		
        String localIP = socket.getLocalAddress().toString().substring(1);
        
		String api = "https://api-generator.retool.com/cEQCXR/movieSchedule/1"; // Ghi vào dòng 1
        String jsonData = "{\"ip\":\"" + localIP + "\"}";
		Jsoup.connect(api)
			       .ignoreContentType(true).ignoreHttpErrors(true)
			       .header("Content-Type", "application/json")
			       .requestBody(jsonData)
			       .method(Connection.Method.PUT).execute();
		socket.close();
		System.out.println("Post IP lên API thành công");
	}

	private static ArrayList<Cinema> getCinemas() {
		String url = "https://moveek.com/api/v2/cinema?region=1";
		try {
			Document doc = Jsoup.connect(url).method(Connection.Method.GET).ignoreContentType(true).execute().parse();
			JSONObject json = new JSONObject(doc.text());
			JSONObject jsonData = json.getJSONObject("data");
			JSONArray cineplex = jsonData.getJSONArray("cinemas");

			ArrayList<Cinema> cinemas = new ArrayList<>();
			for (int i = 0; i < cineplex.length(); i++) {
				JSONObject cine = cineplex.getJSONObject(i);
				JSONArray cineData = cine.getJSONArray("cinemas");
				for (int j = 0; j < cineData.length(); j++) {
					JSONObject cinema = cineData.getJSONObject(j);
					Cinema c = new Cinema();
					c.setId(cinema.getInt("id"));
					c.setName(cinema.getString("name"));
					c.setCineplex(cinema.getString("cineplex"));
					cinemas.add(c);
				}
			}

			return cinemas;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	private static ArrayList<Cinema> getCinemaByCineplex(String cineplex) {
		ArrayList<Cinema> list = new ArrayList<>();
		if (cineplex.equals("all")) {
			list = cinemaList;
		} else {
			for (int i = 0; i < cinemaList.size(); i++) {
				if (cinemaList.get(i).getCineplex().equals(cineplex)) {
					Cinema c = new Cinema();
					c = cinemaList.get(i);
					list.add(c);
				}
			}
		}
		return list;
	}

	private static ArrayList<Cineplex> getCineplexs() {
		String url = "https://moveek.com/api/v2/cinema?region=1";
		try {
			Document doc = Jsoup.connect(url).method(Connection.Method.GET).ignoreContentType(true).execute().parse();
			JSONObject json = new JSONObject(doc.text());
			JSONObject jsonData = json.getJSONObject("data");
			JSONArray cineplex = jsonData.getJSONArray("cinemas");

			ArrayList<Cineplex> cineplexs = new ArrayList<>();
			for (int i = 0; i < cineplex.length(); i++) {
				JSONObject cine = cineplex.getJSONObject(i);
				JSONObject cineData = cine.getJSONObject("data");

				Cineplex c = new Cineplex();
				c.setName(cineData.getString("name"));
				c.setSlug(cineData.getString("slug"));
				c.setImage(cineData.getString("image"));

				cineplexs.add(c);
			}

			return cineplexs;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	private static ArrayList<String> getFilmByDay(int idCinema, String date) {
		String URL = "https://moveek.com/cinema/showtime/" + String.valueOf(idCinema) + "?date=" + date + "&header=1";
		Document doc;
		try {
			doc = Jsoup.connect(URL).method(Connection.Method.GET).ignoreContentType(true).execute().parse();

			Elements text = doc.getElementsByClass("card-title mb-1 name");
			ArrayList<String> res = new ArrayList<>();

			for (int i = 0; i < text.size(); i++) {
				res.add(text.get(i).text());
			}

			return res;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	private static String getURLMomo() {
		String url = "";
		try {
			Document doc = Jsoup.connect("https://momo.vn/cinema").method(Connection.Method.GET).execute().parse();
			Element e = doc.getElementsByAttributeValueContaining("src", "_ssgManifest.js").first();
			String data = e.attr("src");
//            System.out.println(data);
			String key = data.split("/")[6];
			url = "https://momo.vn/_next/data/" + key + "/cinema/phim-chieu.json";
			return url;
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return url;
	}

	private static ArrayList<Film> getFilmData() {
		try {
			// https://momo.vn/_next/data/oiS9q8CYcgd5gdJU1wFbw/cinema/phim-chieu.json
			// https://moveek.com/cinema/showtime/114418?date=2023-05-09&header=1
			// 114418: id rap
			// date: yyyy-mm-dd

//    		https://moveek.com/api/v2/cinema?region=1
			String url = getURLMomo();
			Document doc = Jsoup.connect(url).method(Connection.Method.GET).ignoreContentType(true).execute().parse();
			JSONObject json = new JSONObject(doc.text());
			JSONObject pageProps = json.getJSONObject("pageProps");
			JSONObject dataMoviesNow = pageProps.getJSONObject("dataMoviesNow");
			JSONObject Data = dataMoviesNow.getJSONObject("Data");
			JSONArray Items = Data.getJSONArray("Items");
			ArrayList<Film> list = new ArrayList<Film>();
//			System.out.println(Items.length());
			for (int i = 0; i < Items.length(); i++) {
				Film f = new Film();
				f.setId(Items.getJSONObject(i).getInt("Id"));
				f.setTitle(Items.getJSONObject(i).getString("Title"));
				f.setApiRating(Items.getJSONObject(i).getString("ApiRating"));
				f.setApiFilmType(Items.getJSONObject(i).optString("ApiFilmType", ""));
				f.setApiGenreName(Items.getJSONObject(i).optString("ApiGenreName", ""));
				f.setSynopsisEn(Items.getJSONObject(i).optString("SynopsisEn", ""));
				
//				System.out.println(i);
				list.add(f);
			}

			return list;
		} catch (Exception e) {
			// TODO: handle exception
			return new ArrayList<Film>();
		}
	}

	private static int getIdFilm(String filmName) {
		int res = 0;
		System.out.println(filmList);

		for (int i = 0; i < filmList.size(); i++) {
			if (filmList.get(i).getTitle().contains(filmName)) {
				res = filmList.get(i).getId();
			}
		}
		System.out.println(res);
		return res;
	}

	private static String getURLDetailFromMomo(int id) {
		String url = "";
		try {
//        	https://momo.vn/_next/data/oiS9q8CYcgd5gdJU1wFbw/cinema/hypnotic-988.json?slug=hypnotic-988
			Document doc = Jsoup.connect("https://momo.vn/cinema").method(Connection.Method.GET).execute().parse();
			Element e = doc.getElementsByAttributeValueContaining("src", "_ssgManifest.js").first();
			String data = e.attr("src");
//            System.out.println(data);
			String key = data.split("/")[6];
			url = "https://momo.vn/_next/data/" + key + "/cinema/" + String.valueOf(id) + ".json";
			return url;
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return url;
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	public static Film getFilmDetails(int id) {
		try {
			// https://momo.vn/_next/data/oiS9q8CYcgd5gdJU1wFbw/cinema/phim-chieu.json
			// https://moveek.com/cinema/showtime/114418?date=2023-05-09&header=1
			// 114418: id rap
			// date: yyyy-mm-dd

//    		https://moveek.com/api/v2/cinema?region=1
			String url = getURLDetailFromMomo(id);
//    		String url = getURLMomo();
			System.out.println(url);
			
			Document doc = Jsoup.connect(url).method(Connection.Method.GET).ignoreContentType(true).execute().parse();

//    		
//    		String doc2 = doc.toString().replaceAll("\n", "");
//    		System.out.println(doc.text().length());
			JSONObject json = readJsonFromUrl(url);

			JSONObject pageProps = json.getJSONObject("pageProps");
			JSONObject FilmData = pageProps.getJSONObject("FilmData");
			JSONObject Data = FilmData.getJSONObject("Data");
//    		JSONArray Items = Data.getJSONArray("Items");
//    		ArrayList<Film> list = new ArrayList<>();
			Film f = new Film();
			f.setId(Data.getInt("Id"));
			System.out.println(Data.getInt("Id"));

			f.setTitle(Data.getString("Title"));
			f.setApiRating(Data.getString("ApiRating"));
			f.setApiFilmType(Data.getString("ApiFilmType"));
			f.setApiGenreName(Data.getString("ApiGenreName"));
			f.setSynopsisEn(Data.getString("SynopsisEn"));
			f.setCountries(Data.getJSONArray("Countries").getJSONObject(0).getString("Name"));
			Object ApiImdb = Data.get("ApiImdb");
			if (ApiImdb == JSONObject.NULL) {
				f.setApiImdb("");
			} else {
				f.setApiImdb(Data.getString("ApiImdb"));
			}
			Object ApiRottenTomatoes = Data.get("ApiRottenTomatoes");
			if (ApiRottenTomatoes == JSONObject.NULL) {
				f.setApiRottenTomatoes("");
			} else {
				f.setApiRottenTomatoes(Data.getString("ApiRottenTomatoes"));
			}
			f.setGraphicUrl(Data.getString("GraphicUrl"));
			f.setTitleEn(Data.getString("TitleEn"));

			ArrayList<String> casts = new ArrayList<>();
			JSONArray listCast = Data.getJSONArray("ApiCasts");
			for (int i = 0; i < listCast.length(); i++) {
				casts.add(listCast.getJSONObject(i).getString("name"));
			}
			f.setApiCasts(casts);
			f.setTrailerUrl(Data.getString("TrailerUrl"));
			
//			Fixxxxxxxxxx
//			String[][] topComments = new String[Items.getJSONObject(i).getJSONArray("TopComments").length()][2];
//			for( int j = 0; j < Items.getJSONObject(i).getJSONArray("TopComments").length(); j++){
//				topComments[j][0] = Items.getJSONObject(i).getJSONArray("TopComments").getJSONObject(j).getString("creatorName");
//				topComments[j][1] = Items.getJSONObject(i).getJSONArray("TopComments").getJSONObject(j).getString("desc");
//			};
//			f.setTopComments(topComments);

			return f;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			Film f = new Film();
			f.setTitle("err");
			return f;
		}
	}

	private static ArrayList<String> showtimes(String str) {
		ArrayList<String> res = new ArrayList<>();
		String[] splitString = str.split(" ");
		for (int i = 0; i < splitString.length; i++) {
			if (splitString[i].contains(":")) {
				res.add(splitString[i]);
			}
		}

		return res;
	}

	public static ArrayList<FilmShowTimes> getShowTimes(int idCinema, String date) {
		String URL = "https://moveek.com/cinema/showtime/" + String.valueOf(idCinema) + "?date=" + date + "&header=1";
		Document doc;
		try {
			doc = Jsoup.connect(URL).method(Connection.Method.GET).ignoreContentType(true).execute().parse();

			Elements text = doc.getElementsByClass("mb-1");
			ArrayList<FilmShowTimes> res = new ArrayList<>();
//			System.out.println(URL);
			ArrayList<String> filmNames = new ArrayList<>();

			for (int i = 0; i < text.size(); i++) {
				System.out.println(text.get(i).text());
				if (text.get(i).text().contains("2D") || text.get(i).text().contains("3D")) {

				} else {

					filmNames.add(text.get(i).text());
				}

//				System.out.println(text.get(i).text());
			}

			ArrayList<ArrayList<String>> showtimes = new ArrayList<>();
			System.out.println(URL);
			for (int i = 0; i < text.size(); i++) {
				if (text.get(i).text().contains("2D")) {
//					System.out.println(text.get(i).text());
					showtimes.add(showtimes(text.get(i).text()));
				}

//				System.out.println(showtimes(text.get(i).text()));
			}

			for (int i = 0; i < showtimes.size(); i++) {
				System.out.println(showtimes.get(i));
				if (showtimes.get(i).contains("2D Phụ Đề Việt") && showtimes.get(i + 1).contains("2D Lồng Tiếng")) {
//					System.out.println("log");

					showtimes.remove(i + 1);
				}
			}
//			System.out.println(showtimes.size());

			for (int i = 0; i < (filmNames.size()); i++) {
				FilmShowTimes f = new FilmShowTimes();
				f.setName(filmNames.get(i));
				f.setShowTimes(showtimes.get(i));
				res.add(f);
			}

			return res;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
}
