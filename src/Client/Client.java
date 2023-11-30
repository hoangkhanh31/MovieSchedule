package Client;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.Icon;

import java.awt.CardLayout;
import javax.swing.SpringLayout;
import java.awt.BorderLayout;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import Objects.Cinema;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.JSeparator;
import javax.swing.UIManager;

public class Client {

	private JFrame frame;
	private JTextField searchTxt;
	private JTable tbl_cinema;
	private static ArrayList<Cinema> listCinema;
	DefaultTableModel model_tbl_cinema = new DefaultTableModel();
	DefaultTableModel model_tbl_film = new DefaultTableModel();
	private JTextField txt_cinema;
	public static String currentDay;
	public static int idCinemaChosen;
	public static String date;
	private static ArrayList<String> listFilm;
	private JDateChooser date_date;
	private JTable tbl_showtimes;
	public static int idFilmChosen;
	public static String filmNameChosen;
	public InetAddress IPAddress = getInetAddress();
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Client() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Color primary_color = new Color(216, 45, 139);
		Color white_color = new Color(255, 255, 255);
		Color background_color = new Color(255, 240, 246);
		Color border_color = new Color(235, 47, 150);
		Color gray_color = new Color(192,192,192);

		frame = new JFrame();
		frame.setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 1200, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(white_color);
		frame.getContentPane().setLayout(null);

		JPanel HeaderJP = new JPanel();
		HeaderJP.setBounds(0, 0, 1184, 93);
		frame.add(HeaderJP);
		HeaderJP.setLayout(null);

		JLabel logoHeaderLbl = new JLabel("");
		logoHeaderLbl.setIcon(new ImageIcon("D:\\CODE\\LapTrinhMang\\MovieSchedule\\img\\logo-momo.png"));
		logoHeaderLbl.setBounds(10, 21, 40, 40);
		HeaderJP.add(logoHeaderLbl);

		JLabel headerLbl = new JLabel("LỊCH CHIẾU PHIM");
		headerLbl.setFont(new Font("Tahoma", Font.BOLD, 22));
		headerLbl.setHorizontalAlignment(SwingConstants.CENTER);
		headerLbl.setBounds(358, 11, 404, 58);
		headerLbl.setForeground(primary_color);
		HeaderJP.setBackground(white_color);
		HeaderJP.add(headerLbl);

		JPanel BodyJP = new JPanel();
		BodyJP.setBounds(0, 93, 1184, 668);
		BodyJP.setBackground(white_color);
		frame.getContentPane().add(BodyJP);
		BodyJP.setLayout(null);

		JPanel cinemaJP = new JPanel();
		cinemaJP.setBorder(new LineBorder(new Color(0, 0, 0)));
		cinemaJP.setBounds(0, 0, 1184, 88);
		cinemaJP.setBackground(white_color);
		BodyJP.add(cinemaJP);
		cinemaJP.setLayout(null);

		JPanel panel_all = new JPanel();
		panel_all.setBackground(white_color);
		panel_all.setBounds(10, 11, 100, 66);
		panel_all.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cinemaJP.add(panel_all);
		panel_all.setLayout(null);

		// Scale Logo để thêm vào JLabel
		ImageIcon allCinemaIcon_temp = new ImageIcon("D:\\CODE\\LapTrinhMang\\MovieSchedule\\img\\ALL.png");
		Image scaledAllCinemaIcon = allCinemaIcon_temp.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon allCinemaIcon = new ImageIcon(scaledAllCinemaIcon);

		ImageIcon cgvCinemaIcon_temp = new ImageIcon("D:\\CODE\\LapTrinhMang\\MovieSchedule\\img\\CGV.png");
		Image scaledCGVIcon = cgvCinemaIcon_temp.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon cgvCinemaIcon = new ImageIcon(scaledCGVIcon);

		ImageIcon lotteCinemaIcon_temp = new ImageIcon("D:\\CODE\\LapTrinhMang\\MovieSchedule\\img\\LOTTE.png");
		Image scaledLotteIcon = lotteCinemaIcon_temp.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon lotteCinemaIcon = new ImageIcon(scaledLotteIcon);

		ImageIcon betaCinemaIcon_temp = new ImageIcon("D:\\CODE\\LapTrinhMang\\MovieSchedule\\img\\BETA.png");
		Image scaledBetaIcon = betaCinemaIcon_temp.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon betaCinemaIcon = new ImageIcon(scaledBetaIcon);

