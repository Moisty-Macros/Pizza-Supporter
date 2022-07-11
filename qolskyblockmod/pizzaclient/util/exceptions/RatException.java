package qolskyblockmod.pizzaclient.util.exceptions;

import qolskyblockmod.pizzaclient.util.*;

public class RatException extends RuntimeException
{
    public RatException() {
        super(FileUtil.getExecutorAndMethod() + " is a rat. Jar name: " + FileUtil.getJarName());
    }
}
