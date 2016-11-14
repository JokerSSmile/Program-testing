import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

class LinkParser {

    private final int TIMEOUT = 10000;

    private String startLink;
    private String domain;
    private Vector<String> unparsedUrls;
    private Map<String, Integer> parsedUrls;
    private Map<String, Integer> badUrls;

    LinkParser(String link)
    {
        startLink = link;
        unparsedUrls = new Vector<>();
        parsedUrls = new HashMap<>();
        badUrls = new HashMap<>();
    }

    void Parse() throws Exception
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
            //System.out.println(1);
            throw new Exception("Invalid URL " + startLink + "\n" + "Enter URL like \"http://www.google.ru\"");
        }

        unparsedUrls.add(startLink);
        //domain = startURL.getHost().contains("www.") ? startURL.getHost() : "www." + startURL.getHost();
        domain = startURL.getHost();

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
                //System.out.println(2);
                badUrls.put(startLink, 404);
                continue;
            }

            //System.out.println("Processing URL: " + currentUrl.toString());
            parsedUrls.put(currentUrl.toString(), GetResponseCode(currentUrl));
            unparsedUrls.remove(unparsedUrls.get(0));
            //System.out.println(parsedUrls.size() + " " + unparsedUrls.size());

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
            //System.out.println(3);
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
            url = url.substring(2);
        }
        if (url.startsWith("/") || url.startsWith("?"))
        {
            return "http://" + domain + url;
        }
        if (!url.startsWith("http") && !url.startsWith("www."))
        {
            url = domain + "/" + url;
        }
        if (!url.startsWith("http"))
        {
            url = "http://" + url;
        }
        //System.out.println(url);
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

            if (!urlDomain.equals(domain))
            {
                return false;
            }
        }
        catch (Exception e)
        {
            //System.out.println(4);
            badUrls.put(givenUrl, GetResponseCode(url));
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
            //System.out.println(5);
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
            //System.out.println(6);
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
