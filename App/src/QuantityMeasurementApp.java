public class QuantityMeasurementApp {

    static class Feet {
        private double value1;
        private double value2;

        public Feet(double value1, double value2) {
            this.value1 = value1;
            this.value2 = value2;
        }

        public boolean areEqual() {
            return Double.compare(value1, value2) == 0;
        }
    }

    static class Inches {
        private double value1;
        private double value2;

        public Inches(double value1, double value2) {
            this.value1 = value1;
            this.value2 = value2;
        }

        public boolean areEqual() {
            return Double.compare(value1, value2) == 0;
        }
    }

    public static boolean checkFeetEquality(double v1, double v2) {
        Feet feet = new Feet(v1, v2);
        return feet.areEqual();
    }

    public static boolean checkInchesEquality(double v1, double v2) {
        Inches inches = new Inches(v1, v2);
        return inches.areEqual();
    }

    public static void main(String[] args) {
        System.out.println("Feet Equality: " + checkFeetEquality(10.0, 10.0));
        System.out.println("Feet Equality: " + checkFeetEquality(12.0, 10.0));
        System.out.println("Inches Equality: " + checkInchesEquality(5.0, 5.0));
        System.out.println("Inches Equality: " + checkInchesEquality(6.0, 5.0));
    }
}
