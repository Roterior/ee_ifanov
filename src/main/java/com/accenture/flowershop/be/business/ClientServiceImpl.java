package com.accenture.flowershop.be.business;

import com.accenture.flowershop.be.access.ClientDAO;
import com.accenture.flowershop.be.entity.Client;
import com.accenture.flowershop.config.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import javax.jms.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.Enumeration;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientDAO clientDAO;
    private final ConnectionFactory connectionFactory;
    private static final String XML_FILE_NAME = "C:\\Users\\alexandr.ifanov\\Documents\\GitHub\\ee_ifanov\\customer.xml";

    @Autowired
    public ClientServiceImpl(ClientDAO clientDAO, ConnectionFactory connectionFactory) {
        this.clientDAO = clientDAO;
        this.connectionFactory = connectionFactory;
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

                        AnnotationConfigApplicationContext jmsContext = new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
                        MessageSender ms = jmsContext.getBean(MessageSender.class);
                        ms.sendMessage("OUT_QUEUE", xml2String);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
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
    public Integer getNewDiscount(String login) {
        Client clientQueue = null;

        try {
            if (getQueueSize() > 0) {
                JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
                String queueName = "IN_QUEUE";
                Message message = jmsTemplate.receive(queueName);
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();

                JAXBContext jaxbContext;
                try {
                    jaxbContext = JAXBContext.newInstance(Client.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    clientQueue = (Client) jaxbUnmarshaller.unmarshal(new StringReader(text));

                    if (login.equals(clientQueue.getLogin())) {
                        Client client1 = clientDAO.getByLogin(login);
                        client1.setDiscount(clientQueue.getDiscount());
                        clientDAO.update(client1);
                    }
                }
                catch (JAXBException e) {
                    e.printStackTrace();
                }
            }

            return clientQueue != null ? clientQueue.getDiscount() : null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private int getQueueSize() {
        Connection connection = null;
        Session session = null;
        int count = 0;

        try {
            connection = connectionFactory.createConnection("admin", "admin");
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue("IN_QUEUE");
            QueueBrowser browser = session.createBrowser((Queue) destination);
            Enumeration elems = browser.getEnumeration();

            while (elems.hasMoreElements()) {
                elems.nextElement();
                count++;
            }
        }
        catch (JMSException ex) {
            ex.printStackTrace();
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
        return count;
    }
}
