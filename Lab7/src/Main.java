
public class Main {

    public static void main(String[] args)
    {
        LinkParser linkParser = new LinkParser("https://www.google.ru/");
        try {
            linkParser.Parse();
        }
        catch (Exception e)
        {
            System.out.println("Error");
            e.printStackTrace();
        }
    }
}
