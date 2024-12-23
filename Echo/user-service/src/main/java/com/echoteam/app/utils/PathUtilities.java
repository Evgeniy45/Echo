package com.echoteam.app.utils;

import java.net.URL;
import java.nio.file.Path;

public class PathUtilities {

    public static Path getClasspath() {
        URL resource = PathUtilities.class.getClassLoader().getResource("");
        if (resource == null) {
            throw new IllegalStateException("Classpath root could not be found.");
        }
        return Path.of(resource.getPath());
    }

}
