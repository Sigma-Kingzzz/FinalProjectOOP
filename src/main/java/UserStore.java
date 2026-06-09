import java.util.HashMap;
import java.util.Map;

public class UserStore {

    private static final Map<String, User> store = new HashMap<>();

    static {
        store.put("aiman@payroll.com",
            new Admin("U001", "Aiman", "aiman@payroll.com",   // ← was "System Administrator"
                PasswordUtil.hash("Admin@123"), "IT"));

        store.put("haikal@payroll.com",
            new Employee("U002", "Haikal", "haikal.payroll.com", // ← was "John Doe"
                PasswordUtil.hash("Employee@1"), "Software Engineer", 5000.00));
    }

    private UserStore() {}

    public static boolean emailExists(String email) {
        return store.containsKey(email.toLowerCase());
    }

    public static void addUser(User user) {
        store.put(user.getEmail().toLowerCase(), user);
    }

    public static User authenticate(String email, String plainPassword) {
        User user = store.get(email.toLowerCase());
        if (user == null) return null;
        return PasswordUtil.verify(plainPassword, user.getPasswordHash()) ? user : null;
    }

    public static String nextUserId() {
        return String.format("U%03d", store.size() + 1);
    }
}
