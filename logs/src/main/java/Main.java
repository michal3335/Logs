import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import java.util.*;


public class Main {


    private static HashMap<String, JsonObject> eventStarts = new HashMap<String, JsonObject>();
    private static HashMap<String, JsonObject> eventFinishes = new HashMap<String, JsonObject>();
    private static List<JsonObject> allEvents = new ArrayList<JsonObject>();

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {


        Scanner scan = new Scanner(System.in);
        System.out.println("Enter l to generate random logs, r to convert logs or s to show database: ");
        String decision = scan.nextLine();
        if(decision.equals("r")){
            System.out.println("Enter path to file: ");
            String path = scan.nextLine();
            System.out.println("Reading file... ");
            readLogs(path);
        }else if(decision.equals("s")){
            showLogs();
        }else{
            System.out.println("How many logs do you want to generate?");
            int l = Integer.parseInt(scan.nextLine());
            LogGenerator.logg(l);
            System.out.println("Logs saved to file log.txt");
        }
    }

    public static void readLogs(String path){
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                JsonObject jsonObject = new JsonParser().parse(data).getAsJsonObject();
                if (jsonObject.get("state").getAsString().equals("STARTED")) {
                    eventStarts.put(jsonObject.get("id").getAsString(), jsonObject);
                } else
                    eventFinishes.put(jsonObject.get("id").getAsString(), jsonObject);


            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        for (HashMap.Entry<String, JsonObject> set :
                eventStarts.entrySet()) {

            JsonObject startObject = set.getValue();

            try {
                JsonObject finishObject = eventFinishes.get(set.getKey());
                Long startTime = startObject.get("timestamp").getAsLong();
                Long finishTime = finishObject.get("timestamp").getAsLong();
                int duration = (int) (finishTime - startTime);
                if (duration > 4) {
                    startObject.remove("timestamp");
                    startObject.addProperty("duration", duration);
                    startObject.addProperty("alert",true);
                    allEvents.add(startObject);
                }else{
                    startObject.remove("timestamp");
                    startObject.addProperty("duration", duration);
                    startObject.addProperty("alert",false);
                    allEvents.add(startObject);
                }

            }catch (Exception e){
                System.out.println(e);
            }

        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try{

            transaction = session.beginTransaction();

            for(JsonObject j : allEvents) {

                Gson gson= new Gson();
                Event obj = gson.fromJson(j.toString(),Event.class);
                session.save(obj);

            }
            transaction.commit();


        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Error logs not saved");
            }
            e.printStackTrace();
        }finally {
            session.close();
            System.out.println("Logs saved to database.");
        }

    }

    public static void showLogs(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            List < Event > events = session.createQuery("from Event ", Event.class).list();
            events.forEach(e -> System.out.println(e.toString()));
            session.close();
        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}
