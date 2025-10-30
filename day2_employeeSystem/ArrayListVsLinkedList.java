package assessment2.assessment2;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class ArrayListVsLinkedList {
    public static void main(String[] args) {
        int ranges[] = {10_000, 50_000, 100_000};

        for (int size : ranges) {
            System.out.println("Size: " + size);

            List<Integer> arrayList = new ArrayList<>();
            long insertionTimeAl = calInsertionTime(arrayList, size);
            long deletionTimeAl = calculateDeletionTime(arrayList);
            System.out.println("ArrayList Insertion time: " + insertionTimeAl + " ms, Deletion time: " + deletionTimeAl + " ms");

            List<Integer> linkedList = new LinkedList<>();
            long insertionTimell = calInsertionTime(linkedList, size);
            long deletionTimell = calculateDeletionTime(linkedList);
            System.out.println("LinkedList Insertion time: " + insertionTimell + " ms, Deletion time: " + deletionTimell + " ms");
        }
    }

    public static long calInsertionTime(List<Integer> list, int size) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public static long calculateDeletionTime(List<Integer> list) {
        long startTime = System.currentTimeMillis();
        while (!list.isEmpty()) {
            list.remove(0);
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}


/*  Insertion:
 * ArrayList - zigzag pattern
   ArrayList is usually faster, but performance can fluctuate due to occasional resizing.

 - LinkedList is stable because doesn’t need resizing since each new node is created dynamically,
   but each insertion has more overhead (new node object + pointers).
 */


 /* Deletion :
  * ArrayList -
  shifts all elements one place to the left → O(n) operation.
  At 100k shifting happens 100,000 times making it very slow.

  * LinkedList -
  just changes the head pointer → O(1) operation.
  */

