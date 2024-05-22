package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Course {
    private Integer id;
    private String[] title;
    private String[] instructorName;
    private String[] requirement;
    private String startDate;
}
