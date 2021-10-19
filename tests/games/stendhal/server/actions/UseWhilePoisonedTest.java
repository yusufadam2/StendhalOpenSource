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
package games.stendhal.server.actions;

import static games.stendhal.common.constants.Actions.BASEITEM;
import static games.stendhal.common.constants.Actions.BASEOBJECT;
import static games.stendhal.common.constants.Actions.BASESLOT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.item.ConsumableItem;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.item.StackableItem;
import games.stendhal.server.entity.mapstuff.chest.Chest;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.entity.status.PoisonAttacker;
import games.stendhal.server.entity.status.StatusType;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
import marauroa.common.game.RPAction;
import utilities.PlayerTestHelper;
import utilities.RPClass.ChestTestHelper;

public class UseWhilePoisonedTest {

	@BeforeClass
	public static void setUpBeforeClass() {
		MockStendlRPWorld.get();
		Log4J.init();
	}

	/**
	 * Tests for OnItemInBagWhilePoisoned.
	 */
	@Test
	public void testOnItemInBagWhilePoisoned() {
		MockStendlRPWorld.get();
		final UseAction ua = new UseAction();
		final Player player = PlayerTestHelper.createPlayer("bob");
		
		// Player poisoned check
		final String poisontype = "greater poison";
		final ConsumableItem poison = (ConsumableItem) SingletonRepository.getEntityManager().getItem(poisontype);
		final PoisonAttacker poisoner = new PoisonAttacker(100, poison);
		poisoner.onAttackAttempt(player, SingletonRepository.getEntityManager().getCreature("snake"));
		assertTrue(player.hasStatus(StatusType.POISONED));
		
		Item cheese = SingletonRepository.getEntityManager().getItem("cheese");
		player.equip("bag", cheese);
		final StendhalRPZone zone = new StendhalRPZone("zone");
		zone.add(player);
		RPAction action = new RPAction();
		action.put(BASEITEM, cheese.getID().getObjectID());
		action.put(BASEOBJECT, player.getID().getObjectID());
		action.put(BASESLOT, "bag");
		assertTrue(player.isEquipped("cheese"));
		ua.onAction(player, action);
		assertTrue(player.isEquipped("cheese"));

	}

	/**
	 * Tests for ontemInBagWithTwoCheeseWhilePoisoned.
	 */
	@Test
	public void testOnItemInBagWithTwoCheeseWhilePoisoned() {
		MockStendlRPWorld.get();
		final UseAction ua = new UseAction();
		final Player player = PlayerTestHelper.createPlayer("bob");
		
		// Player poisoned check
		final String poisontype = "greater poison";
		final ConsumableItem poison = (ConsumableItem) SingletonRepository.getEntityManager().getItem(poisontype);
		final PoisonAttacker poisoner = new PoisonAttacker(100, poison);
		poisoner.onAttackAttempt(player, SingletonRepository.getEntityManager().getCreature("snake"));
		assertTrue(player.hasStatus(StatusType.POISONED));
		
		final StackableItem cheese = (StackableItem) SingletonRepository.getEntityManager().getItem("cheese");
		cheese.setQuantity(2);
		player.equip("bag", cheese);
		final StendhalRPZone zone = new StendhalRPZone("zone");
		zone.add(player);
		RPAction action = new RPAction();
		action.put(BASEITEM, cheese.getID().getObjectID());
		action.put(BASEOBJECT, player.getID().getObjectID());
		action.put(BASESLOT, "bag");
		assertTrue(player.isEquipped("cheese"));
		ua.onAction(player, action);
		assertTrue(player.isEquipped("cheese"));
		assertEquals(2, cheese.getQuantity());

	}

	/**
	 * Tests for onIteminChestWhilePoisoned.
	 */
	@Test
	public void testOnIteminChestWhilePoisoned() {
		MockStendlRPWorld.get();
		ChestTestHelper.generateRPClasses();
		final UseAction ua = new UseAction();
		final Player player = PlayerTestHelper.createPlayer("bob");
		
		// Player poisoned check
		final String poisontype = "greater poison";
		final ConsumableItem poison = (ConsumableItem) SingletonRepository.getEntityManager().getItem(poisontype);
		final PoisonAttacker poisoner = new PoisonAttacker(100, poison);
		poisoner.onAttackAttempt(player, SingletonRepository.getEntityManager().getCreature("snake"));
		assertTrue(player.hasStatus(StatusType.POISONED));
		
		final Chest chest = new Chest();
		Item cheese = SingletonRepository.getEntityManager().getItem("cheese");
		chest.add(cheese);
		final StendhalRPZone zone = new StendhalRPZone("zone");
		zone.collisionMap.clear();
		player.setPosition(1, 1);
		chest.setPosition(1, 2);
		zone.add(player);
		zone.add(chest);
		chest.open();
		RPAction action = new RPAction();
		action.put(BASEITEM, cheese.getID().getObjectID());
		action.put(BASEOBJECT, chest.getID().getObjectID());
		action.put(BASESLOT, "content");
		assertFalse(player.has("eating"));
		ua.onAction(player, action);
		assertFalse(player.has("eating"));
	}

}
