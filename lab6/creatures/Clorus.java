package creatures;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.HugLifeUtils;
import java.awt.Color;
import java.util.Map;
import java.util.List;

public class Clorus extends Creature {
    private int r;
    private int g;
    private int b;

    // Constructor
    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }
    public Clorus() {
        this(0.3);
    }
    public String name() {
        return "clorus";
    }
    public void move() {
        this.energy -= 0.03;
    }
    public void stay() {
        this.energy -= 0.01;
    }
    public Color color() {
        r = 34;
        g = 0;
        b = 231;
        return color(r, g, b);
    }
    public void attack(Creature c) {
        energy += c.energy();
    }
    public Clorus replicate() {
        energy = energy * 0.5;
        return new Clorus(energy);
    }
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        if (empties.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }
        List<Direction> occupants = getNeighborsOfType(neighbors, "plip");
        if (occupants.size() > 0) {
            // Random attack
            Direction attackDir = HugLifeUtils.randomEntry(occupants);
            return new Action(Action.ActionType.ATTACK, attackDir);
        }
        if (this.energy >= 1.0) {
            // Replicate
            // Clorus off = this.replicate();
            Direction offDir = empties.get(0);
            return new Action(Action.ActionType.REPLICATE, offDir);
        }
        Direction moveDir = empties.get(0);
        return new Action(Action.ActionType.MOVE, moveDir);
    }
}
