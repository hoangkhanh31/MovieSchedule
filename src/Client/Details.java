package Client;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Objects.Film;
import Objects.FilmShowTimes;
import Server.Server;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Details {

	public JFrame frame;

	private static int idFilm;
	private String date;
	public static String filmNameChosen;
	private String casts;
	private Film film;
	DefaultTableModel model_tbl_showtimes = new DefaultTableModel();
	
	private JLabel graphic_URL;
	private JTable tbl_showtimes;
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Details window = new Details();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public Details() {
		
		
		idFilm = Client.idFilmChosen;
        date = Client.date;
		film = Server.getFilmDetails(idFilm);
        filmNameChosen = Client.filmNameChosen;
        casts = "";
        
//        for(int i = 0; i < Server.getShowTimes(Client.idCinemaChosen, Client.date).size(); i++) {
//        	System.out.print("time:  " + i + " " + Server.getShowTimes(Client.idCinemaChosen, Client.date).get(i).getShowTimes());
//        }
        initialize();
        setModelShowtime(getShowTimes(Client.idCinemaChosen, Client.date));
        
        try {
        	URL url = new URL(film.getGraphicUrl());
        	System.out.println(url);
        	
            ImageIcon icon = new ImageIcon(url);
    		Image scalingIcon = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
    		ImageIcon scaledIcon = new ImageIcon(scalingIcon);
    		
            graphic_URL.setIcon(scaledIcon);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Color primary_color = new Color(216, 45, 139);
		Color white_color = new Color(255, 255, 255);
		Color black_color = new Color(0,0,0);
		
		frame = new JFrame();
		frame.setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 1210, 760);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(white_color);
		frame.getContentPane().setLayout(null);
		
		JPanel Details = new JPanel();
		Details.setBounds(0, 0, 1194, 721);
		frame.getContentPane().add(Details);
		Details.setLayout(null);
		
		JPanel Sidebar = new JPanel();
		Sidebar.setBounds(0, 0, 362, 721);
		Sidebar.setBackground(black_color);
		Details.add(Sidebar);
		Sidebar.setLayout(null);
		
		JLabel lblThongtin = new JLabel("Thông tin phim");
		lblThongtin.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblThongtin.setForeground(white_color);
		lblThongtin.setBounds(82, 11, 178, 45);
		Sidebar.add(lblThongtin);
		
		graphic_URL = new JLabel("");
		graphic_URL.setBounds(24, 76, 314, 306);
		Sidebar.add(graphic_URL);
		
		JLabel lblViewTrailer = new JLabel("Xem Trailer");
		lblViewTrailer.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblViewTrailer.setBounds(121, 432, 113, 37);
		lblViewTrailer.setForeground(white_color);
		Sidebar.add(lblViewTrailer);
		
		JLabel txt_imdb_score = new JLabel();
		txt_imdb_score.setText("7/10");
		txt_imdb_score.setHorizontalAlignment(SwingConstants.CENTER);
		txt_imdb_score.setForeground(new Color(255, 204, 255));
		txt_imdb_score.setFont(new Font("Segoe UI", Font.BOLD, 36));
		txt_imdb_score.setBounds(30, 589, 129, 40);
		Sidebar.add(txt_imdb_score);
		
		JSeparator jSeparator1 = new JSeparator();
		jSeparator1.setOrientation(SwingConstants.VERTICAL);
		jSeparator1.setForeground(new Color(204, 204, 204));
		jSeparator1.setBackground(new Color(204, 204, 204));
		jSeparator1.setBounds(169, 581, 13, 67);
		Sidebar.add(jSeparator1);
		
		JLabel jLabel4 = new JLabel();
		jLabel4.setVerticalAlignment(SwingConstants.TOP);
		jLabel4.setText("IMDB");
		jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel4.setForeground(Color.WHITE);
		jLabel4.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		jLabel4.setBounds(24, 629, 129, 21);
		Sidebar.add(jLabel4);
		
		JLabel txt_rotten_tomatoes_score = new JLabel();
		txt_rotten_tomatoes_score.setText("58%");
		txt_rotten_tomatoes_score.setHorizontalAlignment(SwingConstants.CENTER);
		txt_rotten_tomatoes_score.setForeground(new Color(255, 204, 255));
		txt_rotten_tomatoes_score.setFont(new Font("Segoe UI", Font.BOLD, 36));
		txt_rotten_tomatoes_score.setBounds(192, 589, 129, 40);
		Sidebar.add(txt_rotten_tomatoes_score);
		
		JLabel jLabel6 = new JLabel();
		jLabel6.setVerticalAlignment(SwingConstants.TOP);
		jLabel6.setText("Rotten Tomatoes");
		jLabel6.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel6.setForeground(Color.WHITE);
		jLabel6.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		jLabel6.setBounds(192, 627, 129, 21);
		Sidebar.add(jLabel6);
		
		JPanel Information = new JPanel();
		Information.setBounds(368, 0, 826, 721);
		Details.add(Information);
		Information.setLayout(null);
		
		JLabel lbl_movie_name = new JLabel();
		lbl_movie_name.setBounds(33, 6, 273, 38);
		lbl_movie_name.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_movie_name.setText(film.getTitle());
		lbl_movie_name.setForeground(new Color(204, 0, 153));
		lbl_movie_name.setFont(new Font("Segoe UI", Font.BOLD, 28));
		lbl_movie_name.setBackground(new Color(51, 51, 51));
		Information.add(lbl_movie_name);
		
		JLabel lbl_title_en = new JLabel();
		lbl_title_en.setVerticalAlignment(SwingConstants.TOP);
		lbl_title_en.setText("The Super Mario Bros. Movie");
		lbl_title_en.setForeground(new Color(102, 102, 102));
		lbl_title_en.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lbl_title_en.setBounds(33, 55, 756, 31);
		Information.add(lbl_title_en);
		
		JLabel jLabel9 = new JLabel();
		jLabel9.setText("Thể loại:");
		jLabel9.setFont(new Font("Segoe UI", Font.BOLD, 18));
		jLabel9.setBounds(33, 92, 75, 25);
		Information.add(jLabel9);
		
		JLabel txt_gerne = new JLabel();
		txt_gerne.setText("Hài, Hoạt Hình, Phiêu Lưu, Gia Đình");
		txt_gerne.setForeground(new Color(102, 102, 102));
		txt_gerne.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txt_gerne.setBounds(118, 91, 315, 28);
		Information.add(txt_gerne);
		
		JLabel jLabel7 = new JLabel();
		jLabel7.setText("Quốc gia:");
		jLabel7.setFont(new Font("Segoe UI", Font.BOLD, 18));
		jLabel7.setBounds(497, 91, 82, 25);
		Information.add(jLabel7);
		
		JLabel txt_country = new JLabel();
		txt_country.setText("Mỹ");
		txt_country.setForeground(new Color(102, 102, 102));
		txt_country.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txt_country.setBounds(605, 91, 152, 29);
		Information.add(txt_country);
		
		JLabel jLabel1 = new JLabel();
		jLabel1.setText("Nội dung:");
		jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 18));
		jLabel1.setBounds(33, 136, 756, 29);
		Information.add(jLabel1);
		
		JTextArea txt_description = new JTextArea();
		txt_description.setWrapStyleWord(true);
		txt_description.setText("A plumber named MarioCuộc phiêu lưu đến Vương quốc Nấm của anh chàng thợ sửa ống nước Mario và quá trình anh vươn lên trở thành một huyền thoại.");
		txt_description.setTabSize(4);
		txt_description.setRows(5);
		txt_description.setLineWrap(true);
		txt_description.setForeground(new Color(102, 102, 102));
		txt_description.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txt_description.setEnabled(false);
		txt_description.setDisabledTextColor(new Color(51, 51, 51));
		txt_description.setColumns(20);
		txt_description.setCaretColor(Color.WHITE);
		txt_description.setBorder(null);
		txt_description.setBackground(new Color(255, 249, 254));
		txt_description.setBounds(33, 176, 737, 59);
		Information.add(txt_description);
		
		JLabel jLabel2 = new JLabel();
		jLabel2.setVerticalAlignment(SwingConstants.BOTTOM);
		jLabel2.setText("Diễn viên & Đoàn làm phim:");
		jLabel2.setFont(new Font("Segoe UI", Font.BOLD, 18));
		jLabel2.setBounds(33, 281, 756, 25);
		Information.add(jLabel2);
		
		JLabel txt_actor = new JLabel();
		txt_actor.setVerticalAlignment(SwingConstants.TOP);
		txt_actor.setText("Chris Pratt, Anna Taylor-Joy, Charlie Day, Jack Black,...");
		txt_actor.setForeground(new Color(102, 102, 102));
		txt_actor.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txt_actor.setBounds(33, 327, 756, 32);
		Information.add(txt_actor);
		
		JLabel jLabel11 = new JLabel();
		jLabel11.setText("Lịch chiếu phim:");
		jLabel11.setForeground(new Color(204, 0, 153));
		jLabel11.setFont(new Font("Segoe UI", Font.BOLD, 20));
		jLabel11.setBounds(33, 370, 756, 29);
		Information.add(jLabel11);
		
		tbl_showtimes = new JTable();
		tbl_showtimes.setBounds(33, 397, 737, 135);
		Information.add(tbl_showtimes);
		
		JLabel jLabel12 = new JLabel();
		jLabel12.setText("Bình luận từ người xem:");
		jLabel12.setFont(new Font("Segoe UI", Font.BOLD, 18));
		jLabel12.setBounds(33, 543, 756, 29);
		Information.add(jLabel12);
		
		JTextArea txt_review = new JTextArea();
		txt_review.setWrapStyleWord(true);
		txt_review.setText("- Mario và Luigi đều có nỗi sợ để vượt qua, vs Mario thì a cực ghét nấm nhưng phải ăn nấm để cứu e trai, Luigi thì cũng phải trở nên dũng cảm. Công chúa Peach tưởng chừng là nvat đc cứu nhưng thực ra chính là ng dạy mario thành hero. ncl nhiều góc nhìn hay từ game");
		txt_review.setTabSize(4);
		txt_review.setRows(5);
		txt_review.setLineWrap(true);
		txt_review.setForeground(new Color(102, 102, 102));
		txt_review.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txt_review.setEnabled(false);
		txt_review.setDisabledTextColor(new Color(51, 51, 51));
		txt_review.setColumns(20);
		txt_review.setCaretColor(Color.WHITE);
		txt_review.setBorder(null);
		txt_review.setBackground(new Color(255, 249, 254));
		txt_review.setBounds(35, 583, 754, 110);
		Information.add(txt_review);
		frame.setVisible(true);
	}
	
	private static ArrayList<String> getShowTimes(int idCinemaChosen, String date){
    	try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			DatagramSocket socket = new DatagramSocket();

			InetAddress IPAddress = InetAddress.getByName("localhost");
		    byte[] sendData = new byte[1048576];
		    byte[] receiveData = new byte[1048576];
		    
				
				String input = "getShowTimes;" + String.valueOf(idCinemaChosen) + ";" + date;
				
			    sendData = input.getBytes();
					
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 5000);

			    socket.send(sendPacket);

			    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

			    socket.receive(receivePacket);
			    
			    ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
			    ObjectInputStream ois = new ObjectInputStream(bais);
			    
