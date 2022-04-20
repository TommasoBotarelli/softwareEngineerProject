package dao;

import domainModel.Costumer;
import domainModel.Evaluation;

import java.util.ArrayList;

public interface EvaluationDao {
    void addEvaluation(Evaluation evaluation);
    void deleteEvaluation(Evaluation evaluation);
    ArrayList<Evaluation> getEvaluationOfCostumer(Costumer costumer);
}
