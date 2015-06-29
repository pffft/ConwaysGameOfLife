
/**
 * Based on John Conway's rules from his Game of Life.
 * <p>
 * <ol>
 * <li>Any live cell with fewer than two live neighbours dies, as if caused by under-population.</li>
 * <li>Any live cell with two or three live neighbours lives on to the next generation.</li>
 * <li>Any live cell with more than three live neighbours dies, as if by overcrowding.</li>
 * <li>Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.</li>
 *</ol>
 * 
 * @author PFFFT Productions
 * @version 20140110
 */
public class AppMain implements Runnable
{
    private boolean[][] array;
    private boolean[][] arrayStep2;

    public int updatesPerSecond = 1;
    public boolean running = true;
    public AppMain(int size)
    {
        array = new boolean [size][size];
        arrayStep2 = new boolean [size][size];

        //Spawns a square
        array[12][12] = true;
        array[12][13] = true;
        array[13][12] = true;
        array[13][13] = true;

        //makes sure they despawn
        array[5][5] = true;

        //Should create a blinker
        array[7][7] =true;
        array[8][7] = true;
        array[9][7] = true;
    }

    /**
     * To be used to set the flagged array.
     */
    private void checkLocation(int x, int y)
    {
        int localCount = 0;

        if (y-1>=0 && array[x][y-1])                                localCount++;   //Top
        if (y-1>=0 && x+1< array.length && array[x+1][y-1])         localCount++;   //Top Right
        if (x+1<array.length && array[x+1][y])                      localCount++;   //Right
        if (x+1<array.length && y+1<array.length && array[x+1][y+1])localCount++;   //Botton Right
        if (y+1<array.length && array[x][y+1])                      localCount++;   //Bottom
        if (y+1<array.length && x-1>=0 && array[x-1][y+1])          localCount++;   //Bottom Left
        if (x-1>=0 && array[x-1][y])                                localCount++;   //Left
        if (x-1>=0 && y-1>=0 && array[x-1][y-1])                    localCount++;   //Top Left

        if (localCount == 3)                        arrayStep2[x][y] = true;
        else if (array[x][y] && localCount == 2)    arrayStep2[x][y] = true;
        else                                        arrayStep2[x][y] = false;
    }

    public void update()
    {
        for (int x = 0; x < array.length; x++){
            for (int y = 0; y < array.length; y++){
                checkLocation(x,y);
            }
        }
        array = arrayStep2;
        arrayStep2 = new boolean[array.length][array.length];
    }

    public String toString()
    {
        String ret = "";
        for (int i = 0; i < array.length; i++){
            for (int j = 0; j < array[0].length; j++){
                ret += array[i][j] + "\t";
            }
            ret += "\n";
        }
        return ret;
    }

    public boolean[][] getArray(){return array;}

    public void run() {
        while (true) {
            if (running) {
                update();
            } else {
                System.out.println("Simulation updated");
            }
            try { Thread.sleep(1000/updatesPerSecond); } catch (Exception E) {E.printStackTrace();}
        }
    }
}

