package dao.factoryClass;

import dao.concreteClass.*;
import dao.interfaceClass.*;

public class DaoFactoryFake extends DaoFactory{

    @Override
    public CustomerDao getCustomerDao() {
        return FakeCustomerDao.getInstance();
    }

    @Override
    public AccessDao getAccessDao() {
        return FakeAccessDao.getInstance();
    }

    @Override
    public BadgeDao getBadgeDao() {
        return FakeBadgeDao.getInstance();
    }

    @Override
    public BillDao getBillDao() {
        return FakeBillDao.getInstance();
    }

    @Override
    public EvaluationDao getEvaluationDao() {
        return FakeEvaluationDao.getInstance();
    }

    @Override
    public PersonalTrainerDao getPersonalTrainerDao() {
        return FakePersonalTrainerDao.getInstance();
    }

    @Override
    public ReceptionistDao getReceptionistDao() {
        return FakeReceptionistDao.getInstance();
    }

    @Override
    public TrainingCardDao getTrainingCardDao() {
        return FakeTrainingCardDao.getInstance();
    }

    @Override
    public GymManagerDao getGymManagerDao() {
        return FakeGymManagerDao.getInstance();
    }

    @Override
    public DailyDao getDailyDao() {
        return FakeDailyDao.getInstance();
    }

    @Override
    public SubscriptionDao getSubscriptionDao() {
        return FakeSubscriptionDao.getInstance();
    }

    @Override
    public TrialSubscriptionDao getTrialSubscriptionDao() {
        return FakeTrialSubscriptionDao.getInstance();
    }
}
