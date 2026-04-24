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

        public QuantityLength add(QuantityLength other) {
            if (other == null) {
                throw new IllegalArgumentException("Other quantity must not be null");
            }
            double baseSum = this.unit.toBase(this.value) + other.unit.toBase(other.value);
            double resultValue = this.unit.fromBase(baseSum);
            return new QuantityLength(resultValue, this.unit);
        }

        @Override
        public String toString() {
            return value + " " + unit.name();
        }
    }

    public static void main(String[] args) {
        QuantityLength foot = new QuantityLength(1.0, Unit.FEET);
        QuantityLength inch = new QuantityLength(12.0, Unit.INCH);
        QuantityLength result = foot.add(inch);
        System.out.println("1 Foot + 12 Inches = " + result);

        QuantityLength yard = new QuantityLength(1.0, Unit.YARD);
        QuantityLength feet = new QuantityLength(3.0, Unit.FEET);
        QuantityLength result2 = yard.add(feet);
        System.out.println("1 Yard + 3 Feet = " + result2);

        QuantityLength cm = new QuantityLength(30.48, Unit.CM);
        QuantityLength foot2 = new QuantityLength(1.0, Unit.FEET);
        QuantityLength result3 = foot2.add(cm);
        System.out.println("1 Foot + 30.48 Cm = " + result3);
    }
}
