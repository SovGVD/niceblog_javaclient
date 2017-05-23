import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
//import java.util.Set;

public class blogDB {
	private Connection conn = null;
	
	public blogDB () {
		this.connect();
		this.init();
	}
	
	// http://stackoverflow.com/questions/1128723/how-can-i-test-if-an-array-contains-a-certain-value
	private static boolean in_array(String[] arr, String targetValue) {
	    return Arrays.asList(arr).contains(targetValue);
	}

	private String[] allowed_tables = new String[] {"user"};

	// key-value in SQLite
	public String get(String t, String k) {
		if (!in_array(allowed_tables,t)) {
			//System.out.println("Wrong table");
			return "";
		}
		try {
			String sql = "SELECT v FROM "+t+" WHERE k=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
		    stmt.setString(1, k);
		    ResultSet rs = stmt.executeQuery();
		    String r =rs.getString("v"); 
		    stmt.close();
            return r;
        } catch (SQLException e) {
        	e.printStackTrace();
            //System.out.println("GET ERROR ".concat(e.getMessage()));
            return "";
        }
	}
	
	public boolean storeUserPassword (String user, String password) {
		String _user = blogclient.bCipher.encrypt(user);
		String _password = blogclient.bCipher.encrypt(password);
		return this.set("user", "password", _password)&&this.set("user", "login", _user);
	}
	
	public boolean set (String t, String k, String v) {
		// TODO set data even if there is no key in database
		try {
			String sql = "UPDATE "+t+" SET v=? WHERE k=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
		    stmt.setString(1, v);
		    stmt.setString(2, k);
			stmt.executeUpdate();
			stmt.close();
            return true;
        } catch (SQLException e) {
        	e.printStackTrace();
            System.out.println("SET ERROR ".concat(e.getMessage()));
            return false;
        }
	}
	
	public void disconnect() {
        try {
        	conn.close();
        } catch (SQLException ex) {
            System.out.println("disconnect "+ex.getMessage());
        }		
	}
	
	public void connect() {
		conn = null;
        try {
            String url = "jdbc:sqlite:blog.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println("SQL ex:"+e.getMessage());
        } 
    }
	
	private boolean r(String sql) {
		try (Statement stmt = this.conn.createStatement()) {
            stmt.execute(sql);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
	}
	
	private boolean init() {
		//Boolean init_required = false;
		String sql = "SELECT v FROM user WHERE k='version'";	// TODO right now we are care only about is table exists of not
        //init_required = true;
		if (!this.r(sql)) {
			sql= "CREATE TABLE user ( k CHAR(50), v TEXT );";
			this.r(sql);
			sql= "CREATE UNIQUE INDEX kindex on user (k);";
			this.r(sql);
			sql= "INSERT INTO user (k,v) VALUES ('version','1'), ('login',''), ('password',''), ('remember','1');";
			return this.r(sql);
		}
		return true;
	}
}