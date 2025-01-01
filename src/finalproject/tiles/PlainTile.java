package finalproject.tiles;

import finalproject.system.TileType;


public class PlainTile extends finalproject.system.Tile {

        public PlainTile() {
            this.type = TileType.Plain;
            this.distanceCost = 3;
            this.timeCost = 1;
            this.damageCost = 0;
        }

}



