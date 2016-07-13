/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tse.fi2.hpp.labs.objs;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import tse.fi2.hpp.labs.ComsParser;

/**
 *
 * @author Aladdin
 */
public class CPUTask implements Runnable {

    private final BlockingQueue<String> queue;

    public CPUTask(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        String line;
        String line2;
        long dtnowx=0;
        LinkedList<Comments> comL = new LinkedList<>();

        try {
            // block if the queue is empty
            line = queue.take();
            ComsParser cc = new ComsParser();
            Comments com = cc.loaderComments(line);
            comL.add(com);
            dtnowx = com.getDt();
            while (true) {
                try {
                    line2 = queue.take();
                    com = cc.loaderComments(line2);
                    if (com.getDt() - dtnowx > 86400000) {
                        for (int i = 0; i < comL.size(); i++) {
                            comL.get(i).setScore(comL.get(i).getScore() - ((com.getDt() - dtnowx) / 86400000));
                        }
                    }
                    comL.add(com);
                    dtnowx = com.getDt();
                } catch (InterruptedException ex) {
                    break; // FileTask has completed
                }
            }
        } catch (Exception e) {

        }

        // poll() returns null if the queue is empty
        while ((line = queue.poll()) != null) {
            // do things with line;
        }
    }
}