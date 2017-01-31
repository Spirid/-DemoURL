package demourl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class DemoURL
{
    public static void main(String[] args)
    {
        String spec;
        if (args.length > 0)
            spec = args[0];
        else
        {
            System.out.println("No URL entered.");
            return;
        }
        try
        {
            URL url = new URL(spec);
            InputStream is = url.openStream();
            String doc = "";
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int rd;
            while ((rd = is.read(buffer)) > -1)
            {
                doc += new String(buffer, 0, rd);
            }
            is.close();

            String[] lines = doc.split("<");
            for (int i=1; i<lines.length; i++)
            {
                lines[i] = lines[i].trim(); //comment this line if you want to leave empty lines in your text browser
                if (lines[i].startsWith("script"))
                    continue;
                int closeBraceIndex = lines[i].indexOf('>');
                if (closeBraceIndex != -1)
                    lines[i] = lines[i].substring(closeBraceIndex+1);
                if (!lines[i].isEmpty() && !lines[i].startsWith("!--") && !lines[i].endsWith("-->"))
                    System.out.println(lines[i]);
            }
        }
        catch (MalformedURLException ex)
        {
            System.out.println("Wrong URL entered!\n" + ex.getLocalizedMessage());
        }
        catch (IOException ex)
        {
            System.out.println(ex.getLocalizedMessage());
        }
    }
}
