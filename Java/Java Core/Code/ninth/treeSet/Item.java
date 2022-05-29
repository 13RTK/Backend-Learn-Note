import java.util.*;

public class Item implements Comparable<Item>{
    private String description;
    private int partNumber;

    public Item(String aDescription, int aPartNumber) {
        this.description = aDescription;
        this.partNumber = aPartNumber;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return "[description=" + this.description + ", partNumber=" + partNumber + "]";
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }

        if (otherObject == null) {
            return false;
        }

        if (this.getClass() != otherObject.getClass()) {
            return false;
        }

        Item other = (Item) otherObject;
        return Objects.equals(description, other.description) && partNumber == other.partNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.description, this.partNumber);
    }

    @Override
    public int compareTo(Item other) {
        int diff = Integer.compare(partNumber, other.partNumber);
        return diff != 0 ? diff : description.compareTo(other.description);
    }
}
