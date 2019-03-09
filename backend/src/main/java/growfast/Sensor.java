package growfast;

import lombok.Data;

@Data
public class Sensor
{
    public enum SensorType
    {
        UNKNOWN,
        TEMPERATURE,
        PRESSURE
    }

    private String name;
    private SensorType type;

    Sensor()
    {
        this(SensorType.UNKNOWN, "");
    }

    Sensor(SensorType type, String name)
    {
        this.type = type;
        this.name = name;
    }
}
