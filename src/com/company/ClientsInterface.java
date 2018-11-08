package com.company;

import java.util.ArrayList;
import java.util.List;

public interface ClientsInterface {

    Client findClient(String id);
    void addClient(Client client);
    void deleteClient(String id);

    //TODO:
    //loadClients(Reader reader);
    //saveClients(Writer writer);


}
