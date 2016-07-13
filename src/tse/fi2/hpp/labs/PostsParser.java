package tse.fi2.hpp.labs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import tse.fi2.hpp.labs.objs.Posts;

public class PostsParser {

    final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.S+0000");
    public PostsParser() {
        super();      
    }
 /** The function loaderPosts takes in as param a string and parses it ,
  which means separates the string into 5 different pieces and 
 stores the data in posts object. 
  * 
     * @param lineInput
     * @return Posts
    */
    public Posts loaderPosts(String lineInput) {
        String part1 = str_piece(lineInput, '|', 1);
        String part2 = str_piece(lineInput, '|', 2);
        String part3 = str_piece(lineInput, '|', 3);
        String part4 = str_piece(lineInput, '|', 4);
        String part5 = str_piece(lineInput, '|', 5);
        Posts post;
        try {
            Date date = (Date) dateFormat.parse(part1);
            long t = Long.parseLong(part2);
            long t2 = Long.parseLong(part3);
            post = new Posts(date.getTime(), t, t2, part4, part5, 0); // gettime returns our date to milliseconds since 1970
            return post;
        } catch (NumberFormatException | ParseException e) {
            System.out.println("Error in parsing date" + lineInput);
        }
        return null;
    }

    
/** the function str_piece separates a string by any separator we choose 
 * and an index
 * @param str
 * @param separator
 * @param index
 * @return 
 */
    public static String str_piece(String str, char separator, int index) {
        String str_result = "";
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == separator) {
                count++;
                if (count == index) {
                    break;
                }
            } else if (count == index - 1) {
                str_result += str.charAt(i);
            }
        }
        return str_result;
    }

}
