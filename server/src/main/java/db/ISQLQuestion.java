package db;

import java.util.ArrayList;

import org.testing_system.Question;

public interface ISQLQuestion {
    ArrayList<Question> get_questions(int id_test);
    ArrayList<Integer> get_correct_answers(int id_test);
}
