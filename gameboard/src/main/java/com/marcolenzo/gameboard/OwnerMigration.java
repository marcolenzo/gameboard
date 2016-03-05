package com.marcolenzo.gameboard;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;
import com.marcolenzo.gameboard.api.BoardController;
import com.marcolenzo.gameboard.commons.model.Board;
import com.marcolenzo.gameboard.commons.repositories.BoardRepository;

/**
 * Creates gameboard owners
 * @author Marco Lenzo
 *
 */
@Component
public class OwnerMigration {

	private static final Logger LOGGER = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	private BoardRepository boardRepository;

	public void run() {
		LOGGER.info("Starting owner migration...");
		List<Board> boards = boardRepository.findAll();
		for (Board board : boards) {
			if (board.getOwners() == null || board.getOwners().isEmpty()) {
				board.setOwners(Sets.newHashSet());
				for (String admin : board.getAdmins()) {
					board.getOwners().add(admin);
				}
			}
			boardRepository.save(board);
		}
		LOGGER.info("Owner migration completed.");
	}

}
