package cn.algerfan.service;

import cn.algerfan.domain.Result;
import cn.algerfan.domain.User;
import cn.algerfan.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  管理员
 * </p>
 *
 * @author algerfan
 * @since 2019/4/15 16
 */
public interface UserService {
    /**
     * 后台查询
     * @return
     */
    List<UserDTO> getAllUser();

    /**
     * 添加
     * @param user
     */
    Result addUser(User user);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    User getUserById(Integer id);

    /**
     * 更新
     * @param user
     */
    Result updateUser(Integer userId,User user);

    /**
     * 根据id删除
     * @param userId
     */
    Result deleteUser(Integer userId);

    /**
     * 后台登录
     * @param userName
     * @param password
     * @param request
     * @return
     */
    Result adminLogin(String userName, String password, HttpServletRequest request);

    /**
     * 更新管理员权限
     * @param userId
     * @param role
     * @return
     */
    Result updateAdministrator(Integer userId, Integer role);
}
