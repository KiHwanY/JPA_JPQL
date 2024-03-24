package jpql;

import jakarta.persistence.*;

import java.util.List;

// 조건식(CASE 등등)
public class JpaMain {

    public static void main(String[] args) {


        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin(); //  트랜잭션 시작
        try{

            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);


            Member member = new Member();
            member.setUsername("관리자");
            member.setAge(10);
            member.setType(MemberType.ADMIN);
            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();


            /*
             *   [조건식 - CASE식]
             * */
//            String query = "select " +
//                                    "case when m.age <= 10 then '학생요금'" +
//                                    "     when m.age >= 60 then '경로요금'" +
//                                    "     else '일반요금' " +
//                                    "end" +
//                            " from  Member m";
            /*
             *   [조건식 - CASE식]
             *   COALESCE : 하나씩 조회해서 NULL이 아니면 반환
             *   NULLIF : 두 값이 같으면 NULL 반환, 다르면 첫번째 값 반환
             * */
            //[COALESCE] -> 사용자 이름이 없으면 이름 없는 회원을 반환
//            String query = "select coalesce(m.username, '이름 없는 회원')" +
//                            " from Member m";

            //[NULLIF] -> 사용자 이름이 '관리자'면 null을 반환하고 나머지는 본인의 이름을 반환
            String query = "select nullif(m.username, '관리자')" +
                    " from Member m";
            List<String> resultList = em.createQuery(query, String.class)
                    .getResultList();

            for (String s : resultList) {
                System.out.println("s = " + s);
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

