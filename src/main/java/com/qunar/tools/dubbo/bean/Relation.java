package com.qunar.tools.dubbo.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * since 2016/12/8.
 */
public class Relation {

    private App app;
    private Set<App> depApp = new HashSet<>();

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public Set<App> getDepApp() {
        return depApp;
    }

    public void setDepApp(Set<App> depApp) {
        this.depApp = depApp;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Relation{");
        sb.append("app=").append(app);
        sb.append(", depApp=").append(depApp);
        sb.append('}');
        return sb.toString();
    }
}
