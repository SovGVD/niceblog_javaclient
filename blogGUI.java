import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.*;
//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.ListSelectionListener;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class blogGUI {
	JMenuBar menuBar;
	JMenu menu;
	JFrame mframe, lframe;
	JTextField luser;
	JPasswordField lpass; 
	JTable ArticlesList = new JTable();
	JScrollPane ArticlesPane;
	JTextField postTitle, postUrl;
	JTextArea postText;
	JButton bsave, bdelete;
	JButton badd;
	JPanel bPanel,titlePanel,lPanel;
	JCheckBox lrem;
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
		try { mframe.remove(lPanel);} catch (NullPointerException e) {}
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
		badd = new JButton("Add new");
		badd.addActionListener( new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	blogclient.bArticles.newArticle();
		    }
		});
		mframe.add(ArticlesPane, BorderLayout.CENTER);

		lPanel = new JPanel();
		lPanel.add(badd);

		mframe.add(lPanel, BorderLayout.SOUTH);
		mframe.revalidate();
	}
	public void hideArticle() {
		try { mframe.remove(postText);} catch (NullPointerException e) {}
		try { mframe.remove(bPanel);} catch (NullPointerException e) {}
		try { mframe.remove(titlePanel);} catch (NullPointerException e) {}
		try { mframe.remove(ArticlesPane);} catch (NullPointerException e) {}
	}
	public void showArticle(String title, String text_src, String url) {
		hideArticlesList();
		hideArticle();
		postTitle = new JTextField();
		postUrl = new JTextField();
		postText = new JTextArea(5, 20);
		postText.setLineWrap(true);
        postText.setWrapStyleWord(true);
        postText.setFont(new Font("monospaced", Font.PLAIN, 12));
        postText.setText(text_src);
        postTitle.setText(title);
        postUrl.setText(url);
		ArticlesPane = new JScrollPane(postText);
		
		titlePanel = new JPanel();
		titlePanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
			JLabel tmp_title_label = new JLabel("Title");
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 0.5;
			c.insets = new Insets(0,10,0,0); 
			titlePanel.add(tmp_title_label,c);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 1;
			c.gridy = 0;
			c.weightx = 0.5;
			c.gridwidth = 3;
			c.insets = new Insets(0,0,0,10);
			titlePanel.add(postTitle,c);
		
			JLabel tmp_url_label = new JLabel("URL");
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = 1;
			c.weightx = 0.5;
			c.insets = new Insets(0,10,0,0);
			titlePanel.add(tmp_url_label,c);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 1;
			c.gridy = 1;
			c.weightx = 0.5;
			c.gridwidth = 5;
			c.insets = new Insets(0,0,0,10);
			titlePanel.add(postUrl,c);
			
		bsave = new JButton("Save");
		bsave.addActionListener( new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	blogclient.bArticles.saveArticle(postTitle.getText(), postText.getText(), postUrl.getText());
		    }
		});
		bdelete = new JButton("Delete");
		bdelete.addActionListener( new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	blogclient.bArticles.deleteArticle();
		    }
		});
			
		bPanel = new JPanel();
		bPanel.add(bsave);
		bPanel.add(bdelete);
		
		
		mframe.add(ArticlesPane, BorderLayout.CENTER);
		mframe.add(titlePanel, BorderLayout.NORTH);
		mframe.add(bPanel, BorderLayout.SOUTH);
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
		lframe.setBounds(100,100, 250,150);
		
		JPanel lpanel = new JPanel();
		lpanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JButton blogin = new JButton("Login");
		blogin.addActionListener( new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	doLogin(luser.getText(), String.valueOf(lpass.getPassword()), lrem.isSelected());
		    }
		});

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		c.insets = new Insets(0,10,0,0);  //top padding
		JLabel login_label = new JLabel("Login");
		lpanel.add(login_label, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.5;
		c.gridwidth = 2;
		c.insets = new Insets(0,0,0,10);  //top padding
		luser = new JTextField();
			luser.setText(blogclient.bUser.getUserLogin());
		lpanel.add(luser, c);

		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.5;
		c.insets = new Insets(0,10,0,0);
		JLabel password_label = new JLabel("Password");
		lpanel.add(password_label, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 0.5;
		c.gridwidth = 2;
		c.insets = new Insets(0,0,0,10);
		lpass = new JPasswordField();
			lpass.setText(blogclient.bUser.getUserPassword());
		lpanel.add(lpass, c);
		
				
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		c.weightx = 0.5;
		c.gridwidth = 2;
		c.insets = new Insets(0,0,0,0);
		lrem = new JCheckBox("Remember me");
		System.out.println(blogclient.bDB.get("user","remember").equals("1"));
		lrem.setSelected(blogclient.bDB.get("user","remember").equals("1"));
		lrem.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {
	               blogclient.bDB.set("user","remember",e.getStateChange()==1?"1":"0");
	         }           
	      });
		lpanel.add(lrem, c);
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;
		c.weighty = 1.0;
		c.anchor = GridBagConstraints.PAGE_END;
		c.insets = new Insets(10,10,10,10);
		c.gridx = 1;
		c.gridwidth = 1;
		c.gridy = 3;
		lpanel.add(blogin, c);
		
		lframe.getContentPane().add(lpanel);
		
		centreWindow(lframe);
		lframe.setVisible(true);
	}	
	
	public void doLogin(String login, String password, Boolean store_password) {
		if (!blogclient.bUser.login(login, password)) {
			showError("Unable to login");
		} else {
			// TODO is `save password` checked, if not - set empty strings to DB
			if (store_password) {
				blogclient.bDB.storeUserPassword(login,password);
			} else {
				blogclient.bDB.storeUserPassword("","");
			}
			luser.setText("");	// clear login field for security reason
	    	lpass.setText("");	// clear password field
		}
		// clear raw login/password variables ASAP
		login=null;
		password=null;
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
		blogclient.bDB.disconnect();
		System.exit(code);
	}
}
