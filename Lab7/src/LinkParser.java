import com.sun.corba.se.spi.orbutil.fsm.Input;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sun.reflect.annotation.ExceptionProxy;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class LinkParser {

    private final int TIMEOUT = 10000;

    private String startLink;
    private String domain;
    private Set<URL> unparsedUrls;
    private Map<String, Integer> parsedUrls;
    private Map<String, Integer> badUrls;

    public LinkParser(String link)
    {
        startLink = link;
        unparsedUrls = new HashSet<>();
        parsedUrls = new HashMap<>();
        badUrls = new HashMap<>();
    }

    public void Parse() throws Exception
    {

        URL startURL = new URL(startLink);
        unparsedUrls.add(startURL);
        domain = startURL.getAuthority().substring(4);
        //System.out.println(domain);
        //System.out.println("----------");


        while (!unparsedUrls.isEmpty()) {

            URL currentUrl = unparsedUrls.iterator().next();
            GetUrls(currentUrl);
            unparsedUrls.remove(currentUrl);

        }




        for (Map.Entry entry : parsedUrls.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    }

    private void GetUrls(URL url) throws Exception
    {
        if (!IsCorrectUrl(url))
        {
            System.out.println("ERROR INVALID URL");
            throw new Exception("Invalid url " +  url.toString());
        }
        Document page = Jsoup.connect(url.toString()).timeout(TIMEOUT).ignoreContentType(true).get();
        String regex = "a[href*=" + domain + "]";
        Elements linksInUrl = page.select(regex);

        for (Element curUrl : linksInUrl)
        {
            try {
                System.out.println(curUrl.toString());
                //System.out.println("123");
                //System.out.println(curUrl.getElementsByAttribute("href").attr("href").split("://")[1].split("www.")[1]);
                String href = curUrl.getElementsByAttribute("href").attr("href").split("//")[1].split("www.")[1];
                String hrefWithProtocol = "https://www." + href;

                //System.out.println(href);
                if (!parsedUrls.containsKey(hrefWithProtocol) && href.indexOf(domain) == 0 && !href.isEmpty()) {
                    //System.out.println(hrefWithProtocol);
                    unparsedUrls.add(new URL(hrefWithProtocol));
                }


                parsedUrls.put(hrefWithProtocol, GetResponseCode(url));
                //System.out.println(href);
            }
            catch (Exception e) {
                System.out.println(curUrl + "-----------");

                badUrls.put(curUrl.toString(), GetResponseCode(new URL(curUrl.getElementsByAttribute("href").attr("href"))));
            }

            //System.out.println(parsedUrls.size() + " " + unparsedUrls.size() + " " + badUrls.size() + " " + curUrl.toString());
        }


    }

    private boolean IsCorrectUrl(URL url)
    {
        try {
            URLConnection conn = url.openConnection();
            conn.connect();
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private int GetResponseCode(URL url) throws Exception
    {
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        return connection.getResponseCode();
    }
}
