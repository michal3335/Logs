import com.google.gson.JsonObject;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ThreadLocalRandom;

public class LogGenerator {

    public static void logg(int l) throws FileNotFoundException, UnsupportedEncodingException {

        PrintWriter writer = new PrintWriter("log.txt", "UTF-8");


        for (int i = 0; i < l; i++) {

            JsonObject logSt = new JsonObject();
            JsonObject logFd = new JsonObject();

            int logType = ThreadLocalRandom.current().nextInt(1, 5);
            String generatedString = RandomStringUtils.random(10, true, false);
            Long generatedNumber = Long.parseLong(RandomStringUtils.random(13, false, true));
            String generatedNumber2 = RandomStringUtils.random(5, false, true);
            Long duration = ThreadLocalRandom.current().nextInt(1, 8) + generatedNumber;


            if (logType > 2) {

                logSt.addProperty("id", generatedString);
                logSt.addProperty("state", "STARTED");
                logSt.addProperty("timestamp", generatedNumber);

                logFd.addProperty("id", generatedString);
                logFd.addProperty("state", "FINISHED");
                logFd.addProperty("timestamp", duration);

                writer.println(logSt.toString());
                writer.println(logFd.toString());
            } else {
                logSt.addProperty("id", generatedString);
                logSt.addProperty("state", "STARTED");
                logSt.addProperty("type", "APPLICATION_LOG");
                logSt.addProperty("host", generatedNumber2);
                logSt.addProperty("timestamp", generatedNumber);

                logFd.addProperty("id", generatedString);
                logFd.addProperty("state", "FINISHED");
                logFd.addProperty("type", "APPLICATION_LOG");
                logFd.addProperty("host", generatedNumber2);
                logFd.addProperty("timestamp", duration);

                writer.println(logSt.toString());
                writer.println(logFd.toString());

            }


        }
        writer.close();
    }
}

