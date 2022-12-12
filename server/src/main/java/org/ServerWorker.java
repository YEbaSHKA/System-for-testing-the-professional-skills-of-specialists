package org;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

import org.testing_system.*;

import db.DataBaseFactory;

public class ServerWorker implements Runnable 
{
    protected Socket client_socket = null;
    ObjectInputStream reader;
    ObjectOutputStream writer;

    public ServerWorker(Socket client_socket) {
        this.client_socket = client_socket;
    }

    @Override
    public void run() {
        try {
            reader = new ObjectInputStream(client_socket.getInputStream());
            writer = new ObjectOutputStream(client_socket.getOutputStream());
            while (true) {
                System.out.println("Waiting the request...");
                String command = reader.readObject().toString();
                System.out.println("Request: " + command);
                switch (command) {
                    case "authorizationEmployee":
                    {
                        Employee employee = (Employee) reader.readObject();
                        System.out.println("DOBRO " + employee.getLogin() + " " + employee.getPassword());
                        
                        DataBaseFactory db_factory = new DataBaseFactory();
                        
                        boolean check = db_factory.get_authorization().check_availability(employee);
                        writer.writeObject(check);
                        if (check) 
                        {
                            employee = db_factory.get_employee().getEmployee(employee.getLogin());
                            writer.writeObject(employee);
                        }
                    }
                    break;

                    case "authorizationAdmin":
                    {
                        Admin admin = (Admin) reader.readObject();
                        System.out.println("DOBRO " + admin.getLogin() + " " + admin.getPassword());
                        
                        DataBaseFactory db_factory = new DataBaseFactory();
                        
                        boolean check = db_factory.get_authorization().check_admins(admin);
                        writer.writeObject(check);
                        if (check) 
                        {
                            admin = db_factory.get_admin().getAdmin(admin.getLogin());
                            writer.writeObject(admin);
                        }
                    }
                    break;

                    case "registrationEmployee":
                    {
                        Employee employee = (Employee) reader.readObject();
                        System.out.println("DOBRO " + employee.getId() + " " + employee.getFull_name() + " " + employee.getLogin() + " " + employee.getPassword());

                        DataBaseFactory db_factory = new DataBaseFactory();
                        boolean check = db_factory.get_authorization().check_availability(employee);
                        if (!check) 
                        {
                            writer.writeObject(check);
                            writer.writeObject(db_factory.get_employee().insert(employee));
                            writer.writeObject(db_factory.get_employee().getEmployee(employee.getLogin()));
                        }
                        else
                        {
                            writer.writeObject(check);
                        }
                    }
                    break;

                    case "getTopics": 
                    {
                        DataBaseFactory db_factory = new DataBaseFactory();
                        // System.out.println(client_socket.getInetAddress().to);
                        ArrayList<Topic> topics = db_factory.get_topics().get_topics();
                        writer.writeObject(topics);
                    }
                    break;

                    case "getTests":
                    {
                        String[] topic_and_type = reader.readObject().toString().split(" ");
                        
                        DataBaseFactory db_factory = new DataBaseFactory();
                        ArrayList<Test> tests = db_factory.get_tests().get_tests_by_topic_and_type(topic_and_type[0], topic_and_type[1]);
                        
                        writer.writeObject(tests);
                    }
                    break;

                    case "getsQuestions":
                    {
                        int id_test = (int) reader.readObject();

                        DataBaseFactory db_factory = new DataBaseFactory();
                        ArrayList<Question> questions = db_factory.get_question().get_questions(id_test);

                        writer.writeObject(questions);
                    }
                    break;

                    case "getResultOfPassTheTest":
                    {
                        ArrayList<Integer> answers = (ArrayList<Integer>) reader.readObject();
                        TestEmployee test_employee = (TestEmployee) reader.readObject();
                        
                        DataBaseFactory db_factory = new DataBaseFactory();
                        
                        ArrayList<Integer> right_answers = db_factory.get_question().get_correct_answers(test_employee.getId_test());
                        for (int i = 0; i < answers.size(); i++) {
                            System.out.println(answers.get(i) + " " + right_answers.get(i));
                        }

                        int count_right_answers = 0;
                        for (int i = 0; i < answers.size(); i++) 
                        {
                            if(answers.get(i).equals(right_answers.get(i)))
                            {
                                count_right_answers++;
                            }
                        }

                        test_employee.setResult(count_right_answers);

                        System.out.println(test_employee.getResult());

                        if (db_factory.get_tests_employee().check_availability(test_employee.getId_test(), test_employee.getId_employee()))
                        {
                            boolean check = db_factory.get_tests_employee().update(test_employee);
                            if (check) 
                            {
                                writer.writeObject(check);    
                                writer.writeObject(count_right_answers + " из " + answers.size());
                            }
                            else
                            {
                                writer.writeObject(check);
                            }
                        }
                        else
                        {
                            boolean check = db_factory.get_tests_employee().insert(test_employee);
                            if (check) 
                            {
                                writer.writeObject(check);
                                writer.writeObject(count_right_answers + " из " + answers.size());                    
                            }
                            else
                            {
                                writer.writeObject(check);
                            }
                        }
                        
                    }
                    break;

                    case "getEmployeeInf":
                    {
                        int id_employee = (int) reader.readObject();

                        DataBaseFactory db_factory = new DataBaseFactory();
                        Employee employee = db_factory.get_employee().get_employee_by_id(id_employee);
                        
                        writer.writeObject(employee);

                        ArrayList<ResultOfTest> results = db_factory.get_result_of_test().get_results(id_employee);
                        writer.writeObject(results);

                    }
                    break;

                    case "updateEmployeeWitinPass":
                    {
                        Employee employee = (Employee) reader.readObject();

                        DataBaseFactory db_factory = new DataBaseFactory();
                        boolean check = db_factory.get_employee().update_within_pass(employee);

                        writer.writeObject(check);
                    }
                    break;

                    case "updateEmployee":
                    {
                        DataBaseFactory db_factory = new DataBaseFactory();


                        Employee employee = (Employee) reader.readObject();

                        boolean availability = db_factory.get_employee().check_pass(employee);
                        writer.writeObject(availability);

                        if (availability)
                        {
                            employee.setPassword(reader.readObject().toString());
                            boolean check = db_factory.get_employee().update(employee);

                            writer.writeObject(check);   
                        }
                    }
                    break;

                    case "updateQuestion":
                    {
                        Question question = (Question) reader.readObject();

                        DataBaseFactory db_factory = new DataBaseFactory();

                        writer.writeObject(db_factory.get_question().update(question));

                    }
                    break;

                    case "insertQuestion":
                    {
                        Question question = (Question) reader.readObject();

                        DataBaseFactory db_factory = new DataBaseFactory();

                        boolean check = db_factory.get_question().check(question);
                        if (check) 
                        {
                            writer.writeObject(check);
                            writer.writeObject(db_factory.get_question().insert(question));
                        }
                        else
                        {
                            writer.writeObject(check);
                        }
                    }
                    break;

                    case "deleteQuestion":
                    {
                        Question question = (Question) reader.readObject();

                        DataBaseFactory da_factory = new DataBaseFactory();

                        writer.writeObject(da_factory.get_question().delete(question));
                    }
                    break;

                    case "updateTest":
                    {
                        Test test = (Test) reader.readObject();

                        DataBaseFactory db_factory = new DataBaseFactory();

                        writer.writeObject(db_factory.get_tests().update(test));
                    }
                    break;

                    case "insertTest":
                    {
                        Test test = (Test) reader.readObject();

                        DataBaseFactory db_factory = new DataBaseFactory();

                        boolean check = db_factory.get_tests().check(test);
                        if (check) 
                        {
                            writer.writeObject(check);
                            writer.writeObject(db_factory.get_tests().insert(test));
                        }
                        else
                        {
                            writer.writeObject(check);
                        }
                    }
                    break;

                    case "deleteTest":
                    {
                        Test test = (Test) reader.readObject();

                        DataBaseFactory da_factory = new DataBaseFactory();

                        writer.writeObject(da_factory.get_tests().delete(test));
                    }
                    break;

                    case "updateTopic":
                    {
                        Topic topic = (Topic) reader.readObject();

                        String new_value = reader.readObject().toString();
                        
                        DataBaseFactory db_factory = new DataBaseFactory();

                        writer.writeObject(db_factory.get_topics().update(topic, new_value));
                    }
                    break;

                    case "insertTopic":
                    {
                        Topic topic = (Topic) reader.readObject();

                        DataBaseFactory db_factory = new DataBaseFactory();

                        boolean check = db_factory.get_topics().check(topic);
                        if (check) 
                        {
                            writer.writeObject(check);
                            writer.writeObject(db_factory.get_topics().insert(topic));
                        }
                        else
                        {
                            writer.writeObject(check);
                        }
                    }
                    break;

                    case "deleteTopic":
                    {
                        Topic topic = (Topic) reader.readObject();

                        DataBaseFactory db_factory = new DataBaseFactory();
                        
                        writer.writeObject(db_factory.get_topics().delete(topic));
                    }
                    break;

                    default:
                    {
                        System.out.println("Unknown request!");
                    }
                    break;
                }
            }
        }
        catch(IOException | ClassNotFoundException | SQLException e )
        {
            System.out.println("Client say bye-bye!" + " " + e.getMessage());
        }
    }
}
