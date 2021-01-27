package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.QuestionEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class QuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    public QuestionEntity createQuestion(QuestionEntity questionEntity) {
        entityManager.persist(questionEntity);
        return questionEntity;
    }

    public QuestionEntity getQuestionById(final String questionId) {
        try {
            return entityManager.createNamedQuery("questionById", QuestionEntity.class).setParameter("uuid", questionId).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<QuestionEntity> getQuestionByUserId(final String userId) {
        try {
            return entityManager.createNamedQuery("questionById", QuestionEntity.class).setParameter("uuid", userId).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<QuestionEntity> getAllQuestions() {
        try {
            return entityManager.createNamedQuery("allQuestions", QuestionEntity.class).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public void updateQuestion(final QuestionEntity questionEntity) {
        entityManager.merge(questionEntity);
    }

    public void deleteQuestion(final QuestionEntity question) {
        entityManager.remove(question);
    }
}
