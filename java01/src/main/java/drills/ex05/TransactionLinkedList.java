package drills.ex05;

import java.util.UUID;

public class TransactionLinkedList implements TransactionList {
    private Node head;
    private int size;

    public TransactionLinkedList() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public void add(Transaction transaction) {
        Node newNode = new Node(transaction);
        newNode.next = head;
        head = newNode;
        size++;
    }

    @Override
    public void removeById(UUID id) throws TransactionNotFoundException {
        if (head  != null && head.transaction.getId().equals(id)) {
            head = head.next;
            size--;
            return;
        }

        Node current = head;
        while (current.next != null) {
            if (current.next.transaction.getId().equals(id)) {
                current.next = current.next.next;
                size--;
                return;
            }
            current = current.next;
        }
        throw new TransactionNotFoundException("Transaction not found");
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] transactions = new Transaction[size];
        Node current = head;
        for (int i = 0; i < size; i++) {
            transactions[i] = current.transaction;
            current = current.next;
        }
        return transactions;
    }


    private class Node {
        private Transaction transaction;
        private Node next;

        public Node(Transaction transaction) {
            this.transaction = transaction;
            this.next = null;
        }
    }
    
}