//			    ArrayList<FilmShowTimes> list = (ArrayList<FilmShowTimes>) ois.readObject();
			    Objects.DataForFilmShowTimes data = (Objects.DataForFilmShowTimes) ois.readObject();
//			    data.getShowTimes();
//			    idFilmChosen = (int) ois.readObject();
//			    setModelFilm(list);
//			    filmNameChosen = listFilm.get(i);
			    
			    ArrayList<String> res = new ArrayList<>();
			    ArrayList<FilmShowTimes> list = data.getDataList();
			    
			    
			    for (int i = 0; i < list.size(); i ++) {
			    	if (list.get(i).getName().equals(filmNameChosen)) {
			    		res = list.get(i).getShowTimes();
			    		
			    	}
			    }
//			    System.out.println(filmNameChosen);
			    socket.close();
			    return res;
//			    listCinema = data.getDataList();
			    
		    
		} catch (IOException | ClassNotFoundException e) { 
			System.err.println(e); 
			return new ArrayList<>();
		}
    }
	
	private void setModelShowtime(ArrayList<String> kq) {
		Vector<String> header = new Vector<String>();
		header.add(Client.date);
		model_tbl_showtimes = new DefaultTableModel(header, 0);
		for (String c : kq) {
			Vector<String> row = new Vector<String>();
			row.add(c);
			model_tbl_showtimes.addRow(row);
			tbl_showtimes.setModel(model_tbl_showtimes);
		}
	}
	
}
