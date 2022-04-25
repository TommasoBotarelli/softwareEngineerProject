package dao;

public class DaoFactoryFake extends DaoFactory{

    @Override
    public CostumerDao getCostumerDao() {
        return FakeCostumerDao.getInstance();
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
    public TurnstileDao getTurnstileDao() {
        return FakeTurnstileDao.getInstance();
    }

    @Override
    public TypeOfAccessDao getTypeOfAccessDao() {
        return FakeTypeOfAccessDao.getInstance();
    }
}
