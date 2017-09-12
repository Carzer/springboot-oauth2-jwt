package com.carzer.service;

import com.carzer.model.DemoBaseClientDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangHQ on 2017/7/6 0006.
 */
@Component
public interface DemoBaseClientDetailsDAO extends MongoRepository<DemoBaseClientDetails, String> {
    List<DemoBaseClientDetails> findAll();

    DemoBaseClientDetails findByClientId(String clientId);
}