		ImageIcon bhdCinemaIcon_temp = new ImageIcon("D:\\CODE\\LapTrinhMang\\MovieSchedule\\img\\BHD.png");
		Image scaledBhdIcon = bhdCinemaIcon_temp.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon bhdCinemaIcon = new ImageIcon(scaledBhdIcon);

		ImageIcon cinestarCinemaIcon_temp = new ImageIcon("D:\\CODE\\LapTrinhMang\\MovieSchedule\\img\\CINESTAR.png");
		Image scaledCinestarIcon = cinestarCinemaIcon_temp.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon cinestarCinemaIcon = new ImageIcon(scaledCinestarIcon);

		ImageIcon dcineCinemaIcon_temp = new ImageIcon("D:\\CODE\\LapTrinhMang\\MovieSchedule\\img\\DCINE.png");
		Image scaledDcineIcon = dcineCinemaIcon_temp.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon dcineCinemaIcon = new ImageIcon(scaledDcineIcon);

		ImageIcon megaCinemaIcon_temp = new ImageIcon("D:\\CODE\\LapTrinhMang\\MovieSchedule\\img\\MEGA.png");
		Image scaledMegaIcon = megaCinemaIcon_temp.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon megaCinemaIcon = new ImageIcon(scaledMegaIcon);

		ImageIcon galaxyCinemaIcon_temp = new ImageIcon("D:\\CODE\\LapTrinhMang\\MovieSchedule\\img\\GALAXY.png");
		Image scaledGalaxyIcon = galaxyCinemaIcon_temp.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon galaxyCinemaIcon = new ImageIcon(scaledGalaxyIcon);

		// Label Cinema Logo
		JLabel allCinemaLbl = new JLabel("");
		allCinemaLbl.setBounds(22, 11, 50, 50);
		allCinemaLbl.setIcon(allCinemaIcon);
		panel_all.add(allCinemaLbl);

		JPanel panel_cgv = new JPanel();
		panel_cgv.setLayout(null);
		panel_cgv.setBackground(Color.WHITE);
		panel_cgv.setBounds(115, 11, 95, 66);
		panel_cgv.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cinemaJP.add(panel_cgv);

		JLabel cgvCinemaLbl = new JLabel("");
		cgvCinemaLbl.setIcon(cgvCinemaIcon);
		cgvCinemaLbl.setBounds(22, 11, 50, 50);

		panel_cgv.add(cgvCinemaLbl);

		JPanel panel_lotte = new JPanel();
		panel_lotte.setLayout(null);
		panel_lotte.setBackground(Color.WHITE);
		panel_lotte.setBounds(220, 11, 95, 66);
		panel_lotte.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cinemaJP.add(panel_lotte);

		JLabel lotteCinemaLbl = new JLabel("");
		lotteCinemaLbl.setBounds(22, 11, 50, 50);
		lotteCinemaLbl.setIcon(lotteCinemaIcon);
		panel_lotte.add(lotteCinemaLbl);

		JPanel panel_galaxy = new JPanel();
		panel_galaxy.setLayout(null);
		panel_galaxy.setBackground(Color.WHITE);
		panel_galaxy.setBounds(325, 11, 95, 66);
		panel_galaxy.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cinemaJP.add(panel_galaxy);

		JLabel galaxyCinemaLbl = new JLabel("");
		galaxyCinemaLbl.setBounds(22, 11, 50, 50);
		galaxyCinemaLbl.setIcon(galaxyCinemaIcon);
		panel_galaxy.add(galaxyCinemaLbl);

		JPanel panel_bhd = new JPanel();
		panel_bhd.setLayout(null);
		panel_bhd.setBackground(Color.WHITE);
		panel_bhd.setBounds(430, 11, 95, 66);
		panel_bhd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cinemaJP.add(panel_bhd);

		JLabel bhdCinemaLbl = new JLabel("");
		bhdCinemaLbl.setBounds(22, 11, 50, 50);
		bhdCinemaLbl.setIcon(bhdCinemaIcon);
		panel_bhd.add(bhdCinemaLbl);

		JPanel panel_beta = new JPanel();
		panel_beta.setLayout(null);
		panel_beta.setBackground(Color.WHITE);
		panel_beta.setBounds(535, 11, 95, 66);
		panel_beta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cinemaJP.add(panel_beta);

