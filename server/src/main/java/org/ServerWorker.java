package org;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

import org.testing_system.Authorization;
import org.testing_system.Employee;

import db.SQLFactory;

public class ServerWorker implements Runnable {
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
                        Authorization auth = (Authorization) reader.readObject();
                        System.out.println("DOBRO " + auth.getLogin() + " " + auth.getPassword());
                        
                        SQLFactory sql_factory = new SQLFactory();
                        
                        boolean check = sql_factory.check_availability().check_availability(auth);
                        writer.writeObject(check);
                    }
                    break;

                    case "authorizationAdmin":
                    {
                        Authorization auth = (Authorization) reader.readObject();
                        System.out.println("DOBRO " + auth.getLogin() + " " + auth.getPassword());
                        
                        SQLFactory sql_factory = new SQLFactory();
                        
                        boolean check = sql_factory.check_admins().check_admins(auth);
                        writer.writeObject(check);
                    }
                    break;

                    case "registrationEmployee":
                    {
                        Employee employee = (Employee) reader.readObject();
                        System.out.println("DOBRO " + employee.getId() + " " + employee.getFull_name() + " " + employee.getLogin() + " " + employee.getPassword());
                        
                        Authorization authorization = new Authorization();
                        authorization.setLogin(employee.getLogin());
                        authorization.setPassword(employee.getPassword());

                        SQLFactory sql_factory = new SQLFactory();
                        boolean check = sql_factory.check_availability().check_availability(authorization);
                        if (!check) 
                        {
                            writer.writeObject(check);
                            writer.writeObject(sql_factory.get_employee().insert(employee));
                        }
                        else
                        {
                            writer.writeObject(check);
                        }
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
