public class CalculateZakat {
        final double NISAB = 85; // NISAB threshold in grams of gold
        private double assets;

        public CalculateZakat(double assets) {
            this.assets = assets;
        }
        public double calculateZakat() {
            if (assets >= NISAB) {
                return assets * 0.025; // Zakat is 2.5% of total assets
            } else {
                return 0; // No Zakat due if below NISAB
            }
        }
}
