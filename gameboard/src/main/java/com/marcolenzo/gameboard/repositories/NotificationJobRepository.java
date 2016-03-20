package com.marcolenzo.gameboard.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcolenzo.gameboard.model.NotificationJob;


public interface NotificationJobRepository extends MongoRepository<NotificationJob, String> {

	List<NotificationJob> findByIsDoneIsFalse();

}
