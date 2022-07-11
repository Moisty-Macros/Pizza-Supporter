package qolskyblockmod.pizzaclient.util.rotation.rotaters;

import qolskyblockmod.pizzaclient.*;

public interface IRotater
{
    void rotate();
    
    void add();
    
    default void shutdown() {
        PizzaClient.rotater = null;
    }
}
