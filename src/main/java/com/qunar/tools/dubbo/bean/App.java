package com.qunar.tools.dubbo.bean;

import java.util.Set;

/**
 * since 2016/11/29.
 */
public class App {

    private String name;
    private Set<RegistryInfo> registryInfos;
    private Set<ServiceInfo> providerInfos;
    private Set<ServiceInfo> consumerInfos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<RegistryInfo> getRegistryInfos() {
        return registryInfos;
    }

    public void setRegistryInfos(Set<RegistryInfo> registryInfos) {
        this.registryInfos = registryInfos;
    }

    public Set<ServiceInfo> getProviderInfos() {
        return providerInfos;
    }

    public void setProviderInfos(Set<ServiceInfo> providerInfos) {
        this.providerInfos = providerInfos;
    }

    public Set<ServiceInfo> getConsumerInfos() {
        return consumerInfos;
    }

    public void setConsumerInfos(Set<ServiceInfo> consumerInfos) {
        this.consumerInfos = consumerInfos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        App app = (App) o;

        if (name != null ? !name.equals(app.name) : app.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("App{");
        sb.append("name='").append(name).append('\'');
        sb.append(", registryInfos=").append(registryInfos);
        sb.append(", providerInfos=").append(providerInfos);
        sb.append(", consumerInfos=").append(consumerInfos);
        sb.append('}');
        return sb.toString();
    }
}
