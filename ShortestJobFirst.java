import java.util.Arrays;
import java.util.Scanner;

class Process {
    int processId;
    int arrivalTime;
    int burstTime;
    int waitingTime;
    int turnAroundTime;
    boolean isCompleted;

    public Process(int processId, int arrivalTime, int burstTime) {
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.isCompleted = false;
    }
}

public class ShortestJobFirst {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = scanner.nextInt();

        Process[] processes = new Process[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time for process " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Enter burst time for process " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();
            processes[i] = new Process(i + 1, arrivalTime, burstTime);
        }

        // Sort by arrival time first
        Arrays.sort(processes, (p1, p2) -> p1.arrivalTime - p2.arrivalTime);

        int currentTime = 0;
        int completed = 0;
        double totalWaitingTime = 0, totalTurnAroundTime = 0;

        while (completed < n) {
            int idx = -1;
            int minBurst = Integer.MAX_VALUE;

            // Find process with minimum burst time among arrived processes
            for (int i = 0; i < n; i++) {
                if (!processes[i].isCompleted && processes[i].arrivalTime <= currentTime && processes[i].burstTime < minBurst) {
                    minBurst = processes[i].burstTime;
                    idx = i;
                }
            }

            // If no process has arrived yet, move time forward
            if (idx == -1) {
                currentTime++;
            } else {
                Process p = processes[idx];
                p.waitingTime = currentTime - p.arrivalTime;
                p.turnAroundTime = p.waitingTime + p.burstTime;
                currentTime += p.burstTime;
                p.isCompleted = true;
                completed++;

                totalWaitingTime += p.waitingTime;
                totalTurnAroundTime += p.turnAroundTime;

                System.out.println("P" + p.processId + " completed at time " + currentTime);
            }
        }

        System.out.println("\nProcess\tArrival\tBurst\tWaiting\tTurnaround");
        for (Process p : processes) {
            System.out.println("P" + p.processId + "\t" + p.arrivalTime + "\t" + p.burstTime + "\t" + p.waitingTime + "\t" + p.turnAroundTime);
        }

        System.out.printf("\nAverage Waiting Time: %.2f\n", totalWaitingTime / n);
        System.out.printf("Average Turnaround Time: %.2f\n", totalTurnAroundTime / n);

        scanner.close();
    }
}
