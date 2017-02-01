package creatures;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

public class TestClorus {
    @Test
    public void testBasics() {
        Clorus c = new Clorus(0.5);
        assertEquals(0.5, c.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), c.color());
        c.move();
        assertEquals(0.47, c.energy(), 0.01);
        c.move();
        assertEquals(0.44, c.energy(), 0.01);
        c.stay();
        assertEquals(0.43, c.energy(), 0.01);
        c.stay();
        assertEquals(0.42, c.energy(), 0.01);
    }
    @Test
    public void testReplicate() {
        Clorus c = new Clorus(1.2);
        Clorus coff = c.replicate();
        assertEquals(0.6, c.energy(), 0.01);
        assertEquals(0.6, coff.energy(), 0.01);
        assertNotSame(c, coff);
    }
    @Test
    public void testChoose() {
        Clorus c = new Clorus(0.8);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());
        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);
        assertEquals(expected, actual);
    }
    public static void main(String[] args) {
        System.exit(jh61b.junit.textui.runClasses(TestClorus.class));
    }
}
