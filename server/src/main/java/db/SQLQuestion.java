package db;

import java.sql.SQLException;
import java.util.ArrayList;

import org.testing_system.Question;

public class SQLQuestion implements ISQLQuestion
{
    private static SQLQuestion instance;
    private DBConnection dbConnection;

    private SQLQuestion() throws SQLException, ClassNotFoundException {
        dbConnection = DBConnection.getInstance();
    }

    public static synchronized SQLQuestion getInstance() throws SQLException, ClassNotFoundException 
    {
        if (instance == null) 
        {
            instance = new SQLQuestion();
        }
        return instance;
    }

    @Override
    public ArrayList<Question> get_questions(int id_test)
    {
        String SQL = "SELECT * FROM Questions WHERE id_test = " + id_test;

        ArrayList<String[]> result = DBConnection.getArrayResult(SQL);
        ArrayList<Question> questions = new ArrayList<>();

        for (String[] items : result)
        {
            Question question = new Question();
            question.setId(Integer.parseInt(items[0]));
            question.setName(items[1]);
            question.setFirst_answer(items[2]);
            question.setSecond_answer(items[3]);
            question.setThird_answer(items[4]);
            question.setCorrect_answer_number(Integer.parseInt(items[5]));
            question.setId_test(Integer.parseInt(items[6]));
            questions.add(question);
        }
        return questions;
    }

    @Override
    public ArrayList<Integer> get_correct_answers(int id_test) {
        String SQL = "SELECT correct_answer_number FROM Questions WHERE id_test = " + id_test;
        
        ArrayList<String[]> result = DBConnection.getArrayResult(SQL);
        ArrayList<Integer> correct_answers = new ArrayList<>();

        for (String[] items : result)
        {
            correct_answers.add(Integer.parseInt(items[0]));
        }

        return correct_answers;
    }


}
