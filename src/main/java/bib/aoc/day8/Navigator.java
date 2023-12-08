package bib.aoc.day8;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Navigator {

    private int countSteps=0;

    private int nextElement=-1;

    /**
     * visited[n° noeud depart][n° mvt appliqué]=n° step où on applique mvt sur le noeud
     */
    private int[][] visited;

    @Getter
    private final char[] directions;
    @Setter
    private int[][] map;
    private boolean cycle=false;
    private int nodeCycle;

    private int directionCycle;

    private int stepCycle;


    public Navigator(String line){
        directions=line.toCharArray();
    }

    public String nextRef(Node node) {
        var direction=nextDirection();
        return findNextNodeWithDirection(node, direction);
    }

    private String findNextNodeWithDirection(Node node, char direction) {
        return isRight(direction) ? node.right() : node.left();
    }

    private boolean isRight(char direction) {
        return direction=='R';
    }

    private char nextDirection() {
        countSteps++;
        increaseNextElement();
        return directions[nextElement];
    }

    private void increaseNextElement() {
        nextElement++;
        if (nextElement==directions.length){
            nextElement=0;
        }
    }

    public int countSteps() {
        return countSteps;
    }


    public boolean nextRefAndCheckZ(int[] nodes, boolean[] mapZ) {
        var direction=nextDirection();
        boolean isZ=true;
        for (int i = 0; i < nodes.length; i++) {
            var newNode=findNextNodeWithDirectionAndMap(nodes[i], direction);
            nodes[i]=newNode;
            isZ=isZ&&mapZ[newNode];
        }
        return isZ;
    }

    private int findNextNodeWithDirectionAndMap(int nodeId, char direction) {
        return isRight(direction) ? map[nodeId][1] : map[nodeId][0];
    }

    public void init(){
        visited=new int[map.length][getDirections().length];
        cycle=false;
        countSteps=0;
        nextElement=-1;
    }

    public int nextNodeAndfindCycle(int node, boolean[] nodesWithZ) {
        var direction=nextDirection();
        if (visited[node][nextElement]!=0){
            this.cycle=true;
            this.nodeCycle=node;
            this.directionCycle=nextElement;
            this.stepCycle=countSteps;
        }else{
            visited[node][nextElement]=countSteps;
        }
        var newNode=findNextNodeWithDirectionAndMap(node, direction);
        if (nodesWithZ[newNode]){
            log.info(STR."Trouvé un Z (\{newNode} à \{countSteps}");
        }

        return newNode;

    }

    public boolean hasCycle() {
        return cycle;
    }

    public String printCycle() {
        return STR."Node \{nodeCycle} avec direction \{directionCycle} trouvé aux étapes \{visited[nodeCycle][directionCycle]} et \{stepCycle}";
    }

    public int getCycle() {
        return stepCycle-visited[nodeCycle][directionCycle];
    }
}
