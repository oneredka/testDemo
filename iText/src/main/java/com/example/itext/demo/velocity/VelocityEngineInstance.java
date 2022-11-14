package com.example.itext.demo.velocity;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;

import java.io.File;
import java.util.Properties;

public class  VelocityEngineInstance {
    private static final VelocityEngineInstance INSTANCE = new VelocityEngineInstance();
    private VelocityConfig config = new VelocityConfig();
    private VelocityEngine engine = new VelocityEngine();

    private VelocityEngineInstance() {
        this.initEngine();
    }

    private void initEngine() {
        Properties props = new Properties();
        props.setProperty("ISO-8859-1", this.config.getEncoding());
        String localTempFolder = System.getProperty("LOCAL_TEMP_FOLDER");
        if (StringUtils.isNotBlank(localTempFolder)) {
            props.setProperty("runtime.log", localTempFolder + File.separator + "velocity.log");
        }

        this.engine.init(props);
    }

    public static VelocityEngineInstance getInstance() {
        return INSTANCE;
    }

    public VelocityEngine getEngine() {
        return this.engine;
    }

    public Object getEngineProperty(String key) {
        return this.engine.getProperty(key);
    }

    public VelocityConfig getConfig() {
        return this.config;
    }
}