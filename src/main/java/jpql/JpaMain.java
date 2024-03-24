package jpql;

import jakarta.persistence.*;

import java.util.List;

// Sub Query
public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin(); //  트랜잭션 시작
        try{
            /*
            *   [SubQuery]
            *   나이가 평균보다 많으 회원
            *   select m from Member m
            *   where m.age > (select avg(m2.age) from member m2)
            *
            *   한 건이라도 주문한 고객
            *   select m from member m
            *   where (select count(0) from Order o where m = o.member) > 0
            *
            *   [서브쿼리 지원 함수]
            *   [NOT] EXISTS(subQuery) : 서브 쿼리에 결과가 존재하면 참
            *       - {ALL | ANY | SOME}(subQuery)
            *       - ALL 모두 만족하면 참
            *       - ANY, SOME : 같은 의미, 조건을 하나라도 만족하면 참
            *   [NOT] IN (subQuery) : 서브 쿼리의 결과 중 하나라도 같은 것이 있으면 참
            *
            *   [JPA 서브 쿼리 한계]
            *   JPA는 WHERE , HAVING 절에서만 서브 쿼리 사용 가능
            *   SELECT 절도 가능(하이버네이트에서 지원)
            *   FROM 절의 서브 쿼리는 현재 JPQL에서 불가능(타격이 큼)
            *       - 조인으로 풀 수 있으면 풀어서 해결
            * */
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);


            Member member = new Member();
            member.setUsername("TeamA");
            member.setAge(10);
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            //EXISTS 예제
//            String query = "select m from Member m where exists(select t from m.team t where t.name = 'teamA')";

            // 하이버네이트에서 지원해주는 select 절 예제
            String query = "select (select avg(m1.age) from Member m1) as avgAge from Member m join Team t on m.username = t.name )";
            List<Member> result = em.createQuery(query, Member.class)
                    .getResultList();

            System.out.println("result = " + result.size());


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

