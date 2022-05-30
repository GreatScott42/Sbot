package student;

import snakes.Bot;
import snakes.Coordinate;
import snakes.Direction;
import snakes.Snake;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

public class RandomBot implements Bot {
    private static final Direction[] DIRECTIONS = new Direction[] {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};

    @Override
    public Direction chooseDirection(Snake snake, Snake opponent, Coordinate mazeSize, Coordinate apple) {
        Random random = new Random();
        Direction randomDir = DIRECTIONS[random.nextInt(DIRECTIONS.length)];


        //Obtiene la coordenada de la parte después de la cabeza de la serpiente para no ir hacia atrás
        Coordinate afterHeadNotFinal = null;
        if (snake.body.size() >= 2) {
            Iterator<Coordinate> it = snake.body.iterator();
            it.next();
            afterHeadNotFinal = it.next();
        }

        final Coordinate afterHead = afterHeadNotFinal;

        while(snake.getHead().moveTo(randomDir).equals(afterHead) || //Evita que choque con sí misma
                !snake.getHead().moveTo(randomDir).inBounds(mazeSize)  || //Evita que choque contra los bordes del tablero
                opponent.elements.contains(snake.getHead().moveTo(randomDir)) //Evita que choque contra cualquier parte del enemigo
        ){
            randomDir = DIRECTIONS[random.nextInt(DIRECTIONS.length)]; //En caso de que alguna de las condiciones se cumpla, busca un nuevo movimiento aleatorio

            //System.out.println(Math.abs(apple.x - snake.getHead().x) + Math.abs(apple.y - snake.getHead().y));
        }
        //ordena las direcciones
        Arrays.sort(DIRECTIONS, new Comparator<Direction>() {
            @Override
            //las ordena comparando
            public int compare(Direction o1, Direction o2) {
                return Integer.compare(Manhattan(snake.getHead().moveTo(o1), apple), Manhattan(snake.getHead().moveTo(o2), apple));
            }
        });
        Direction finaldir = null;
        finaldir = DIRECTIONS[0];
        //return randomDir; //retorna el movimiento
        return  finaldir;
    }
    private static int Manhattan(Coordinate a,Coordinate b){
        return Math.abs(b.x - a.x) + Math.abs(b.y - a.y);
    }


}
