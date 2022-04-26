package dao.concreteClass;

import dao.interfaceClass.TurnstileDao;
import domainModel.Turnstile;

public class FakeTurnstileDao implements TurnstileDao {
    private Turnstile entryTurnstile;
    private static FakeTurnstileDao instance = null;

    private FakeTurnstileDao(){
        entryTurnstile = new Turnstile();
    }

    public static FakeTurnstileDao getInstance(){
        if (instance == null){
            instance = new FakeTurnstileDao();
        }
        return instance;
    }

    @Override
    public Turnstile getEntryTurnstile() {
        return entryTurnstile;
    }
}
