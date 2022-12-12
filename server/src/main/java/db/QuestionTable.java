package db;

import java.sql.SQLException;
import java.util.ArrayList;

import org.testing_system.Question;

public class QuestionTable implements IQuestionTable
{
    private static QuestionTable instance;
    private TestingSystemDB dbConnection;

    private QuestionTable() throws SQLException, ClassNotFoundException {
        dbConnection = dbConnection.getInstance();
    }

    public static synchronized QuestionTable getInstance() throws SQLException, ClassNotFoundException 
    {
        if (instance == null) 
        {
            instance = new QuestionTable();
        }
        return instance;
    }

    @Override
    public ArrayList<Question> get_questions(int id_test)
    {
        String SQL = "SELECT * FROM Questions WHERE id_test = " + id_test;

        ArrayList<String[]> result = dbConnection.getArrayResult(SQL);
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
        
        ArrayList<String[]> result = dbConnection.getArrayResult(SQL);
        ArrayList<Integer> correct_answers = new ArrayList<>();

        for (String[] items : result)
        {
            correct_answers.add(Integer.parseInt(items[0]));
        }

        return correct_answers;
    }

    @Override
    public boolean update(Question question) 
    {
        String SQL = "UPDATE Questions SET name = '" + question.getName() + "', first_answer = '" 
                    + question.getFirst_answer() + "', second_answer = '" + question.getSecond_answer() 
                    + "', third_answer = '" + question.getThird_answer() + "', correct_answer_number = " 
                    + question.getCorrect_answer_number() + " WHERE id = " + question.getId();
        return dbConnection.insert_values(SQL);
    }

    @Override
    public boolean insert(Question question) 
    {
        String SQL = "INSERT INTO Questions(name, first_answer, second_answer, third_answer, correct_answer_number, id_test) Values('" 
                    + question.getName() + "', '" + question.getFirst_answer() + "', '" 
                    + question.getSecond_answer() + "', '" + question.getThird_answer() + "', " + question.getCorrect_answer_number() 
                    + ", " + question.getId_test() + ")";
        return dbConnection.insert_values(SQL);
    }

    @Override
    public boolean check(Question question) {
        String SQL = "SELECT * FROM Questions WHERE name = '" + question.getName() + "'";

        ArrayList<String[]> result = dbConnection.getArrayResult(SQL);
        return result.isEmpty();
    }

    @Override
    public boolean delete(Question question) {
        String SQL = "DELETE FROM Questions WHERE id = " + question.getId();
        return dbConnection.insert_values(SQL);
    }

}
