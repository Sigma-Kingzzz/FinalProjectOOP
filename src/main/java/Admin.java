
public class Admin extends User {

    private String department;

    public Admin(String userId, String fullName, String email,
                 String passwordHash, String department) {
        super(userId, fullName, email, passwordHash);
        this.department = department;
    }

    @Override
    public String getRole() { return "Admin"; }

    @Override
    public String getDashboardView() { return "admin_dashboard.fxml"; }

    public String getDepartment()            { return department; }
    public void   setDepartment(String dept) { this.department = dept; }
}