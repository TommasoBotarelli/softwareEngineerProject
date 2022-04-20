package dao;

import domainModel.Costumer;
import domainModel.Evaluation;

import java.util.ArrayList;

public class FakeEvaluationDao implements EvaluationDao{

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
    public ArrayList<Evaluation> getEvaluationOfCostumer(Costumer costumer) {
        ArrayList<Evaluation> evaluationOfCostumer = new ArrayList<>();

        for(Evaluation e : evaluations){
            if (e.getCostumer().equals(costumer))
                evaluationOfCostumer.add(e);
        }

        return evaluationOfCostumer;
    }
}
