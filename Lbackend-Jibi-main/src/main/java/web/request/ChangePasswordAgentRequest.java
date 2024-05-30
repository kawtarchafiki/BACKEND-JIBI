package web.request;

import jakarta.validation.constraints.NotBlank;

public class ChangePasswordAgentRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String newPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
