package domainModel;

public class Measurement {

    private float height;
    private float weight;
    private float leanMass;
    private float fatMass;


    public Measurement(float height, float weight, float leanMass, float fatMass) {
        this.height = height;
        this.weight = weight;
        this.leanMass = leanMass;
        this.fatMass = fatMass;
    }

    public float getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public float getLeanMass() {
        return leanMass;
    }

    public float getFatMass() {
        return fatMass;
    }
}
