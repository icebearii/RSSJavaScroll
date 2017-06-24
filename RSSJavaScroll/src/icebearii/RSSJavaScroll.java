package icebearii;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.*;


public class RSSJavaScroll {

    private void display() {
        JFrame f = new JFrame("MarqueeTest");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // String s = "Everything you want is out there waiting for you to ask. Everything you want also wants you. But you have to take action to get it.";
       String s;
        s = readRSSFeed("http://rss.cnn.com/rss/edition.rss");
       MarqueePanel mp = new MarqueePanel(s, 120);
        f.add(mp);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        mp.start();
    }

    public static void main(String[] args) {
        
      System.out.println(readRSSFeed("http://rss.cnn.com/rss/edition.rss"));          

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new RSSJavaScroll().display();
            }
        });
        
    }

  public static String readRSSFeed(String urlAddress){
        try{
            URL rssUrl = new URL (urlAddress);
            BufferedReader in = new BufferedReader(new InputStreamReader(rssUrl.openStream()));
            String sourceCode = "";
            String line;
            while ((line = in.readLine()) != null) {
    int titleEndIndex = 0;
    int titleStartIndex = 0;
    while (titleStartIndex >= 0) {
        titleStartIndex = line.indexOf("<title>", titleEndIndex);
        if (titleStartIndex >= 0) {
            titleEndIndex = line.indexOf("</title>", titleStartIndex);
            sourceCode += line.substring(titleStartIndex + "<title>".length(), titleEndIndex) + "\n";
        }
    }
}
            in.close();
            return sourceCode;
        } catch (MalformedURLException ue){
            System.out.println("Malformed URL");
        } catch (IOException ioe){
            System.out.println("Something went wrong reading the contents");
        }
        return null;
    }}


/** Side-scroll n characters of s. */
class MarqueePanel extends JPanel implements ActionListener {

    private static final int RATE = 12;
    private final Timer timer = new Timer(1000 / RATE, this);
    private final JLabel label = new JLabel();
    private final String s;
    private final int n;
    private int index;

    public MarqueePanel(String s, int n) {
        if (s == null || n < 1) {
            throw new IllegalArgumentException("Null string or n < 1");
        }
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append(' ');
        }
        this.s = sb + s + sb;
        this.n = n;
        label.setFont(new Font("Serif", Font.ITALIC, 36));
        label.setText(sb.toString());
        this.add(label);
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        index++;
        if (index > s.length() - n) {
            index = 0;
        }
        label.setText(s.substring(index, index + n));
    }
    
}

