package sskj.local.tmp;

import lombok.Data;

import java.util.List;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/09/07
 */
@Data
public class Company {

    private String name;
    private int age;
    private User user;
    private List<User> list;
}
