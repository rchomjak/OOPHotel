package com.company;

public interface ClientsInterface {

    Client findClient(String id);
    void addClient(Client client);
    void deleteClient(String id);

    //TODO:
    //loadClients(Reader reader);
    //saveClients(Writer writer);


}
