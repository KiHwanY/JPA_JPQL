package jpql;

import jakarta.persistence.*;

import java.util.List;

// JPQL TYPE 표현과 기타식
public class JpaMain {

    public static void main(String[] args) {
        /*
        *   [JPQL 타입 표현]
        *   문자 : 'HELLO', 'She"s'
        *   숫자 : 10L(Long) , 10D(Double) , 10F(Float)
        *   Boolean : TRUE , FALSE
        *   ENUM : jpabook.MemberType.Admin(패키지명 포함) -> 파라미터 설정해서 사용하자.
        *   엔티티 타입 : TYPE(m) = Member (상속 관계에서 사용) -> 잘 사용 안한다.
        * */

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin(); //  트랜잭션 시작
        try{

            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);


            Member member = new Member();
            member.setUsername("TeamA");
            member.setAge(10);
            member.setType(MemberType.ADMIN);
            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            //패키지명 예제
//            String query = "select m.username,'HELLO',true from Member m " +
//                            "where m.type = jpql.MemberType.USER";
            // 파라미터 설정 예제
            String query = "select m.username,'HELLO',true from Member m " +
                    "where m.type = :userType";
            
            List<Object[]> result = em.createQuery(query)
                    .setParameter("userType" , MemberType.ADMIN)
                    .getResultList();

            for (Object[] objects : result) {
                System.out.println("objects[0] = " + objects[0]);
                System.out.println("objects[1] = " + objects[1]);
                System.out.println("objects[2] = " + objects[2]);
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

