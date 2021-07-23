package com.codereader.evelator;

import com.codereader.evelator.model.Expention.LiftException;

import java.io.IOException;
import java.lang.reflect.Array;

import java.util.Scanner;

import java.io.File;





public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("input number of floors: ");
        int numOfFlors = scanner.nextInt();

        System.out.println("input floor height (m): ");
        double floorHeight = scanner.nextDouble();

        System.out.println("input floor velocity (m/s): ");
        double floorVelocity = scanner.nextDouble();

        System.out.println("input floor doors time (seconds): ");
        long waitingInterval = scanner.nextLong() * 1000;

        long floorTime = (long) (1000.0 * floorHeight / floorVelocity);

        Elevator elevator = new Elevator(numOfFlors, floorTime, waitingInterval);

        scanner.nextLine();

        while (true) {
            String line = scanner.nextLine();

            if ("q".equals(line)) {

                elevator.stop();

                return;

            }
            try {

                int nextFloor = Integer.parseInt(line);

                if (nextFloor < 1 || nextFloor > numOfFlors) {

                    System.out.println("Wrong floor. Try again.");

                } else {

                    elevator.buttonPressedForFloor(nextFloor);

                }
            } catch (Exception e) {

                System.out.println(line + " not a number");

            }

        }
    }

    }

