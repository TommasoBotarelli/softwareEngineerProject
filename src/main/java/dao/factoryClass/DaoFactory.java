package dao.factoryClass;

import dao.interfaceClass.*;

public abstract class DaoFactory {
    public static final int FAKE = 1;

    private static DaoFactoryFake instance = null;

    public abstract CostumerDao getCostumerDao();
    public abstract AccessDao getAccessDao();
    public abstract BadgeDao getBadgeDao();
    public abstract BillDao getBillDao();
    public abstract EvaluationDao getEvaluationDao();
    public abstract PersonalTrainerDao getPersonalTrainerDao();
    public abstract ReceptionistDao getReceptionistDao();
    public abstract TrainingCardDao getTrainingCardDao();
    public abstract GymManagerDao getGymManagerDao();
    public abstract DailyDao getDailyDao();
    public abstract SubscriptionDao getSubscriptionDao();
    public abstract TrialSubscriptionDao getTrialSubscriptionDao();

    public static DaoFactory getDaoFactory(int nFactory){
        switch (nFactory){
            case FAKE:
            {
                if (instance == null)
                    instance = new DaoFactoryFake();
                return instance;
            }
            default:
                return null;
        }
    }
}
