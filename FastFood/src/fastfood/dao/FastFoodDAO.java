/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fastfood.dao;

import java.util.List;

/**
 *
 * @author truong
 * @param <EntityType>
 * @param <KeyType>
 */
abstract public class FastFoodDAO<EntityType, KeyType> {
    abstract  public void insert(EntityType enity);
    abstract public void update(EntityType enity);
    abstract public void delete(KeyType key);
    abstract public EntityType selectByID(KeyType key);
    abstract  public List<EntityType> getAllDate();
    abstract protected  List<EntityType> getById(String sql,Object...args);
}
