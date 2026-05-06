import java.util.*;
import java.text.*;

/**
 * A repository which stores commits in reverse chronological order as a linked
 * list. The head of the repository is always the most recent commit.
 */
public class Repository {
    private String name;
    private Commit head;
    private int size;

    /**
     * Constructs an empty repository with the given name.
     *
     * @param name
     *            the name of the repository.
     * @throws IllegalArgumentException
     *             if the provided name is null or empty.
     */
    public Repository(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.name = name;
        this.head = null;
        this.size = 0;
    }

    /**
     * Returns the ID of the current head commit.
     *
     * @return the head commit ID, or null if the repository is empty.
     */
    public String getRepoHead() {
        if (head == null) {
            return null;
        }

        return head.id;
    }

    /**
     * Returns the total number of commits in this repository.
     *
     * @return the number of commits int he repository.
     */
    public int getRepoSize() {
        return size;
    }

    /**
     * Returns a string representation of this repository.
     *
     * @return a string describing the repository and its current head, or
     *         indicating that the repository contains no commits.
     */
    public String toString() {
        if (head == null) {
            return name + " - No commits";
        }

        return name + " - Current head: " + head.toString();
    }

    /**
     * Determines whether a commit with the given ID exists in the repository.
     *
     * @param targetId
     *            the commit ID to search for.
     * @return true if the commit exists, false otherwise.
     * @throws IllegalArgumentException
     *             if the targetID is null.
     */
    public boolean contains(String targetId) {
        if (targetId == null) {
            throw new IllegalArgumentException();
        }

        Commit cur = head;
        while (cur != null) {
            if (cur.id.equals(targetId)) {
                return true;
            }

            cur = cur.past;
        }

        return false;
    }

    /**
     * Returns the string representation of the most recent n commits, ordered from
     * more recent to least recent.
     *
     * @param n
     *            the maximum number of commits to include.
     * @return a string containing up to n commits.
     * @throws IllegalArgumentException
     *             if n is non-positive.
     */
    public String getHistory(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        String history = "";
        Commit cur = head;
        int count = 0;

        while (cur != null && count < n) {
            if (count > 0) {
                history += "\n";
            }

            history += cur.toString();
            cur = cur.past;
            count++;
        }

        return history;
    }

    /**
     * Creates a new commit with the provided message and adds it to the front of
     * the repository history.
     *
     * @param message
     *            the commit message.
     * @return the ID of the newly created commit.
     * @throws IllegalArgumentException
     *             if message is null.
     */
    public String commit(String message) {
        if (message == null) {
            throw new IllegalArgumentException();
        }

        head = new Commit(message, head);
        size++;
        return head.id;
    }

    /**
     * Removes the commit with the specified ID from the repository.
     *
     * <p>
     * The surrounding commit history is preserved.
     *
     * @param targetId
     *            the ID of the commit to remove.
     * @return true if the commit was removed, false otherwise.
     * @throws IllegalArgumentException
     *             if targetId is null.
     */
    public boolean drop(String targetId) {
        if (targetId == null) {
            throw new IllegalArgumentException();
        }

        if (head == null) {
            return false;
        }

        if (head.id.equals(targetId)) {
            head = head.past;
            size--;
            return true;
        }

        Commit cur = head;
        while (cur.past != null) {
            if (cur.past.id.equals(targetId)) {
                cur.past = cur.past.past;
                size--;
                return true;
            }

            cur = cur.past;
        }

        return false;
    }

    /**
     * Moves all commits from another repository into this repository while
     * preserving chronological order from most recent to least recent.
     *
     * @param other
     *            the repository whose commits will be merged into this one.
     * @throws IllegalArgumentException
     *             if other is null.
     */
    public void synchronize(Repository other) {
        if (other == null) {
            throw new IllegalArgumentException();
        }

        if (other.head == null) {
            return;
        }

        if (head == null) {
            head = other.head;
            size = other.size;

            other.head = null;
            other.size = 0;
            return;
        }

        Commit mergedHead = null;
        Commit mergedTail = null;
        Commit cur = head;
        Commit otherCur = other.head;

        while (cur != null && otherCur != null) {
            Commit next;

            if (cur.timeStamp >= otherCur.timeStamp) {
                next = cur;
                cur = cur.past;
            } else {
                next = otherCur;
                otherCur = otherCur.past;
            }

            if (mergedHead == null) {
                mergedHead = next;
                mergedTail = next;
            } else {
                mergedTail.past = next;
                mergedTail = next;
            }
        }

        if (cur != null) {
            mergedTail.past = cur;
        } else {
            mergedTail.past = otherCur;
        }

        head = mergedHead;
        size += other.size;

        other.head = null;
        other.size = 0;
    }

    /**
     * DO NOT MODIFY A class that represents a single commit in the repository.
     * Commits are characterized by an identifier, a commit message, and the time
     * that the commit was made. A commit also stores a reference to the immediately
     * previous commit if it exists.
     *
     * Staff Note: You may notice that the comments in this class openly mention the
     * fields of the class. This is fine because the fields of the Commit class are
     * public. In general, be careful about revealing implementation details!
     */
    public static class Commit {

        private static int currentCommitID;

        /**
         * The time, in milliseconds, at which this commit was created.
         */
        public final long timeStamp;

        /**
         * A unique identifier for this commit.
         */
        public final String id;

        /**
         * A message describing the changes made in this commit.
         */
        public final String message;

        /**
         * A reference to the previous commit, if it exists. Otherwise, null.
         */
        @SuppressWarnings("checkstyle:VisibilityModifier")
        public Commit past;

        /**
         * Constructs a commit object. The unique identifier and timestamp are
         * automatically generated.
         *
         * @param message
         *            A message describing the changes made in this commit. Should be
         *            non-null.
         * @param past
         *            A reference to the commit made immediately before this commit.
         */
        public Commit(String message, Commit past) {
            this.id = "" + currentCommitID++;
            this.message = message;
            this.timeStamp = System.currentTimeMillis();
            this.past = past;
        }

        /**
         * Constructs a commit object with no previous commit. The unique identifier and
         * timestamp are automatically generated.
         *
         * @param message
         *            A message describing the changes made in this commit. Should be
         *            non-null.
         */
        public Commit(String message) {
            this(message, null);
        }

        /**
         * Returns a string representation of this commit. The string representation
         * consists of this commit's unique identifier, timestamp, and message, in the
         * following form: "[identifier] at [timestamp]: [message]"
         *
         * @return The string representation of this collection.
         */
        @Override
        public String toString() {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(timeStamp);

            return id + " at " + formatter.format(date) + ": " + message;
        }

        /**
         * Resets the IDs of the commit nodes such that they reset to 0. Primarily for
         * testing purposes.
         */
        public static void resetIds() {
            Commit.currentCommitID = 0;
        }
    }
}
