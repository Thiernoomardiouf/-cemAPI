package com.cybersourcecem.services;

import com.cybersourcecem.entities.Client;
import com.cybersourcecem.entities.Person;
import com.cybersourcecem.entities.Provider;

import java.util.List;

public interface PersonService {
    void createProvider(Provider provider);
    List<Provider> findAllProviders();
    void createClient(Client client);
    List<Person> findAll();
    List<Client> findAllClients();
    void deletePerson(long  id);
    void editPerson(Provider provider, long id);
    void editPerson(Client client, long id);
    Person findOnePerson(long id);
    Provider findOneProviderById(long id);
    Client findOneClientById(long id);
    void deletePersonAll();
}
