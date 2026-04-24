public class QuantityMeasurementApp {

    enum Unit {
        FEET(12.0),
        INCH(1.0);

        private final double conversionFactor;

        Unit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double toBase(double value) {
            return value * conversionFactor;
        }
    }

    static class QuantityLength {
        private double value;
        private Unit unit;

        public QuantityLength(double value, Unit unit) {
            this.value = value;
            this.unit = unit;
        }

        public boolean areEqual(QuantityLength other) {
            return Double.compare(this.unit.toBase(this.value), other.unit.toBase(other.value)) == 0;
        }
    }

    public static void main(String[] args) {
        QuantityLength feet1 = new QuantityLength(1.0, Unit.FEET);
        QuantityLength feet2 = new QuantityLength(1.0, Unit.FEET);
        System.out.println("Feet Equality: " + feet1.areEqual(feet2));

        QuantityLength inch1 = new QuantityLength(12.0, Unit.INCH);
        QuantityLength inch2 = new QuantityLength(12.0, Unit.INCH);
        System.out.println("Inches Equality: " + inch1.areEqual(inch2));

        QuantityLength foot = new QuantityLength(1.0, Unit.FEET);
        QuantityLength inch = new QuantityLength(12.0, Unit.INCH);
        System.out.println("Cross Unit Equality: " + foot.areEqual(inch));
    }
}
