package Objects;

import java.io.Serializable;
import java.util.ArrayList;

public class DataForFilmShowTimes implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<FilmShowTimes> dataList;

    public DataForFilmShowTimes(ArrayList<FilmShowTimes> listCinema) {
        this.dataList = listCinema;
    }

    public ArrayList<FilmShowTimes> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<FilmShowTimes> dataList) {
        this.dataList = dataList;
    }
}