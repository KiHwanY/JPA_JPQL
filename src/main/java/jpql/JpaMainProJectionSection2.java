//package jpql;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.EntityTransaction;
//import jakarta.persistence.Persistence;
//
//import java.util.List;
//
//// 프로젝션 Section 소개
//public class JpaMainProJectionSection2 {
//
//    public static void main(String[] args) {
//
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
//        EntityManager em = emf.createEntityManager();
//
//        EntityTransaction tx = em.getTransaction();
//        tx.begin(); //  트랜잭션 시작
//        try{
//            /*
//            *     [프로젝션]
//            *   - select 절에 조회할 대상을 지정하는 것
//            *   - 프로젝션 대상 : 엔티티 , 임베디드 타입, 스칼라 타입(숫자, 문자 등 기본 데이터 타입)
//            * */
//            Member member = new Member();
//
//            member.setUsername("member1");
//            member.setAge(10);
//            em.persist(member);
//
//            em.flush();
//            em.clear();
//
//            // 엔티티 프로젝션
////            List<Member> result = em.createQuery("select m from Member m", Member.class)
////                            .getResultList();
////            Member findMember = result.get(0);
////            findMember.setAge(20);
//
//            //엔티티 프로젝션 => Join 쿼리가 나간다. 하지만 SQL과 동일하게 작성하는 게 좋다. 아래를 참고하자.
////            List<Team> result = em.createQuery("select m.team from Member m", Team.class)
////                    .getResultList();
//
//            //예측을 위한 명확한 쿼리를 작성하는 게 좋다.
////            List<Team> result = em.createQuery("select t from Member m join m.team t", Team.class)
////                    .getResultList();
//
//            // 임베디드 프로젝션
////            em.createQuery("select o.address from Order o", Address.class)
////                    .getResultList();
//
//            // 스칼라 타입 프로젝션
////            em.createQuery("select distinct m.username, m.age from Member m")
////                    .getResultList();
//
//            // 프로젝션 - 여러 값 조회
//            // 1. Query 타입으로 조회
////            List resultList = em.createQuery("select  m.username, m.age from Member m")
////                    .getResultList();
//            //2. Object[] 타입으로 조회
////            List<Object[]> resultList = em.createQuery("select  m.username, m.age from Member m")
////                    .getResultList();
//
//            //3. new 명령어로 조회
//            // 제일 깔끔한 방법이지만 문제이기 때문에 패키지 명까지 다 적어줘야 한다. 나중에 QueryDSL 사용하면 극복이 된다.
//            // 순서와 타입이 일치하는 생성자 필요
//            List<MemberDTO> resultList = em.createQuery("select new jpql.MemberDTO(m.username, m.age )from Member m", MemberDTO.class)
//                    .getResultList();
//
//            MemberDTO memberDTO =resultList.get(0);
//            System.out.println("memberDTO = " + memberDTO.getUsername());
//            System.out.println("memberDTO = " + memberDTO.getAge());
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
