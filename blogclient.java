import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class blogclient {
	public static final blogCipher bCipher = new blogCipher();
	public static final blogDB bDB = new blogDB();
	public static final blogAPI bAPI = new blogAPI();
	public static final user bUser = new user();
	public static final blogGUI bGUI = new blogGUI();
	public static final blogDomains bDomains = new blogDomains();
	public static final blogArticles bArticles = new blogArticles();
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		bGUI.initGUI();
		System.out.println("Done");
	}
	
}