		JLabel betaCinemaLbl = new JLabel("");
		betaCinemaLbl.setBounds(22, 11, 50, 50);
		betaCinemaLbl.setIcon(betaCinemaIcon);
		panel_beta.add(betaCinemaLbl);

		JPanel panel_cinestar = new JPanel();
		panel_cinestar.setLayout(null);
		panel_cinestar.setBackground(Color.WHITE);
		panel_cinestar.setBounds(640, 11, 95, 66);
		panel_cinestar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cinemaJP.add(panel_cinestar);

		JLabel cinestarCinemaLbl = new JLabel("");
		cinestarCinemaLbl.setBounds(22, 11, 50, 50);
		cinestarCinemaLbl.setIcon(cinestarCinemaIcon);
		panel_cinestar.add(cinestarCinemaLbl);

		JPanel panel_mega = new JPanel();
		panel_mega.setLayout(null);
		panel_mega.setBackground(Color.WHITE);
		panel_mega.setBounds(745, 11, 95, 66);
		panel_mega.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cinemaJP.add(panel_mega);

		JLabel megaCinemaLbl = new JLabel("");
		megaCinemaLbl.setBounds(22, 11, 50, 50);
		megaCinemaLbl.setIcon(megaCinemaIcon);
		panel_mega.add(megaCinemaLbl);

		JPanel panel_dcine = new JPanel();
		panel_dcine.setLayout(null);
		panel_dcine.setBackground(Color.WHITE);
		panel_dcine.setBounds(850, 11, 95, 66);
		panel_dcine.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cinemaJP.add(panel_dcine);

		JLabel dcineCinemaLbl = new JLabel("");
		dcineCinemaLbl.setBounds(22, 11, 50, 50);
		dcineCinemaLbl.setIcon(dcineCinemaIcon);
		panel_dcine.add(dcineCinemaLbl);

		JPanel SearchCinemaJP = new JPanel();
		SearchCinemaJP.setBounds(0, 87, 417, 581);
		SearchCinemaJP.setBackground(white_color);
		BodyJP.add(SearchCinemaJP);
		SearchCinemaJP.setLayout(null);

		JPanel SearchBarJP = new JPanel();
		SearchBarJP.setBounds(43, 11, 309, 68);
		SearchCinemaJP.add(SearchBarJP);
		SearchBarJP.setLayout(null);

		searchTxt = new JTextField();
		searchTxt.setFont(new Font("Tahoma", Font.PLAIN, 12));
		searchTxt.setText("Tìm theo tên rạp...");
		searchTxt.setBounds(10, 11, 197, 42);
		searchTxt.setForeground(primary_color);
		SearchBarJP.add(searchTxt);
		searchTxt.setColumns(10);

		JLabel searchBtn = new JLabel("");
		searchBtn.setIcon(new ImageIcon("D:\\CODE\\LapTrinhMang\\MovieSchedule\\img\\icon-search.png"));
		searchBtn.setBounds(253, 11, 24, 36);
		SearchBarJP.add(searchBtn);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(43, 90, 309, 441);
		SearchCinemaJP.add(scrollPane);

		tbl_cinema = new JTable();
		tbl_cinema.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tbl_cinema.setRowHeight(25);
		scrollPane.setViewportView(tbl_cinema);

		JPanel ContentJP = new JPanel();
		ContentJP.setBounds(418, 87, 766, 581);
		ContentJP.setBackground(white_color);
		BodyJP.add(ContentJP);
		ContentJP.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Lịch chiếu tại :");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(21, 11, 97, 69);
		lblNewLabel.setForeground(primary_color);
		ContentJP.add(lblNewLabel);
		
		txt_cinema = new JTextField();
		txt_cinema.setBounds(119, 30, 176, 35);
		txt_cinema.setForeground(primary_color);
		ContentJP.add(txt_cinema);
		txt_cinema.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Ngày");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(305, 13, 63, 69);
		lblNewLabel_1.setForeground(primary_color);
		ContentJP.add(lblNewLabel_1);
		
		date_date = new JDateChooser();
		date_date.setBounds(367, 30, 213, 35);
		ContentJP.add(date_date);
		
