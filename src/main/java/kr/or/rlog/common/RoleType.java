package kr.or.rlog.common;

public enum RoleType {
    USER("user"),
    ADMIN("admin");

    private final String ROLE_PREFIX = "ROLE_";
    private String name;

    RoleType(String name) { this.name = name; }

    public String getRoleType() { return ROLE_PREFIX + name.toUpperCase(); }

    public String getValue() { return name; }

    public boolean isEquals(String authority) { return this.getRoleType().equals(authority);}
}