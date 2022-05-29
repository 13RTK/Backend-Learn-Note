import java.util.*;

public class TreeSetTest {
    public static void main(String[] args) {
        SortedSet<Item> parts = new TreeSet<>();
        parts.add(new Item("Toaster", 1234));
        parts.add(new Item("Widget", 4562));
        parts.add(new Item("Modem", 9912));
        System.out.println(parts);

        NavigableSet<Item> sortedByDescription = new TreeSet<>(Comparator.comparing(Item::getDescription));
        sortedByDescription.addAll(parts);
        System.out.println(sortedByDescription);
    }    
}