		JButton btnUpdate = new JButton("Cập nhật");
		btnUpdate.setForeground(primary_color);
		btnUpdate.setBackground(background_color);
		btnUpdate.setBorder(new LineBorder(border_color));
		btnUpdate.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Clear dữ liệu cũ của Table
				model_tbl_film.setRowCount(0);
				
				int selectedRow = tbl_cinema.getSelectedRow();
                if (selectedRow >= 0) {
//                	getCurrentDate();
                	idCinemaChosen = listCinema.get(selectedRow).getId();
        			txt_cinema.setText(listCinema.get(selectedRow).getName());
        			try {
            			DatagramSocket socket = new DatagramSocket();
            			
            			InetAddress IPAddress = InetAddress.getByName("localhost");
            		    byte[] sendData = new byte[1048576];
            		    byte[] receiveData = new byte[1048576];
            				
            				String input = "getFilmByDay;" + String.valueOf(idCinemaChosen) + ";" + String.valueOf(date_date.getDate().getYear() + 1900) + "-" + String.valueOf(date_date.getDate().getMonth() + 1) + "-" + date_date.getDate().getDate();
            				System.out.println(input);

            			    sendData = input.getBytes();
            					
            				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 5000);

            			    socket.send(sendPacket);

            			    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            			    socket.receive(receivePacket);
            			    
            			    ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
            			    ObjectInputStream ois = new ObjectInputStream(bais);
            			    
//            			    Objects.DataForCinema data = (Objects.DataForCinema) ois.readObject();
            			    ArrayList<String> list = (ArrayList<String>) ois.readObject();
            			    
            			    listFilm = list;
            			    setModelFilm(list);
            			    date = String.valueOf(date_date.getDate().getYear() + 1900) + "-" + String.valueOf(date_date.getDate().getMonth() + 1) + "-" + date_date.getDate().getDate();
//            			    System.out.println(list);
//            			    listCinema = data.getDataList();
            			    
            		    socket.close();
            		} catch (IOException | ClassNotFoundException err) { 
            			System.err.println(err); 
            		}
                }
			}
		});
		btnUpdate.setBounds(603, 33, 117, 29);
		ContentJP.add(btnUpdate);
		
		JButton btnDetail = new JButton("Xem chi tiết");
		btnDetail.setForeground(primary_color);
		btnDetail.setBackground(background_color);
		btnDetail.setBorder(new LineBorder(border_color));
		btnDetail.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        if(idFilmChosen == 0) {
		        	System.out.println("Không tìm thấy dữ liệu!");
		        } else {
		        	Details details = new Details();
		        	
//		        	details.frame.setVisible(true);
		        }
			}
		});
		btnDetail.setBounds(494, 537, 222, 29);
		ContentJP.add(btnDetail);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(21, 91, 710, 441);
		ContentJP.add(scrollPane_1);
		
		tbl_showtimes = new JTable();
		tbl_showtimes.setRowHeight(25);
		tbl_showtimes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane_1.setViewportView(tbl_showtimes);
		//thêm sự kiện cho bảng ds phim
        tbl_showtimes.addMouseListener(new java.awt.event.MouseAdapter() {
    		public void mouseClicked(java.awt.event.MouseEvent evt) {
    			tblShowTimesMouseClicked(evt);
    		}
    	});

		// Thêm sự kiện cho Panel All Cinema
		panel_all.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				try {
        			DatagramSocket socket = new DatagramSocket();

//        			InetAddress IPAddress = InetAddress.getByName("localhost");
        		    byte[] sendData;
        		    byte[] receiveData = new byte[1048576];	
        		 
        				
        				String input = "getCinemaByCineplex;all";
        				
        				System.out.println(input);
        				
        			    sendData = input.getBytes();
        					
        				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 5000);

        			    socket.send(sendPacket);

        			    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        			    socket.receive(receivePacket);
        			    
        			    ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
        			    ObjectInputStream ois = new ObjectInputStream(bais);
        			    
        			    ArrayList<Cinema> list = (ArrayList<Cinema>) ois.readObject();
        			    
        			    setModelCinema(list);
        			    
        			    listCinema = list;
        			    
        		    socket.close();
        		} catch (IOException | ClassNotFoundException e) { 
        			System.err.println(e); 
        		}
			}
		});
		
		panel_cgv.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				try {
        			DatagramSocket socket = new DatagramSocket();

//        			InetAddress IPAddress = InetAddress.getByName("localhost");
        		    byte[] sendData;
        		    byte[] receiveData = new byte[1048576];	
        		 
        				
        				String input = "getCinemaByCineplex;cgv-cineplex";
        				
        				System.out.println(input);
        				
        			    sendData = input.getBytes();
        					
        				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 5000);

        			    socket.send(sendPacket);

        			    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        			    socket.receive(receivePacket);
        			    
        			    ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
        			    ObjectInputStream ois = new ObjectInputStream(bais);
        			    
        			    ArrayList<Cinema> list = (ArrayList<Cinema>) ois.readObject();
        			    
        			    setModelCinema(list);
        			    
        			    listCinema = list;
        			    
        		    socket.close();
        		} catch (IOException | ClassNotFoundException e) { 
        			System.err.println(e); 
        		}
			}
		});
		
		panel_lotte.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				try {
        			DatagramSocket socket = new DatagramSocket();

//        			InetAddress IPAddress = InetAddress.getByName("localhost");
        		    byte[] sendData;
        		    byte[] receiveData = new byte[1048576];	
        		 
        				
        				String input = "getCinemaByCineplex;lotte-cinema";
        				
        				System.out.println(input);
        				
        			    sendData = input.getBytes();
        					
        				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 5000);

        			    socket.send(sendPacket);

        			    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        			    socket.receive(receivePacket);
        			    
        			    ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
        			    ObjectInputStream ois = new ObjectInputStream(bais);
        			    
        			    ArrayList<Cinema> list = (ArrayList<Cinema>) ois.readObject();
        			    
        			    setModelCinema(list);
        			    
        			    listCinema = list;
        			    
        		    socket.close();
        		} catch (IOException | ClassNotFoundException e) { 
        			System.err.println(e); 
        		}
			}
		});
		
		panel_galaxy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				try {
        			DatagramSocket socket = new DatagramSocket();

//        			InetAddress IPAddress = InetAddress.getByName("localhost");
        		    byte[] sendData;
        		    byte[] receiveData = new byte[1048576];	
        		 
        				
        				String input = "getCinemaByCineplex;galaxy-cinema";
        				
        				System.out.println(input);
        				
        			    sendData = input.getBytes();
        					
        				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 5000);

        			    socket.send(sendPacket);

        			    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        			    socket.receive(receivePacket);
        			    
        			    ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
        			    ObjectInputStream ois = new ObjectInputStream(bais);
        			    
        			    ArrayList<Cinema> list = (ArrayList<Cinema>) ois.readObject();
        			    
        			    setModelCinema(list);
        			    
        			    listCinema = list;
        			    
        		    socket.close();
        		} catch (IOException | ClassNotFoundException e) { 
        			System.err.println(e); 
        		}
			}
		});
		
		panel_bhd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				try {
        			DatagramSocket socket = new DatagramSocket();

//        			InetAddress IPAddress = InetAddress.getByName("localhost");
        		    byte[] sendData;
        		    byte[] receiveData = new byte[1048576];	
        		 
        				
        				String input = "getCinemaByCineplex;bhd-cineplex";
        				
        				System.out.println(input);
        				
        			    sendData = input.getBytes();
        					
        				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 5000);

        			    socket.send(sendPacket);

        			    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        			    socket.receive(receivePacket);
        			    
        			    ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
        			    ObjectInputStream ois = new ObjectInputStream(bais);
        			    
        			    ArrayList<Cinema> list = (ArrayList<Cinema>) ois.readObject();
        			    
        			    setModelCinema(list);
        			    
        			    listCinema = list;
        			    
        		    socket.close();
        		} catch (IOException | ClassNotFoundException e) { 
        			System.err.println(e); 
        		}
			}
		});
		
		panel_beta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				try {
        			DatagramSocket socket = new DatagramSocket();

//        			InetAddress IPAddress = InetAddress.getByName("localhost");
        		    byte[] sendData;
        		    byte[] receiveData = new byte[1048576];	
        		 
        				
        				String input = "getCinemaByCineplex;beta-cineplex";
        				
        				System.out.println(input);
        				
        			    sendData = input.getBytes();
        					
        				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 5000);

        			    socket.send(sendPacket);

        			    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        			    socket.receive(receivePacket);
        			    
        			    ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
        			    ObjectInputStream ois = new ObjectInputStream(bais);
        			    
        			    ArrayList<Cinema> list = (ArrayList<Cinema>) ois.readObject();
        			    
        			    setModelCinema(list);
        			    
        			    listCinema = list;
        			    
        		    socket.close();
        		} catch (IOException | ClassNotFoundException e) { 
        			System.err.println(e); 
        		}
			}
		});
		
		panel_cinestar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				try {
        			DatagramSocket socket = new DatagramSocket();

//        			InetAddress IPAddress = InetAddress.getByName("localhost");
        		    byte[] sendData;
        		    byte[] receiveData = new byte[1048576];	
        		 
        				
        				String input = "getCinemaByCineplex;cinestar";
        				
        				System.out.println(input);
        				
        			    sendData = input.getBytes();
        					
        				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 5000);

        			    socket.send(sendPacket);

        			    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        			    socket.receive(receivePacket);
        			    
        			    ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
        			    ObjectInputStream ois = new ObjectInputStream(bais);
        			    
        			    ArrayList<Cinema> list = (ArrayList<Cinema>) ois.readObject();
        			    
        			    setModelCinema(list);
        			    
        			    listCinema = list;
        			    
        		    socket.close();
        		} catch (IOException | ClassNotFoundException e) { 
        			System.err.println(e); 
        		}
			}
		});
		
		panel_mega.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				try {
        			DatagramSocket socket = new DatagramSocket();

//        			InetAddress IPAddress = InetAddress.getByName("localhost");
        		    byte[] sendData;
        		    byte[] receiveData = new byte[1048576];	
        		 
        				
        				String input = "getCinemaByCineplex;mega-gs-cinemas";
        				
        				System.out.println(input);
        				
        			    sendData = input.getBytes();
        					
        				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 5000);

        			    socket.send(sendPacket);

        			    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        			    socket.receive(receivePacket);
        			    
        			    ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
        			    ObjectInputStream ois = new ObjectInputStream(bais);
        			    
        			    ArrayList<Cinema> list = (ArrayList<Cinema>) ois.readObject();
        			    
        			    setModelCinema(list);
        			    
        			    listCinema = list;
        			    
        		    socket.close();
        		} catch (IOException | ClassNotFoundException e) { 
        			System.err.println(e); 
        		}
			}
		});
	
		panel_dcine.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				try {
        			DatagramSocket socket = new DatagramSocket();

//        			InetAddress IPAddress = InetAddress.getByName("localhost");
        		    byte[] sendData;
        		    byte[] receiveData = new byte[1048576];	
        		 
        				
        				String input = "getCinemaByCineplex;dcine";
        				
        				System.out.println(input);
        				
        			    sendData = input.getBytes();
        					
        				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 5000);

        			    socket.send(sendPacket);

        			    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        			    socket.receive(receivePacket);
        			    
        			    ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
        			    ObjectInputStream ois = new ObjectInputStream(bais);
        			    
        			    ArrayList<Cinema> list = (ArrayList<Cinema>) ois.readObject();
        			    
        			    setModelCinema(list);
        			    
        			    listCinema = list;
        			    
        		    socket.close();
        		} catch (IOException | ClassNotFoundException e) { 
        			System.err.println(e); 
        		}
			}
		});
		
		tbl_cinema.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {		
            	model_tbl_film.setRowCount(0);
                int selectedRow = tbl_cinema.getSelectedRow();
                if (selectedRow >= 0) {
                	getCurrentDate();
                	idCinemaChosen = listCinema.get(selectedRow).getId();
        			txt_cinema.setText(listCinema.get(selectedRow).getName());
                    try {
            			DatagramSocket socket = new DatagramSocket();

//            			InetAddress IPAddress = InetAddress.getByName("localhost");
            		    byte[] sendData = new byte[1048576];
            		    byte[] receiveData = new byte[1048576];
            		    
            				String input = "getFilmByDay;" + String.valueOf(idCinemaChosen) + ";" + currentDay;
            				
            				System.out.println(input);
            				
            			    sendData = input.getBytes();
            					
            				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 5000);

            			    socket.send(sendPacket);

            			    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            			    socket.receive(receivePacket);
            			    
            			    ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
            			    ObjectInputStream ois = new ObjectInputStream(bais);
            			    
            			    ArrayList<String> list = (ArrayList<String>) ois.readObject();
            			    setModelFilm(list);
            			    listFilm = list;
            			    
            		    socket.close();
            		} catch (IOException | ClassNotFoundException err) { 
            			System.err.println(err); 
            		}
                }
            }
        });
		
	}
	
	//Thêm cinema vào bảng khi bấm chọn cụm rạp
    private void setModelCinema(ArrayList<Cinema> list) {
		Vector<String> header = new Vector<String>();
		header.add("");
		model_tbl_cinema = new DefaultTableModel(header, 0);
		for (Cinema c : list) {
			Vector<String> row = new Vector<String>();
			row.add(c.getName());
			model_tbl_cinema.addRow(row);
			tbl_cinema.setModel(model_tbl_cinema);
		}
	}
    
    private void getCurrentDate() {
    	LocalDate currentDate = LocalDate.now();
        int day = currentDate.getDayOfMonth();
        int month = currentDate.getMonthValue();
        int year = currentDate.getYear();
        currentDay = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day);
