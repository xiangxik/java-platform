package com.whenling.centralize.repo;

import com.whenling.centralize.model.User;
import com.whenling.module.domain.repository.BaseRepository;

/**
 * 用户仓库
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:04:34
 */
public interface UserRepository extends BaseRepository<User, Long> {

	User findByUsername(String username);

}
