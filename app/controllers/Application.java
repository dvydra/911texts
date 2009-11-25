package controllers;

import models.Message;
import play.mvc.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
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

    public static void importFile() throws IOException {
        List<Message> messages = new ArrayList<Message>();
        String filename = params.get("file");
        URL url = new URL(filename);
        URLConnection yc = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            messages.add(extractMessage(inputLine));
        }
        in.close();
    }

    public static Message extractMessage(String inputLine) {
        Message message = null;
        Pattern pattern = Pattern.compile("<%s>.*By ([\\w\\s']+)\\<br\\>\\s+(\\d.*)</%s>");
        Matcher matcher = pattern.matcher(inputLine);
        if (matcher.find()) {
            message = new Message(formatAsDate(matcher.group(1)),matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5),matcher.group(6));
        }

        return message;

    }

    private static Date formatAsDate(String s) {
        return new Date();
    }

}
