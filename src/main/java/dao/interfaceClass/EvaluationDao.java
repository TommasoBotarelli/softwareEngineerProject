package dao.interfaceClass;

import domainModel.Customer;
import domainModel.Evaluation;

import java.util.ArrayList;

public interface EvaluationDao {
    void addEvaluation(Evaluation evaluation);
    void deleteEvaluation(Evaluation evaluation);
    ArrayList<Evaluation> getEvaluationOfCustomer(Customer customer);
    ArrayList<Evaluation> getAllEvaluation();
    void deleteAll();
}
