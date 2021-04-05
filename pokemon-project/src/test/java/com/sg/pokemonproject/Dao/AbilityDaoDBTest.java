package com.sg.pokemonproject.Dao;

import com.sg.pokemonproject.Entity.Ability;
import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AbilityDaoDBTest {

    @Autowired
    AbilityDao abilityDao;

    public AbilityDaoDBTest(){}

    @Before
    public void setUp() {
        List<Ability> abilities = abilityDao.getAll();
        for(Ability ability : abilities){
            abilityDao.deleteAbilityById(ability.getId());
        }
    }

    @Test
    public void testAddAndGetAbility() {
        Ability ability = new Ability();
        ability.setAP(5);
        ability.setAttack(5);
        ability.setName("Test Attack");
        ability = abilityDao.addAbility(ability);

        Ability fromDao = abilityDao.getAbilityById(ability.getId());

        TestCase.assertEquals(ability, fromDao);
    }

    @Test
    public void getAll() {
        Ability ability = new Ability();
        ability.setAP(5);
        ability.setAttack(5);
        ability.setName("Test Attack");
        ability = abilityDao.addAbility(ability);

        Ability ability2 = new Ability();
        ability2.setAP(6);
        ability2.setAttack(5);
        ability2.setName("Another Test Attack");
        ability2 = abilityDao.addAbility(ability2);

        List<Ability> abilities = abilityDao.getAll();
        TestCase.assertEquals(2, abilities.size());
        TestCase.assertTrue(abilities.contains(ability));
        TestCase.assertTrue(abilities.contains(ability2));
    }

    @Test
    public void updateAbility() {
        Ability ability = new Ability();
        ability.setAP(5);
        ability.setAttack(5);
        ability.setName("Test Attack");
        ability = abilityDao.addAbility(ability);

        Ability fromDao = abilityDao.getAbilityById(ability.getId());
        TestCase.assertEquals(ability, fromDao);

        ability.setName("Updated Name");
        abilityDao.updateAbility(ability);
        TestCase.assertNotSame(ability, fromDao);

        fromDao = abilityDao.getAbilityById(ability.getId());
        TestCase.assertEquals(ability, fromDao);
    }

    @Test
    public void deleteAbilityById() {
        Ability ability = new Ability();
        ability.setAP(5);
        ability.setAttack(5);
        ability.setName("Test Attack");
        ability = abilityDao.addAbility(ability);

        Ability fromDao = abilityDao.getAbilityById(ability.getId());
        abilityDao.deleteAbilityById(ability.getId());

        fromDao = abilityDao.getAbilityById(ability.getId());

        TestCase.assertNull(fromDao);
    }


}