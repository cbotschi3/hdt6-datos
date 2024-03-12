import java.util.HashMap;
import java.util.Map;

/**
 * Implementación de la interfaz MapFactory para crear instancias de HashMap.
 */

public class HashMapFactory implements MapFactory<String, String> {
    
    @Override
    public Map<String, String> createMap() {
        return new HashMap<>();
    }
}