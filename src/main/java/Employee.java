
public class Employee extends User {

    private String position;
    private double baseSalary;
    private String zakatStatus;

    public Employee(String userId, String fullName, String email,
                    String passwordHash, String position, double baseSalary) {
        super(userId, fullName, email, passwordHash);
        this.position   = position;
        this.baseSalary = baseSalary;
    }

    @Override
    public String getRole() { return "Employee"; }

    @Override
    public String getDashboardView() { return "employee_dashboard.fxml"; }

    public String getPosition()           { return position; }
    public double getBaseSalary()         { return baseSalary; }
    public void   setPosition(String p)   { this.position = p; }
    public void   setBaseSalary(double s) { this.baseSalary = s; }
}