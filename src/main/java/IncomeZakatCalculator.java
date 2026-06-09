public class IncomeZakatCalculator {

    // --- OOP Encapsulation: Constants ---
    private static final double ZAKAT_RATE = 0.025; // 2.5%
    
    // Default Nisab value (Fluctuates per state based on gold prices)
    private static final double DEFAULT_NISAB_MYR = 25000.00; 

    // --- Instance Variables (State) ---
    // 1. Gross Annual Income Categories
    private double salaryAndAllowances;
    private double bonusAndCommission;
    private double dividendAndRoyalty;

    // 2. Allowed Deductions / Exemptions (Pelepasan)
    private double epfContribution; // KWSP / Pension scheme
    private double basicSelfAndFamilyExpenses;
    private double parentsDependentExemption;
    private double medicalAndEducationExpenses;
    private double otherPaidZakat;

    private double currentNisab;

    /**
     * Default Constructor
     */
    public IncomeZakatCalculator() {
        this.currentNisab = DEFAULT_NISAB_MYR;
    }

    /**
     * Overloaded Constructor for custom Nisab depending on the state
     */
    public IncomeZakatCalculator(double currentNisab) {
        this.currentNisab = Math.max(0, currentNisab);
    }

    // --- Mutators (Setters) for Income ---
    public void setIncome(double salary, double bonus, double dividend) {
        this.salaryAndAllowances = Math.max(0, salary);
        this.bonusAndCommission = Math.max(0, bonus);
        this.dividendAndRoyalty = Math.max(0, dividend);
    }

    // --- Mutators (Setters) for Deductions ---
    public void setDeductions(double epf, double basic, double parents, double medEdu, double otherZakat) {
        this.epfContribution = Math.max(0, epf);
        this.basicSelfAndFamilyExpenses = Math.max(0, basic);
        this.parentsDependentExemption = Math.max(0, parents);
        this.medicalAndEducationExpenses = Math.max(0, medEdu);
        this.otherPaidZakat = Math.max(0, otherZakat);
    }

    public double getCurrentNisab() { return currentNisab; }
    public void setCurrentNisab(double nisab) { this.currentNisab = Math.max(0, nisab); }

    // --- Business Logic Methods ---

    /**
     * Calculates Total Gross Annual Income
     */
    public double calculateTotalGrossIncome() {
        return salaryAndAllowances + bonusAndCommission + dividendAndRoyalty;
    }

    /**
     * Calculates Total Allowed Deductions
     */
    public double calculateTotalDeductions() {
        return epfContribution + basicSelfAndFamilyExpenses + parentsDependentExemption + medicalAndEducationExpenses + otherPaidZakat;
    }

    /**
     * Calculates Net Income subject to Nisab check (Gross - Deductions)
     */
    public double calculateNetIncome() {
        double net = calculateTotalGrossIncome() - calculateTotalDeductions();
        return Math.max(0, net);
    }

    /**
     * Condition 1: Checks if net income meets Nisab threshold
     */
    public boolean isNisabMet() {
        return calculateNetIncome() >= currentNisab;
    }

    /**
     * Computes Annual Income Zakat due (2.5% of net income if Nisab is met)
     */
    public double calculateAnnualZakat() {
        if (isNisabMet()) {
            return calculateNetIncome() * ZAKAT_RATE;
        }
        return 0.0;
    }

    /**
     * Computes Monthly Zakat payment (Salary Deduction/Skim Potongan Gaji)
     */
    public double calculateMonthlyZakat() {
        return calculateAnnualZakat() / 12.0;
    }
}
