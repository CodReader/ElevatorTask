package com.codereader.evelator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Elevator {

    private final long floorInterval;

    private final long waitingInterval;

    private int currentFloor = 0;

    private boolean[] floorButtons;

    private ExecutorService executor = Executors.newSingleThreadExecutor();




    public Elevator(int floors, long floorInterval, long waitingInterval) {

        this.floorInterval = floorInterval;

        this.waitingInterval = waitingInterval;

        this.floorButtons = new boolean[floors];

    }



    private void doorOpenClose() throws InterruptedException {

        sendMessage("DOORS OPENED");

        Thread.sleep(waitingInterval);

        sendMessage("DOORS CLOSED");
    }



    private static void sendMessage(String message) {

        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME) + " " + message);

    }




    public void stop() {

        executor.shutdown();
    }




    private void moveTo(int targetFloor) throws InterruptedException {

        int step = 1;

        if (targetFloor < currentFloor) step = -1;

        while (currentFloor != targetFloor) {

            Thread.sleep(floorInterval);

            currentFloor += step;

            sendMessage("CURRENT FLOOR: " + currentFloor);

            if (floorButtons[currentFloor]) {

                floorButtons[currentFloor] = false;

                doorOpenClose();
            }
        }
    }




    private void moveUp() throws InterruptedException {

        for (int i = currentFloor; i < floorButtons.length; i++)

            if (floorButtons[i]) {

                moveTo(i);

                return;
            }
    }



    private void moveDown() throws InterruptedException {

        for (int i = currentFloor; i > 0; i--)

            if (floorButtons[i]) {

                moveTo(i);

                return;
            }
    }




    public void buttonPressedForFloor(final int floor) {

        floorButtons[floor] = true;

        executor.submit(() -> {

            try {

                this.moveUp();

                this.moveDown();

            } catch (InterruptedException e) {

                e.printStackTrace();

            }
        });
    }

}
