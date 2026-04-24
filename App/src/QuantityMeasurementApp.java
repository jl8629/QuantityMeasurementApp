public class QuantityMeasurementApp {
    private double value1;
    private double value2;

    public QuantityMeasurementApp(double value1, double value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public boolean areEqual() {
        return Double.compare(value1, value2) == 0;
    }

    public static void main(String[] args) {
        QuantityMeasurementApp app = new QuantityMeasurementApp(10.0, 10.0);
        System.out.println(app.areEqual());
    }
}
