import java.util.Map;
import java.util.TreeMap;

/**
 * Implementación de la interfaz MapFactory para crear instancias de TreeMap.
 */

public class treemap implements MapFactory<String, String> {
    @Override
    public Map<String, String> createMap() {
        return new TreeMap<>();
    }
}