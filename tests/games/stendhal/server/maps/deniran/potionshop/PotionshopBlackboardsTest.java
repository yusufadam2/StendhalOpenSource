package games.stendhal.server.maps.deniran.potionshop;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.NPC;
import games.stendhal.server.maps.MockStendlRPWorld;
import games.stendhal.server.maps.deniran.cityinterior.potionsshop.*;
import marauroa.common.Log4J;

public class PotionshopBlackboardsTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Log4J.init();
		MockStendlRPWorld.get();
	}

	/**
	 * Tests for configureZone.
	 */
	@Test
	public void testConfigureZone() {

		SingletonRepository.getRPWorld();
		final PotionsDealerNPC potionConfigurator = new PotionsDealerNPC();

		final StendhalRPZone zone = new StendhalRPZone("testzone");
		potionConfigurator.configureZone(zone, null);
		assertFalse(zone.getNPCList().isEmpty());
		final NPC potDealer = zone.getNPCList().get(0);
		assertThat(potDealer.getName(), is("Wanda"));
		final String buys = zone.getEntityAt(5, 6).getTitle();
		assertThat(buys, is("blackboard"));
		final String sells = zone.getEntityAt(10, 6).getTitle();
		assertThat(sells, is("blackboard"));
	}
}
