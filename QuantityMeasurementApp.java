import java.util.Objects;

enum WeightUnit {
    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double toKgFactor;

    WeightUnit(double toKgFactor) {
        this.toKgFactor = toKgFactor;
    }

    public double toBase(double value) {
        return value * toKgFactor;
    }

    public double fromBase(double baseValue) {
        return baseValue / toKgFactor;
    }
}

class QuantityWeight {
    private final double value;
    private final WeightUnit unit;

    public QuantityWeight(double value, WeightUnit unit) {
        if (unit == null) throw new IllegalArgumentException();
        this.value = value;
        this.unit = unit;
    }

    public QuantityWeight convertTo(WeightUnit targetUnit) {
        double base = unit.toBase(value);
        double converted = targetUnit.fromBase(base);
        return new QuantityWeight(converted, targetUnit);
    }

    public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {
        double base1 = this.unit.toBase(this.value);
        double base2 = other.unit.toBase(other.value);
        double sumBase = base1 + base2;
        double result = targetUnit.fromBase(sumBase);
        return new QuantityWeight(result, targetUnit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuantityWeight)) return false;
        QuantityWeight that = (QuantityWeight) o;
        double base1 = this.unit.toBase(this.value);
        double base2 = that.unit.toBase(that.value);
        return Math.abs(base1 - base2) < 0.0001;
    }

    @Override
    public int hashCode() {
        return Objects.hash(unit.toBase(value));
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}

public class QuantityMeasurementApp {
    public static void main(String[] args) {
        QuantityWeight w1 = new QuantityWeight(1, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000, WeightUnit.GRAM);
        QuantityWeight w3 = new QuantityWeight(2.20462, WeightUnit.POUND);

        System.out.println(w1.equals(w2));
        System.out.println(w1.equals(w3));

        QuantityWeight converted = w3.convertTo(WeightUnit.KILOGRAM);
        System.out.println(converted);

        QuantityWeight sum = w1.add(w3, WeightUnit.KILOGRAM);
        System.out.println(sum);
    }
}