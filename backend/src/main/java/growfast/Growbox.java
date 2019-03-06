package growfast;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
public class Growbox
{
    @Id
    private String id;
    private String name;
    private List<Sensor> sensors;

    public Growbox()
    {
        this("");
    }

    public Growbox(String name)
    {
        this.name = name;
        this.sensors = new ArrayList<>();
    }
}
