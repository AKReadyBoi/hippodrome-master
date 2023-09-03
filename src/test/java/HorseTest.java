import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HorseTest {
    @Test
    public void testIAENull() {
       Exception exception = assertThrows(IllegalArgumentException.class, ()-> new Horse(null, 1, 1));
       assertEquals("Name cannot be null.", exception.getMessage());
    }

    @Test
    public void testIAENullAndSpace() {
       Exception exception = assertThrows(IllegalArgumentException.class, ()-> new Horse(stringChecker(" "), 1, 1));
       assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    public void testIAESpeed() {
        Exception exception = assertThrows(IllegalArgumentException.class, ()-> new Horse("j", -1, 1));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    public void testIAEDistance() {
        Exception exception = assertThrows(IllegalArgumentException.class, ()-> new Horse("j", 1, -1));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    public void testGetName() {
        Horse horse = new Horse("j", 4.6);
        assertEquals("j",horse.getName());
    }

    @Test
    public void testGetSpeed() {
        Horse horse = new Horse("j", 4.6);
        assertEquals(4.6, horse.getSpeed());
    }

    @Test
    public void testGetDistanceWithTwoParams() {
        Horse horse = new Horse("j", 4.6);
        assertEquals(0, horse.getDistance());
    }

    @Test
    public void testGetDistanceWithThreeParams() {
        Horse horse = new Horse("j", 4.6, 10);
        assertEquals(10, horse.getDistance());
    }


    @Test
    public void testInvocationRandomDouble() {
        try (MockedStatic<Horse> testSubject = Mockito.mockStatic(Horse.class)) {
            Horse.getRandomDouble(0.2, 0.9);
            testSubject.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @CsvSource({"1.0, 1.0", "2.0, 2.0", "3.0, 3.0"})
    public void testFormulaRandomDouble(double speed, double initialDistance) {
        Horse horse = new Horse("test", speed, initialDistance);
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.5);
            double expectedDistance = initialDistance+speed*0.5;
            horse.move();
            assertEquals(expectedDistance, horse.getDistance());
        }
    }

    public String stringChecker(String name) {
        if (name == null || name.matches("\\s*")) {
            return null;
        } else {
            return name;
        }
    }
}