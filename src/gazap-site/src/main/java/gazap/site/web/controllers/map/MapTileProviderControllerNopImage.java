package gazap.site.web.controllers.map;

import org.springframework.security.util.InMemoryResource;

import java.io.IOException;

public class MapTileProviderControllerNopImage extends InMemoryResource {
    public MapTileProviderControllerNopImage() {
        super(new byte[0], "");
    }

    @Override
    public String getFilename() throws IllegalStateException {
        return "nop.png";
    }

    @Override
    public long contentLength() throws IOException {
        return 0;
    }
}
