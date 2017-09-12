package com.carzer.service;

import com.carzer.model.BaseUserDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

/**
 * Created by WangHQ on 2017/7/6 0006.
 */
@Component
public interface BaseUserDAO extends MongoRepository<BaseUserDTO, String> {
    BaseUserDTO findByLoginName(String loginName);
}
