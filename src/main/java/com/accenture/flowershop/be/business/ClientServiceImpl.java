package com.accenture.flowershop.be.business;

import com.accenture.flowershop.be.access.ClientDAO;
import com.accenture.flowershop.be.entity.Client;
import com.accenture.flowershop.config.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientDAO clientDAO;
    private static final String XML_FILE_NAME = "C:\\Users\\alexandr.ifanov\\Documents\\GitHub\\ee_ifanov\\customer.xml";

    @Autowired
    public ClientServiceImpl(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @Override
    public Client login(String login, String password) {
        try {
            Client client = clientDAO.getByLogin(login);
            if (!password.equals(client.getPassword())) {
                return null;
            }
            return client;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Client register(Client client) {
        try {
            Client tempClient;
            if (client.getLogin() != null && client.getPassword() != null) {

                tempClient = clientDAO.add(client);

                if (tempClient != null) {


                    ApplicationContext appContext = new ClassPathXmlApplicationContext("app-context.xml");
                    XMLConverter converter = (XMLConverter) appContext.getBean("XMLConverter");
                    converter.convertFromObjectToXML(tempClient, XML_FILE_NAME);

                    try {
                        File xmlFile = new File(XML_FILE_NAME);
                        Reader fileReader = new FileReader(xmlFile);
                        BufferedReader bufReader = new BufferedReader(fileReader);
                        StringBuilder sb = new StringBuilder();
                        String line = bufReader.readLine();
                        while( line != null) {
                            sb.append(line).append("\n");
                            line = bufReader.readLine();
                        }
                        String xml2String = sb.toString();
                        bufReader.close();

//                        ApplicationContext jmsContext = new ClassPathXmlApplicationContext("jmsContext.xml");
//                        SimpleMessageSender messageSender = (SimpleMessageSender) jmsContext.getBean("simpleMessageSender");
//                        messageSender.sendMessage(xml2String);

                        AnnotationConfigApplicationContext jmsContext = new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
                        MessageSender ms = jmsContext.getBean(MessageSender.class);
                        ms.sendMessage("OUT_QUEUE", xml2String);

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

//                    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JmsConfig.class);
//                    String queueName = "OUT_QUEUE";
//                    MessageReceiver mr = context.getBean(MessageReceiver.class);
//                    mr.receiveMessage(queueName);



                }
                return tempClient;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Client updateBalance(String login, Double balance) {
        try {
            Client client = clientDAO.getByLogin(login);
            client.setBalance(balance);
            return clientDAO.update(client);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Client getByLogin(String login) {
        try {
            return clientDAO.getByLogin(login);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Client getNewDiscount() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
        String queueName = "IN_QUEUE";
        MessageReceiver mr = context.getBean(MessageReceiver.class);
        mr.receiveMessage(queueName);

//        if (mr.receiveMessage(queueName) != null) {
//            String msg = mr.receiveMessage(queueName);
//            System.out.println("MESSAGE IS " + msg);
//        }

        return null;
    }
}
