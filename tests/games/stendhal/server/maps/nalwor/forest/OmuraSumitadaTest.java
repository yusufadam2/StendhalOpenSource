package games.stendhal.server.maps.nalwor.forest;

import static org.junit.Assert.*;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class OmuraSumitadaTest extends ZonePlayerAndNPCTestImpl {

	private static final String ZONE_NAME = "0_nalwor_forest_n";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();

		setupZone(ZONE_NAME);
	}

	public OmuraSumitadaTest() {
		setNpcNames("Omura Sumitada");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new Dojo(), ZONE_NAME);
	}
	
	// 1. Test Hi and Bye
	@Test
	public void testHiandBye() {
		final SpeakerNPC npc = getNPC("Omura Sumitada");
		final Engine en = npc.getEngine();
		
		assertTrue(en.step(player, "hi"));
		assertEquals("This is the assassins' dojo.", getReply(npc));

		assertTrue(en.step(player, "bye"));
		assertEquals("Bye.", getReply(npc));
	}
	
	// 2. Test fee when atkLevel is too high
	@Test
	public void testTooHighAtkToTrain() {
		final SpeakerNPC npc = getNPC("Omura Sumitada");
		final Engine en = npc.getEngine();
		
		// Setting high atk strength (player.getAtk() >= player.getLevel)
		player.setLevel(10);
		player.setAtk(10);
		
		assertTrue(en.step(player, "hi"));
		assertEquals("This is the assassins' dojo.", getReply(npc));
		assertTrue(en.step(player, "fee"));
		assertEquals("At your level of experience, your attack strength is too high to train here at this time.", getReply(npc));
		assertTrue(en.step(player, "bye"));
		assertEquals("Bye.", getReply(npc));
		
		// Test again but more stringent
		player.setLevel(10);
		player.setAtk(11);
		
		assertTrue(en.step(player, "hi"));
		assertEquals("This is the assassins' dojo.", getReply(npc));
		assertTrue(en.step(player, "fee"));
		assertEquals("At your level of experience, your attack strength is too high to train here at this time.", getReply(npc));
		assertTrue(en.step(player, "bye"));
		assertEquals("Bye.", getReply(npc));
	}
		
	// 3. Test fee when atkLevel is valid
	@Test
	public void testvalidAtktoTrain() {
		final SpeakerNPC npc = getNPC("Omura Sumitada");
		final Engine en = npc.getEngine();
		
		// Setting low atk strength (player.getAtk() < player.getLevel)
		player.setLevel(11);
		player.setAtk(10);
		
		assertTrue(en.step(player, "hi"));
		assertEquals("This is the assassins' dojo.", getReply(npc));
		assertTrue(en.step(player, "fee"));
		assertEquals("The fee to #train for your skill level is " + 625 + " money.", getReply(npc));
		assertTrue(en.step(player, "bye"));
		assertEquals("Bye.", getReply(npc));
		
		// Test again but more relaxed
		player.setLevel(18);
		player.setAtk(10);
		
		assertTrue(en.step(player, "hi"));
		assertEquals("This is the assassins' dojo.", getReply(npc));
		assertTrue(en.step(player, "fee"));
		assertEquals("The fee to #train for your skill level is " + 625 + " money.", getReply(npc));
		assertTrue(en.step(player, "bye"));
		assertEquals("Bye.", getReply(npc));
	}

}
