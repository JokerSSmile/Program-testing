import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class LinkParser {

    private final int TIMEOUT = 10000;

    private String startLink;
    private String domain;
    private Vector<String> unparsedUrls;
    private Map<String, Integer> parsedUrls;
    private Map<String, Integer> badUrls;

    public LinkParser(String link)
    {
        startLink = link;
        unparsedUrls = new Vector<>();
        parsedUrls = new HashMap<>();
        badUrls = new HashMap<>();
    }

    public void Parse() throws Exception
    {
        startLink = startLink.startsWith("http") ? startLink : "http://" + startLink;

        URL startURL;
        try {
            startURL = new URL(startLink);
            HttpURLConnection connection = (HttpURLConnection)startURL.openConnection();
            connection.setRequestMethod ("GET");
            connection.connect ();
        }
        catch (Exception e)
        {
            badUrls.put(startLink, 404);
            throw new Exception("Invalid URL " + startLink);
        }

        unparsedUrls.add(startLink);
        domain = startURL.getHost().contains("www.") ? startURL.getHost() : "www." + startURL.getHost();

        Parsing();

        OutputToFiles();
    }

    private void Parsing() throws Exception
    {
        while (!unparsedUrls.isEmpty()) {

            String tempUrl = unparsedUrls.get(0);
            if (!tempUrl.startsWith("http"))
            {
                tempUrl = "http://" + tempUrl;
            }

            URL currentUrl;
            try {
                currentUrl = new URL(tempUrl);
            }
            catch (Exception e)
            {
                badUrls.put(startLink, 404);
                continue;
            }

            parsedUrls.put(currentUrl.toString(), GetResponseCode(currentUrl));
            unparsedUrls.remove(unparsedUrls.get(0));

            GetUrls(currentUrl);
        }
    }

    private void GetUrls(URL url) throws Exception
    {
        if (!IsCorrectUrl(url))
        {
            return;
        }

        Document page;
        try {
            page = Jsoup.connect(url.toString()).timeout(TIMEOUT).ignoreContentType(true).get();
        }
        catch (Exception e)
        {
            badUrls.put(url.toString(), GetResponseCode(url));
            return;
        }

        final String regex = "a[href]";
        Elements linksInUrl = page.select(regex);

        for (Element link : linksInUrl) {
            String href = link.attr("href");
            href = MakeAbsoluteUrl(href);

            if (!parsedUrls.containsKey(href) && !unparsedUrls.contains(href) && IsSameDomain(href))
            {
                unparsedUrls.add(href);
            }
        }
    }

    private String MakeAbsoluteUrl(String url)
    {
        if (url.startsWith("//"))
        {
            return "http://www." + url;
        }
        if (url.startsWith("/") || url.startsWith("?"))
        {
            return "http://" + domain + url;
        }
        if (!url.startsWith("http"))
        {
            url = "http://" + url;
        }
        return url;
    }

    private boolean IsSameDomain(String givenUrl)
    {
        String tempUrl = givenUrl;
        if (!tempUrl.startsWith("http"))
        {
            tempUrl = "http://" + tempUrl;
        }
        URL url = null;
        try {
            url = new URL(tempUrl);
            String urlDomain = url.getHost();

            if (!urlDomain.contains("www."))
            {
                urlDomain = "www." + urlDomain;
            }
            if (!urlDomain.equals(domain))
            {
                return false;
            }
        }
        catch (Exception e)
        {
            badUrls.put(url.toString(), GetResponseCode(url));
            return false;
        }
        return true;
    }

    private boolean IsCorrectUrl(URL url)
    {
        try {
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod ("GET");
            connection.connect();
        }
        catch (Exception e) {
            badUrls.put(url.toString(), GetResponseCode(url));
            return false;
        }
        return true;
    }

    private int GetResponseCode(URL url)
    {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            return connection.getResponseCode();
        }
        catch (Exception e)
        {
            return 404;
        }
    }

    private void OutputToFiles() throws Exception
    {
        PrintWriter parsedWriter = new PrintWriter("parsedUrls.txt", "UTF-8");
        for (Map.Entry url: parsedUrls.entrySet())
        {
            parsedWriter.println(url.getKey() + "\t" + url.getValue());
        }
        parsedWriter.close();

        PrintWriter badWriter = new PrintWriter("badUrls.txt", "UTF-8");
        for (Map.Entry url: badUrls.entrySet())
        {
            badWriter.println(url.getKey() + "\t" + url.getValue());
        }
        badWriter.close();
    }

}
