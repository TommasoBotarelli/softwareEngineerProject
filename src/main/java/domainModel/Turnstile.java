package domainModel;

public class Turnstile {

    private boolean operative;
    private boolean open;


    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isOperative() {
        return operative;
    }


    public void setOperative(boolean operative) {
        this.operative = operative;
    }

}
