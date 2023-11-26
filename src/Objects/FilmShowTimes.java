package Objects;

import java.io.Serializable;
import java.util.ArrayList;

public class FilmShowTimes implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private ArrayList<String> showTimes;
	
	public ArrayList<String> getShowTimes() {
		return showTimes;
	}
	
	public void setShowTimes(ArrayList<String> showTimes) {
		this.showTimes = showTimes;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
