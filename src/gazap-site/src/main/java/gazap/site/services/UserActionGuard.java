package gazap.site.services;

public interface UserActionGuard {
    boolean createMap();

    boolean editMap(int mapId);
}
