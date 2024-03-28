package jpql;

import jakarta.persistence.*;
import org.hibernate.mapping.Collection;

import java.util.List;
//엔티티 직접 사용
public class JpaMain {


    public static void main(String[] args) {


        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin(); //  트랜잭션 시작
        try{

            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);


            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);
            Member member2 = new Member();
            member2.setUsername("회원2");
            member1.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원2");
            member1.setTeam(teamB);
            em.persist(member3);


            em.flush();
            em.clear();
            //엔티티를 파라미터로 전달
//            String query = "select m from Member m where m = :member";

            //식별자를 직접 전달
//            String query = "select m from Member m where m.id = :memberId";

            //외래 키 값 전달
            String query = "select m from Member m where m.team = :team";

            List<Member> findMember = em.createQuery(query , Member.class)
                            .setParameter("team",member1.getTeam()).getResultList();


            for (Member members : findMember) {

            System.out.println("members = " + members);
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

