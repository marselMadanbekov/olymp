package olymp.mental_arithmetic.model.enums;

import java.util.List;

public enum Role {
    ROLE_ADMIN("ADMIN_CRUD"),
    ROLE_PARTICIPANT("PARTICIPANT"),
    ROLE_TEMP_USER("TEMP_USER");

    private final List<String> authorities;
    Role(String... authorities){
        this.authorities = List.of((authorities));
    }

    public List<String> getAuthorities(){
        return authorities;
    }
}
