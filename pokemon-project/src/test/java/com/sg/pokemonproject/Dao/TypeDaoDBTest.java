package com.sg.pokemonproject.Dao;

import com.sg.pokemonproject.Entity.Type;
import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TypeDaoDBTest extends TestCase{

    @Autowired
    TypeDao typeDao;

    public TypeDaoDBTest(){}

    @Before
    public void setUp(){
        List<Type> types = typeDao.getAll();
        for(Type type : types){
            typeDao.deleteTypeById(type.getId());
        }
    }

    @Test
    public void testAddAndGetType(){
        Type type = new Type();
        type.setName("Test Type");
        type = typeDao.addType(type);

        Type fromDao = typeDao.getTypeById(type.getId());

        TestCase.assertEquals(type, fromDao);
    }

    @Test
    public void testGetAllTypes(){
        Type type = new Type();
        type.setName("Test Type");
        type = typeDao.addType(type);

        Type type2 = new Type();
        type2.setName("Another test type");
        type2 = typeDao.addType(type2);

        List<Type> types = typeDao.getAll();

        TestCase.assertEquals(2, types.size());
        TestCase.assertTrue(types.contains(type));
        TestCase.assertTrue(types.contains(type2));
    }

    @Test
    public void testUpdateType(){
        Type type = new Type();
        type.setName("Test Type");
        type = typeDao.addType(type);

        Type fromDao = typeDao.getTypeById(type.getId());
        TestCase.assertEquals(type, fromDao);

        type.setName("Updated Name");
        typeDao.updateType(type);
        TestCase.assertNotSame(type, fromDao);

        fromDao = typeDao.getTypeById(type.getId());
        TestCase.assertEquals(type, fromDao);
    }

    @Test
    public void testDeleteType(){
        Type type = new Type();
        type.setName("Test Type");
        type = typeDao.addType(type);

        Type fromDao = typeDao.getTypeById(type.getId());
        TestCase.assertEquals(type, fromDao);

        typeDao.deleteTypeById(type.getId());

        fromDao = typeDao.getTypeById(type.getId());

        TestCase.assertNull(fromDao);
    }

}