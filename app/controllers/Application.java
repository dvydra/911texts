package controllers;

import models.Message;
import play.mvc.*;
import siena.PersistenceManager;
import siena.Query;
import siena.gae.GaePersistenceManager;
import siena.jdbc.JdbcPersistenceManager;
import utils.PlayConnectionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    public static void importFile(String file) throws IOException, ParseException {
        URL url = new URL(file);
        URLConnection yc = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            Message message = extractMessage(inputLine);
            message.insert();
        }
        in.close();
    }

    public static Message extractMessage(String inputLine) throws ParseException {
        Message message = null;
        Pattern pattern = Pattern.compile("(\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d)\\s+(\\w+)\\s+\\[(\\d+)\\]\\s+([A-Z])\\s+(\\w+)\\s+(.*)");
        Matcher matcher = pattern.matcher(inputLine);
        if (matcher.find()) {
            message = new Message(formatAsDate(matcher.group(1)),matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5),matcher.group(6));
        }

        return message;

    }

    private static Date formatAsDate(String s) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        Date date = dateFormat.parse(s);
        return date;
    }

}
