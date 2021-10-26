package games.stendhal.server.entity.creature;

//import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

import org.junit.Test;
import static org.hamcrest.Matchers.*;
import games.stendhal.common.constants.Nature;
import games.stendhal.server.core.engine.SingletonRepository;

public class CentaurTest {
	/**
	 * Tests for solar centaur.
	 */
	@Test
	public void solarCentaurSusceptibiliy() {
		//Creature solarCentaur = new Creature();
		final Creature solarCentaur = SingletonRepository.getEntityManager().getCreature("solar centaur");
		System.out.println(solarCentaur);
		assertThat(solarCentaur.getSusceptibility(Nature.ICE), greaterThan(solarCentaur.getSusceptibility(Nature.FIRE)));
		assertTrue(solarCentaur.getSusceptibility(Nature.FIRE)<1);
		assertTrue(solarCentaur.getSusceptibility(Nature.ICE)>1);
	}
	
	/**
	 * Tests for glacier centaur.
	 */
	@Test
	public void glacierCentaurSusceptibiliy() {
		//Creature solarCentaur = new Creature();
		final Creature glacierCentaur = SingletonRepository.getEntityManager().getCreature("glacier centaur");
		System.out.println(glacierCentaur);
		assertThat(glacierCentaur.getSusceptibility(Nature.FIRE), greaterThan(glacierCentaur.getSusceptibility(Nature.ICE)));
		assertTrue(glacierCentaur.getSusceptibility(Nature.FIRE)>1);
		assertTrue(glacierCentaur.getSusceptibility(Nature.ICE)<1);
	}
	
}
