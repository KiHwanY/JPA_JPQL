//package jpql;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.EntityTransaction;
//import jakarta.persistence.Persistence;
//
//import java.util.List;
//
////Fetch JOIN
//public class JpaMainFetchJoinSection9 {
//
//
//    public static void main(String[] args) {
//        /*
//        *   [페치 조인과 일반 조인의 차이]
//        *   - 페치 조인을 사용할 때만 연관된 엔티티도 함께 조회(즉시 로딩)
//        *   - 페치 조인은 객체 그래프를 SQL 한번에 조회하는 개념
//        *
//        * */
//
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
//        EntityManager em = emf.createEntityManager();
//
//        EntityTransaction tx = em.getTransaction();
//        tx.begin(); //  트랜잭션 시작
//        try{
//
//            Team teamA = new Team();
//            teamA.setName("팀A");
//            em.persist(teamA);
//
//            Team teamB = new Team();
//            teamB.setName("팀B");
//            em.persist(teamB);
//
//
//            Member member1 = new Member();
//            member1.setUsername("회원1");
//            member1.setTeam(teamA);
//            em.persist(member1);
//            Member member2 = new Member();
//            member2.setUsername("회원2");
//            member1.setTeam(teamA);
//            em.persist(member2);
//
//            Member member3 = new Member();
//            member3.setUsername("회원2");
//            member1.setTeam(teamB);
//            em.persist(member3);
//
//
//            em.flush();
//            em.clear();
//
//            //일반 쿼리 쿼리가 3번 나간다.
////            String query = "select m From Member m";
//
//            //fetch 조인 예제 select 절에 member와 team 둘 다 조회하는 걸 볼 수 있다. 한방에 묶어서 조인한다.
//            //여기서 팀은 프록시가 아니라 엔티티이다.
////            String query = "select m From Member m join fetch m.team";
//
////            List<Member> resultList = em.createQuery(query, Member.class)
////                    .getResultList();
//            //컬렉션 페치 조인
////            String query = "select t From Team t join fetch t.members";
////            List<Team> resultList = em.createQuery(query, Team.class)
////                    .getResultList();
//
////            for (Team member : resultList) {
////                System.out.println("Member = " + member.getUsername() + ", " + member.getTeam().getName());
////                //회원1, 팀A(SQL)
////                //회원2, 팀A(1차캐시)
////                //회원3, 팀B(SQL)
////                //만약에 회원 100명을 조회하면? -> N + 1 : 첫번째 날린 쿼리로 결과 얻은 Result 결과 만큼 n번을 날리는 문제
////            }
////            for (Team team : resultList) {
////                System.out.println("Member = " + team.getName() + ", " + team.getMembers().size());
////                for (Member member : team.getMembers()) {
////                    System.out.println(" -> member = " + member);
////                }
//               //조회를 해보면 size가 3개 나온다. -> db가 뻥튀기 된다..
////            }
//
//            /*
//            * [페치 조인과 DISTINCT]
//            * SQL의 DISTINCT는 중복된 결과를 제거하는 명령어
//            * JPQL의 DISTINCT 2가지 기능 제공
//            *   -> SQL에 DISTINCT를 추가
//            *   -> 애플리케이션에서 엔티티 중복 제거
//            * */
//            //DISTINCT 페치 조인
//            String query = "select distinct t From Team t join fetch t.members";
//            List<Team> resultList = em.createQuery(query, Team.class)
//                    .getResultList();
//
//            for (Team team : resultList) {
//                System.out.println("Member = " + team.getName() + ", " + team.getMembers().size());
//                for (Member member : team.getMembers()) {
//                    System.out.println(" -> member = " + member);
//                    // 결과가 3으로 나오던 값이 2개가 나온다.
//                    // 중복을 제거한 것이다.
//                }
//            }
//
//            tx.commit(); //  트랜잭션 커밋
//        } catch (Exception e){
//            tx.rollback();
//            e.printStackTrace();
//        }finally {
//            em.close();
//        }
//        emf.close();
//    }
//}
//
