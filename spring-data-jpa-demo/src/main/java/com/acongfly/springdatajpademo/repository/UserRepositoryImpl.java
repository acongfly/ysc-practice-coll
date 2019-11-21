package com.acongfly.springdatajpademo.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @program: ysc-practice-coll
 * @description:
 * @author: shicong yang
 * @create: 2019-11-19 14:50
 **/
public class UserRepositoryImpl {

    @PersistenceContext
    private EntityManager em;

    // @SuppressWarnings("unchecked")
    // public Page<User> search(User user) {
    // String dataSql = "select t from User t where 1 = 1";
    // String countSql = "select count(t) from User t where 1 = 1";
    //
    // if (null != user && !StringUtils.isEmpty(user.getName())) {
    // dataSql += " and t.name = ?1";
    // countSql += " and t.name = ?1";
    // }
    //
    // Query dataQuery = em.createQuery(dataSql);
    // Query countQuery = em.createQuery(countSql);
    //
    // if (null != user && !StringUtils.isEmpty(user.getName())) {
    // dataQuery.setParameter(1, user.getName());
    // countQuery.setParameter(1, user.getName());
    // }
    // long totalSize = (long)countQuery.getSingleResult();
    // Page<User> page = new Page();
    // page.setTotalSize(totalSize);
    // List<User> data = dataQuery.getResultList();
    // page.setData(data);
    // return page;
    // }
}
