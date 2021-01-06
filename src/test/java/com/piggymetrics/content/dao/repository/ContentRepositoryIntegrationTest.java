//package com.piggymetrics.content.dao.repository;
//
//import com.piggymetrics.content.dao.model.Content;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//@DataJpaTest
//class ContentRepositoryIntegrationTest {
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Autowired
//    private ContentRepository contentRepository;
//
//    @Test
//    void whenFindByAccountName_thenReturnContent() {
//        Content content = new Content();
//        content.setAccountName("test");
//        entityManager.persist(content);
//        entityManager.flush();
//
//        Content found = contentRepository.findByAccountName(content.getAccountName());
//
//        assertEquals(content.getAccountName(), found.getAccountName());
//
//    }
//}