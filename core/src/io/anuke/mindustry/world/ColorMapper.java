package io.anuke.mindustry.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectIntMap;
import io.anuke.mindustry.game.Content;
import io.anuke.mindustry.game.Team;
import io.anuke.mindustry.type.ContentList;

public class ColorMapper implements ContentList{
    private static IntMap<Block> blockMap = new IntMap<>();
    private static ObjectIntMap<Block> colorMap = new ObjectIntMap<>();
    private static Color tmpColor = new Color();

    public static Block getByColor(int color){
        return blockMap.get(color);
    }

    public static int getBlockColor(Block block){
        return colorMap.get(block, 0);
    }

    public static int colorFor(Block floor, Block wall, Team team, int elevation){
        int color = wall.breakable ? team.intColor : getBlockColor(wall);
        if(color == 0) color = ColorMapper.getBlockColor(floor);
        if(elevation > 0){
            float mul = 1.1f + elevation / 4f;
            tmpColor.set(color);
            tmpColor.mul(mul, mul, mul, 1f);
            color = Color.rgba8888(tmpColor);
        }
        return color;
    }

    @Override
    public void load(){
        for(Block block : Block.all()){
            int color = Color.rgba8888(block.minimapColor);
            if(color == 0) continue; //skip blocks that are not mapped

            blockMap.put(color, block);
            colorMap.put(block, color);
        }
    }

    @Override
    public Array<? extends Content> getAll(){
        return new Array<>();
    }
}
