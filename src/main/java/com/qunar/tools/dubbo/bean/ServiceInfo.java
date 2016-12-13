package com.qunar.tools.dubbo.bean;

public class ServiceInfo {
    private String name;
    private String regName;
    private String group;
    private boolean isProvider;

    public boolean isProvider() {
        return isProvider;
    }

    public void setProvider(boolean isProvider) {
        this.isProvider = isProvider;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegName() {
        return regName;
    }

    public void setRegName(String regName) {
        this.regName = regName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceInfo that = (ServiceInfo) o;

        if (isProvider != that.isProvider) return false;
        if (group != null ? !group.equals(that.group) : that.group != null) return false;
        if (!name.equals(that.name)) return false;
        if (!regName.equals(that.regName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + regName.hashCode();
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (isProvider ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ServiceInfo{");
        sb.append("name='").append(name).append('\'');
        sb.append(", regName='").append(regName).append('\'');
        sb.append(", group='").append(group).append('\'');
        sb.append(", isProvider=").append(isProvider);
        sb.append('}');
        return sb.toString();
    }
}