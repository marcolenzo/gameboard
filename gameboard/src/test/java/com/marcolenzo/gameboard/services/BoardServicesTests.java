package com.marcolenzo.gameboard.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.security.core.context.SecurityContextHolder;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.marcolenzo.gameboard.exceptions.BadRequestException;
import com.marcolenzo.gameboard.exceptions.ForbiddenException;
import com.marcolenzo.gameboard.model.Board;
import com.marcolenzo.gameboard.model.PlayerStatistics;
import com.marcolenzo.gameboard.repositories.BoardRepository;
import com.marcolenzo.gameboard.utils.MockAuthenticationFactory;


/**
 * Tests for {@link AvatarServices}
 * @author Marco Lenzo
 *
 */
public class BoardServicesTests {

	private BoardServices boardServices = new BoardServices();

	private BoardRepository boardRepository = Mockito.mock(BoardRepository.class);

	@Before
	public void init() {
		SecurityContextHolder.getContext().setAuthentication(MockAuthenticationFactory.createMockAuthentication());
		boardServices.boardRepository = this.boardRepository;
	}

	@Test
	public void testBoardCreationWithSpoofedElos() {
		Board board = getBoard(5);

		Mockito.when(boardRepository.save(Mockito.any(Board.class))).thenAnswer(new Answer<Board>() {

			@Override
			public Board answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				return (Board) args[0];
			}
			
		});

		Board createdBoard = boardServices.createBoard(board);

		for (PlayerStatistics player : createdBoard.getPlayers()) {
			Assert.assertEquals(1500, player.getElo().intValue());
			Assert.assertEquals(0, player.getEloVariation().intValue());
			Assert.assertEquals(0, player.getMatchesPlayed().intValue());
			Assert.assertEquals(0, player.getMatchesPlayedAsResistance().intValue());
			Assert.assertEquals(0, player.getMatchesPlayedAsSpy().intValue());
			Assert.assertEquals(0, player.getMatchesWon().intValue());
			Assert.assertEquals(0, player.getMatchesWonAsResistance().intValue());
			Assert.assertEquals(0, player.getMatchesWonAsSpy().intValue());
			Assert.assertEquals(0, player.getPosition().intValue());
			Assert.assertEquals(0, player.getPositionVariation().intValue());
		}

	}

	@Test
	public void testBoardUpdateWithMissingOwner() throws BadRequestException, ForbiddenException {
		SecurityContextHolder.getContext().setAuthentication(
				MockAuthenticationFactory.createMockAuthentication("userId0"));

		Board board = getBoard(5);

		Mockito.when(boardRepository.findOne(Mockito.anyString())).thenReturn(getBoard(3));
		Mockito.when(boardRepository.save(Mockito.any(Board.class))).thenAnswer(new Answer<Board>() {

			@Override
			public Board answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				return (Board) args[0];
			}

		});

		board.getAdmins().clear();
		board.getOwners().clear();

		Board updatedBoard = boardServices.updateBoard(board, board.getId());

		boolean isOwnerPresent = false;
		for (String owner : updatedBoard.getOwners()) {
			if (owner.equals("userId0")) {
				isOwnerPresent = true;
			}
		}

		boolean isOwnerPresentAsAdmin = false;
		for (String admin : updatedBoard.getAdmins()) {
			if (admin.equals("userId0")) {
				isOwnerPresentAsAdmin = true;
			}
		}

		Assert.assertTrue(isOwnerPresentAsAdmin);
		Assert.assertTrue(isOwnerPresent);
	}

	private Board getBoard(int numberOfPlayers) {
		Board board = new Board();
		board.setId("id");
		board.setName("Name");
		board.setType("RESISTANCE");
		board.setPlayers(Lists.newArrayList());
		board.setAdmins(Sets.newHashSet("userId0"));
		board.setOwners(Sets.newHashSet("userId0"));

		for (int i = 0; i < numberOfPlayers; i++) {
			PlayerStatistics spoofedStats = new PlayerStatistics();

			spoofedStats.setElo(1600);
			spoofedStats.setMatchesPlayed(10);
			spoofedStats.setMatchesWon(10);
			spoofedStats.setMatchesPlayedAsResistance(10);
			spoofedStats.setMatchesWonAsResistance(10);
			spoofedStats.setMatchesPlayedAsSpy(20);
			spoofedStats.setMatchesWonAsSpy(20);
			spoofedStats.setPosition(1);
			spoofedStats.setPositionVariation(1);
			spoofedStats.setEloVariation(100);
			spoofedStats.setUserId("userId" + i);
			spoofedStats.setNickname("Nickname" + i);

			board.getPlayers().add(spoofedStats);
		}

		return board;
	}

}
