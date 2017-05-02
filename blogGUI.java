import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.Font;

//import com.google.gson.JsonElement;

public class blogGUI {
	JMenuBar menuBar;
	JMenu menu;
	JFrame mframe, lframe;
	JTextField luser;
	JPasswordField lpass; 
	JTable ArticlesList = new JTable();
	JScrollPane ArticlesPane;
	JTextField textField;
	JTextArea textArea;
	JButton bsave;
	public JMenu menuItemsBlogs;
	
	public blogGUI () {
	}
	
	private void updateMenu() {
		String [] results = blogclient.bDomains.list();
		for (String result : results) {
			JMenuItem menuItemB = new JMenuItem(result);
			menuItemB.addActionListener(new blogDomainChanger(result));	
			menuItemsBlogs.add(menuItemB);
		}
	}
	
	public void hideArticlesList() {
		try { mframe.remove(ArticlesPane);} catch (NullPointerException e) {}
	}
	
	public void showArticlesList(Object[][] data, String[] columnNames) {
		hideArticlesList();
		hideArticle();
		ArticlesList = new JTable(data, columnNames);
			ArticlesList.setDefaultEditor(Object.class, null);
			ArticlesList.getColumn("Edit").setCellRenderer(new ButtonRenderer());
			ArticlesList.getColumn("Edit").setCellEditor(new ButtonEditor(new JCheckBox()));
		ArticlesPane = new JScrollPane(ArticlesList);
		ArticlesList.setFillsViewportHeight(true);
		mframe.add(ArticlesPane);
		mframe.revalidate();
	}
	public void hideArticle() {
		try { mframe.remove(textField);} catch (NullPointerException e) {}
		try { mframe.remove(textArea);} catch (NullPointerException e) {}
		try { mframe.remove(bsave);} catch (NullPointerException e) {}
		try { mframe.remove(ArticlesPane);} catch (NullPointerException e) {}
	}
	public void showArticle(String title, String text_src) {
		hideArticlesList();
		hideArticle();
		textField = new JTextField(20);
		textArea = new JTextArea(5, 20);
        //textArea.setEditable(false);
		textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("monospaced", Font.PLAIN, 12));
        textArea.setText(text_src);
        textField.setText(title);
		ArticlesPane = new JScrollPane(textArea);
		bsave = new JButton("Save");
		bsave.addActionListener( new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	//doLogin(luser.getText(), String.valueOf(lpass.getPassword()));
		    }
		});
		
		mframe.add(ArticlesPane, BorderLayout.CENTER);
		mframe.add(textField, BorderLayout.NORTH);
		mframe.add(bsave, BorderLayout.SOUTH);
		mframe.revalidate();
	}
	
	public void reInitGUI() {
		if (blogclient.bUser.isUserLogedIn()) {
			
			updateMenu();
			
			lframe.setVisible(false);
			mframe.setVisible(true);
		} else {
			lframe.setVisible(true);
			mframe.setVisible(false);
		}
	}
	
	public void initGUI() {
		mframe = new JFrame("Blog client");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		mframe.setBounds(0,0, (int)(screenSize.width/1.5),(int)(screenSize.height/1.5));
		//mframe.setBounds(0,0, 200, 200);
		mframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		menuBar = new JMenuBar();
		menu = new JMenu("Blogs");

		menuItemsBlogs = new JMenu("My Blogs");
		menu.add(menuItemsBlogs);
		
		menu.addSeparator();

		JMenuItem menuItemE = new JMenuItem("Exit");
		menuItemE.addActionListener( new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	exitProgram(0);
		    }
		});		
		menu.add(menuItemE);
		
		menuBar.add(menu);
		mframe.setJMenuBar(menuBar);
		centreWindow(mframe);
		//mframe.setVisible(true);
		if (blogclient.bUser.isUserLogedIn()) {
			mframe.setVisible(true);
		} else {
			initLoginPass();
		}
	}
	private void initLoginPass() {
		lframe = new JFrame("Login");
		lframe.setBounds(100,100, 200,120);
		
		JPanel lpanel = new JPanel();
		JButton blogin = new JButton("Login");
		blogin.addActionListener( new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	doLogin(luser.getText(), String.valueOf(lpass.getPassword()));
		    }
		});
		luser = new JTextField(15);
		lpass = new JPasswordField(15);
		
		lpanel.add(luser);
		lpanel.add(lpass);
		lpanel.add(blogin);
		
		lframe.getContentPane().add(lpanel);
		
		centreWindow(lframe);
		lframe.setVisible(true);
	}	
	
	public void doLogin(String login, String password) {
		if (!blogclient.bUser.login(login, password)) {
			showError("Unable to login");
		}
		reInitGUI();
	}
	public void showError (String t) {
		JOptionPane.showMessageDialog(null, t);
	}
	public static void centreWindow(Window frame) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
	}
	
	public static void exitProgram (int code) {
		System.exit(code);
	}
}
