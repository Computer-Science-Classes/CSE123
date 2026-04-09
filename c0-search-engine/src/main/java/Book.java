import java.util.*;

/**
 * Represents a book as a form of {@link Media}
 *
 * Books as {@link Comparable} and are ordered primarily by average rating.
 */
public class Book implements Media, Comparable<Book> {
    private final String title;
    private final List<String> authors;
    private List<Integer> rating;
    private final List<String> content;

    /**
     * Constructs a new {@code Book}.
     *
     * @param title
     *            the title of the book.
     * @param authors
     *            the list of authors associated with the book.
     * @param contentScanner
     *            a {@code Scanner} used to read the and tokenize the book's
     *            content.
     */
    public Book(String title, List<String> authors, Scanner contentScanner) {
        this.title = title;
        this.authors = authors;
        this.rating = new ArrayList<>();
        this.content = new ArrayList<>();

        while (contentScanner.hasNext()) {
            this.content.add(contentScanner.next());
        }
    }

    /**
     * Gets the title of the book.
     *
     * @return The title of this book.
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * Gets all artists associated with this book.
     *
     * @return A list of artists for this book.
     */
    @Override
    public List<String> getArtists() {
        return authors;
    }

    /**
     * Adds a rating to this book.
     *
     * @param score
     *            The score for the new rating.
     */
    @Override
    public void addRating(int score) {
        rating.add(score);
    }

    /**
     * Gets the number of times this book has been rated.
     *
     * @return The number of ratings for this book.
     */
    @Override
    public int getNumRatings() {
        return rating.size();
    }

    /**
     * Gets the mean of all ratings for this book.
     *
     * @return The mean of all ratings for this book. If no ratings exist, returns
     *         0.
     */
    @Override
    public double getAverageRating() {
        int numRatings = rating.size();

        if (numRatings == 0) {
            return 0.0;
        }

        double sum = 0.0;
        for (Integer score : rating) {
            sum += score;
        }

        return sum / numRatings;
    }

    /**
     * Gets all of the content contained in this book.
     *
     * @returns The content stored in a List of strings. If there is no content, an
     *          empty list.
     */
    @Override
    public List<String> getContent() {
        return content;
    }

    /**
     * Produce a readable string representation. of this media
     *
     * If the media has zero ratings, the format will be: "<title> by [<artists>]"
     *
     * If the media has at least one review, the format will be: "<title> by
     * [<artists>]: <average rating> (<num ratings> ratings)"
     *
     * The average rating displayed will be rounded to at most two decimal places.
     *
     * @returns The appropriately formatted string representation
     */
    @Override
    public String toString() {
        String out = title + " by " + authors.toString();
        if (rating.size() == 0) {
            return out;
        }

        return out + ": " + String.format("%.2f", this.getAverageRating()) + " " + "("
                + rating.size() + " ratings)";
    }

    /**
     * Compares a Book to another Book.
     *
     * Books are compared based on author and title, if both are equal they are
     * equivalent. Otherwise, ratings are compared.
     *
     * @param other
     *            Book to compare this to.
     * @return 1 if this book rating is higher than other and 0 if same title, same
     *         author, and same rating; otherwise, -1.
     */
    @Override
    public int compareTo(Book other) {
        Boolean isSameBook = this.title.equals(other.title) && this.authors.equals(other.authors);
        double compareRatings = this.getAverageRating() - other.getAverageRating();

        if (compareRatings > 0) {
            return 1;
        } else if (isSameBook && compareRatings == 0) {
            return 0;
        }

        return -1;
    }
}
