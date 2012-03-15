package gazap.site.exceptions;

public class UserProfileNotFound extends RecoverableException {
    public UserProfileNotFound() {
        super("{gazap.site.exceptions.UserProfileNotFound}");
    }
}
