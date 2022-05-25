import java.util.*;

public class SkipList<T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {

    static class Node<T> {
        public Node<T> above;
        public Node<T> below;
        public Node<T> next;
        public Node<T> prev;
        public int key;

        public Node(int key) {

            this.key = key;
            this.above= null;
            this.below = null;
            this.next = null;
            this.prev = null;
        }
    }

    private Node<T> head;
    private Node<T> tail;

    private final int NEG_INFINITY = Integer.MIN_VALUE;
    private final int POS_INFINITY = Integer.MAX_VALUE;

    private int heightOfSkipList = 0;

    public Random random = new Random();

    public SkipList() {
        head = new Node<T>(NEG_INFINITY);
        tail = new Node<T>(POS_INFINITY);
        head.next = tail;
        tail.prev = head;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    public Node<T> skipSearch(int key) {
        Node<T> n = head;

        while (n.below != null) {
            n = n.below;

            while (key >= n.next.key) {
                n = n.next;
            }
        }
        return n;
    }

    public Node<T> skipInsert(int key) {
        Node<T> postition = skipSearch(key);
        Node<T> q;

        int level = -1;
        int numberOfHeads = -1;

        if (postition.key == key) {
            return postition;
        }

        do {
            numberOfHeads++;
            level++;

            canIncreaseLevel(level);

            q = postition;

            while (postition.above == null) {
                postition = postition.prev;
            }

            postition = postition.above;

            q = insertAfterAbove(postition, q, key);

        } while (random.nextBoolean());

        return q;
    }

    public Node<T> remove(int key) {
        Node<T> nodeToBeRemoved = skipSearch(key);

        if (nodeToBeRemoved.key != key) {
            return null;
        }

        removeReferencesToNode(nodeToBeRemoved);

        while (nodeToBeRemoved != null) {
            removeReferencesToNode(nodeToBeRemoved);

            if (nodeToBeRemoved.above != null) {
                nodeToBeRemoved = nodeToBeRemoved.above;
            } else {
                break;
            }
        }

        return nodeToBeRemoved;
    }

    private void removeReferencesToNode(Node<T> nodeToBeRemoved) {
        Node<T> afterNodeToBeRemoved = nodeToBeRemoved.next;
        Node<T> beforeNodeToBeRemoved = nodeToBeRemoved.prev;

        beforeNodeToBeRemoved.next = afterNodeToBeRemoved;
        afterNodeToBeRemoved.prev = beforeNodeToBeRemoved;
    }

    private void canIncreaseLevel(int level) {
        if (level >= heightOfSkipList) {
            heightOfSkipList++;
            addEmptyLevel();
        }
    }

    private void addEmptyLevel() {
        Node<T> newHeadNode = new Node<T>(NEG_INFINITY);
        Node<T> newTailNode = new Node<T>(POS_INFINITY);


        newHeadNode.next = newTailNode;
        newHeadNode.below = head;
        newTailNode.prev = newHeadNode;
        newTailNode.below = tail;

        head.above = newHeadNode;
        tail.above = newTailNode;

        head = newHeadNode;
        tail = newTailNode;
    }

    private Node<T> insertAfterAbove(Node<T> position, Node<T> q, int key) {
        Node<T> newNode = new Node<T>(key);
        Node<T> nodeBeforeNewNode = position.below.below;

        setBeforeAndAfterReferences(q, newNode);
        setAboveAndBelowReferences(position, key, newNode, nodeBeforeNewNode);

        return newNode;
    }

    private void setBeforeAndAfterReferences(Node<T> q, Node<T> newNode) {
        newNode.next = q.next;
        newNode.prev = q;
        q.next.prev = newNode;
        q.next = newNode;
    }

    private void setAboveAndBelowReferences(Node<T> position, int key, Node<T> newNode, Node<T> nodeBeforeNewNode) {
        if (nodeBeforeNewNode != null) {
            while (true) {
                if (nodeBeforeNewNode.next.key != key) {
                    nodeBeforeNewNode = nodeBeforeNewNode.next;
                } else {
                    break;
                }
            }

            newNode.below = nodeBeforeNewNode.next;
            nodeBeforeNewNode.next.above = newNode;
        }

        if (position != null) {
            if (position.next.key == key) {
                newNode.above = position.next;
            }
        }
    }

    public void printSkipList() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nSkipList starting with top-left most node.\n");

        Node<T> starting = head;

        Node<T> highestLevel = starting;
        int level = heightOfSkipList;

        while (highestLevel != null) {
            sb.append("\nLevel: " + level + "\n");

            while (starting != null) {
                sb.append(starting.key);

                if (starting.next != null) {
                    sb.append(" : ");
                }

                starting = starting.next;
            }


            highestLevel = highestLevel.below;
            starting = highestLevel;
            level--;
        }

        System.out.println(sb.toString());
    }

    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return null;
    }

    @Override
    public SortedSet<T> headSet(T toElement) {
        return null;
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return null;
    }

    @Override
    public T first() {
        return null;
    }

    @Override
    public T last() {
        return null;
    }

}