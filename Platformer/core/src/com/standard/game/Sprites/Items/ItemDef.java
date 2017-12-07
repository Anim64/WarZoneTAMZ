package com.standard.game.Sprites.Items;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Standard on 07.12.2017.
 */

public class ItemDef
{
    public Vector2 position;
    public Class<?> type;

    public ItemDef(Vector2 position, Class<?> type)
    {
        this.position = position;
        this.type =type;
    }
}
