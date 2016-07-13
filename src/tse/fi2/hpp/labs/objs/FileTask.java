/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tse.fi2.hpp.labs.objs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aladdin
 */
class FileTask implements Runnable {

    private final BlockingQueue<String> queue;

    public FileTask(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("D:/abc.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                // block if the queue is full
                queue.put(line);
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (InterruptedException ex) {
            Logger.getLogger(FileTask.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException e) {
            }
        }
    }
}
