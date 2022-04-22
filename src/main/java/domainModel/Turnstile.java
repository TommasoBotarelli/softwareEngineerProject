package domainModel;

public class Turnstile {

    private boolean canAccess;

    public Turnstile(){
        this.canAccess = false;
    }

    public boolean canAccess() {
        return canAccess;
    }

    public void setCanAccess(boolean canAccess) {
        this.canAccess = canAccess;
    }

}
