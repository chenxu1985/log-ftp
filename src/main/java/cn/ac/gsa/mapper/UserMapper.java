package cn.ac.gsa.mapper;

import cn.ac.gsa.model.User;

public interface UserMapper {
   User selectUserByEmail(String email);
   int insertHumanUser(User user);
}
