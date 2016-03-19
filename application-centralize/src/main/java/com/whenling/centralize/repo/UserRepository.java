package com.whenling.centralize.repo;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.whenling.centralize.model.QUser;
import com.whenling.centralize.model.User;
import com.whenling.module.domain.repository.BaseRepository;

/**
 * 用户仓库
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:04:34
 */
public interface UserRepository extends BaseRepository<User, Long>, QuerydslBinderCustomizer<QUser> {

	User findByUsername(String username);

	@Override
	default void customize(QuerydslBindings bindings, QUser root) {
		bindings.bind(root.username).first((path, value) -> path.contains(value));
		bindings.bind(root.name).first((path, value) -> path.contains(value));
		bindings.bind(root.email).first((path, value) -> path.contains(value));
		bindings.bind(root.mobile).first((path, value) -> path.contains(value));

		bindings.excluding(root.password);
	}
}
