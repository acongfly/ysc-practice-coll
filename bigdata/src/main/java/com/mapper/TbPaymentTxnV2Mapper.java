package com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.domain.TbPaymentTxnV2;

@Mapper
public interface TbPaymentTxnV2Mapper {
    /**
     * delete by primary key
     *
     * @param id
     *            primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert record to table
     *
     * @param record
     *            the record
     * @return insert count
     */
    int insert(TbPaymentTxnV2 record);

    /**
     * insert record to table selective
     *
     * @param record
     *            the record
     * @return insert count
     */
    int insertSelective(TbPaymentTxnV2 record);

    /**
     * select by primary key
     *
     * @param id
     *            primary key
     * @return object by primary key
     */
    TbPaymentTxnV2 selectByPrimaryKey(Long id);

    /**
     * update record
     *
     * @param record
     *            the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(TbPaymentTxnV2 record);

    /**
     * update record selective
     *
     * @param record
     *            the updated record
     * @return update count
     */
    int updateByPrimaryKey(TbPaymentTxnV2 record);

    List<Result> findByUpdateTime(@Param("updateTime") String updateTime);

}