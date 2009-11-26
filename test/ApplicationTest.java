import controllers.Application;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import org.junit.*;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import models.*;

import java.text.ParseException;
import java.util.Date;

public class ApplicationTest extends FunctionalTest {

    @Test
    public void testThatIndexPageWorks() {
        Response response = GET("/");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset("utf-8", response);
    }

    @Test
    public void testParsingLine() throws Exception {
        Message message = Application.extractMessage("2001-09-11 10:00:29 Metrocall [1457006] D  ALPHA  I NEED TO KNOW YOUR AVAILABILITY ASAP  WE KNOW NO DETAILS YET CALL ME AT 881-1158  THE WHOLE TEAM NEEDS TO RESPOND WILL HAVE MORE DETAILS LA");
        //assertThat(message.timestamp, is(equalTo(new Date(0))));
        assertThat(message.network, is(equalTo("Metrocall")));
        assertThat(message.messageId, is(equalTo("1457006")));
        assertThat(message.code, is(equalTo("D")));
        assertThat(message.type, is(equalTo("ALPHA")));
        assertThat(message.body, is(equalTo("I NEED TO KNOW YOUR AVAILABILITY ASAP  WE KNOW NO DETAILS YET CALL ME AT 881-1158  THE WHOLE TEAM NEEDS TO RESPOND WILL HAVE MORE DETAILS LA")));
                
    }

    @Test
    public void testParsingWeirdSyntaxLine() throws Exception {
        Message message = Application.extractMessage("2001-09-11 22:40:32 Metrocall [1305447] 17:41:49 PM B  ALPHA  Please call Robert Dereal with the LA Times ----- another number 510-708-3223    [W/C] (1/1)");
        //assertThat(message.timestamp, is(equalTo(new Date(0))));
        assertThat(message.network, is(equalTo("Metrocall")));
        assertThat(message.messageId, is(equalTo("1305447")));
        assertThat(message.code, is(equalTo("B")));
        assertThat(message.type, is(equalTo("ALPHA")));
        assertThat(message.body, is(equalTo("Please call Robert Dereal with the LA Times ----- another number 510-708-3223    [W/C] (1/1)")));
    }
    
}
