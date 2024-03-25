//package jpql;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.EntityTransaction;
//import jakarta.persistence.Persistence;
//import org.hibernate.mapping.Collection;
//
//import java.util.List;
///*
//*   [경로 표현식 용어 정리]
//*   - 상태 필드(state field) :단순히 값을 저장하기 위한 필드 (ex : m.username)
//*   - 연관 필드(association field) : 연관관계를 위한 필드
//*       -> 단일 값 연관 필드 : @ManyToOne , @OneToOne , 대상이 엔티티(ex:m.team)
//*       -> 컬렉션 값 연관 필드 : @OneToMany , @ManyToMany , 대상이 컬렉션(ex:m.orders)
//* */
//
//// 경로 표현식 -> .(점)을 찍어 객체 그래프를 탐색하는 것
//public class JpaMainFieldSection8 {
//    /*
//    *   [경로 표현식 특징]
//    *
//    *   - 상태 필드(state field) : 경로 탐색의 끝, 탐색 x
//    *   - 단일 값 연관 경로 : 묵시적 내부 조인(inner join)발생 , 탐색 o
//    *   - 컬렉션 값 연관 경로 : 묵시적 내부 조인 발생, 탐색 x
//    *       -> FROM 절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색 가능
//    * */
//
//    public static void main(String[] args) {
//
//
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
//        EntityManager em = emf.createEntityManager();
//
//        EntityTransaction tx = em.getTransaction();
//        tx.begin(); //  트랜잭션 시작
//        try{
//
//            Team team = new Team();
//            em.persist(team);
//
//
//            Member member1 = new Member();
//            member1.setUsername("관리자1");
//            member1.setType(MemberType.ADMIN);
//            em.persist(member1);
//            Member member2 = new Member();
//            member2.setUsername("관리자2");
//            member2.setType(MemberType.ADMIN);
//            em.persist(member2);
//
//
//            em.flush();
//            em.clear();
//
//            // 상태 필드
////            String query = "select m.username From Member m";
//
//            //단일 값 연관 경로 -> 묵시적 내부 조인 발생(inner join)
////            String query = "select m.team.name From Member m";
//
////            List<String> resultList = em.createQuery(query, String.class)
////                    .getResultList();
//
//            // 컬렉션 값 연관 경로 -> 사용하기 힘들다. -> 명시적 조인을 통해 별칭을 얻고 사용하자.
////            String query = "select t.members.size From Team t";
//            String query = "select m.username From Team t join t.members m";
//            List<Collection> resultList = em.createQuery(query, Collection.class)
//                    .getResultList();
//
//            for (Collection collection : resultList) {
//                System.out.println("collection = " + collection);
//            }
//            /*
//            *   [ 경로 탐색을 사용한 묵시적 조인 시 주의사항 ]
//            *   - 항상 내부 조인
//            *   - 컬렉션은 경로 탐색의 끝, 명시적 조인을 통해 별칭을 얻어야 함
//            *   - 경로 탐색은 주로 SELECT, WHERE 절에서 사용하지만 묵시적 조인으로 인해 SQL의 FROM(JOIN)절에 영향을 줌
//            *
//            *   [ 실무 조언 ]
//            *   - 가급적 묵시적 조인 대신에 명시적 조인 사용
//            *   - 조인은 SQL 튜닝에 중요 포인트
//            *   - 묵시적 조인은 조인이 일어나는 상황을 한눈에 파악하기 어려움
//            * */
//
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
