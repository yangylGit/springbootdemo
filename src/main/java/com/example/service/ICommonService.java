package com.example.service;

import com.example.model.User;

/**
 * 公用业务层接口
 */
public interface ICommonService {
    /**
     * 通过id查询用户信息
     * @param id
     * @return
     */
    public User getCaheUserById(int id);

    /**
     * 新增用户信息
     * @param user
     * @return
     */
    public User insert(User user);
    /**
     * 更新用户信息
     * @param user
     * @return
     */
    public boolean updateById(User user);

    /**
     * 删除用户信息
     * @param id
     * @return
     */
    public boolean deleteById(int id);
}
