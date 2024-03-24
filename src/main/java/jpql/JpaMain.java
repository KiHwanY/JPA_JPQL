package jpql;

import jakarta.persistence.*;

import java.util.List;

// JOIN
public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin(); //  트랜잭션 시작
        try{
            /*
            *   [JOIN]
            *
            *   - 내부 조인 : SELECT m FROM Member m [INNER] JOIN m.team t
            *   - 외부 조인 : SELECT m FROM Member m LEFT [OUTER] JOIN m.team t
            *   - 세타 조인 : SELECT COUNT(m) FROM Member m , Team t where m.username = t.name
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

            //내부 조인 = inner 생략 가능
//            String query = "select m from Member m inner join m.team t where t.name =:teamName";
            //외부 조인 = outer 생략 가능
//            String query = "select m from Member m left join m.team t ";
            //세타 조인
//            String query = "select m from Member m, Team t where m.username = t.name ";

            /*
             *   [ JOIN - ON 절]
             *   ON절을 활용한 조인(JPA 2.1부터 지원)
             *   조인 대상 필터링
             *   연관관계 없는 엔티티 외부 조인(하이버네이트 5.1부터)
             * */
            //조인 대상 필터링
            //ex) 회원과 팀을 조인하면서, 팀 이름이 TeamA인 팀만 조인
//            String query = "select m from Member m left join m.team t on t.name = 'TeamA' ";

            //연관관계 없는 엔티티 외부 조인
            //ex)회원의 이름과 팀의 이름이 같은 대상 외부 조인
            //주의) 스프링 부트 1점대 쓰거나 하이버네이트 5.1이 이상이 아니면 버전 업해서 사용해야 한다.
            String query = "select m from Member m left join Team t on m.username = t.name";
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

