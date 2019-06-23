package com.pjb.springbootjjwt.mapper;

import com.pjb.springbootjjwt.entity.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;
/**
 * @author yc
 * @date 2018-07-08 20:44
 */
public interface UserMapper {
    User findByUsername(@Param("username") String username);
    User findByStunumber(@Param("stunumber") String stunumber);
    List<User> findByRole(@Param("role") String role);
    boolean adduser(User user);
    boolean Resetpassword(@Param("stunumber") String stunumber, @Param("newpassword") String newpassword);
    List<User> findByStunumberAndIDcar(@Param("stunumber") String stunumber,@Param("idcards") String idcards);
    boolean Updateuserrole(@Param("stunumber") String stunumber,@Param("role") String role);
    List<User> findAllUser();
    boolean deleteUser(@Param("stunumber") String stunumber);
    int selectByStunumberReturnCount(String stunumber);
    boolean updateuser(User user);
    boolean updatetoken (@Param("stunumber") String stunumber,@Param("token") String token);
    String selectStunumberBytoken(@Param("token") String token);
    String selectNameBytoken(@Param("token") String token);
    String selectRoleBytoken(@Param("token") String token);
}
