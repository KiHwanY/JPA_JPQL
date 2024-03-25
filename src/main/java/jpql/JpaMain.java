package jpql;

import jakarta.persistence.*;
import org.hibernate.mapping.Collection;

import java.util.List;

public class JpaMain {


    public static void main(String[] args) {


        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin(); //  트랜잭션 시작
        try{

            Team team = new Team();
            em.persist(team);


            Member member1 = new Member();
            member1.setUsername("관리자1");
            member1.setType(MemberType.ADMIN);
            em.persist(member1);
            Member member2 = new Member();
            member2.setUsername("관리자2");
            member2.setType(MemberType.ADMIN);
            em.persist(member2);


            em.flush();
            em.clear();


            String query = "select m.username From Team t join t.members m";
            List<Collection> resultList = em.createQuery(query, Collection.class)
                    .getResultList();

            for (Collection collection : resultList) {
                System.out.println("collection = " + collection);
            }
         

            tx.commit(); //  트랜잭션 커밋
        } catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
        emf.close();
    }
}

