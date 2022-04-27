package domainModel;

public class Turnstile {

    private boolean open;

    public Turnstile(){
        this.open = false;
    }

    public boolean canAccess() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

}
