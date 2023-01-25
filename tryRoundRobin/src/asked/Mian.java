package asked;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Mian {
    public static void main(String args[]){
        ArrayList<Process> proccesses = new ArrayList<Process>();//array to store processes read in from file
        System.out.println("enter number of proccesses");
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        System.out.println();
        for (int i = 0; i < num; i++) {
            Process process;
            System.out.print("Enter the arrival time of process " + i + " : ");
            int arrival = sc.nextInt();
            System.out.print("Enter the burst time of process " + i + " : ");
            int burst = sc.nextInt();
            process = new Process(i,arrival,burst);
            proccesses.add(process);
            System.out.println();
        }
        System.out.println("Enter Quantum time");
        int q=sc.nextInt();
        RR rr = new RR();
        rr.runRoundRobin(proccesses, q);
        }
    }