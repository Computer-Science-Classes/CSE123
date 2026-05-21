import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class Testing {

    @Test
    @DisplayName("STUDENT TEST - Case #1")
    public void firstTestCase() {
        List<Region> sites = new ArrayList<>();
        sites.add(new Region("A", 50, 100.0));
        sites.add(new Region("B", 75, 200.0));
        sites.add(new Region("C", 25, 50.0));

        Allocation result = Client.allocateRelief(350.0, sites);

        assertEquals(150, result.totalPeople());
        assertEquals(350.0, result.totalCost());
    }

    @Test
    @DisplayName("STUDENT TEST - Case #2")
    public void secondTestCase() {
        List<Region> sites = new ArrayList<>();
        sites.add(new Region("A", 50, 100.0));
        sites.add(new Region("B", 75, 200.0));
        sites.add(new Region("C", 25, 50.0));

        Allocation result = Client.allocateRelief(40.0, sites);

        assertEquals(0, result.totalPeople());
        assertEquals(0.0, result.totalCost());
    }

    @Test
    @DisplayName("STUDENT TEST - Case #3")
    public void thirdTestCase() {
        List<Region> sites = new ArrayList<>();
        sites.add(new Region("A", 50, 500.0));
        sites.add(new Region("B", 100, 700.0));
        sites.add(new Region("C", 60, 1000.0));
        sites.add(new Region("D", 20, 1000.0));
        sites.add(new Region("E", 200, 900.0));

        Allocation result = Client.allocateRelief(1600.0, sites);

        assertEquals(300, result.totalPeople());
        assertEquals(1600.0, result.totalCost());
    }
}
