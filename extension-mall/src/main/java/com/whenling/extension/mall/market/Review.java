package com.whenling.extension.mall.market;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.whenling.centralize.model.User;
import com.whenling.extension.mall.product.model.Product;
import com.whenling.module.domain.model.BaseEntity;

@Entity
@Table(name = "xx_review")
public class Review extends BaseEntity<Long> {

	private static final long serialVersionUID = -6605617011633585395L;

	/**
	 * 类型
	 */
	public enum Type {

		/** 好评 */
		positive,

		/** 中评 */
		moderate,

		/** 差评 */
		negative
	}

	/** 评分 */
	@NotNull
	@Min(1)
	@Max(5)
	@Column(nullable = false, updatable = false)
	private Integer score;

	/** 内容 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false, updatable = false)
	private String content;

	/** 是否显示 */
	@Column(nullable = false)
	private Boolean isShow;

	/** IP */
	@Column(nullable = false, updatable = false)
	private String ip;

	/** 会员 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false)
	private User user;

	/** 商品 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Product product;

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
