package main.zadani_6;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * class representing personal data
 * (name, surname, email, date of birth and post index)
 *
 * @author Volodymyr Pavlov
 * @version 25.03.2022
 */
public class PersonData {

    /**
     * name of the person
     */
    private String name;

    /**
     * surname of the person
     */
    private String surname;

    /**
     * personal email
     */
    private String email;

    /**
     * date of the person birth
     */
    private LocalDate birth;

    /**
     * person post index
     */
    private String post;

    /**
     * email pattern (RFC 5322 standard)
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

    /**
     * name and surname pattern
     */
    private static final Pattern NAME_PATTERN = Pattern.compile("[A-Z][A-Za-z]*");

    /**
     * post index pattern
     */
    private static final Pattern POST_PATTERN = Pattern.compile("[0-9]{3} ?[0-9]{2}");

    /**
     * date format
     */
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    /**
     * constructor of the class
     *
     * @param name    -person name
     * @param surname -person surname
     * @param birth   -birthdate
     * @param email   -email
     * @param post    -post index
     */
    public PersonData(String name, String surname, String birth, String email, String post) {
        setName(name);
        setSurname(surname);
        setEmail(email);
        setBirth(birth);
        setPost(post);
    }

    /**
     * constructor, using to create an instance using source String (for example from a file)
     *
     * @param personData -source String in format "name/surname,birthdate,email,post_index"
     */
    public PersonData(String personData) {
        String[] data = personData.split("/");
        if (data.length != 5) throw new IllegalArgumentException("Incorrect line with person data");
        setName(data[0]);
        setSurname(data[1]);
        setBirth(data[2]);
        setEmail(data[3]);
        setPost(data[4]);
    }

    /**
     * name getter
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * name setter
     *
     * @param name new surname
     */
    public void setName(String name) {
        Matcher nameMatcher = NAME_PATTERN.matcher(name);
        if (!nameMatcher.matches()) throw new IllegalArgumentException("Name is not acceptable");
        this.name = name;
    }

    /**
     * surname getter
     *
     * @return surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * surname setter
     *
     * @param surname new surname
     */
    public void setSurname(String surname) {
        Matcher surnameMatcher = NAME_PATTERN.matcher(surname);
        if (!surnameMatcher.matches()) throw new IllegalArgumentException("Surname is not acceptable");
        this.surname = surname;
    }

    /**
     * email getter
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * email setter
     *
     * @param email new email
     */
    public void setEmail(String email) {
        Matcher emailMatcher = EMAIL_PATTERN.matcher(email);
        if (!emailMatcher.matches()) throw new IllegalArgumentException("Email is not acceptable");
        this.email = email;
    }

    /**
     * birthdate getter
     *
     * @return birthdate
     */
    public LocalDate getBirth() {
        return birth;
    }

    /**
     * birthdate setter
     *
     * @param birth new birthdate
     */
    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    /**
     * birthdate setter
     *
     * @param birth new birthdate
     */
    public void setBirth(String birth) {
        birth = birth.strip();
        try {
            this.birth = LocalDate.parse(birth, DATE_FORMAT);
        }catch (DateTimeParseException e){
            throw new IllegalArgumentException ("Invalid date format");
        }
    }

    /**
     * post index getter
     *
     * @return post index
     */
    public String getPost() {
        return post;
    }

    /**
     * post index setter
     *
     * @param post new post index
     */
    public void setPost(String post) {
        Matcher postMatcher = POST_PATTERN.matcher(post);
        if (!postMatcher.matches()) throw new IllegalArgumentException("Post index is not acceptable");
        if (!post.contains(" ")) {
            post = post.substring(0, 3) + " " + post.substring(3);
        }
        this.post = post;
    }

    /**
     * represents this personal data as ListProperty
     *
     * @return ListProperty of this personal data properties
     */
    public ListProperty<String> getAsList() {
        ListProperty<String> list = new SimpleListProperty<>(FXCollections.observableArrayList());
        list.add(name);
        list.add(surname);
        list.add(birth.format(getDateFormat()));
        list.add(email);
        list.add(post);

        return list;
    }

    /**
     * email pattern getter
     *
     * @return email pattern
     */
    public Pattern getEmailPattern() {
        return EMAIL_PATTERN;
    }

    /**
     * name pattern getter
     *
     * @return name pattern
     */
    public Pattern getNamePattern() {
        return NAME_PATTERN;
    }

    /**
     * post index pattern getter
     *
     * @return post index pattern
     */
    public Pattern getPostPattern() {
        return POST_PATTERN;
    }

    /**
     * date format getter
     *
     * @return date format
     */
    public DateTimeFormatter getDateFormat() {
        return DATE_FORMAT;
    }

    /**
     * full name getter
     *
     * @return name and surname as string with space
     */
    public String getFullName() {
        return getName() + " " + getSurname();
    }

    @Override
    public String toString() {
        return "name: " + name + " , " +
                "surname: " + surname + " , " +
                "email: " + email + " , " +
                "birth: " + birth.format(DATE_FORMAT) + " , " +
                "post: " + post;
    }

    public String toSource() {
        return getName() + '/' + getSurname() + '/' + getBirth().format(DATE_FORMAT) + '/' + getEmail() + '/' + getPost();
    }
}
