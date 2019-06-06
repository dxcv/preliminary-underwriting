package cn.algerfan.service.impl;

import cn.algerfan.base.BaseDao;
import cn.algerfan.domain.Result;
import cn.algerfan.domain.User;
import cn.algerfan.dto.UserDTO;
import cn.algerfan.enums.ResultCodeEnum;
import cn.algerfan.mapper.UserMapper;
import cn.algerfan.service.UserService;
import cn.algerfan.util.AesUtil;
import cn.algerfan.util.AesUtilTwo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  管理员
 * </p>
 *
 * @author algerfan
 * @since 2019/4/15 16
 */
@Service
public class UserServiceImpl extends BaseDao<User> implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public List<UserDTO> getAllUser() {
        List<User> userList = userMapper.getAllUser();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : userList){
            UserDTO userModel = new UserDTO(user.getUserId(), user.getUserName(),
                    user.getName(), user.getPhone(), user.getRole());
            userDTOS.add(userModel);
        }
        return userDTOS;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result addUser(User user) {
        if(user.getRole()==null || user.getRole()==0 ||
                user.getUserName()==null || "".equals(user.getUserName()) ||
                user.getPassword()==null || "".equals(user.getPassword())) {
            return new Result(ResultCodeEnum.SAVEFAIL);
        }
        try {
            user.setPassword(AesUtilTwo.aesEncrypt(String.valueOf(user.getPassword()), "lovewlgzs5201314"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(userMapper.selectByName(user.getUserName()) !=null) {
            return new Result(-1,"添加失败，该用户名已存在");
        }
        if(userMapper.insertSelective(user)==0) {
            return new Result(ResultCodeEnum.UNSAVE);
        }
        return new Result(ResultCodeEnum.SAVE) ;
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public Result updateUser(Integer userId, User user) {
        if(user.getRole()==null || user.getRole()==0 ||
                user.getUserName()==null || "".equals(user.getUserName()) ||
                user.getPassword()==null || "".equals(user.getPassword())) {
            return new Result(ResultCodeEnum.UPDATEFAIL);
        }
        User user1 = userMapper.selectByPrimaryKey(userId);
        if(!user1.getUserName().equals(user.getUserName())) {
            if(userMapper.selectByName(user.getUserName()) !=null) {
                return new Result(-1,"修改失败，该用户名已存在");
            }
        }
        String loveUser = null;
        try {
            loveUser = AesUtilTwo.aesEncrypt(String.valueOf(user.getPassword()), "lovewlgzs5201314");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!user1.getPassword().equals(loveUser)) {
            user.setPassword(loveUser);
        }
        user.setUserId(userId);
        if(userMapper.updateByPrimaryKeySelective(user)==0) {
            return new Result(ResultCodeEnum.UNUPDATE);
        }
        return new Result(ResultCodeEnum.UPDATE);
    }

    @Override
    public Result deleteUser(Integer userId) {
        if(userId == null || userId == 0) {
            return new Result(ResultCodeEnum.UNDELETE);
        }
        User user = userMapper.selectByPrimaryKey(userId);
        if(user.getRole()==200) {
            return new Result(ResultCodeEnum.UNDELETE);
        }
        if(userMapper.deleteByPrimaryKey(userId) == 0) {
            return new Result(ResultCodeEnum.UNDELETE);
        }
        return new Result(ResultCodeEnum.DELETE);
    }

    @Override
    public Result adminLogin(String userName, String password, HttpServletRequest request) {
        if(userName==null || "".equals(userName) || password==null || "".equals(password)) {
            return new Result(ResultCodeEnum.FAIL);
        }
        try {
            password = AesUtilTwo.aesEncrypt(password, "lovewlgzs5201314");
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<User> allUser = userMapper.getAllUser();
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(60 * 20);
        Result result = new Result(ResultCodeEnum.SUCCESS);
        for (User user : allUser) {
            if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                if (user.getRole() == 300) {
                    result.setMsg("您的账号未启用！");
                    return result;
                }
                session.setAttribute("user", user);
                if (user.getRole() == 100) {
                    result.setMsg("普通管理员登录成功");
                    return result;
                }
                if (user.getRole() == 200) {
                    result.setMsg("超级管理员登录成功");
                    return result;
                }
            }
        }
        Result result1 = new Result(ResultCodeEnum.FAIL);
        result1.setMsg("用户名或密码错误");
        return result1;
    }

    @Override
    public Result updateAdministrator(Integer userId) {
        if(userId==null || userId==0) {
            return new Result(ResultCodeEnum.UPDATEFAIL);
        }
        User user = userMapper.selectByPrimaryKey(userId);
        if(user==null) {
            return new Result(ResultCodeEnum.UNUPDATE);
        }
        log.info(user);
        if(user.getRole()==100) {
            user.setRole(300);
        } else if(user.getRole()==300) {
            user.setRole(100);
        } else {
            return new Result(ResultCodeEnum.UNUPDATE);
        }
        if(userMapper.updateByPrimaryKeySelective(user)==0) {
            return new Result(ResultCodeEnum.UNUPDATE);
        }
        return new Result(ResultCodeEnum.UPDATE);
    }
}
