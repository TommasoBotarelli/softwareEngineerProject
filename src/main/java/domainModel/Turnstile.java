package domainModel;

public class Turnstile {

    private boolean operative;
    private boolean canAccess;

    public Turnstile(boolean isOperative){
        this.operative = isOperative;
        this.canAccess = false;
    }

    public boolean canAccess() {
        return canAccess;
    }

    public void setCanAccess(boolean canAccess) {
        this.canAccess = canAccess;
    }

    public boolean isOperative() {
        return operative;
    }

    public void setOperative(boolean operative) {
        this.operative = operative;
    }

}
