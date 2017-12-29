package logic.treinamento.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("dbControleBancario");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
