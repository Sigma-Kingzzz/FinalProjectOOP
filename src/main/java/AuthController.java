public class AuthController {

    public User login(String email, String password) throws AuthException {
        if (email == null || email.isBlank())
            throw new AuthException("Please enter your email.");
        if (password == null || password.isBlank())
            throw new AuthException("Please enter your password.");

        User user = UserStore.authenticate(email.trim(), password);
        if (user == null)
            throw new AuthException("Incorrect email or password.");

        return user;
    }

    public User register(String fullName, String email,
                         String password, String confirmPassword,
                         String role, String extraField) throws AuthException {

        if (fullName == null || fullName.isBlank())
            throw new AuthException("Full name is required.");

        if (email == null || !email.matches("^[\\w._%+\\-]+@[\\w.\\-]+\\.[a-zA-Z]{2,}$"))
            throw new AuthException("Please enter a valid email address.");

        if (UserStore.emailExists(email.trim()))
            throw new AuthException("An account with this email already exists.");

        if (password == null || password.length() < 6)
            throw new AuthException("Password must be at least 6 characters.");

        if (!password.equals(confirmPassword))
            throw new AuthException("Passwords do not match.");

        String id   = UserStore.nextUserId();
        String hash = PasswordUtil.hash(password);
        User newUser;

        if ("Admin".equalsIgnoreCase(role)) {
            String dept = (extraField == null || extraField.isBlank()) ? "General" : extraField;
            newUser = new Admin(id, fullName.trim(), email.trim().toLowerCase(), hash, dept);
        } else {
            String position = (extraField == null || extraField.isBlank()) ? "Staff" : extraField;
            newUser = new Employee(id, fullName.trim(), email.trim().toLowerCase(),
                                   hash, position, 0.0);
        }

        UserStore.addUser(newUser);
        return newUser;
    }

    public static class AuthException extends Exception {
        public AuthException(String message) { super(message); }
    }
}