package dao.concreteClass;

import dao.interfaceClass.EvaluationDao;
import domainModel.Customer;
import domainModel.Evaluation;

import java.util.ArrayList;

public class FakeEvaluationDao implements EvaluationDao {

    private static FakeEvaluationDao instance = null;
    private ArrayList<Evaluation> evaluations;

    private FakeEvaluationDao(){
        evaluations = new ArrayList<>();
    }

    public static FakeEvaluationDao getInstance(){
        if(instance == null)
            instance = new FakeEvaluationDao();
        return instance;
    }

    @Override
    public void addEvaluation(Evaluation evaluation) {
        evaluations.add(evaluation);
    }

    @Override
    public void deleteEvaluation(Evaluation evaluation) {
        evaluations.remove(evaluation);
    }

    @Override
    public ArrayList<Evaluation> getEvaluationOfCustomer(Customer customer) {
        ArrayList<Evaluation> evaluationOfCustomer = new ArrayList<>();

        for(Evaluation e : evaluations){
            if (e.getCustomer().equals(customer))
                evaluationOfCustomer.add(e);
        }

        return evaluationOfCustomer;
    }

    @Override
    public ArrayList<Evaluation> getAllEvaluation() {
        return evaluations;
    }

    @Override
    public void deleteAll() {
        evaluations.clear();
    }
}
