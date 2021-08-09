
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "events")
public class Event {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "duration")
    private int duration;

    @Column(name = "type")
    private String type;

    @Column(name = "host")
    private String host;

    @Column(name = "alert")
    private Boolean alert;

    public Event() {

    }


    public Event(String id, int duration, Boolean alert) {
        this.id = id;
        this.duration = duration;
        this.type = null;
        this.host = null;
        this.alert = alert;
    }

    public Event(String id, int duration, String type, String host, Boolean alert) {
        this.id = id;
        this.duration = duration;
        this.type = type;
        this.host = host;
        this.alert = alert;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Boolean getAlert() {
        return alert;
    }

    public void setAlert(Boolean alert) {
        this.alert = alert;
    }


    @Override
    public String toString() {
        return "Event [id=" + id + ", duration=" + duration + ", type=" + type + ", host=" + host + ", alert=" + alert + "]";
    }
}