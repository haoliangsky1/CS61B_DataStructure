import java.util.ArrayList;

public class Soundulate {

    public static void main(String[] args) {
        // Generator generator = new SawToothGenerator(200);
        // Generator generator = new StrangeBitwiseGenerator(200);
        // GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
        // gav.drawAndPlay(4096, 1000000);
    //     Generator g1 = new SineWaveGenerator(200);
    //     Generator g2 = new SineWaveGenerator(201);

    //     ArrayList<Generator> generators = new ArrayList<Generator>();
    //     generators.add(g1);
    //     generators.add(g2);
    //     MultiGenerator mg = new MultiGenerator(generators);

    //     GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(mg);
    //     gav.drawAndPlay(500000, 1000000);
        Generator generator = new AcceleratingSawToothGenerator(200, 1.1);
        GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
        gav.drawAndPlay(4096, 1000000);
    }
} 