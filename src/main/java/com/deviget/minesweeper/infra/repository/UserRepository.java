package com.deviget.minesweeper.infra.repository;

import com.deviget.minesweeper.domain.model.user.User;
import com.deviget.minesweeper.domain.model.user.UserId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, UserId> {

}
