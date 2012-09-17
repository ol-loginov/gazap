package waypalm.site.services;

public interface UserActionGuard {
    boolean createSurface(int worldId);

    boolean editSurface(int surfaceId);
}
