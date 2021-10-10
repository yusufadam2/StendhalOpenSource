package games.stendhal.server.maps.quests;

import static org.junit.Assert.*;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.semos.library.HistorianGeographerNPC;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;

public class MeetZynnTest {

	private Player player = null;
	private SpeakerNPC npc = null;
	private Engine en = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
	}

	@Before
	public void setUp() {
		StendhalRPZone zone = new StendhalRPZone("admin_test");
		new HistorianGeographerNPC().configureZone(zone, null);
		npc = SingletonRepository.getNPCList().get("Zynn Iwuhos");
		en = npc.getEngine();

		AbstractQuest quest = new MeetZynn();
		quest.addToWorld();

		player = PlayerTestHelper.createPlayer("bob");
	}
	
	@Test
	public void testQuest() {
		player.setXP(1000);
		int oldxp = player.getXP();

		npc = SingletonRepository.getNPCList().get("Zynn Iwuhos");
		en = npc.getEngine();

		en.step(player, "hi");
		
		en.step(player, "history");
		assertEquals("At present, there are two significant powers on Faiumoni; the Deniran Empire, and the dark legions of Blordrough. Blordrough has recently conquered the south of the island, seizing several steel mines and a large gold mine. At present, Deniran still controls the central and northern parts of Faiumoni, including several gold and mithril mines.", getReply(npc));
		assertEquals(oldxp + 5, player.getXP());
		oldxp = player.getXP();
		
		en.step(player, "news");
		assertEquals("The Deniran Empire is currently seeking adventurers to sign on as mercenaries with their army to retake southern Faiumoni from the forces of Blordrough. Unfortunately Blordrough is still holding out against everything the Empire can throw at him.", getReply(npc));
		assertEquals(oldxp + 5, player.getXP());
		oldxp = player.getXP();
		
		en.step(player, "geography");
		assertEquals("Let's talk about the different #places you can visit on Faiumoni! I can also help you #get and #use a map, or give you advice on using the psychic #SPS system.", getReply(npc));
		assertEquals(oldxp + 5, player.getXP());
		oldxp = player.getXP();

		en.step(player, "places");
		assertEquals("The most important locations on #Faiumoni are #Or'ril Castle, #Semos, #Ados, #Nalwor, and of course #Deniran City.", getReply(npc));
		assertEquals(oldxp + 5, player.getXP());
		oldxp = player.getXP();
		
		en.step(player, "Faiumoni");
		assertEquals("Faiumoni is the island on which you stand! You've probably already noticed the mountains to the north. There is also a large desert in the middle of the island, and of course the river which bisects it, just below #Or'ril Castle to the south.", getReply(npc));
		assertEquals(oldxp + 5, player.getXP());
		oldxp = player.getXP();
		
		en.step(player, "Semos");
		assertEquals("Semos is our town where you are right now. We're on the north side of Faiumoni, with a population of about 40-50.", getReply(npc));
		assertEquals(oldxp + 5, player.getXP());
		oldxp = player.getXP();
		
		en.step(player, "Ados");
		assertEquals("Ados is an important coastal city to the east of us here in #Semos, where merchants bring trade from #Deniran. It's widely considered to be one of the Empire's most important shipping routes.", getReply(npc));
		assertEquals(oldxp + 5, player.getXP());
		oldxp = player.getXP();
		
		en.step(player, "Or'ril");
		assertEquals("Or'ril Castle is one of a series of such castles built to defend the imperial road between #Ados and #Deniran. When in use, it housed a fighting force of around 60 swordsmen, plus ancillary staff. Now that most of the Empire's army has been sent south to fend off the invaders, the castle has been abandoned by the military. As far as I'm aware, it should be empty; I hear some rumours, though...", getReply(npc));
		assertEquals(oldxp + 5, player.getXP());
		oldxp = player.getXP();
		
		en.step(player, "Nalwor");
		assertEquals("Nalwor is an ancient elven city, built deep inside the forest to the southeast of us long before humans ever arrived on this island. Elves don't like mixing with other races, but we're given to understand that Nalwor was built to help defend their capital city of Teruykeh against an evil force.", getReply(npc));
		assertEquals(oldxp + 5, player.getXP());
		oldxp = player.getXP();
		
		en.step(player, "Deniran");
		assertEquals("The Empire's capital city of Deniran lies in the heart of Faiumoni, and is the main base of operations for the Deniran army. Most of the Empire's main trade routes with other countries originate in this city, then extending north through #Ados, and south to Sikhw. Unfortunately, the southern routes were been destroyed when Blordrough and his armies conquered Sikhw, some time ago now.", getReply(npc));
		assertEquals(oldxp + 5, player.getXP());
		oldxp = player.getXP();
		
		en.step(player, "use");
		assertEquals("Once you #get a map, there are three scales on which you need to understand it. Firstly, there are the map #levels, then you need to familiarize yourself with the #naming conventions for the different zones within those levels, and lastly you should learn how we describe a person's #positioning within a zone. We'll have you navigating around in no time!", getReply(npc));
		assertEquals(oldxp + 5, player.getXP());
		oldxp = player.getXP();
		
		en.step(player, "levels");
		assertEquals("Maps are split into levels according to the height of that particular area above or below the surface. Areas on the surface are at level 0. The level number is the first thing in a map's name. For instance, #Semos itself is at ground level, so it is level 0; its map is called \"0_semos_city\". The first level of the dungeon beneath us is at level -1, so its map is called \"-1_semos_dungeon\". You should note, though, that a map of a building's interior will usually have the level at the end of the name instead, with \"int\" (for \"interior\") at the start. For instance, the upstairs floor of the tavern would be mapped out as \"int_semos_tavern_1\".", getReply(npc));
		assertEquals(oldxp + 5, player.getXP());
		oldxp = player.getXP();
		
		en.step(player, "naming");
		assertEquals("Each map is usually split up into \"sets\" of zones, with a central feature that is used as a reference point. The zones surrounding this central zone are named by the direction in which they lie from it. For instance, from the central zone \"0_semos_city\", you can travel west to the old village at \"0_semos_village_w\", or you could travel two zones north and one west to the mountains at \"0_semos_mountain_n2_w\".", getReply(npc));
		assertEquals(oldxp + 5, player.getXP());
		oldxp = player.getXP();
		
		en.step(player, "positioning");
		assertEquals("Positioning within a zone is simply described in terms of co-ordinates. We conventionally take the top-left corner (that is, the northwest corner) to be the origin point with co-ordinates (0, 0). The first co-ordinate ('x') increases as you move to the right (that is, east) within the zone, and the second co-ordinate ('y') increases as you move down (that is, south).", getReply(npc));
		assertEquals(oldxp + 5, player.getXP());
		oldxp = player.getXP();
		
		en.step(player, "get");
		assertEquals("You can get a map of Stendhal at #https://stendhalgame.org/world/atlas.html if you want one. Careful you don't spoil any surprises for yourself, though!", getReply(npc));
		assertEquals(oldxp + 5, player.getXP());
		oldxp = player.getXP();
		
		en.step(player, "SPS");
		assertEquals("SPS stands for Stendhal Positioning System; you can ask #Io in the Temple about the exact details of how it works. Essentially, it allows you to ascertain the exact current location of yourself or your friends at any time.", getReply(npc));
		assertEquals(oldxp + 5, player.getXP());
		oldxp = player.getXP();
		
		en.step(player, "Io");
		assertEquals("Her full name is \"Io Flotto\". She spends most of her time in the Temple, um, floating. She may seem weird, but her \"intuition\" works far better than any mere compass, as I can vouch.", getReply(npc));
		assertEquals(oldxp + 5, player.getXP());
		

	}

}
