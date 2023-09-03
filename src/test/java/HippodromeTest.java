import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class HippodromeTest {
    @Test
    public void testIAENull() {
        Exception exception =assertThrows(IllegalArgumentException.class, ()-> {
            new Hippodrome(null);
        });
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    public void testIAENullList() {
        List<Horse> horses = new ArrayList<>();
        Exception exception =assertThrows(IllegalArgumentException.class, ()-> {
            new Hippodrome(horses);
        });
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    public void testGetHorses() {
        List<Horse> horses = new ArrayList<>();
        for(int i=0;i<30;i++) {
            horses.add(new Horse(String.valueOf(i),1,1));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }
    @Mock
    Horse horse;
    @Test
    public void testMove() {
        List<Horse> horses = new ArrayList<>();
        for(int i=0;i<50;i++) {
            horses.add(horse);
        }
        Hippodrome hippodrome =new Hippodrome(horses);
        hippodrome.move();
        Mockito.verify(horse, Mockito.times(50)).move();
    }

    @Test
    public void testGetWinner() {
        List<Horse> horses = new ArrayList<>();
        for(int i=0;i<30;i++) {
            horses.add(new Horse(String.valueOf(i),i,i));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        Horse horse1 = hippodrome.getHorses().stream().max(Comparator.comparing(Horse::getDistance)).get();
        assertEquals(horse1, hippodrome.getWinner());
    }
}