package asked;

import java.util.*;

import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

class CircularLinkedList<AnyType> implements List<AnyType> {

   

    //nested Node class
    private static class Node<AnyType> {
        private AnyType data;
        private Node<AnyType> next;

        public Node(AnyType d, Node<AnyType> n) {
            setData(d);
            setNext(n);
        }

        public AnyType getData() {
            return data;
        }

        public void setData(AnyType d) {
            data = d;
        }

        public Node<AnyType> getNext() {
            return next;
        }

        public void setNext(Node<AnyType> n) {
            next = n;
        }
    }

    private int size;
    private int modCount;
    private Node<AnyType> tail;

    public CircularLinkedList() {
        tail = new Node<AnyType>(null, null);
        modCount = 0;
        size = 0;
    }

    public void clear() {
        tail.setData(null);
        tail.setNext(tail);
        size = 0;
    }

    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return (size() == 0);
    }

    public AnyType get(int index) {
        Node<AnyType> theNode = getNode(index);
        return theNode.getData();
    }

    public AnyType set(int index, AnyType newValue) {
        Node<AnyType> nodeSet = getNode(index);
        AnyType old = nodeSet.getData();

        nodeSet.setData(newValue);
        return old;
    }


    public boolean add(AnyType newValue) {
        add(size(), newValue);
        return true;
    }

    public void add(int index, AnyType newValue) {
        if (index == 0 && size() == 0) addFirst(newValue);
        else if (index == 0) addBeginning(newValue);
        else if (index == size()) addLast(newValue);
        else {
            Node<AnyType> nextNode = getNode(index);
            Node<AnyType> prevNode = getNode(index - 1);
            Node<AnyType> newNode = new Node<AnyType>(newValue, nextNode);
            prevNode.setNext(newNode);
        }
        size++;
        modCount++;
    }

    public AnyType remove(int index) {
        AnyType old = null;
        if (index == 0) old = removeFirst(getNode(index));
        else if (index == size() - 1) old = removeLast(getNode(index));
        else old = removeNode(getNode(index), getNode(index - 1));

        size--;
        modCount++;
        return old;
    }

    public void rotate() {
        Node<AnyType> currHeadNode = getNode(0);
        tail = currHeadNode;
    }

    public Iterator<AnyType> iterator() {
        return new LinkedListIterator();
    }

    private Node<AnyType> getNode(int index) {
        return (getNode(index, 0, size() - 1));
    }

    private Node<AnyType> getNode(int index, int lower, int upper) {
        Node<AnyType> currNode;

        if (index < lower || index > upper)
            throw new IndexOutOfBoundsException();

        currNode = tail.getNext();
        for (int i = 0; i < index; i++) currNode = currNode.getNext();
        return currNode;
    }

    private void addFirst(AnyType newString) {
        tail.setData(newString);
        tail.setNext(tail);

    }

    private void addBeginning(AnyType newString) {
        Node<AnyType> nextNode = getNode(0);
        Node<AnyType> newNode = new Node<AnyType>(newString, nextNode);
        tail.setNext(newNode);
    }

    private void addLast(AnyType newString) {
        Node<AnyType> prevNode = getNode(size() - 1);
        Node<AnyType> newNode = new Node<AnyType>(newString, getNode(0));
        tail = newNode;
        prevNode.setNext(newNode);
    }

    private AnyType removeNode(Node<AnyType> currNode, Node<AnyType> prevNode) {
        AnyType old = currNode.getData();
        prevNode.setNext(currNode.getNext());
        return old;
    }

    private AnyType removeFirst(Node<AnyType> firstNode) {
        AnyType old = firstNode.getData();
        tail.setNext(firstNode.getNext());
        return old;
    }

    private AnyType removeLast(Node<AnyType> lastNode) {
        AnyType old = lastNode.getData();
        Node<AnyType> secondToLastNode = getNode(size() - 2);
        tail = secondToLastNode;
        secondToLastNode.setNext(lastNode.getNext());
        return old;
    }


    private class LinkedListIterator implements Iterator<AnyType> {
        private Node<AnyType> previous;
        private Node<AnyType> current;
        private int expectedModCount;
        private boolean okToRemove;

        LinkedListIterator() {
            current = getNode(0);
            previous = tail;
            expectedModCount = modCount;
            okToRemove = false;
        }
        public boolean hasNext() {
            return true;
        }

        public AnyType next() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            if (!hasNext())
                throw new NoSuchElementException();

            AnyType nextVal = current.getData();
            previous = current;
            current = current.getNext();
            okToRemove = true;
            return nextVal;
        }

        public void remove() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            if (!okToRemove)
                throw new IllegalStateException();

            CircularLinkedList.this.removeNode(current, previous);
            expectedModCount++;
            okToRemove = false;

        }
    }
}
public class Scheduler {
    private CircularLinkedList<Process> readyQueue;


    public Scheduler(){
        readyQueue = new CircularLinkedList<Process>();
    }

    public void addProcess(Process p){
        readyQueue.add(p);
    }

    public void removeProcess(int p){
        readyQueue.remove(p);
    }

    public Process getProcess(int p){
        return readyQueue.get(p);
    }

    public boolean readyQueueEmpty(){
        return readyQueue.isEmpty();
    }

    public int readyQueueSize(){
        return readyQueue.size();
    }

    public void rotateReadyQueue(){
        readyQueue.rotate();
    }

}
