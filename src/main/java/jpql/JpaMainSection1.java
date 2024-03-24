//package jpql;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.EntityTransaction;
//import jakarta.persistence.Persistence;
//
//public class JpaMainSection1 {
//
//    public static void main(String[] args) {
//
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
//        EntityManager em = emf.createEntityManager();
//
//        EntityTransaction tx = em.getTransaction();
//        tx.begin(); //  트랜잭션 시작
//        try{
//            Member member = new Member();
//
//            member.setUsername("member1");
//            member.setAge(10);
//            em.persist(member);
//
//
//            Member result = em.createQuery("select m from Member m where m.username =:username", Member.class)
//                            .setParameter("username", "member1")
//                            .getSingleResult();
//            System.out.println("singleResult = " + result.getUsername());
//
//
//            //TypedQuery = 반환 타입이 명확할 때 사용
//            //Query = 반환 타입이 명확하지 않을 때 사용
////            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
////            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
////            Query query3 = em.createQuery("select m.username, m.age from Member m");
//            //query.getResultList() => 결과가 하나 이상일 때, 리스트 반환 / 결과가 없으면 빈 리스트 반환
////            List<Member> resultList = query.getResultList();
//
////            for (Member member1 : resultList) {
////                System.out.println("member1 = " + member1);
////            }
//
//            //query.getSingleResult() => 결과가 정확히 하나, 단일 객체 반환
////            Member result = query.getSingleResult();
////            System.out.println("result = " + result);
//            // 만약 결과가 없으면 NoResultException 발생
//            // 결과가 둘 이상이면 NonUniqueResultException 발생한다.
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
