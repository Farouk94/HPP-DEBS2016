package tse.fi2.hpp.labs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import tse.fi2.hpp.labs.objs.Comments;

public class ComsParser {

    final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.S+0000");

    public long numberOflines;

    public Comments loaderComments(String lineInput) {
        String part1 = str_piece(lineInput, '|', 1);
        String part2 = str_piece(lineInput, '|', 2);
        String part3 = str_piece(lineInput, '|', 3);
        String comment = str_piece(lineInput, '|', 4);
        String user = str_piece(lineInput, '|', 5);
        String strCommRID = str_piece(lineInput, '|', 6);
        String strCommPostID = str_piece(lineInput, '|', 7);
        Comments com;
        try {
            Date date = (Date) dateFormat.parse(part1);
            Long comID = Long.parseLong(part2);
            long comUserID = Long.parseLong(part3);

            Long lCommRID = new Long(-1);
            try {
                lCommRID = Long.parseLong(strCommRID);
            } catch (Exception ex) {
            }

            Long lCommPostID = new Long(-1);
            try {
                lCommPostID = Long.parseLong(strCommPostID);
            } catch (Exception ex) {
            }

            com = new Comments(date.getTime(), comID, comUserID, comment, user, lCommRID, lCommPostID, 0); // gettime returns our date to milliseconds since 1970
            return com;
        } catch (NumberFormatException | ParseException e) {
            System.out.println("Error in parsing date" + lineInput);
        }
        return null;
    }

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
