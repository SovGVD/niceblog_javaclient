/*import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Calendar;
import java.util.Date;*/


public class blogclient {
	public static final blogAPI bAPI = new blogAPI();
	public static final user bUser = new user();
	public static final blogGUI bGUI = new blogGUI();
	public static final blogDomains bDomains = new blogDomains();
	public static final blogArticles bArticles = new blogArticles();
	
	public static void main(String[] args) {
		bGUI.initGUI();
		System.out.println("Done");
	}
}
