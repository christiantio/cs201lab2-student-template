import java.util.*;


public class SinglyLinkedList<E extends Comparable<E>> {
    private Node<E> head = null;
    private Node<E> tail = null;
    private int size = 0;

    private static class Node<E> {
        private E element;
        private Node<E> next;
    
        public Node(E e, Node<E> n){
            element = e;
            next = n;
        }
    
        public E getElement(){
            return element;
        }
    
        public Node<E> getNext(){
            return next;
        }
    
        public void setNext(Node<E> n){
            next = n;
        }
    }

    public SinglyLinkedList(){

    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public E first(){
        if (isEmpty()){
            return null;
        } 
        return head.getElement();
    }

    public E last(){
        if (isEmpty()){
            return null;
        }
        return tail.getElement();
    }

    public void addFirst(E e){
        head = new Node<>(e, head);

        if (isEmpty()){
            tail = head;
        }
        size++;
    }

    public void addLast(E e){
        Node<E> newest = new Node<>(e, null);
        if (isEmpty()){
            head = newest;
        } else {
            tail.setNext(newest);
        }
        tail = newest;
        size++;
    }

    public E removeFirst(){
        if (isEmpty()){
            return null;
        }

        E answer = head.getElement();
        head = head.getNext();
        size--;

        if (isEmpty()){
            tail = null;
        }
        return answer;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        Node<E> current = head;
        while (current != null) {
            sb.append(current.getElement());
            sb.append(" ");
            current = current.getNext();
        }
        return sb.toString();
    }

    // write your code here
    public void swap(){

        if (size <= 1) return; // return if list is empty or there is only 1 element inside it

        // store all the nodes into an arraylist
        ArrayList<Node<E>> originalList = new ArrayList<>();
        ArrayList<Node<E>> sortedList = new ArrayList<>();
        Node<E> walk = head;
        while (walk != null) {

            originalList.add(walk);
            sortedList.add(walk);
            walk = walk.getNext();
        }

        // sort this arraylist
        sortedList.sort((a,b) -> a.getElement().compareTo(b.getElement())); // Collections.sort() cannot sort
        // Node<E> because a node is not a Comparable -> we need to use a Comparator

        // now we create a map to store the rank of the sorted elements
        // notice that the key is the Node, and the value is the node's position in the sortedlist
        // this helps us easily get the node's rank when we are swapping the original list
        HashMap<Node<E>, Integer> rank = new HashMap<>();
        for (int i = 0; i < size; i++) {

            rank.put(sortedList.get(i), i);
        }

        // create a buffer arraylist that will store the nodes in their supposed swapped order
        // no need for any linking at this point
        ArrayList<Node<E>> buffer = new ArrayList<>();
        for (int i = 0; i < size; i++) {

            Node<E> currentNode = originalList.get(i);
            int nodeRank = rank.get(currentNode);
            int mirrorRank = size - 1 - nodeRank;
            buffer.add(sortedList.get(mirrorRank));
        }

        // now we go through buffer and just link one node to the next
        for (int i = 0; i < size - 1; i++) { // size - 1 because we only want to iterate till the second last node

            buffer.get(i).setNext(buffer.get(i + 1));
        }

        head = buffer.get(0);
        tail = buffer.get(size - 1);
        tail.setNext(null);
    }
}

