package controllers;

import models.Message;
import play.mvc.*;
import siena.PersistenceManager;
import siena.Query;
import siena.gae.GaePersistenceManager;
import siena.jdbc.JdbcPersistenceManager;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application extends Controller {

    public static void index() {
        render();
    }

    public static void uploadFile() {
        render();
    }

    public static void listAll() {
        List<Message> things = Message.all().fetch();
        render(things);
    }

    public static void importFile(File messagefile) throws IOException, ParseException {
        BufferedReader in = new BufferedReader(new FileReader(messagefile));
        String inputLine;   
        List<String> failed = new ArrayList<String>();
        int i = 0;
        int failCount = 0;
        while ((inputLine = in.readLine()) != null) {
            if (i % 1000 == 0) {
                System.out.println(String.format("processed %d messages - %d failed", i, failCount));
            }

            Message message = extractMessage(inputLine);
            if (message != null) {
                message.insert();
            } else {
                failed.add(("FAILED TO PARSE LINE>"+ inputLine +""));
                failCount++;
            }
            i++;
        }
        in.close();
        for (String l : failed) {
            System.out.println(l);    
        }
    }

    public static Message extractMessage(String inputLine) throws ParseException {
        Message message = null;
        String regex =  "(\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d)\\s+" +      //date
                        "(\\w+)\\s+" +                                                 //network
                        "[\\[{]([\\d\\?]+)[\\]}]( \\d:\\d\\d:\\d\\d [AP]M)?\\s+" +                                //messageId
                        "(\\w)\\s+" +                                                  //code
                        "([\\w\\/]+)\\s+" +                                            //type
                        "(.*)";                                                        //message
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputLine);
        if (matcher.find()) {
            message = new Message(formatAsDate(matcher.group(1)),matcher.group(2), matcher.group(3), matcher.group(5), matcher.group(6),matcher.group(7));
        }

        return message;

    }

    private static Date formatAsDate(String s) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        Date date = dateFormat.parse(s);
        return date;
    }

}
