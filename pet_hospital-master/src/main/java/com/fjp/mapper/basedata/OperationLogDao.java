package com.fjp.mapper.basedata;

import com.fjp.pojo.entity.basedata.OperationLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface OperationLogDao extends Mapper<OperationLog> {
}
