import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class main {
    private static Map<String, String> cardMap;

    /**
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Seleccionar la implementación de Map en tiempo de ejecución
        System.out.println("Seleccione la implementación de Map que desea utilizar:");
        System.out.println("1. HashMap");
        System.out.println("2. TreeMap");
        System.out.println("3. LinkedHashMap");

        int choice = 0;
        try {
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Error: Por favor ingrese un número válido.");
            System.exit(1);
        }

        MapFactory<String, String> mapFactory;

        switch (choice) {
            case 1:
                mapFactory = new HashMapFactory();
                break;
            case 2:
                mapFactory = new TreeMapFactory();
                break;
            case 3:
                mapFactory = new LinkedHashMapFactory();
                break;
            default:
                System.out.println("Opción no válida. Se utilizará HashMap por defecto.");
                mapFactory = new HashMapFactory();
        }

        cardMap = mapFactory.createMap();

        // Leer el archivo de cartas
        readCardFile("cards_desc.txt");

        int option;
        do {
            System.out.println("\nSeleccione una opción:");
            System.out.println("1. Agregar una carta a la colección del usuario.");
            System.out.println("2. Mostrar el tipo de una carta específica.");
            System.out.println("3. Mostrar el nombre, tipo y cantidad de cada carta en la colección.");
            System.out.println("4. Mostrar el nombre, tipo y cantidad de cada carta en la colección, ordenadas por tipo.");
            System.out.println("5. Mostrar el nombre y tipo de todas las cartas existentes.");
            System.out.println("6. Mostrar el nombre y tipo de todas las cartas existentes, ordenadas por tipo.");
            System.out.println("0. Salir.");
            try {
                option = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: Por favor ingrese un número válido.");
                option = -1;
            }
            scanner.nextLine(); // Limpiar el buffer del scanner

            switch (option) {
                case 1:
                    addCardToCollection(scanner);
                    break;
                case 2:
                    showTypeOfCard(scanner);
                    break;
                case 3:
                    showUserCollection();
                    break;
                case 4:
                    showUserCollectionSortedByType();
                    break;
                case 5:
                    showAllCards();
                    break;
                case 6:
                    showAllCardsSortedByType();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (option != 0);
    }

    private static void readCardFile(String filename) {
        try (Scanner scanner = new Scanner(new FileReader(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    String cardName = parts[0].trim();
                    String cardType = parts[1].trim();
                    cardMap.put(cardName, cardType);
                }
            }
            System.out.println("Cartas cargadas exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de cartas: " + e.getMessage());
        }
    }

    private static void addCardToCollection(Scanner scanner) {
        System.out.println("Ingrese el nombre de la carta que desea agregar:");
        String cardName = scanner.nextLine();
        String cardType = cardMap.get(cardName);
        if (cardType != null) {
            System.out.println("Carta agregada a la colección exitosamente.");
        } else {
            System.out.println("La carta ingresada no se encuentra disponible.");
        }
    }

    private static void showTypeOfCard(Scanner scanner) {
        System.out.println("Ingrese el nombre de la carta:");
        String cardName = scanner.nextLine();
        String cardType = cardMap.get(cardName);
        if (cardType != null) {
            System.out.println("Tipo de carta: " + cardType);
        } else {
            System.out.println("La carta ingresada no se encuentra disponible.");
        }
    }

    private static void showUserCollection() {
        Map<String, Integer> userCollection = new HashMap<>();
        for (Map.Entry<String, String> entry : cardMap.entrySet()) {
            String cardName = entry.getKey();
            userCollection.put(cardName, userCollection.getOrDefault(cardName, 0) + 1);
        }
        for (Map.Entry<String, Integer> entry : userCollection.entrySet()) {
            System.out.println("Nombre: " + entry.getKey() + ", Tipo: " + cardMap.get(entry.getKey()) + ", Cantidad: " + entry.getValue());
        }
    }

    private static void showUserCollectionSortedByType() {
        Map<String, List<String>> cardsByType = new TreeMap<>();
        for (Map.Entry<String, String> entry : cardMap.entrySet()) {
            String cardType = entry.getValue();
            String cardName = entry.getKey();
            cardsByType.putIfAbsent(cardType, new ArrayList<>());
            cardsByType.get(cardType).add(cardName);
        }

        for (Map.Entry<String, List<String>> entry : cardsByType.entrySet()) {
            String cardType = entry.getKey();
            List<String> cards = entry.getValue();
            System.out.println("Tipo: " + cardType);
            for (String card : cards) {
                System.out.println("  - " + card);
            }
        }
    }

    private static void showAllCards() {
        for (Map.Entry<String, String> entry : cardMap.entrySet()) {
            System.out.println("Nombre: " + entry.getKey() + ", Tipo: " + entry.getValue());
        }
    }

    private static void showAllCardsSortedByType() {
        Map<String, List<String>> cardsByType = new TreeMap<>();
        for (Map.Entry<String, String> entry : cardMap.entrySet()) {
            String cardType = entry.getValue();
            String cardName = entry.getKey();
            cardsByType.putIfAbsent(cardType, new ArrayList<>());
            cardsByType.get(cardType).add(cardName);
        }

        for (Map.Entry<String, List<String>> entry : cardsByType.entrySet()) {
            String cardType = entry.getKey();
            List<String> cards = entry.getValue();
            System.out.println("Tipo: " + cardType);
            for (String card : cards) {
                System.out.println("  - " + card);
            }
        }
    }
}

interface MapFactory<K, V> {
    Map<K, V> createMap();
}

class HashMapFactory implements MapFactory<String, String> {

    @Override
    public Map<String, String> createMap() {
        return new HashMap<>();
    }
}

class TreeMapFactory implements MapFactory<String, String> {

    @Override
    public Map<String, String> createMap() {
        return new TreeMap<>();
    }
}

class LinkedHashMapFactory implements MapFactory<String, String> {

    @Override
    public Map<String, String> createMap() {
        return new LinkedHashMap<>();
    }
}


