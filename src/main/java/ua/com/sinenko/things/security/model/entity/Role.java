package ua.com.sinenko.things.security.model.entity;


public enum Role {
    ADMIN("ADMIN"),
    USER("USER");

    private String roleType;
    Role(String roleType) {
        this.roleType = roleType;
    }

    public String getRole() {
        return roleType;
    }
}
