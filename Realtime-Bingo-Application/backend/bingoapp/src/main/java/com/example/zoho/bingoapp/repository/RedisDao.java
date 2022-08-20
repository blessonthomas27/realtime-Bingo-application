package com.example.zoho.bingoapp.repository;

import com.example.zoho.bingoapp.model.redisDto.RedisDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisDao extends CrudRepository<RedisDto,String> {
}
