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

        public double fromBase(double baseValue) {
            return baseValue / conversionFactor;
        }
    }

    static class QuantityLength {
        private double value;
        private Unit unit;

        public QuantityLength(double value, Unit unit) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Value must be finite");
            }
            if (unit == null) {
                throw new IllegalArgumentException("Unit must not be null");
            }
            this.value = value;
            this.unit = unit;
        }

        public boolean areEqual(QuantityLength other) {
            return Double.compare(this.unit.toBase(this.value), other.unit.toBase(other.value)) == 0;
        }

        public static double convert(double value, Unit sourceUnit, Unit targetUnit) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Value must be finite");
            }
            if (sourceUnit == null || targetUnit == null) {
                throw new IllegalArgumentException("Units must not be null");
            }
            double baseValue = sourceUnit.toBase(value);
            return targetUnit.fromBase(baseValue);
        }
    }

    public static void main(String[] args) {
        double feetToInches = QuantityLength.convert(1.0, Unit.FEET, Unit.INCH);
        System.out.println("1 Foot in Inches: " + feetToInches);

        double yardsToInches = QuantityLength.convert(1.0, Unit.YARD, Unit.INCH);
        System.out.println("1 Yard in Inches: " + yardsToInches);

        double cmToFeet = QuantityLength.convert(30.48, Unit.CM, Unit.FEET);
        System.out.println("30.48 Cm in Feet: " + cmToFeet);

        QuantityLength q1 = new QuantityLength(1.0, Unit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, Unit.INCH);
        System.out.println("Equality Check (1 Foot vs 12 Inches): " + q1.areEqual(q2));
    }
}
