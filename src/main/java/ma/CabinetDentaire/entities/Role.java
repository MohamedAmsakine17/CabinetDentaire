package ma.CabinetDentaire.entities;

import java.util.List;

public class Role {
    private String nomRole;
    private List<String> privileges;

    public void setNomRole(String nomRole) { this.nomRole = nomRole; }
    public String getNomRole() { return nomRole; }

    public Role(String nomRole) {
        this.nomRole = nomRole;
    }

    public void ajouterPrivilege(String nomPrivilege) {
        this.privileges.add(nomPrivilege);
    }

    public void supprimerPrivilege(String nomPrivilege) {
        this.privileges.remove(nomPrivilege);
    }

    public List<String> getPrivileges() {
        return privileges;
    }
}
