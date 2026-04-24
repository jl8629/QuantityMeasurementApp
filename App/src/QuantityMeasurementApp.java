public class QuantityMeasurementApp {

    enum Unit {
        FEET(12.0),
        INCH(1.0),
        YARD(36.0),
        CM(0.393701);

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
        QuantityLength feet = new QuantityLength(3.0, Unit.FEET);
        QuantityLength yard = new QuantityLength(1.0, Unit.YARD);
        System.out.println("Feet vs Yard Equality: " + feet.areEqual(yard));

        QuantityLength inch = new QuantityLength(12.0, Unit.INCH);
        QuantityLength foot = new QuantityLength(1.0, Unit.FEET);
        System.out.println("Inch vs Foot Equality: " + inch.areEqual(foot));

        QuantityLength cm = new QuantityLength(2.54, Unit.CM);
        QuantityLength inch2 = new QuantityLength(1.0, Unit.INCH);
        System.out.println("Cm vs Inch Equality: " + cm.areEqual(inch2));

        QuantityLength yard2 = new QuantityLength(2.0, Unit.YARD);
        QuantityLength feet2 = new QuantityLength(6.0, Unit.FEET);
        System.out.println("Yard vs Feet Equality: " + yard2.areEqual(feet2));
    }
}
