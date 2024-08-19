package Tests;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.inno.course.player.data.DataProvider;
import ru.inno.course.player.model.Player;
import ru.inno.course.player.service.PlayerServiceImpl;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerServiceImplTest {
    private PlayerServiceImpl playerService;
    private final DataProvider DataProvider;

    public PlayerServiceImplTest(ru.inno.course.player.data.DataProvider dataProvider) {
        DataProvider = dataProvider;
    }

    @BeforeEach
    void setUp() {

        playerService = new PlayerServiceImpl(DataProvider);

        // Инициализация игроков и никнеймов
        playerService.players = new HashMap<>();
        playerService.nicknames = new HashSet<>();
    }


    @Test
    void testCreatePlayer() {
        String nickname = "Player1";
        int playerId = playerService.createPlayer(nickname);
        //- создать игрока
        assertEquals(1, playerId);
        assertTrue(playerService.getPlayers().stream().anyMatch(p -> p.getNick().equals(nickname)));
    }

    @Test
    void testCreatePlayerWithExistingNickname() {
        String nickname = "Player1";
        playerService.createPlayer(nickname);

        assertThrows(IllegalArgumentException.class, () -> playerService.createPlayer(nickname));
    }

    @Test
    void testGetPlayerById() {
        String nickname = "Player1";
        int playerId = playerService.createPlayer(nickname);
        //получить игрока по id,

        Player foundPlayer = playerService.getPlayerById(playerId);
        assertEquals(nickname, foundPlayer.getNick());
    }

    @Test
    void testGetNonExistentPlayerById() {
        assertThrows(NoSuchElementException.class, () -> playerService.getPlayerById(999));
        //получить игрока по id, которого нет
    }

    @Test
    void testDeletePlayer() {
        String nickname = "Player1";
        int playerId = playerService.createPlayer(nickname);
        //удалить игрока
        Player deletedPlayer = playerService.deletePlayer(playerId);
        assertEquals(nickname, deletedPlayer.getNick());
        assertThrows(NoSuchElementException.class, () -> playerService.getPlayerById(playerId));
    }

    @Test
    void testDeleteNonExistentPlayer() {
        assertThrows(NoSuchElementException.class, () -> playerService.deletePlayer(999));
        //удалить игрока, которого нет
    }
}


