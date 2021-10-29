/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.maps.quests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.mapstuff.portal.Door;
import games.stendhal.server.entity.mapstuff.sign.Sign;
import games.stendhal.server.entity.npc.NPCList;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.game.RPClass;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;

public class ReverseArrowTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
		QuestHelper.setUpBeforeClass();
		StendhalRPZone zone = new StendhalRPZone("int_ados_reverse_arrow");
		MockStendlRPWorld.get().addRPZone(zone);
		MockStendlRPWorld.get().addRPZone(new StendhalRPZone("0_semos_mountain_n2"));

		if (!RPClass.hasRPClass("door")) {
			Door.generateRPClass();
		}
		if (!RPClass.hasRPClass("sign")) {
			Sign.generateRPClass();
		}

	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		MockStendlRPWorld.reset();
		NPCList.get().clear();
	}


	/**
	 * Tests for getSlotName.
	 */
	@Test
	public void testGetSlotName() {
		assertEquals("reverse_arrow", new ReverseArrow().getSlotName());
	}

	/**
	 * Tests for addToWorld.
	 */
	@Test
	public void testAddToWorld() {

		ReverseArrow arrowquest = new ReverseArrow();
		arrowquest.addToWorld();
	}
	
	/**
	 * Test for arrow
	 */
	@Test
	public void testReverseArrowCheck() {
		ReverseArrow arrowquest = new ReverseArrow();
		arrowquest.addToWorld();
		arrowquest.player = PlayerTestHelper.createPlayer("bob");
		arrowquest.player.onAdded(arrowquest.zone);
		arrowquest.start(arrowquest.player);
		arrowquest.tokens.get(8).setPosition(11, 8);
		arrowquest.onTokenMoved(arrowquest.player, arrowquest.tokens.get(8));
		arrowquest.tokens.get(7).setPosition(10, 7);
		arrowquest.onTokenMoved(arrowquest.player, arrowquest.tokens.get(7));
		arrowquest.tokens.get(5).setPosition(9, 8);
		arrowquest.onTokenMoved(arrowquest.player, arrowquest.tokens.get(5));
		ReverseArrow.ReverseArrowCheck check = arrowquest.new ReverseArrowCheck();
		check.onTurnReached(3);
		assertTrue(arrowquest.player.isQuestCompleted("reverse_arrow"));
	}

	/**
	 * Tests for finish.
	 */
	@Test
	public void testFinish() {
		ReverseArrow arrowquest = new ReverseArrow();
		arrowquest.addToWorld();
		arrowquest.player = PlayerTestHelper.createPlayer("bob");
		assertNotNull(arrowquest.player);
		arrowquest.finish(false, null);
		assertNotNull(arrowquest.player);
		arrowquest.finish(true, null);
		assertNull(arrowquest.player);
	}

}