//	    Date date = new Date(currentDate.getYear() - 1900, currentDate.getMonthValue(), currentDate.getDayOfMonth());
	    Date date1 = new Date();
	    date = currentDay;
	    date_date.setDate(date1);
	    date_date.setMinSelectableDate(date1);
    }
    
  //thêm phim vào bảng khi bấm chọn rạp
    private void setModelFilm(ArrayList<String> kq) {
		Vector<String> header = new Vector<String>();
		header.add("");
		model_tbl_film = new DefaultTableModel(header, 0);
		for (String c : kq) {
			Vector<String> row = new Vector<String>();
			row.add(c);
			model_tbl_film.addRow(row);
			tbl_showtimes.setModel(model_tbl_film);
		}
	}
    
  //thêm sự kiện chọn film để lấy id phim từ Momo
    private void tblShowTimesMouseClicked(java.awt.event.MouseEvent evt) {
		int i = tbl_showtimes.getSelectedRow();
		if (i >= 0) {
			System.out.println("id cinema duoc chon: " + listCinema.get(i).getId());
//			idCinemaChosen = listCinema.get(i).getId();
//			txt_cinema.setText(listCinema.get(i).getName());
			
			try {
    			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    			DatagramSocket socket = new DatagramSocket();

//    			InetAddress IPAddress = InetAddress.getByName("localhost");
    		    byte[] sendData = new byte[1048576];
    		    byte[] receiveData = new byte[1048576];
    		    
    				
    				String input = "getIdFilm;" + listFilm.get(i);
    				System.out.println(input);
    			    sendData = input.getBytes();
    					
    				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 5000);

    			    socket.send(sendPacket);

    			    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

    			    socket.receive(receivePacket);
    			    
    			    ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
    			    ObjectInputStream ois = new ObjectInputStream(bais);
    			    
//    			    ArrayList<String> list = (ArrayList<String>) ois.readObject();
    			    
    			    idFilmChosen = (int) ois.readObject();
//    			    setModelFilm(list);
    			    filmNameChosen = listFilm.get(i);
    			    
//    			    System.out.println(idFilmChosen);
//    			    System.out.println(filmNameChosen);
//    			    listCinema = data.getDataList();
    			    
    		    socket.close();
    		} catch (IOException | ClassNotFoundException e) { 
    			System.err.println(e); 
    		}
		}
	}
    
    private static String getIpFromApi(){
    	try {
    		String api = "https://api-generator.retool.com/cEQCXR/movieSchedule/1";
    		
    		Document doc = Jsoup.connect(api)
                    .ignoreContentType(true).ignoreHttpErrors(true)
                    .header("Content-Type", "application/json")
                    .method(Connection.Method.GET).execute().parse();
            JSONObject jsonObject = new JSONObject(doc.text());
            
            String ip = jsonObject.get("ip").toString();
//            System.out.println(jsonObject.get("ip"));
            return ip;
    	}
    	catch (IOException e) {
    		System.out.println("Lỗi tại hàm getIpFromApi");
    		return null;
    	}
    }
    
    private static InetAddress getInetAddress() {
    	try {
    		String ip = getIpFromApi();
    		InetAddress IPAddress = InetAddress.getByName(ip);
    		return IPAddress;
    	}
    	catch (UnknownHostException e){
    		System.out.println("Lỗi tại hàm getInetAddress");
    		return null;
    	}
    }
}
