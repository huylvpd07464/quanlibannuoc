/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package duan.qlbn.dao;

import java.util.List;

/**
 *
 * @author HP
 */
public abstract class QLBNDAO<EntityType,KeyType> {

    public abstract void insert(EntityType entity);

    public abstract void update(EntityType entity);

    public abstract void delete(KeyType id);

    public abstract List<EntityType> selectAll();

    public abstract EntityType selectByID(KeyType id);

    public abstract List<EntityType> selectBySql(String sql, Object... args);

}
