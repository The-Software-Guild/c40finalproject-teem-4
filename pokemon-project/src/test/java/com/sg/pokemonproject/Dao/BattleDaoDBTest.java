package com.sg.pokemonproject.Dao;

import com.sg.pokemonproject.Entity.Ability;
import com.sg.pokemonproject.Entity.Battle;
import com.sg.pokemonproject.Entity.Type;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BattleDaoDBTest {
    @Autowired
    BattleDao battleDao;

    @Before
    public void setUp(){
        List<Battle> battles = battleDao.getAll();
        for(Battle battle : battles){
            battleDao.deleteBattleById(battle.getId());
        }
    }

    @Test
    public void testAddAndGetBattle(){
        Battle battle = new Battle();
        battle.setUserId(1);
        battle.setOpponentId(1);
        battle.setUserPokemonId(1);
        battle = battleDao.addBattle(battle);

        Battle fromDao = battleDao.getBattleById(battle.getId());
        assertEquals(battle, fromDao);
    }

    @Test
    public void testGetAllBattles(){
        Battle battle = new Battle();
        battle.setUserId(1);
        battle.setOpponentId(1);
        battle.setUserPokemonId(1);
        battle = battleDao.addBattle(battle);

        Battle battle2 = new Battle();
        battle2.setUserId(2);
        battle2.setOpponentId(2);
        battle2.setUserPokemonId(2);
        battle2 = battleDao.addBattle(battle2);

        List<Battle> battles = battleDao.getAll();
        assertEquals(2, battles.size());
        assertTrue(battles.contains(battle));
        assertTrue(battles.contains(battle2));
    }

    @Test
    public void testDeleteBattleById() {
        Battle battle = new Battle();
        battle.setUserId(1);
        battle.setOpponentId(1);
        battle.setUserPokemonId(1);
        battle = battleDao.addBattle(battle);

        Battle fromDao = battleDao.getBattleById(battle.getId());
        assertEquals(battle, fromDao);

        battleDao.deleteBattleById(battle.getId());
        fromDao = battleDao.getBattleById(battle.getId());
        assertNull(fromDao);
    }

    @Test
    public void testDeleteBattleByUserId() {
        Battle battle = new Battle();
        battle.setUserId(1);
        battle.setOpponentId(1);
        battle.setUserPokemonId(1);
        battle = battleDao.addBattle(battle);

        Battle battle2 = new Battle();
        battle2.setUserId(1);
        battle2.setOpponentId(2);
        battle2.setUserPokemonId(2);
        battle2 = battleDao.addBattle(battle2);

        Battle fromDao = battleDao.getBattleById(battle.getId());
        assertEquals(battle, fromDao);
        Battle fromDao2 = battleDao.getBattleById(battle2.getId());
        assertEquals(battle2, fromDao2);

        battleDao.deleteBattleByUserId(battle.getUserId());
        fromDao = battleDao.getBattleById(battle.getId());
        assertNull(fromDao);
        fromDao2 = battleDao.getBattleById(battle2.getId());
        assertNull(fromDao2);
    }
}