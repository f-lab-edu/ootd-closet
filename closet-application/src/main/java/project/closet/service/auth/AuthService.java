package project.closet.service.auth;

public interface AuthService {

    void initAdmin();

    void resetPassword(String email);
}
