package bib.aoc;

import bib.aoc.day8.Navigator;
import bib.aoc.day8.Node;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.*;

@Slf4j
public final class Day8 extends Puzzle {

    @Override
    protected PuzzlePart part1() {
        return lines -> {
            var allLines=lines.toList();
            Navigator navigator=new Navigator(allLines.getFirst());
            Map<String, Node> refrencedNodes = initReferencedNodes(allLines);
            var node=refrencedNodes.get("AAA");
            while(!"ZZZ".equals(node.id())){
                node=refrencedNodes.get(navigator.nextRef(node));
            }
            
            return String.valueOf(navigator.countSteps());
        };
    }

    private static Map<String, Node> initReferencedNodes(List<String> allLines) {
        Map<String, Node> refrencedNodes=new HashMap<>();
        for (int i = 2; i < allLines.size(); i++) {
            var node=Node.of(allLines.get(i));
            refrencedNodes.put(node.id(), node);
        }
        return refrencedNodes;
    }


    @Override
    protected PuzzlePart part2() {
        return lines -> {
            var allLines=lines.toList();
            Navigator navigator=new Navigator(allLines.getFirst());
            Map<String, Node> refrencedNodes = initReferencedNodes(allLines);
            var nodeCount=refrencedNodes.size();
            int[][] map=new int[nodeCount][2];
            boolean[] nodesWithZ=new boolean[nodeCount];
            Map<String, Integer> nodesIndex=new HashMap<>();
            var id=0;
            for(String name:refrencedNodes.keySet()){
                nodesWithZ[id]=name.endsWith("Z");
                nodesIndex.put(name, id);
                id++;
            }
            log.info(nodesIndex.toString());
            for(Node node:refrencedNodes.values()){
                map[nodesIndex.get(node.id())][0]=nodesIndex.get(node.left());
                map[nodesIndex.get(node.id())][1]=nodesIndex.get(node.right());
            }
            int[] nodes = refrencedNodes.entrySet().stream()
                    .filter(entry->entry.getKey().endsWith("A"))
                    .map(Map.Entry::getValue)
                    .map(Node::id)
                    .map(nodesIndex::get)
                    .mapToInt(Integer::intValue)
                    .toArray();
            navigator.setMap(map);
            List<BigInteger> cycles=new ArrayList<>();
            log.info(Arrays.toString(nodes));
            log.info("Start navigation");
            StringBuilder sb=new StringBuilder();
            for (int b=0; b<nodesWithZ.length;b++){
                if (nodesWithZ[b]){
                    sb.append(b).append(" ");
                }
            }
            log.info(sb.toString());
            for(int node:nodes){
                log.info(STR."Analyze \{node}");
                var currentNode=node;
                navigator.init();
                while(!navigator.hasCycle()){
                    currentNode=navigator.nextNodeAndfindCycle(currentNode, nodesWithZ);
                }
                log.info(navigator.printCycle());
                var cycle=navigator.getCycle();
                log.info("Verify Cycle 1 -> {}", 10*cycle);
                cycles.add(BigInteger.valueOf(cycle));
                currentNode=node;
                navigator.init();
                for(int i=0; i<=10*cycle ; i++){
                    currentNode=navigator.nextNodeAndfindCycle(currentNode, nodesWithZ);
                }
            }
            var gcd=cycles.stream().reduce((a,b)->a.multiply(b).divide(a.gcd(b))).get();
            log.info(cycles.toString());
            return String.valueOf(gcd);
        };
    }


}
