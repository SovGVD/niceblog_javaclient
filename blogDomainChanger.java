import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class blogDomainChanger implements ActionListener {
    private String domain;

    public blogDomainChanger(String domain) {
        this.domain = domain;
    }

    public void actionPerformed(ActionEvent e) {
    	blogclient.bArticles.list(blogclient.bAPI.API_articles(domain));
    	System.out.println(domain);
    	blogclient.bDomains.setCurrent(domain);
    }
}