package gazap.panel.services.impl;

import gazap.panel.services.FormatService;
import gazap.panel.services.ModelViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelViewerImpl implements ModelViewer {
    @Autowired
    protected FormatService formatService;
}
