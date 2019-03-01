package growfast;

import lombok.Data;

@Data
public class Sensor
{
    public enum SensorType
    {
        TEMPERATURE,
        PRESSURE
    }

    private Long id;
    private String name;
    private SensorType type;

    Sensor(SensorType type, String name)
    {
        this.type = type;
        this.name = name;
    }
}
