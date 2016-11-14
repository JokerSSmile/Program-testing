public class Main {

    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("Usage must be CheckLinks.jar <URL>");
            return;
        }

        LinkParser linkParser = new LinkParser(args[0]);
        //LinkParser linkParser = new LinkParser("")
        try {
            linkParser.Parse();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
