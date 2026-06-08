public abstract class User {

    private String userId;
    private String fullName;
    private String email;
    protected String passwordHash;

    public User(String userId, String fullName, String email, String passwordHash) {
        this.userId       = userId;
        this.fullName     = fullName;
        this.email        = email;
        this.passwordHash = passwordHash;
    }

    public abstract String getRole();
    public abstract String getDashboardView();

    public String getUserId()       { return userId; }
    public String getFullName()     { return fullName; }
    public String getEmail()        { return email; }
    public String getPasswordHash() { return passwordHash; }

    public void setFullName(String fullName)   { this.fullName = fullName; }
    public void setEmail(String email)         { this.email = email; }
    public void setPasswordHash(String hash)   { this.passwordHash = hash; }

    @Override
    public String toString() {
        return String.format("[%s] %s <%s>  role=%s", userId, fullName, email, getRole());
    }
}