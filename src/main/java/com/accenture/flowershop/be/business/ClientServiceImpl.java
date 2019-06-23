package com.accenture.flowershop.be.business;

import com.accenture.flowershop.be.access.ClientDAO;
import com.accenture.flowershop.be.entity.Client;
import com.accenture.flowershop.config.SimpleMessageSender;
import com.accenture.flowershop.config.XMLConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;

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

                    ApplicationContext jmsContext;
                    jmsContext = new FileSystemXmlApplicationContext("path/to/jmsContext.xml");
                    SimpleMessageSender messageSender = (SimpleMessageSender) jmsContext.getBean("simpleMessageSender");
                    messageSender.sendMessage(XML_FILE_NAME);
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
}
