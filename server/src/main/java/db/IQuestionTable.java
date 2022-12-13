package db;

import java.util.ArrayList;

import org.testing_system.Question;

public interface IQuestionTable 
{
    ArrayList<Question> get_questions(int id_test);
    ArrayList<Integer> get_correct_answers(int id_test);
    boolean update(Question question);
    boolean insert(Question question);
    boolean check(Question question);
    boolean delete(Question question);
    int get_count_of_question(String test_name);
}
