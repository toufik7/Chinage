package sample;

import java.util.HashSet;

public class RULE {
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    private int number;
    private HashSet<String> permissions = new HashSet<>();
    private HashSet<String> actions = new HashSet<>();

    public HashSet<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(String permission) {
        this.permissions.add(permission);
    }

    public HashSet<String> getActions() {
        return actions;
    }

    public void setActions(String action) {
        this.actions.add(action);
    }


    public RULE(int number,HashSet<String> permissions, HashSet<String> actions) {
        this.permissions = permissions;
        this.actions = actions;
        this.number = number;
    }
}
