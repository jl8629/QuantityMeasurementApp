import java.util.Objects;

interface IMeasurable {
    double getConversionFactor();
    double convertToBaseUnit(double value);
    double convertFromBaseUnit(double baseValue);
    String getUnitName();
}

enum LengthUnit implements IMeasurable {
    FEET(0.3048),
    INCHES(0.0254),
    YARDS(0.9144),
    CENTIMETERS(0.01);

    private final double factor;

    LengthUnit(double factor) {
        this.factor = factor;
    }

    public double getConversionFactor() {
        return factor;
    }

    public double convertToBaseUnit(double value) {
        return value * factor;
    }

    public double convertFromBaseUnit(double baseValue) {
        return baseValue / factor;
    }

    public String getUnitName() {
        return name();
    }
}

enum WeightUnit implements IMeasurable {
    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double factor;

    WeightUnit(double factor) {
        this.factor = factor;
    }

    public double getConversionFactor() {
        return factor;
    }

    public double convertToBaseUnit(double value) {
        return value * factor;
    }

    public double convertFromBaseUnit(double baseValue) {
        return baseValue / factor;
    }

    public String getUnitName() {
        return name();
    }
}

enum VolumeUnit implements IMeasurable {
    LITRE(1.0),
    MILLILITRE(0.001),
    GALLON(3.78541);

    private final double factor;

    VolumeUnit(double factor) {
        this.factor = factor;
    }

    public double getConversionFactor() {
        return factor;
    }

    public double convertToBaseUnit(double value) {
        return value * factor;
    }

    public double convertFromBaseUnit(double baseValue) {
        return baseValue / factor;
    }

    public String getUnitName() {
        return name();
    }
}

class Quantity<U extends IMeasurable> {
    private final double value;
    private final U unit;

    private enum Operation {
        ADD, SUBTRACT, DIVIDE
    }

    public Quantity(double value, U unit) {
        if (unit == null || Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException();
        }
        this.value = value;
        this.unit = unit;
    }

    private double compute(Quantity<U> other, Operation op) {
        if (other == null) throw new IllegalArgumentException();
        if (!this.unit.getClass().equals(other.unit.getClass())) throw new IllegalArgumentException();

        double base1 = this.unit.convertToBaseUnit(this.value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        switch (op) {
            case ADD:
                return base1 + base2;
            case SUBTRACT:
                return base1 - base2;
            case DIVIDE:
                if (base2 == 0.0) throw new ArithmeticException();
                return base1 / base2;
            default:
                throw new IllegalArgumentException();
        }
    }

    public Quantity<U> convertTo(U targetUnit) {
        double base = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(base);
        double rounded = Math.round(converted * 100.0) / 100.0;
        return new Quantity<>(rounded, targetUnit);
    }

    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        double baseResult = compute(other, Operation.ADD);
        double result = targetUnit.convertFromBaseUnit(baseResult);
        double rounded = Math.round(result * 100.0) / 100.0;
        return new Quantity<>(rounded, targetUnit);
    }

    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        double baseResult = compute(other, Operation.SUBTRACT);
        double result = targetUnit.convertFromBaseUnit(baseResult);
        double rounded = Math.round(result * 100.0) / 100.0;
        return new Quantity<>(rounded, targetUnit);
    }

    public double divide(Quantity<U> other) {
        return compute(other, Operation.DIVIDE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quantity<?>)) return false;
        Quantity<?> that = (Quantity<?>) o;
        if (!this.unit.getClass().equals(that.unit.getClass())) return false;
        double base1 = this.unit.convertToBaseUnit(this.value);
        double base2 = that.unit.convertToBaseUnit(that.value);
        return Double.compare(base1, base2) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(unit.getClass(), unit.convertToBaseUnit(value));
    }

    @Override
    public String toString() {
        return value + " " + unit.getUnitName();
    }
}

public class QuantityMeasurementApp {
    public static void main(String[] args) {
        Quantity<LengthUnit> l1 = new Quantity<>(10, LengthUnit.FEET);
        Quantity<LengthUnit> l2 = new Quantity<>(6, LengthUnit.INCHES);

        System.out.println(l1.add(l2));
        System.out.println(l1.subtract(l2));
        System.out.println(l1.divide(l2));

        Quantity<WeightUnit> w1 = new Quantity<>(10, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(5, WeightUnit.KILOGRAM);

        System.out.println(w1.add(w2));
        System.out.println(w1.subtract(w2));
        System.out.println(w1.divide(w2));

        Quantity<VolumeUnit> v1 = new Quantity<>(5, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(2, VolumeUnit.LITRE);

        System.out.println(v1.add(v2));
        System.out.println(v1.subtract(v2));
        System.out.println(v1.divide(v2));
    }
}