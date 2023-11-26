package Objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Film implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String title = "";
	private String ApiRating = "";
	private String ApiFilmType = "";
	private String ApiGenreName = "";
	private String SynopsisEn = "";
	private String Countries = "";
	private String ApiImdb;
	private String ApiRottenTomatoes;
	private String TitleEn;
	private ArrayList<String> ApiCasts;
	private String GraphicUrl;
	private String TrailerUrl;
	
	
	public String getSynopsisEn() {
		return SynopsisEn;
	}
	
	public void setSynopsisEn(String synopsisEn) {
		SynopsisEn = synopsisEn;
	}
	
	public String getApiGenreName() {
		return ApiGenreName;
	}
	
	public void setApiGenreName(String apiGenreName) {
		ApiGenreName = apiGenreName;
	}
	
	public String getApiFilmType() {
		return ApiFilmType;
	}
	
	public void setApiFilmType(String apiFilmType) {
		ApiFilmType = apiFilmType;
	}
	
	public String getApiRating() {
		return ApiRating;
	}
	
	public void setApiRating(String apiRating) {
		ApiRating = apiRating;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountries() {
		return Countries;
	}

	public void setCountries(String countries) {
		Countries = countries;
	}

	public String getApiImdb() {
		return ApiImdb;
	}

	public void setApiImdb(String apiImdb) {
		ApiImdb = apiImdb;
	}

	public String getApiRottenTomatoes() {
		return ApiRottenTomatoes;
	}

	public void setApiRottenTomatoes(String apiRottenTomatoes) {
		ApiRottenTomatoes = apiRottenTomatoes;
	}

	public String getTitleEn() {
		return TitleEn;
	}

	public void setTitleEn(String titleEn) {
		TitleEn = titleEn;
	}

	public ArrayList<String> getApiCasts() {
		return ApiCasts;
	}

	public void setApiCasts(ArrayList<String> apiCasts) {
		ApiCasts = apiCasts;
	}

	public String getGraphicUrl() {
		return GraphicUrl;
	}

	public void setGraphicUrl(String graphicUrl) {
		GraphicUrl = graphicUrl;
	}

	public String getTrailerUrl() {
		return TrailerUrl;
	}

	public void setTrailerUrl(String trailerUrl) {
		TrailerUrl = trailerUrl;
	}
}
